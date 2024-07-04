import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Scanner;



		public class gameMaster{
			
		
		public static void menu() {
	
		Scanner input = new Scanner (System.in);
		int userChoice = 0;
	
		while (userChoice != 3) {
			try {
				String [] mainMenu = {"PLAY", "OPTIONS", "QUIT"};
				menuGenerator(mainMenu);
				
				userChoice = input.nextInt();	
	
				switch(userChoice) {
					case 1:
						LocalDateTime start = LocalDateTime.now();
						//format the currentTime object to a readable string
						//DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
						//LocalDateTime end = LocalDateTime.now();
						System.out.println("---------------------");
						System.out.println("|GAME MODE SELECTION|"); 
						System.out.printf("|1) Battle and Catch|\n","");
						System.out.printf("|2) Catch Now%7s|\n","");
						System.out.printf("|3) Back%12s|\n","");
						System.out.println("---------------------");
						System.out.println("Make a selection: ");
						
						break;
						
					case 2:
						System.out.println("2. Options");
						
					case 3:
						System.out.println("3. Quit");
						
					}
			}
		
			catch(Exception e)
			{
				System.out.println("Only accepts integer as input\n");
				input.nextLine();
				continue;
			}
		}
		input.close();
	}
		
	private static void battleTime() {
		
		
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
		
}	
