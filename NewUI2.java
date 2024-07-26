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
        System.out.printf("Move 1: %s\n", pokemon.getMove1().getMove_name());
        System.out.printf("Move 2: %s\n", pokemon.getMove2().getMove_name());
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

    public void displayCatchOptions(List<Pokemon> catchOptions) {
        displaySeparator();
        System.out.println("Choose a Pokémon to catch:");
        for (int i = 0; i < catchOptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, catchOptions.get(i).getName());
        }
        displaySeparator();
        System.out.print("Enter the number of the Pokémon you want to catch: ");
        System.out.println();
    }

    public int getCatchChoice() {
        return scanner.nextInt();
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

    // New methods for battle messages
    public void displayBattleStart() {
        displayMessage("Battle Start! Get ready to fight!");
        System.out.println();
    }
    
    // Method to display moves and get user choice
    public Moves displaySelectMove(Pokemon pokemon) {
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

    public void displayPokemonDefeated(Pokemon pokemon) {
        displaySeparator();
        System.out.printf("%s is defeated!%n", pokemon.getName());
        displaySeparator();
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
        // Create some sample data for testing
        String[] types1 = {"Fire", "Flying"};
        String[] types2 = {"Electric"};
        String[] types3 = {"Water"};
        String[] types4 = {"Grass", "Poison"};
        String[] types5 = {"Dragon", "Flying"};
        
        // Creating sample Pokémon objects
        Pokemon charizard = new Pokemon(1, "Charizard", types1, 78, 84, 78, 100, 100, "None", "FR1", "FR2");
        Pokemon pikachu = new Pokemon(2, "Pikachu", types2, 35, 55, 40, 90, 100, "None", "ET1", "ET2");
        Pokemon blastoise = new Pokemon(3, "Blastoise", types3, 79, 83, 100, 78, 100, "None", "WT1", "WT2");
        Pokemon venusaur = new Pokemon(4, "Venusaur", types4, 80, 82, 83, 80, 100, "None", "GS1", "GS2");
        
        
        // Creating a list of sample Pokémon
        List<Pokemon> samplePokemon = new ArrayList<>();
        samplePokemon.add(charizard);
        samplePokemon.add(pikachu);
        samplePokemon.add(blastoise);
        samplePokemon.add(venusaur);
        
        
        // Set current HP for each Pokémon
        charizard.setCurrent_hp(80);
        pikachu.setCurrent_hp(18);
        blastoise.setCurrent_hp(79);
        venusaur.setCurrent_hp(80);
        

        Player player = new Player("Ash", 1000, new ArrayList<>(List.of("Charizard")));
        List<Pokemon> allPokemons = new ArrayList<>(samplePokemon);

        Moves Move1 = new Moves(samplePokemon.get(0), "Flamethrower");
        Moves Move2 = new Moves(samplePokemon.get(0),"Fly");

        Pokemon opponent = new Pokemon(2, "Bulbasaur", types4, 45, 49, 49, 45, 100, "None", "GS1", "GS2");
        opponent.setCurrent_hp(15);

        //test user input name
        //gamemaster or main will say this
        String playerName = ui.getPlayerInput("Enter your name: ");
        ui.displayMessage("Hello, " + playerName + "!");

        
        ui.displayGameStart();
        
        ui.displayLoadingData();
        ui.displayGameSetup();

        ui.displayCatchOptions(allPokemons);
       
        ui.getCatchChoice();
        //test if pokemon caught
        //gamemaster or main will say this
        ui.displayCatchResult(true);
        
        // Test if pokemon display works
        System.out.println("====================================================");
        System.out.println("                  Player's Pokémon                  ");
        System.out.println("====================================================");
        ui.displayPokemonDetails(samplePokemon.get(0));
        
        
        //ui.displayBattleStatus(player, allPokemons, opponent);

        
        ui.displayBattleStart();
        
        // Test move selection
        Moves selectedMove = ui.displaySelectMove(charizard);
        ui.displayMessage("You selected: " + selectedMove.getMove_name());

        ui.displaySuperEffective();
        
        //An example of choosing a pokemon from the samplepokemon list
        ui.displayPlayerAttack(samplePokemon.get(1), opponent);
        ui.displayOpponentAttack(samplePokemon.get(1), opponent);

        ui.displayNotEffective();
        
        ui.displayStatusChange("paralyzed");
        ui.displayOpponentStatusChange("asleep");

        ui.displayPokemonDefeated(opponent);
        // Simulate low HP warning
        ui.displayLowHPWarning();

        // Simulate battle end
        ui.displayVictory();
        ui.displayGameEnd();

    }
}
