import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

public class Moves{
	Pokemon user;
	private int move_ID, power, priority;
	private String type, move_name;
	
	public Moves(Pokemon user, int move_ID){
		this.move_ID = move_ID;
		this.user = user;
		
		ArrayList<String[]> move_data = new ArrayList<String[]>();
		
		try {
			File textfile = new File(System.getProperty("user.dir") + "\\src\\movelist.txt");
			Scanner data = new Scanner(textfile);
			while (data.hasNextLine()) {
				move_data.add(data.nextLine().split(" ", 0));
			}
			data.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Move File Can't Be Read");
		}
		
		for (String[] moveLog : move_data) {
			if (Integer.parseInt(moveLog[0]) == move_ID) {
				move_name = moveLog[1];
				type = moveLog[2];
				power = Integer.parseInt(moveLog[3]);
				priority = Integer.parseInt(moveLog[4]);
			}
		}
	}
	public void Quick_Attack(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy)); 
	}
	
	private int CalculateDmg(Pokemon enemy) {
		return power * user.atk/enemy.def;
	}
	
	public String toString() {
		return String.format("Move ID: %d, Name: %s, Type: %s, Power: %d, Priority: %d", move_ID, move_name, type, power, priority);
	}
			
	
	
	
	
}
