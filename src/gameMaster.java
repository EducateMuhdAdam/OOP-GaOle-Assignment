package src;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class gameMaster {
	static Scanner input = new Scanner(System.in);

	private enum STATE {
		MENU,
		GAME
	}

	private static STATE state = STATE.MENU;

	public static void menu() {
		int userChoice = 0;

		while (true) {
			try {

				String[] mainMenu = {"PLAY", "OPTIONS", "QUIT"};
				menuGenerator(mainMenu);

				System.out.println("\nEnter selected option: ");
				userChoice = input.nextInt();

				if (userChoice == 1 || userChoice == 2 || userChoice == 3) {
					switch (userChoice) {
						case 1:
							playChoice();
							break;
						case 2:
							System.out.println("Options selected.");
							break;
						case 3:
							System.out.println("Exiting game.");
							System.exit(0);
							break;
					}
				} else {
					System.out.println("Please select a valid option (1 to 3).");
				}
			} catch (InputMismatchException e) {
				System.out.println("Only accepts integer as input\n");
				input.nextLine(); // Clear the buffer
			}
		}
	}

	private static void playChoice() {
		int gameMode;
		while (true) {
			try {

				String[] playMenu = {"Battle and Catch", "Catch Now", "Back"};
				menuGenerator(playMenu);

				System.out.println("\nEnter selected option:");
				gameMode = input.nextInt();

				if (gameMode == 1 || gameMode == 2 || gameMode == 3) {
					switch (gameMode) {
						case 1:
							LocalDateTime start = LocalDateTime.now();
							battleTime(choosePokemon(generatePokemon(3, Team.Ally)), generatePokemon(2, Team.Enemy));
							break;
						case 2:
							choosePokemon(generatePokemon(3, Team.Ally));
							break;
						case 3:
							return;
					}
				} else {
					System.out.println("Please select a valid option (1 to 3).");
				}
			} catch (InputMismatchException e) {
				System.out.println("Only accepts integer as input\n");
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
			System.out.printf("=");
		}
		System.out.println();

		for (int i = 0; i < options.length; i++) {
			System.out.printf("|%d)%" + max + "s|", i + 1, options[i]);
			System.out.println();
		}

		for (int i = 0; i < max + 4; i++) {
			System.out.printf("=");
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

	public static ArrayList<Pokemon> choosePokemon(ArrayList<Pokemon> pokelist) {
		int counter = 1;
		int chosenID = 0;
		ArrayList<Pokemon> chosenPokemonList = new ArrayList<>();
		while (counter <= 2) {

			while (true) {
				try {
					for (Pokemon pokemon : pokelist) {
						UI.displayPokemonDetails(pokemon);
					}
					System.out.println("Choose your " + counter + " Pokémon by entering its ID: ");

					int chosenPokemon = input.nextInt();
					if (chosenID == chosenPokemon) {
						System.out.println("You have already chosen this Pokémon. Please choose another Pokémon.");
						continue;
					}

					boolean validChoice = false;
					for (Pokemon pokemon : pokelist) {
						if (pokemon.getPokemon_id() == chosenPokemon) {
							UI.displayMessage("You have caught a " + pokemon.getName());
							chosenID = chosenPokemon;
							chosenPokemonList.add(pokemon);
							counter++;
							validChoice = true;
							break;
						}
					}

					if (!validChoice) {
						System.out.println("Invalid choice. No Pokémon with the ID " + chosenPokemon + " was found.");
					} else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("Enter a valid Pokemon ID (Integer)");
					input.nextLine(); // Clear the buffer
				}
			}
		}
		return chosenPokemonList;
	}

	public static void battleTime(ArrayList<Pokemon> ally, ArrayList<Pokemon> enemy) {
		UI.clearTerminal();
		UI.displayBattleStart();


	}
}