import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;


public class gameMaster {
	static Scanner input = new Scanner(System.in);
	//global array of integer to be used by methods menu and play choice

	private enum STATE {
		MENU,
		GAME
	}

    private static STATE state = STATE.MENU;


	//this is a Interface Method therefore it should be public
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
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("Only accepts integer as input\n");
				System.out.println(e);
				input.nextLine(); // Clear the buffer
				continue;
			}
		}
	}

	//implement the functionality of menu method therefore it is private
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
							//state = STATE.GAME;
							LocalDateTime start = LocalDateTime.now();
							choosePokemon(generatePokemon(3));


							break;

						//format the currentTime object to a readable string
						//DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
						//LocalDateTime end = LocalDateTime.now();
						//game.generateWildPokemon();
						//game.catchPokemon();
						//game.startBattle();

						case 2:
							choosePokemon(generatePokemon(3));
							break;

						case 3:
							return;
					}
				} 
				else {
					System.out.println("Please select a valid option (1 to 3).");
				}
			} catch (InputMismatchException e) {
				System.out.println("Only accepts integer as input\n");
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

	private static ArrayList<Pokemon> generatePokemon(int num) {
		final int TOTALPOKE = 29;
		
		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<Pokemon> generatedPokemons = new ArrayList<>();
		for (int i = 1; i < TOTALPOKE; i++) {
			indices.add(i);
		}
		if (num > TOTALPOKE) num = TOTALPOKE; 
		
		Collections.shuffle(indices);
		for (int i = 0; i < num; i++) {
			int index = indices.get(i);
			Pokemon pokemon = new Pokemon(index, Team.Ally);
			generatedPokemons.add(pokemon);
		}
		return generatedPokemons;
		}
	
	public static Pokemon choosePokemon(ArrayList<Pokemon> pokelist) {
		System.out.println("Choose your Pokémon by entering its ID: ");
		Scanner input = new Scanner(System.in);

		while (true) {
			try {
				for (Pokemon pokemon: pokelist) {
					UI.displayPokemonDetails(pokemon);
				}
				
				int chosenPokemon = input.nextInt();

				
				for (Pokemon pokemon : pokelist) {

					if (pokemon.getPokemon_id() == chosenPokemon) {
						UI.displayMessage("You have caught a " + pokemon.getName());
						return pokemon;
					}
				}
					System.out.println("Invalid choice. No Pokémon with the name " + chosenPokemon + " found.");
					System.out.println("Please choose a valid Pokémon by entering its name: ");
					input.nextLine();

			}

			catch (InputMismatchException e) {
				System.out.println("Enter a valid Pokemon ID (Integer)");
				input.nextLine();
				continue;
			}
		}
	}
}






//for game mechanics code remember to add if(State == STATE.GAME)
	
	
	
	
	



