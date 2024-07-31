package src;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;



public class gameMaster {
	static Scanner input = new Scanner(System.in);
	static int turnCounter = 0;
	private static ArrayList<Moves> queue;
	private static ArrayList<Pokemon> ally;
	private static ArrayList<Pokemon> enemy;
	private static ArrayList<Pokemon> catchChance;
	private static HashMap<Integer, ArrayList<Moves>> mainlog = new HashMap<Integer, ArrayList<Moves>>();
	private static ArrayList<Pokemon> skipped = new ArrayList<Pokemon>();

	private static LocalDateTime start;
	private static LocalDateTime end;

	private static final String[] ballList = {"pokeball", "greatball", "ultraball", "masterball"};
	private static final double[] ballCatchRate = {0.2, 0.4, 0.7, 0.9};

	private enum STATE {
		MENU,
		GAME
	}

	private static STATE state = STATE.MENU;

	public static void menu() {

		int userChoice = 0;

		System.out.println(UI.colorFont("Enter your username: ", UI.GREEN));
		String name = input.nextLine();
		Player player = new Player(name, 0, null);


		while (true) {
			try {
				String[] mainMenu = {"PLAY", "OPTIONS", "QUIT"};
				menuGenerator(mainMenu);

				System.out.println(UI.colorFont("\nEnter selected option: ", UI.GREEN));
				userChoice = input.nextInt();

				if (userChoice == 1 || userChoice == 2 || userChoice == 3) {
					switch (userChoice) {
						case 1:
							playChoice(player);
							break;
						case 2:
							System.out.println(UI.colorFont("Options selected.", UI.GREEN));
							break;
						case 3:
							System.out.println(UI.colorFont("Exiting game.", UI.RED));
							System.exit(0);
							break;
					}
				} else {
					System.out.println(UI.colorFont("Please select a valid option (1 to 3).", UI.YELLOW));
				}
			} catch (InputMismatchException e) {
				System.out.println(UI.colorFont("Only accepts integer as input\n", UI.RED));
				input.nextLine(); // Clear the buffer
			}
		}
	}

	private static void playChoice(Player player) {
		int gameMode;
		while (true) {
			try {

				String[] playMenu = {"Battle and Catch", "Catch Now", "Back"};
				menuGenerator(playMenu);

				System.out.println(UI.colorFont("\nEnter selected option:", UI.GREEN));
				gameMode = input.nextInt();

				if (gameMode == 1 || gameMode == 2 || gameMode == 3) {
					switch (gameMode) {
						case 1:
							state = STATE.GAME;
							start = LocalDateTime.now();
							battleTime(choosePokemon(generatePokemon(3, Team.Ally), player), generatePokemon(2, Team.Enemy),player);
							catchTime(catchChance);
							LocalDateTime end = LocalDateTime.now();
							System.out.println(gameDuration(start, end));
							UI.displayGameEnd();
							break;

						case 2:
							state = STATE.GAME;
							choosePokemon(generatePokemon(3, Team.Ally), player);
							catchTime(catchChance);

							break;
						case 3:
							state = STATE.MENU;

							return;
					}
				} else {
					System.out.println(UI.colorFont("Please select a valid option (1 to 3).", UI.YELLOW));
				}
			} catch (InputMismatchException e) {
				System.out.println(UI.colorFont("Only accepts integer as input\n", UI.RED));
				input.nextLine(); // Clear the buffer
			}
		}
	}

	private static void menuGenerator(String[] options) {
		int max = 0;
		for (String option : options) {
			if (option.length() > max) {
				max = option.length();
			}
		}

		for (int i = 0; i < max + 4; i++) {
			System.out.printf(UI.colorFont("=", UI.YELLOW));
		}
		System.out.println();

		for (int i = 0; i < options.length; i++) {
			System.out.printf("|%d)%" + max + "s|", i + 1, options[i]);
			System.out.println();
		}

		for (int i = 0; i < max + 4; i++) {
			System.out.printf(UI.colorFont("=", UI.YELLOW));
		}
		System.out.println();
	}

	private static ArrayList<Pokemon> generatePokemon(int num, Team team) {
		final int TOTALPOKE = 23;

		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<Pokemon> generatedPokemons = new ArrayList<>();
		for (int i = 1; i <= TOTALPOKE; i++) {
			indices.add(i);
		}
		if (num > TOTALPOKE) num = TOTALPOKE;

		Collections.shuffle(indices);
		for (int i = 0; i < num; i++) {
			int index = indices.get(i);
			Pokemon pokemon = new Pokemon(index, team);
			generatedPokemons.add(pokemon);
		}

		return generatedPokemons;
	}

