import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewUI2 {
    private Scanner scanner;

    public NewUI2() {
        this.scanner = new Scanner(System.in);
    }

    private void displaySeparator() {
        System.out.println("====================================================");
    }
    //After you catch a pokemon, you can display this
    public void displayPokemonDetails(Pokemon pokemon) {
        displaySeparator();
        System.out.println("                   Pokémon Details                  ");
        displaySeparator();
        System.out.printf("ID: %d\n", pokemon.getPokemon_id());
        System.out.printf("Name: %s\n", pokemon.getName());
        System.out.printf("Type: %s\n", String.join(", ", pokemon.getType()));
        System.out.printf("HP: %d\n", pokemon.getHp_max());
        System.out.printf("Attack: %d\n", pokemon.getAttack());
        System.out.printf("Defense: %d\n", pokemon.getDefense());
        System.out.printf("Speed: %d\n", pokemon.getMax_speed());
        System.out.printf("Move 1: %s\n", pokemon.getMove1());
        System.out.printf("Move 2: %s\n", pokemon.getMove2());
        displaySeparator();
        System.out.println();
    }    
    
    // Method to display health bar
    private void displayHealthBar(String label, int currentHP, int maxHP) {
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

    public void displayCatchResult(boolean success) {
        if (success) {
            System.out.println("Congratulations! You caught the Pokémon.");
            System.out.println();
        } else {
            System.out.println("Failed to catch the Pokémon. Better luck next time.");
            System.out.println();
        }
    }

    public String getPlayerInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
    

    // Method to display the game start message
    public void displayGameStart() {
        displayMessage("Welcome to the Pokémon Ga-Ole Game!");
        System.out.println();
    }

    public void displayGameSetup() {
        displayMessage("Setting up the game...");
        System.out.println();
    }

    public void displayLoadingData() {
        displayMessage("Loading Pokémon data...");
        System.out.println();
    }

    // New methods for battle messages
    public void displayBattleStart() {
        displayMessage("Battle Start! Get ready to fight!");
        System.out.println();
    }
    
    public void displayPlayerAttack(Pokemon playerPokemon, Pokemon opponentPokemon) {
        displayMessage("Your Pokémon attacks the opponent!");
        displaySeparator();
        displayHealthBar("Current Enemy Pokémon", opponentPokemon.getCurrent_hp(), opponentPokemon.getHp_max());
        System.out.println();
    }

    public void displayOpponentAttack(Pokemon playerPokemon, Pokemon opponentPokemon) {
        displayMessage("The opponent's Pokémon attacks your Pokémon!");
        displaySeparator();
        displayHealthBar("Current Pokémon", playerPokemon.getCurrent_hp(), playerPokemon.getHp_max());
        System.out.println();
    }

    public void displayPlayerAttack() {
        displayMessage("Your Pokémon attacks the opponent!");
        System.out.println();
    }

    public void displayOpponentAttack() {
        displayMessage("The opponent's Pokémon attacks your Pokémon!");
        System.out.println();
    }

    public void displayPlayerTurn() {
        displayMessage("It's your turn! Choose your action.");
        System.out.println();
    }

    public void displaySelectMove() {
        displayMessage("Select a move for your Pokémon.");
        System.out.println();
    }

    public void displayOpponentTurn() {
        displayMessage("Opponent's turn. Please wait...");
        System.out.println();
    }

    public void displaySuperEffective() {
        displayMessage("It's super effective!");
        System.out.println();
    }

    public void displayNotEffective() {
        displayMessage("It's not very effective...");
        System.out.println();
    }


    public void displayStatusChange(String status) {
        displayMessage("Your Pokémon is now " + status + "!");
        System.out.println();
    }

    public void displayOpponentStatusChange(String status) {
        displayMessage("Opponent's Pokémon is now " + status + "!");
        System.out.println();
    }

    public void displayVictory() {
        displayMessage("You won the battle!");
        System.out.println();
    }

    public void displayDefeat() {
        displayMessage("You lost the battle. Better luck next time.");
        System.out.println();
    }

    //show this when HP is red, which is 20%
    public void displayLowHPWarning() {
        displayMessage("Warning: Your Pokémon's HP is low!");
        System.out.println();
    }

    // Method to display the game end message
    public void displayGameEnd() {
        displayMessage("Thank you for playing the Pokémon Ga-Ole Game!");
        System.out.println();
    }


    //everything below will not be part of UI class
    //testing the code myself using temporary main
    public static void main(String[] args) {
        NewUI2 ui = new NewUI2();

        // Create some sample data for testing
        String[] types = {"Fire", "Flying"};
        Moves move1 = new Moves(null, "FR1");
        Moves move2 = new Moves(null, "FR2");
        Pokemon samplePokemon = new Pokemon(1, "Charizard", types, 78, 84, 78, 100, 100, "None", "FR1", "FR2");
        samplePokemon.setCurrent_hp(80);
        Player player = new Player("Ash", 1000, new ArrayList<>(List.of("Charizard")));
        List<Pokemon> allPokemons = new ArrayList<>(List.of(samplePokemon));
        Pokemon opponent = new Pokemon(2, "Bulbasaur", types, 45, 49, 49, 45, 100, "None", "GS1", "GS2");
        opponent.setCurrent_hp(50);

        //test user input name
        //gamemaster or main will say this
        String playerName = ui.getPlayerInput("Enter your name: ");
        ui.displayMessage("Hello, " + playerName + "!");

        ui.displayGameStart();
        ui.displayLoadingData();
        ui.displayGameSetup();

        //test if pokemon caught
        //gamemaster or main will say this
        ui.displayCatchResult(true);
        
        // Test if pokemon display works
        System.out.println("====================================================");
        System.out.println("                  Player's Pokémon                  ");
        System.out.println("====================================================");
        ui.displayPokemonDetails(samplePokemon);
        
        
        //ui.displayBattleStatus(player, allPokemons, opponent);

        
        ui.displayBattleStart();
    
        ui.displaySuperEffective();
        

        ui.displayPlayerAttack(samplePokemon, opponent);
        ui.displayOpponentAttack(samplePokemon, opponent);

        ui.displayNotEffective();
        
        ui.displayStatusChange("paralyzed");
        ui.displayOpponentStatusChange("asleep");

        // Simulate low HP warning
        ui.displayLowHPWarning();

        // Simulate battle end
        ui.displayVictory();
        ui.displayGameEnd();

    }
}
