package src;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class gameMaster {
	static Scanner input = new Scanner(System.in);
	static int turnCounter = 0;
	private static ArrayList<Moves> queue;
	private static ArrayList<Pokemon> ally;
	private static ArrayList<Pokemon> enemy;
	private static ArrayList<Pokemon> catchChance;
	private static HashMap<Integer, ArrayList<Moves>> mainlog;

	private static LocalDateTime start;
	private static LocalDateTime end;

	private enum STATE {
		MENU,
		GAME
	}

	private static STATE state = STATE.MENU;

	public static void menu() {

		int userChoice = 0;

		System.out.println(UI.colorFont("Enter your username: ", UI.GREEN));
		String name = input.nextLine();
		Player player = new Player(name,0,null);


		while (true) {
			try {
				String[] mainMenu = {"PLAY", "OPTIONS", "QUIT"};
				menuGenerator(mainMenu);

				System.out.println(UI.colorFont("\nEnter selected option: ",UI.GREEN));
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
							battleTime(choosePokemon(generatePokemon(3, Team.Ally), player), generatePokemon(2, Team.Enemy));
							LocalDateTime end = LocalDateTime.now();
							System.out.println(gameDuration(start, end));
							UI.displayGameEnd();
							break;
						case 2:
							state = STATE.GAME;
							choosePokemon(generatePokemon(3, Team.Ally), player);

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
		final int TOTALPOKE = 28;

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
					for (Pokemon pokemon : pokelist) {
						UI.displayPokemonDetails(pokemon);
					}
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

	public static void battleTime(ArrayList<Pokemon> allylist, ArrayList<Pokemon> enemylist) {
		ally = allylist;
		enemy = enemylist;
		queue = new ArrayList<Moves>();
		catchChance = new ArrayList<Pokemon>();
		mainlog = new HashMap<Integer, ArrayList<Moves>>();
		ArrayList<Moves> turnlog = new ArrayList<Moves>();
		ArrayList<Pokemon> defeated = new ArrayList<Pokemon>();
		turnCounter = 0;

		UI.displayBattleStart();
		UI.DisplayBattlePokemon(allylist, enemylist);
		while(!ally.isEmpty() && !enemy.isEmpty()) {
			turnlog.clear();
			turnCounter++;
			initQueue(ally, enemy);
			for (Moves move: queue){
				if (CheckAlive(move.user)){
					move.user.getStatus().CheckUpkeep();
					if (CheckAlive(move.user)) {DoTurn(move); turnlog.add(move);}
					UpdateAlive();
				}

			}
			mainlog.put(turnCounter, turnlog);
			queue.clear();
			EndstepTrigger();
			UpdateAlive();
			}
		if (ally.isEmpty()) UI.displayDefeat();
		if (enemy.isEmpty()) UI.displayVictory();
	}
	public static void DoTurn(Moves move){
		if (move.getMove_action().equalsIgnoreCase("self")) move.UseOn(move.user);
		else {
			if (move.user.getTeam() == Team.Ally) {
				for (Pokemon poke: enemy){
					move.UseOn(poke);
				}
			}
			if (move.user.getTeam() == Team.Enemy) {
				for (Pokemon poke: ally){
					move.UseOn(poke);
				}
			}
		}
	}
	public static int getTurnCounter(){
		return turnCounter;
	}
	public static HashMap<Integer, ArrayList<Moves>> getMainlog(){
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
	public static void EndstepTrigger(){
		for (Pokemon poke: ally){
			poke.getStatus().CheckEndstep();
		}
		for (Pokemon poke: enemy){
			poke.getStatus().CheckEndstep();
		}
	}
	public static void initQueue(ArrayList<Pokemon> ally, ArrayList<Pokemon> enemy){
		for (int i = 0; i < ally.size(); i++){
			queue.add(UI.displaySelectMove(ally.get(i)));
		}
		for (int i = 0; i < enemy.size(); i++) {
			queue.add(UI.displaySelectMove(enemy.get(i), (int) (Math.random() * 2 + 1)));
		}

		queue.sort((o1, o2) -> o2.user.getSpeed() - o1.user.getSpeed());
		queue.sort((o1, o2) -> o2.getPriority() - o1.getPriority());

		for (Moves m: queue) {
			System.out.println(m.user.getPokemon_id()+ " " + m.getMove_name());
		}
	}
	public static void skipTurn (Pokemon poke) {
		queue.removeIf(move -> move.user.equals(poke));
		UI.displayMessage((poke.getName() + "skipped its turn!"));
	}

	public static String gameDuration(LocalDateTime start, LocalDateTime end) {
		Duration duration = Duration.between(start, end);
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		return String.format("Game duration: %d hours, %d minutes, %d seconds", hours, minutes, seconds);
	}
}