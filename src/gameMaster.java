package src;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class gameMaster {

	//global array of integer to be used by methods menu and play choice
	private static final ArrayList<Integer> acceptedOptions = new ArrayList<>(Arrays.asList(1, 2, 3));

	private enum STATE {
		MENU,
		GAME
	}

    private static STATE state = STATE.MENU;


	//this is a Interface Method therefore it should be public
	public static void menu() {


		Scanner input = new Scanner(System.in);
		int userChoice = 0;


		while (true) {
			try {
				String[] mainMenu = {"PLAY", "OPTIONS", "QUIT"};
				menuGenerator(mainMenu);

				System.out.println("\nEnter selected option: ");
				userChoice = input.nextInt();

				if (acceptedOptions.contains(userChoice)) {
					switch (userChoice) {
						case 1:
							playChoice(input);
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
					continue;
				}
			} catch (Exception InputMismatchException) {
				System.out.println("Only accepts integer as input\n");
				input.nextLine(); // Clear the buffer
				continue;
			}
		}
	}

	//implement the functionality of menu method therefore it is private
	private static void playChoice(Scanner input) {

		while (true) {
			try {
				String[] playMenu = {"Battle and Catch", "Catch Now", "Back"};
				menuGenerator(playMenu);

				System.out.println("\nEnter selected option: ");
				int gameMode = input.nextInt();

				if (acceptedOptions.contains(gameMode)) {
					switch (gameMode) {
						case 1:
							state = STATE.GAME;
							LocalDateTime start = LocalDateTime.now();
							generatePokemon();


							break;

						//format the currentTime object to a readable string
						//DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
						//LocalDateTime end = LocalDateTime.now();
						//game.generateWildPokemon();
						//game.catchPokemon();
						//game.startBattle();

						case 2:
							generatePokemon();
							break;

						case 3:
							return;
					}
				} else {
					System.out.println("Please select a valid option (1 to 3).");
					continue;
				}
			} catch (Exception InputMismatchException) {
				System.out.println("Only accepts integer as input\n");
				input.nextLine(); // Clear the buffer
				continue;
			}
		}

	}

	//this is a helper method that implement the functionality of menu method therefore it should be private for encapsulation
	private static void menuGenerator(String[] options) {
		int max = 0;
		for (int i = 0; i < options.length; i++) {
			if (options[i].length() > max) {
				max = options[i].length();
			}
		}

		for (int i = 0; i < max + 4; i++) {
			System.out.printf("=");
		}
		System.out.println();

		for (int i = 0; i < options.length; i++) {
			//string literal format
			System.out.printf("|%d)%" + Integer.toString(max) + "s|", i + 1, options[i]);
			System.out.println();
		}

		for (int i = 0; i < max + 4; i++) {
			System.out.printf("=");
		}
	}

	private static void generatePokemon() {
		Pokemon chosenPokemon = null;
		Pokemon wildPokemon = null;
		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<Pokemon> generatedPokemons = new ArrayList<>();
		for (int i = 1; i < 29; i++) {
			indices.add(i);
		}

		Collections.shuffle(indices);
		for (int i = 0; i < 3; i++) {
			int index = indices.get(i);
			Pokemon pokemon = new Pokemon(index, Team.Ally);
			generatedPokemons.add(pokemon);
			UI.displayPokemonDetails(pokemon);
		}

		System.out.println("Choose your Pokémon by entering its ID: ");
		Scanner input = new Scanner(System.in);


		while (true) {
			try {
				int chosenPokemonID = input.nextInt();

				boolean validChoice = false;
				for (Pokemon pokemon : generatedPokemons) {

					if (pokemon.getPokemon_id() == chosenPokemonID) {
						chosenPokemon = pokemon;
						UI.displayMessage("You have chosen " + chosenPokemon.getName());
						validChoice = true;
						break;
					}
				}

				if (!validChoice) {
					System.out.println("Invalid choice. No Pokémon with the name " + chosenPokemonID + " found.");
					System.out.println("Please choose a valid Pokémon by entering its name: ");
					input.nextLine();

				}

			} catch (Exception InputTypeMismatchException) {
				throw new RuntimeException(InputTypeMismatchException);
			}


			input.close();
			for (Pokemon pokemon : generatedPokemons) {
				if (!pokemon.equals(chosenPokemon)) {
					wildPokemon = new Pokemon(pokemon.getPokemon_id(), Team.Enemy);

				}
			}
			ArrayList<Pokemon> pokemonList = new ArrayList<>();
			pokemonList.add(wildPokemon);
			pokemonList.add(chosenPokemon);


		}

	}
}






//for game mechanics code remember to add if(State == STATE.GAME)
	
	
	
	
	



