package salvoproject.battleship;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);

	}
	@Bean
	public CommandLineRunner initData(PlayerRepository repository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			Player p1 = new Player( "j.bauer@ctu.gov");
			Player p2 = new Player( "c.obrian@ctu.gov");
			Player p3 = new Player( "kim_bauer@gmail.com");
			Player p4 = new Player( "t.almeida@ctu.gov");

			repository.save(p1);
			repository.save(p2);
			repository.save(p3);
			repository.save(p4);



			Date newDate = new Date();
			newDate = Date.from(newDate.toInstant().plusSeconds(3600));
			Date newDate2 = Date.from(newDate.toInstant().plusSeconds(3600));
			Date newDate3 = Date.from(newDate2.toInstant().plusSeconds(3600));
			Date newDate4 = Date.from(newDate3.toInstant().plusSeconds(3600));
			Date newDate5 = Date.from(newDate4.toInstant().plusSeconds(3600));
			Date newDate6 = Date.from(newDate5.toInstant().plusSeconds(3600));
			Date newDate7 = Date.from(newDate6.toInstant().plusSeconds(3600));


            Game game1 = new Game();
			gameRepository.save(game1);


            Game game2 = new Game();
            game2.setDate(newDate);
            gameRepository.save(game2);

            Game game3 = new Game();
            game3.setDate(newDate2);
            gameRepository.save(game3);

			Game game4 = new Game();
			game4.setDate(newDate3);
			gameRepository.save(game4);

			Game game5 = new Game();
			game5.setDate(newDate4);
			gameRepository.save(game5);

			Game game6 = new Game();
			game6.setDate(newDate5);
			gameRepository.save(game6);

			Game game7 = new Game();
			game7.setDate(newDate6);
			gameRepository.save(game7);

			Game game8 = new Game();
			game8.setDate(newDate7);
			gameRepository.save(game8);

            GamePlayer gp1 = new GamePlayer(game1, p1);
			gamePlayerRepository.save(gp1);
			GamePlayer gp2 = new GamePlayer(game1, p2);
			gamePlayerRepository.save(gp2);

			GamePlayer gp3 = new GamePlayer(game2, p1);
			gamePlayerRepository.save(gp3);
			GamePlayer gp4 = new GamePlayer(game2, p2);
			gamePlayerRepository.save(gp4);

			GamePlayer gp5 = new GamePlayer(game3, p2);
			gamePlayerRepository.save(gp5);
			GamePlayer gp6 = new GamePlayer(game3, p4);
			gamePlayerRepository.save(gp6);

			GamePlayer gp7 = new GamePlayer(game4, p2);
			gamePlayerRepository.save(gp7);
			GamePlayer gp8 = new GamePlayer(game4, p3);
			gamePlayerRepository.save(gp8);

			GamePlayer gp9 = new GamePlayer(game5, p4);
			gamePlayerRepository.save(gp9);
			GamePlayer gp10 = new GamePlayer(game5, p3);
			gamePlayerRepository.save(gp10);

			GamePlayer gp11 = new GamePlayer(game6, p1);
			gamePlayerRepository.save(gp11);


			GamePlayer gp12 = new GamePlayer(game7, p4);
			gamePlayerRepository.save(gp12);


			GamePlayer gp13 = new GamePlayer(game8, p3);
			gamePlayerRepository.save(gp13);
			GamePlayer gp14 = new GamePlayer(game8, p4);
			gamePlayerRepository.save(gp14);


			List<String> location0 = Arrays.asList("H2", "H3", "H4");
			List<String> location1 = Arrays.asList("E1", "F1", "G1");
			List<String> location2 = Arrays.asList("B4", "B5");
			List<String> location3 = Arrays.asList("B5", "C5", "D5");
			List<String> location4 = Arrays.asList("F1", "F2");
			List<String> location5 = Arrays.asList("B5", "C5", "D5");
			List<String> location6 = Arrays.asList("C6", "C7");
			List<String> location7 = Arrays.asList("A2", "A3", "A4");
			List<String> location8 = Arrays.asList("G6", "H6");
			List<String> location9 = Arrays.asList("B5", "C5", "D5");
			List<String> location10 = Arrays.asList("C6", "C7");
			List<String> location11 = Arrays.asList("A2", "A3", "A4");
			List<String> location12 = Arrays.asList("G6", "H6");
			List<String> location13 = Arrays.asList("B5", "C5", "D5");
			List<String> location14 = Arrays.asList("C6", "C7");
			List<String> location15 = Arrays.asList("A2", "A3", "A4");
			List<String> location16 = Arrays.asList("G6", "H6");
			List<String> location17 = Arrays.asList("B5", "C5", "D5");
			List<String> location18 = Arrays.asList("C6", "C7");
			List<String> location19 = Arrays.asList("A2", "A3", "A4");
			List<String> location20 = Arrays.asList("G6", "H6");
			List<String> location21 = Arrays.asList("B5", "C5", "D5");
			List<String> location22 = Arrays.asList("C6", "C7");
			List<String> location23 = Arrays.asList("B5", "C5", "D5");
			List<String> location24 = Arrays.asList("C6", "C7");
			List<String> location25 = Arrays.asList("A2", "A3", "A4");
			List<String> location26 = Arrays.asList("G6", "H6");


			Ship ship0 = new Ship("Destroyer",location0, gp1);
			shipRepository.save(ship0);
			Ship ship1 = new Ship("Submarine",location1, gp1);
			shipRepository.save(ship1);
			Ship ship2 = new Ship("Patrol Boat",location2, gp1);
			shipRepository.save(ship2);
			Ship ship3 = new Ship("Destroyer",location3, gp1);
			shipRepository.save(ship3);
			Ship ship4 = new Ship("Patrol Boat",location4, gp1);
			shipRepository.save(ship4);

			Ship ship5 = new Ship("Destroyer",location5, gp2);
			shipRepository.save(ship5);
			Ship ship6 = new Ship("Patrol Boat",location6, gp2);
			shipRepository.save(ship6);
			Ship ship7 = new Ship("Submarine",location7, gp2);
			shipRepository.save(ship7);
			Ship ship8 = new Ship("Patrol Boat",location8, gp2);
			shipRepository.save(ship8);

			Ship ship9 = new Ship("Submarine",location9, gp3);
			shipRepository.save(ship9);
			Ship ship10 = new Ship("Patrol Boat",location10, gp3);
			shipRepository.save(ship10);
			Ship ship11 = new Ship("Destroyer",location11, gp3);
			shipRepository.save(ship11);
			Ship ship12 = new Ship("Patrol Boat",location12, gp3);
			shipRepository.save(ship12);

			Ship ship13 = new Ship("Submarine",location13, gp4);
			shipRepository.save(ship13);
			Ship ship14 = new Ship("Patrol Boat",location14, gp4);
			shipRepository.save(ship14);
			Ship ship15 = new Ship("Destroyer",location15, gp4);
			shipRepository.save(ship15);
			Ship ship16 = new Ship("Destroyer",location16, gp4);
			shipRepository.save(ship16);

			Ship ship17 = new Ship("Destroyer",location17, gp5);
			shipRepository.save(ship17);
			Ship ship18 = new Ship("Destroyer",location18, gp5);
			shipRepository.save(ship18);
			Ship ship19 = new Ship("Destroyer",location19, gp5);
			shipRepository.save(ship19);
			Ship ship20 = new Ship("Destroyer",location20, gp5);
			shipRepository.save(ship20);

			Ship ship21 = new Ship("Destroyer",location21, gp6);
			shipRepository.save(ship21);
			Ship ship22 = new Ship("Destroyer",location22, gp6);
			shipRepository.save(ship22);

			Ship ship23 = new Ship("Destroyer",location23, gp8);
			shipRepository.save(ship23);
			Ship ship24 = new Ship("Destroyer",location24, gp8);
			shipRepository.save(ship24);
			Ship ship25 = new Ship("Destroyer",location25, gp8);
			shipRepository.save(ship25);
			Ship ship26 = new Ship("Destroyer",location26, gp8);
			shipRepository.save(ship26);

			List<String> salvoLocation0 = Arrays.asList("B5", "C5", "F1");
			List<String> salvoLocation1 = Arrays.asList("B4", "B5", "B6");
			List<String> salvoLocation2 = Arrays.asList("F2", "D5");
			List<String> salvoLocation3 = Arrays.asList("E1", "H3", "H3");
			List<String> salvoLocation4 = Arrays.asList("A2", "A4", "G6");
			List<String> salvoLocation5 = Arrays.asList("B5", "D5", "C7");
			List<String> salvoLocation6 = Arrays.asList("A3", "H6");
			List<String> salvoLocation7 = Arrays.asList("C5", "C6");
			List<String> salvoLocation8 = Arrays.asList("G6", "H6", "A4");
			List<String> salvoLocation9 = Arrays.asList("H1", "H2", "H3");
			List<String> salvoLocation10 = Arrays.asList("A2", "A3", "D8");
			List<String> salvoLocation11 = Arrays.asList("E1", "F2", "G3");
			List<String> salvoLocation12 = Arrays.asList("A3", "A4", "F7");
			List<String> salvoLocation13 = Arrays.asList("B5", "C6", "H1");
			List<String> salvoLocation14 = Arrays.asList("A2", "G6", "H6");
			List<String> salvoLocation15 = Arrays.asList("C5", "C7", "D5");
			List<String> salvoLocation16 = Arrays.asList("A1", "A2", "A3");
			List<String> salvoLocation17 = Arrays.asList("B5", "B6", "C7");
			List<String> salvoLocation18 = Arrays.asList("G6", "G7", "G8");
			List<String> salvoLocation19 = Arrays.asList("C6", "D6", "E6");
			List<String> salvoLocation20 = Arrays.asList("H1", "H8");


			Salvo salvo0 = new Salvo(1, salvoLocation0, gp1);
			salvoRepository.save(salvo0);
			Salvo salvo1 = new Salvo(1, salvoLocation1, gp2);
			salvoRepository.save(salvo1);

			Salvo salvo2 = new Salvo(2, salvoLocation2, gp1);
			salvoRepository.save(salvo2);
			Salvo salvo3 = new Salvo(2, salvoLocation3, gp2);
			salvoRepository.save(salvo3);

			Salvo salvo4 = new Salvo(1, salvoLocation4, gp3);
			salvoRepository.save(salvo4);
			Salvo salvo5 = new Salvo(1, salvoLocation5, gp4);
			salvoRepository.save(salvo5);

			Salvo salvo6 = new Salvo(2, salvoLocation6, gp3);
			salvoRepository.save(salvo6);
			Salvo salvo7 = new Salvo(2, salvoLocation7, gp4);
			salvoRepository.save(salvo7);

			Salvo salvo8 = new Salvo(1, salvoLocation8, gp5);
			salvoRepository.save(salvo8);
			Salvo salvo9 = new Salvo(1, salvoLocation9, gp6);
			salvoRepository.save(salvo9);

			Salvo salvo10 = new Salvo(2, salvoLocation10, gp5);
			salvoRepository.save(salvo10);
			Salvo salvo11 = new Salvo(2, salvoLocation11, gp6);
			salvoRepository.save(salvo11);

			Salvo salvo12 = new Salvo(1, salvoLocation12, gp7);
			salvoRepository.save(salvo12);
			Salvo salvo13 = new Salvo(1, salvoLocation13, gp8);
			salvoRepository.save(salvo13);

			Salvo salvo14 = new Salvo(2, salvoLocation14, gp7);
			salvoRepository.save(salvo14);
			Salvo salvo15 = new Salvo(2, salvoLocation15, gp8);
			salvoRepository.save(salvo15);

			Salvo salvo16 = new Salvo(1, salvoLocation16, gp9);
			salvoRepository.save(salvo16);
			Salvo salvo17 = new Salvo(1, salvoLocation17, gp10);
			salvoRepository.save(salvo17);

			Salvo salvo18 = new Salvo(2, salvoLocation18, gp9);
			salvoRepository.save(salvo12);
			Salvo salvo19 = new Salvo(2, salvoLocation19, gp10);
			salvoRepository.save(salvo13);

			Salvo salvo20 = new Salvo(3, salvoLocation20, gp10);
			salvoRepository.save(salvo20);


			Score score1 = new Score(1.0, game1, p1);
			scoreRepository.save(score1);
			Score score2 = new Score(0.0, game1, p2);
			scoreRepository.save(score2);

			Score score3 = new Score(0.5, game2, p1);
			scoreRepository.save(score3);
			Score score4 = new Score(0.5, game2, p1);
			scoreRepository.save(score1);

			Score score5 = new Score(1.0, game3, p2);
			scoreRepository.save(score5);
			Score score6 = new Score(0.0, game3, p4);
			scoreRepository.save(score6);

			Score score7 = new Score(0.5, game4, p2);
			scoreRepository.save(score7);
			Score score8 = new Score(0.5, game4, p1);
			scoreRepository.save(score8);

		};
	}

}
