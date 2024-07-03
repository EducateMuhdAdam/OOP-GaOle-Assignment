public class UI {
    // Display the main menu
    public void displayMainMenu() {
        System.out.println("Welcome to Pokémon Ga-Ole!");
        System.out.println("1. Start Game");
        System.out.println("2. View Top Scores");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        UI ui = new UI();
        ui.displayMainMenu();
    }
}
