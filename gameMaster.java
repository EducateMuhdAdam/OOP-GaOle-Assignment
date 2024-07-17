package pokemon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;


		public class gameMaster{
		
		public static final ArrayList<Integer> acceptedOptions = new ArrayList<>(Arrays.asList(1, 2, 3));
		private enum STATE{
			MENU,
			GAME
		};
		
		private STATE state = STATE.MENU;
		
		
		public static void menu() {
		
			
		Scanner input = new Scanner (System.in);
		int userChoice = 0;
		
		//Player player = new Player("");
		//Game game = new Game(player);
		
				
		while(true) {
            try {
                String[] mainMenu = { "PLAY", "OPTIONS", "QUIT" };
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
            } catch (Exception e) {
                System.out.println("Only accepts integer as input\n");
                input.nextLine(); // Clear the buffer
                continue;
            }
		}
	}
		
	private static void playChoice(Scanner input) {
		
		while (true){
            try {
            	String [] playMenu = {"Battle and Catch", "Catch Now", "Back"};
        		menuGenerator(playMenu);
        		
        		System.out.println("\nEnter selected option: ");
                int gameMode = input.nextInt();

                if (acceptedOptions.contains(gameMode)) {
                	switch (gameMode) {
                    case 1:
                        LocalDateTime start = LocalDateTime.now();
                        //format the currentTime object to a readable string
            			//DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            			//LocalDateTime end = LocalDateTime.now();
                        
                        //game.generateWildPokemon();
                        //game.catchPokemon();
                        //game.startBattle();
                        ;
                    case 2:
                        //game.generateWildPokemon();
                        //game.catchPokemon();
                        break;
                    case 3:
                    	return;
                    }
                } else {
                    System.out.println("Please select a valid option (1 to 3).");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Only accepts integer as input\n");
                input.nextLine(); // Clear the buffer
                continue;
            }
        } 
		
	}
	
	private static void menuGenerator(String [] options) {
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
			System.out.printf("|%d)%" + Integer.toString(max) + "s|", i+1, options[i]);
			System.out.println();
		}
		
		for (int i = 0; i < max + 4; i++) {
			System.out.printf("=");
		}
	}
	
	
	private static void generatePokemon(Pokemon number) {
		
	}
	
	
	//for game mechanics code remember to add if(State == STATE.GAME)
}	
