package salvoproject.battleship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/Games")
    public List<Map<String, Object>> getGame() {
        return repo.findAll().stream().map(Game -> makeGameDTO(Game)).collect(toList());
    }

    private Map<String, Object> makeGameDTO(Game game){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        Set<GamePlayer> gps = game.getGamePlayer();
        dto.put("id", game.getId());
        dto.put("date", game.getDate());
        dto.put("gamePlayer", gps.stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(toList()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
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

}
