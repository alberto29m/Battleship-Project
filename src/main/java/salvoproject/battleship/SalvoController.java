package salvoproject.battleship;


import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sun.applet.resources.MsgAppletViewer;

import java.util.*;
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


    @RequestMapping(path = "game/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> joinGame(@PathVariable Long id, Authentication auth){
        if(auth== null){
            return new ResponseEntity<Map<String, Object>>(makeMap("Error", "You must be logged") , HttpStatus.UNAUTHORIZED);
        }
        Game game = repo.findOne(id);
        if(game.getId() == null){
            return new ResponseEntity<Map<String, Object>>(makeMap("Error", "No such game"), HttpStatus.FORBIDDEN);
        }
        if(game.getPlayers().size()== 2){
            return new ResponseEntity<Map<String, Object>>(makeMap("Error", "This game is full"), HttpStatus.FORBIDDEN);
        }
        GamePlayer newGamePlayer = new GamePlayer(game, getCurrentPlayer(auth));
        return new ResponseEntity<Map<String, Object>>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createGame(Authentication auth){
        if(auth== null){
            return new ResponseEntity<Map<String, Object>>(makeMap("Error", "You must be logged") , HttpStatus.UNAUTHORIZED);
        }else{
            Game newGame = new Game();
            repo.save(newGame);
            GamePlayer newGamePlayer = new GamePlayer(newGame, getCurrentPlayer(auth));
            repoGamePlayer.save(newGamePlayer);
            return new ResponseEntity<Map<String, Object>>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
        }
    }



    @RequestMapping(path = "/games", method= RequestMethod.GET)
    public Map<String, Object> makeGamesDTO(Authentication auth) {
        Map<String, Object> gamesdto = new LinkedHashMap<String, Object>();
        List<Game> games = repo.findAll();
        List<Player> players = repoPlayer.findAll();
        gamesdto.put("games", games.stream().map(game -> makeGameDTO2(game)).collect(toList()));
        gamesdto.put("leaderboard",players.stream().map(player -> scoreMap(player)).collect(toList()) );
        if(auth != null) {
            gamesdto.put("currentUser", makePlayerDTO(getCurrentPlayer(auth)));
        }
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
    public  ResponseEntity<Map<String, Object>> makeGameView(@PathVariable Long id, Authentication auth) {
        Map<String,Object> makeGameViewDTO = new LinkedHashMap<>();
        if(auth != null){
            GamePlayer gamePlayer = repoGamePlayer.findOne(id);
            Set<Ship> ships = gamePlayer.getShips();
            Set<Salvo> salvos = gamePlayer.getSalvo();
            Set<GamePlayer>gamePlayers = gamePlayer.getGame().getGamePlayers();
            makeGameViewDTO.put("game", makeGameDTO(gamePlayer.getGame()));
            if(gamePlayer.getShips().size() != 0){
                makeGameViewDTO.put("ships", ships.stream().map(ship -> makeShipsDTO(ship)).collect(toList()));
            }
            if(gamePlayer.getSalvo().size() != 0){
                makeGameViewDTO.put("salvoes", gamePlayers.stream().map(gamePlayer1 -> makeSalvoesDTO(gamePlayer1.getSalvo())).collect(toList()));
            }
            if(gamePlayer.getPlayer().getId() != getCurrentPlayer(auth).getId()){
                return new ResponseEntity<>(makeMap("Don't cheat", gamePlayer.getId()) , HttpStatus.UNAUTHORIZED);
            }else {
                return new ResponseEntity<> (makeGameViewDTO, HttpStatus.OK );
            }
        }
        else{
            return new ResponseEntity<Map<String, Object>> (makeMap("Error", "You can´t  do it"), HttpStatus.OK );
        }
    }


    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
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