	public static ArrayList<Pokemon> choosePokemon(ArrayList<Pokemon> pokelist, Player player) {
		int counter = 1;
		int chosenID = 0;
		ArrayList<Pokemon> chosenPokemonList = new ArrayList<>();
		while (counter <= 2) {

			while (true) {
				try {
					for(Pokemon poke : pokelist)
						UI.displayPokemonDetails(poke);
					System.out.println(UI.colorFont("Choose your " + counter + " Pokémon by entering its ID: ", UI.GREEN));

					int chosenPokemon = input.nextInt();
					if (chosenID == chosenPokemon) {
						System.out.println(UI.colorFont("You have already chosen this Pokémon. Please choose another Pokémon.", UI.RED));
						continue;
					}

					boolean validChoice = false;
					for (Pokemon pokemon : pokelist) {
						if (pokemon.getPokemon_id() == chosenPokemon) {
							UI.displayMessage("You have caught a " + pokemon.getName());
							chosenID = chosenPokemon;
							player.addPokemon(chosenID);
							chosenPokemonList.add(pokemon);
							counter++;
							validChoice = true;
							break;
						}
					}

					if (!validChoice) {
						System.out.println(UI.colorFont("Invalid choice. No Pokémon with the ID " + chosenPokemon + " was found.", UI.RED));
					} else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println(UI.colorFont("Enter a valid Pokemon ID (Integer)", UI.RED));
					input.nextLine(); // Clear the buffer
				}
			}
		}
		return chosenPokemonList;
	}

	public static void battleTime(ArrayList<Pokemon> allylist, ArrayList<Pokemon> enemylist, Player player) {

		ally = allylist;
		enemy = enemylist;
		queue = new ArrayList<Moves>();
		catchChance = new ArrayList<Pokemon>();
		turnCounter = 0;

		UI.displayBattleStart();
		while (!ally.isEmpty() && !enemy.isEmpty()) {
			turnCounter++;
			mainlog.put(turnCounter, new ArrayList<Moves>());
			System.out.printf("TURN %d\n", getTurnCounter());
			UI.DisplayBattlePokemon(allylist, enemylist);
//player.CalculateScore(pokemon);
			initQueue(ally, enemy);
			for (Moves move : queue) {
				if (CheckAlive(move.user)) {
					move.user.getStatus().CheckUpkeep();
					if (CheckAlive(move.user) && !skipped.contains(move.user)) {
						addToHash(getTurnCounter(), move);
						DoTurn(move);
						//player.CalculateScore(pokemon);

					}
					UpdateAlive();
				}

			}
			queue.clear();
			EndstepTrigger();
			UpdateAlive();
		}
		//readHash();
		if (ally.isEmpty()) UI.displayDefeat();

		if (enemy.isEmpty()) UI.displayVictory();


	}


	public static void DoTurn(Moves move) {
		if (move.getMove_action().equalsIgnoreCase("self")) {
			UI.displayAttack(move);
			move.UseOn();
		} else {
			if (move.user.getTeam() == Team.Ally) {
				UI.displayAttack(move);
				for (Pokemon poke : enemy) {
					move.UseOn(poke);
				}
			}
			if (move.user.getTeam() == Team.Enemy) {
				UI.displayAttack(move);
				for (Pokemon poke : ally) {
					move.UseOn(poke);
				}
			}
		}
	}

	public static int getTurnCounter() {
		return turnCounter;
	}

	public static void readHash() {
		for (int i = 1; i <= mainlog.size(); i++) {
			System.out.println(UI.colorFont(String.format("Turn " + i), UI.CYAN));
			for (Moves m : mainlog.get(i)) {
				System.out.println(UI.colorFont(m.getMove_name(), UI.CYAN));
			}

		}
	}

	public static void addToHash(int Key, Moves move) {
		ArrayList<Moves> moveList = mainlog.get(Key);

		// if list does not exist create it
		if (moveList == null) {
			moveList = new ArrayList<Moves>();
			moveList.add(move);
			mainlog.put(Key, moveList);
		} else {
			// add if item is not already in list
			if (!moveList.contains(move)) moveList.add(move);
		}
	}

	public static HashMap<Integer, ArrayList<Moves>> getMainlog() {
		return mainlog;
	}

	public static void UpdateAlive() {
		ArrayList<Pokemon> toRemove = new ArrayList<Pokemon>();
		if (!enemy.isEmpty()) {
			for (Pokemon poke : enemy) {
				if (poke.getCurrent_hp() <= 0) {

					catchChance.add(poke);
					toRemove.add(poke);
				}
			}

			if (!toRemove.isEmpty()) {
				for (Pokemon poke : toRemove) {
					if (poke.getCurrent_hp() <= 0) {
						enemy.remove(poke);
					}
				}
			}
		}
		if (!ally.isEmpty()) {
			for (Pokemon poke : ally) {
				if (poke.getCurrent_hp() <= 0) {
					toRemove.add(poke);

				}
			}

			if (!toRemove.isEmpty()) {
				for (Pokemon poke : toRemove) {
					if (poke.getCurrent_hp() <= 0) {
						ally.remove(poke);
					}
				}
			}
		}
	}

	public static boolean CheckAlive(Pokemon checkPoke) {
		if (ally.contains(checkPoke)) return true;
		if (enemy.contains(checkPoke)) return true;
		return false;
	}

