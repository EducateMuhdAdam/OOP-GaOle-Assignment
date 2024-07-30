import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private static Scanner scanner = new Scanner(System.in);;


    private static void displaySeparator() {
        System.out.println("====================================================");
    }
    
    // Method to clear the terminal screen
    public static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    //After you catch a pokemon, you can display this
    public static void displayPokemonDetails(Pokemon pokemon) {
        displaySeparator();
        System.out.println("                   Pokémon Details                  ");
        displaySeparator();
        System.out.printf("ID: %d\n", pokemon.getPokemon_id());
        System.out.printf("Name: %s\n", pokemon.getName());
        System.out.printf("Type: ");
        for (Type t : pokemon.getType()) {
        	System.out.printf(" %s", t.name());
        }
        System.out.println();
        System.out.printf("Move 1: %s\n", pokemon.getMove1().getMove_name());
        System.out.printf("Move 2: %s\n", pokemon.getMove2().getMove_name());
        displaySeparator();
        System.out.println();
    }    
    
    // Method to display health bar
    public static void displayHealthBar(String label, int currentHP, int maxHP) {
        int barLength = 20; // Fixed length for the bar
        int filledLength = (int) ((currentHP / (double) maxHP) * barLength);
        StringBuilder bar = new StringBuilder();

        String greenColor = "\u001B[32m"; // Green color for high HP
        String orangeColor = "\u001B[33m"; // Orange color for medium HP
        String redColor = "\u001B[31m"; // Red color for low HP
        String whiteColor = "\u001B[37m"; // White color for empty part
        String resetColor = "\u001B[0m"; // Reset color

        String color;
        if (currentHP > maxHP * 0.55) {
            color = greenColor;
        } else if (currentHP > maxHP * 0.20) {
            color = orangeColor;
        } else {
            color = redColor;

        }

        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append(color).append("|").append(resetColor);
            } else {
                bar.append(whiteColor).append("-").append(resetColor);
            }
        }
        System.out.printf("%s HP: [%s] %d/%d\n", label, bar.toString(), currentHP, maxHP);
    }
    
    public static void displayHealthBar(Pokemon poke) {
        int barLength = 20; // Fixed length for the bar
        int filledLength = (int) ((poke.getCurrent_hp() / (double) poke.getHp_max()) * barLength);
        StringBuilder bar = new StringBuilder();

        String greenColor = "\u001B[32m"; // Green color for high HP
        String orangeColor = "\u001B[33m"; // Orange color for medium HP
        String redColor = "\u001B[31m"; // Red color for low HP
        String whiteColor = "\u001B[37m"; // White color for empty part
        String resetColor = "\u001B[0m"; // Reset color

        String color;
        if (poke.getCurrent_hp() > poke.getHp_max() * 0.55) {
            color = greenColor;
        } else if (poke.getCurrent_hp() > poke.getHp_max() * 0.20) {
            color = orangeColor;
        } else {
            color = redColor;

        }

        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append(color).append("|").append(resetColor);
            } else {
                bar.append(whiteColor).append("-").append(resetColor);
            }
        }
        System.out.printf("%s %s HP: [%s] %d/%d\n", poke.getTeam(), poke.getName(), bar.toString(), poke.getCurrent_hp(), poke.getHp_max());
    }

    public static String getPlayerInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }
    
    public static void displayDamage(Pokemon poke, int dmg) {
        System.out.printf("%s %s took %d damage!", poke.getTeam(), poke.getName(), dmg);
        System.out.println();
    }
    

    // Method to display the game start message
    public static void displayGameStart() {
        displayMessage("Welcome to the Pokémon Ga-Ole Game!");
        System.out.println();
    }
    
    public static void displayGameSetup() {
        displayMessage("Setting up the game...");
        System.out.println();
    }

    public static void displayLoadingData() {
        displayMessage("Loading Pokémon data...");
        System.out.println();
    }

    public static void displayCatchOptions(List<Pokemon> catchOptions) {
        displaySeparator();
        System.out.println("Choose a Pokémon to catch:");
        for (int i = 0; i < catchOptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, catchOptions.get(i).getName());
        }
        displaySeparator();
        System.out.print("Enter the number of the Pokémon you want to catch: ");
        System.out.println();
    }

    public static int getCatchChoice() {
        return scanner.nextInt();
    }
    public static void displayCatchResult(boolean success) {
        if (success) {
            System.out.println("Congratulations! You caught the Pokémon.");
            System.out.println();
        } else {
            System.out.println("Failed to catch the Pokémon. Better luck next time.");
            System.out.println();
        }
    }

    // New methods for battle messages
    public static void displayBattleStart() {
        displayMessage("Battle Start! Get ready to fight!");
        System.out.println();
    }
    
    // Method to display moves and get user choice
    public static Moves displaySelectMove(Pokemon pokemon) {
        displaySeparator();
        displaySelectMove();
        System.out.printf("1. %s%n", pokemon.getMove1().getMove_name());
        System.out.printf("2. %s%n", pokemon.getMove2().getMove_name());
        displaySeparator();
        System.out.print("Enter the number of the move you want to use: ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            return pokemon.getMove1();
        } else if (choice == 2) {
            return pokemon.getMove2();
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
            return displaySelectMove(pokemon);
        }
    }


    public static void displayFullAttack(Moves move, Pokemon opponentPokemon) {
        displayAttack(move);
        displaySeparator();
        displayHealthBar("Current Enemy Pokémon", opponentPokemon.getCurrent_hp(), opponentPokemon.getHp_max());
        System.out.println();
    }

    public static void displayAttack(Moves move) {
        displayMessage(move.user.getTeam() + " " + move.user.getName() + " uses " + move.getMove_name() + "!");
        System.out.println();
    }

    public static void displayPlayerTurn() {
        displayMessage("It's your turn! Choose your action.");
        System.out.println();
    }

    public static void displaySelectMove() {
        displayMessage("Select a move for your Pokémon.");
        System.out.println();
    }

    public static void displayOpponentTurn() {
        displayMessage("Opponent's turn. Please wait...");
        System.out.println();
    }

    public static void displaySuperEffective() {
        displayMessage("It's super effective!");
        System.out.println();
    }

    public static void displayNotEffective() {
        displayMessage("It's not very effective...");
        System.out.println();
    }


    public static void displayStatusChange(Pokemon poke, String status) {
        displayMessage(poke.getTeam() + " " + poke.getName() + " is now " + status + "!");
        System.out.println();
    }
    
    public static void displayAction(Pokemon poke, String action) {
        displayMessage(poke.getTeam() + " " + poke.getName() + " " + action + "!");
        System.out.println();
    }

    public static void displayPokemonDefeated(Pokemon pokemon) {
        displaySeparator();
        System.out.printf("%s %s is defeated!%n", pokemon.getTeam(), pokemon.getName());
        displaySeparator();
    }
    public static void displayVictory() {
        displayMessage("You won the battle!");
        System.out.println();
    }

    public static void displayDefeat() {
        displayMessage("You lost the battle. Better luck next time.");
        System.out.println();
    }

    //show this when HP is red, which is 20%
    public static void displayLowHPWarning() {
        displayMessage("Warning: Your Pokémon's HP is low!");
        System.out.println();
    }

    // Method to display the game end message
    public static void displayGameEnd() {
        displayMessage("Thank you for playing the Pokémon Ga-Ole Game!");
        System.out.println();
    }
}

    
