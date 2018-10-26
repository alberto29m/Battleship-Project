package salvoproject.battleship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository repo;

    @Autowired
    private GamePlayerRepository repoGamePlayer;

    @Autowired
    private PlayerRepository repoPlayer;

    @RequestMapping("/games")
    public Map<String, Object> makeGamesDTO(Authentication auth) {
        Map<String, Object> gamesdto = new LinkedHashMap<String, Object>();
        List<Game> games = repo.findAll();
        List<Player> players = repoPlayer.findAll();
        gamesdto.put("games", games.stream().map(game -> makeGameDTO2(game)).collect(toList()));
        gamesdto.put("leaderboard",players.stream().map(player -> scoreMap(player)).collect(toList()) );
        if(auth != null) {
            gamesdto.put("currentUser", makePlayerDTO(getCurrentPlayer(auth)));
        }
//        Set<Player> players = game
//        Set<Score> scores = games.getScore();
//        gamesdto.put("id", games.getId());
//        gamesdto.put("scores", game.stream().map(score -> makeScoreDTO(score)).collect(toList()));
        return gamesdto;
    }

    private Map<String, Object> makeGameDTO(Game game){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        Set<GamePlayer> gps = game.getGamePlayer();
        dto.put("id", game.getId());
        dto.put("date", game.getDate());

        dto.put("gamePlayer", gps.stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(toList()));
        return dto;
    }

    private Map<String, Object> makeGameDTO2(Game game){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        List<Player> gps = game.getPlayers();
        Set<GamePlayer> gameplayer = game.getGamePlayer();
        dto.put("gamePlayer", gameplayer.stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(toList()));
        dto.put("id", game.getId());
        dto.put("date", game.getDate());
//        dto.put("players",gps.stream().map(player -> scoreMap(player)).collect(toList()) );

        return dto;
    }

    private Map<String, Object> scoreMap (Player p){
        Map<String, Object> mapScore = new LinkedHashMap<>();
        mapScore.put("name",p.getUserName());
        if(p.getScores().size() != 0) {
            Set<Score> scores = p.getScores();
            List<Object> winlist = scores.stream().filter(score -> score.getScoreNumber() == 1.0).collect(toList());
            List<Object> lostlist = scores.stream().filter(score -> score.getScoreNumber() == 0.0).collect(toList());
            List<Object> tielist = scores.stream().filter(score -> score.getScoreNumber() == 0.5).collect(toList());
            List<Double> totalList = scores.stream().map(score -> score.getScoreNumber()).collect(toList());

            mapScore.put("win", winlist.size());
            mapScore.put("lost", lostlist.size());
            mapScore.put("tie", tielist.size());
            mapScore.put("total", totalList.stream().reduce((a, b) -> a + b).get());
        }else {
            mapScore.put("total",null);
        }

        return mapScore;
    }

    private Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        return dto;
    }

    private Map<String, Object> makePlayerDTO2(Player player){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        dto.put("scoreNumber", player.getScores());
        return dto;
    }
    private Map<String, Object> makeScoreDTO(Score score){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", score.getId());

        dto.put("game", score.getGame());
        dto.put("score", score.getScoreNumber());
        return dto;
    }

    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("Player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }


    private GamePlayer getOponent(GamePlayer gameplayer){
        Set<GamePlayer>gps = gameplayer.getGame().getGamePlayers();
        GamePlayer op = gps.stream().filter(gp -> gp.getId() != gameplayer.getId()).collect(toList()).get(0);
        return op;
    }




    @RequestMapping("/game_view/{id}")
    public Map<String, Object> makeGameView(@PathVariable Long id) {
        Map<String,Object> makeGameViewDTO = new LinkedHashMap<>();
        GamePlayer gamePlayer = repoGamePlayer.findOne(id);
        Set<Ship> ships = gamePlayer.getShips();
        Set<Salvo> salvos = gamePlayer.getSalvo();
        Set<GamePlayer>gamePlayers = gamePlayer.getGame().getGamePlayers();
        makeGameViewDTO.put("game", makeGameDTO(gamePlayer.getGame()));
        makeGameViewDTO.put("ships", ships.stream().map(ship -> makeShipsDTO(ship)).collect(toList()) );
        makeGameViewDTO.put("salvoes", gamePlayers.stream().map(gamePlayer1 -> makeSalvoesDTO(gamePlayer1.getSalvo())).collect(toList()));
        return makeGameViewDTO;
    }

    private Map<String, Object> makeShipsDTO(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getShipType());
        dto.put("location", ship.getLocations());
        return dto;
    }

    private Map<String, Object> makeSalvoesDTO(Set<Salvo> salvoes){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("gameplayerID", salvoes.stream().map(salvo -> salvo.getGamePlayer().getId()).collect(toList()).get(0));
        dto.put("salvoes", salvoes.stream().map(salvo -> makeSalvoDTO(salvo)).collect(toList()) );
        return dto;
    }

    private Map<String, Object> makeSalvoDTO(Salvo salvo){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", salvo.getTurnNumber());
        dto.put("location", salvo.getsalvoLocations());
        return dto;
    }


    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String userName, @RequestParam String password) {

        if ( userName.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (repoPlayer.findByUserName(userName) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        repoPlayer.save(new Player( userName, password));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Player getCurrentPlayer(Authentication authentication) {
        return repoPlayer.findByUserName(authentication.getName());
    }

    private boolean isGuest(Authentication authentication) {

        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}