	public static void EndstepTrigger() {
		for (Pokemon poke : ally) {
			poke.getStatus().CheckEndstep();
		}
		for (Pokemon poke : enemy) {
			poke.getStatus().CheckEndstep();
		}
	}

	public static void initQueue(ArrayList<Pokemon> ally, ArrayList<Pokemon> enemy) {

		ArrayList<Pokemon> prequeue = new ArrayList<Pokemon>();
		for (Moves moves : queue) {
			prequeue.add(moves.user);
		}
		for (Pokemon pokemon : ally) {
			if (!prequeue.contains(pokemon))
				queue.add(UI.displaySelectMove(pokemon));
		}
		for (int i = 0; i < enemy.size(); i++) {
			if (!prequeue.contains(enemy.get(i)))
				queue.add(UI.displaySelectMove(enemy.get(i), (int) (Math.random() * 2 + 1)));
		}

		queue.sort((o1, o2) -> o2.user.getSpeed() - o1.user.getSpeed());
		queue.sort((o1, o2) -> o2.getPriority() - o1.getPriority());

		System.out.println("VVVV GOES FIRST VVVV");
		for (Moves m : queue) {
			System.out.println(UI.colorFont(String.format("%s : %s", m.user.getName(), m.getMove_name()), UI.YELLOW));
		}
		System.out.println("VVVV GOES LAST VVVV");
	}

	public static void skipTurn(Pokemon poke) {
		skipped.add(poke);
		UI.displayMessage(UI.colorFont(String.format((poke.getTeam() + " " + poke.getName() + " skipped its turn!")), UI.YELLOW));
	}

	public static void addTurn(Moves move) {
		queue.add(move);
	}

	public static String gameDuration(LocalDateTime start, LocalDateTime end) {
		Duration duration = Duration.between(start, end);
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		return (UI.colorFont(String.format("Game duration: %d hours, %d minutes, %d seconds", hours, minutes, seconds), UI.CYAN));
	}

	public static void catchTime(ArrayList<Pokemon> catchChance) {

		Pokemon chosenPoke = new Pokemon (1,Team.Ally);
		if (catchChance.size() > 0) {
			UI.displayCatchOptions(catchChance);
			while (true) {
				try {

					int catchchoice = input.nextInt();
					switch (catchchoice) {
						case 1:
							chosenPoke = catchChance.get(0);
							break;
						case 2:
							chosenPoke = catchChance.get(1);
							break;
						default:
							System.out.println(UI.colorFont("Please select a valid option (1 to 2).", UI.YELLOW));
							break;
					}
					int randindex = (int) (Math.random() * 4);
					String playerBall = ballList[randindex];
					System.out.println(UI.colorFont("You got a " + playerBall, UI.YELLOW));
					double catchRate = ballCatchRate[randindex];
					if (catchRate > Math.random()) {
						System.out.println(UI.colorFont("You caught " + chosenPoke.getName(), UI.GREEN));
						ally.add(chosenPoke);
						UI.displayPokemonDetails(chosenPoke);
					} else {
						System.out.println(UI.colorFont("You failed to catch " + chosenPoke.getName(), UI.RED));
					}

				} catch (InputMismatchException e) {
					System.out.println(UI.colorFont("Please enter a valid number", UI.RED));
					input.nextLine();
				}
			}
		}

	}
	public void view_score(Player player) throws IOException {
		int player_score = player.getScore();
        try {
            String[] data = read_file();
			data = sort(data, player.getScore(),player.getUsername());
            /* here then I will call UI to print out the table of score */
			UI.view_score(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


	public static String[] sort(String[] data, int score, String username) {
		List<String> dataList = new ArrayList<>(Arrays.asList(data));
		dataList.add(username + "," + score);
		dataList.sort((a, b) -> {
			int scoreA = Integer.parseInt(a.split(",")[1]);
			int scoreB = Integer.parseInt(b.split(",")[1]);
			return Integer.compare(scoreB, scoreA); // Sort in descending order
		});
		return dataList.toArray(new String[0]);
	}

	public static String[] read_file() throws IOException {
		String fileName = System.getProperty("user.dir") + "\\src\\stopscorelist.csv";
		FileReader reader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = "";
		String data[] = new String[0];
		while(true) {
            if (bufferedReader.readLine() == null) break;
            data = line.split(",");
        }
		return data;
	}
	public static void write_changes_file(String[] scores) throws IOException {
		String fileName = System.getProperty("user.dir") + "\\src\\topscorelist.csv";
		FileWriter writer = new FileWriter(fileName);

		for(int i = 0; i < scores.length; i++){
			writer.append(scores[i]+ ','+ scores[1] + "\n");
		}
		writer.close();


	}

}


	//}

	//public static void write_changes_file( String[] scores) throws IOException {
		//String fileName = System.getProperty("user.dir") + "\\src\\score.csv";
		//FileWriter writer = new FileWriter(fileName);
	    //
		//for(int i = 0; i < scores.length; i++){
		//		writer.append("%s,%s\n"scores[i], scores[1]);
		// }
		//writer.close();

	//}


