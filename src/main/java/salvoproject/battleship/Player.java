package salvoproject.battleship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers = new LinkedHashSet<>();

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> score = new LinkedHashSet<>();

    private String userName;

    public Player(){ }



    public Player(String email){userName = email;}


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String toString() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<salvoproject.battleship.GamePlayer> getGamePlayer() {
        return gamePlayers;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Set<Score> getScores() {
        return score;
    }

    public void setScore(Set<Score> score) {
        this.score = score;
    }

    public void setGamePlayer(Set<salvoproject.battleship.GamePlayer> gamePlayer) {
        gamePlayers = gamePlayer;
    }
    @JsonIgnore
    public List<Game> getGames () {
        return gamePlayers.stream().map(gamePlayer -> gamePlayer.getGame()).collect(toList());
    }
}
