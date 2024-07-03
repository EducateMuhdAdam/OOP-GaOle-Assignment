import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Scanner;

public class gameMaster {
	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		int userChoice;

		userChoice = displayMenu(input);	
		switch(userChoice) {
			case 1:
				//LocalDateTime start = LocalDateTime.now();
				//format the currentTime object to a readable string
				//DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				//LocalDateTime end = LocalDateTime.now();
				break;
				
			case 2:
				System.out.println("2. Options");
				break;
			case 3:
				System.out.println("3. Quit");
				break;
			}
		input.close();
	}
		
			
	private static int displayMenu(Scanner input) {
	
		System.out.println("-------------------");
		System.out.printf("|1) PLAY%10s|\n","");
		System.out.printf("|2) OPTIONS%7s|\n","");
		System.out.printf("|1) QUIT%10s|\n","");
		System.out.println("-------------------");
		System.out.println("Make a selection: ");
		
		return input.nextInt();
	}	
}
