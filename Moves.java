import java.util.*;
import java.util.function.*;
import java.io.*;
import java.lang.Math;

public class Moves{
	Pokemon user;
	private int move_ID, power, priority;
	private String type, move_name;
	private boolean doesDamage = false;
	private Consumer<Pokemon> instruction = t -> {}; //dummy instruction, waiting assignment
	
	
	public Moves(Pokemon user, int move_ID){
		this.move_ID = move_ID;
		this.user = user;
		instruction = getInstruction();		
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
				if ("yes".equals(moveLog[3].toLowerCase())) doesDamage = true;
				power = Integer.parseInt(moveLog[4]);
				priority = Integer.parseInt(moveLog[5]);
			}
		}
	}
	private void Quick_Attack(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy)); 
	}
	
	private void Growl(Pokemon enemy) {
		enemy.decreaseAttack(1);
	}
	
	private void Flamethrower(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
		if (Math.random() <= 0.1) {
			enemy.addStatus("burn");
		}
	}
	
	
	private void Flame_Charge(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
		user.increaseSpeed(1);
	}
	
	private void Hydro_Pump(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
	}
	
	private void Leaf_Blade(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
	}
	
	private void Thunderbolt(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
		if (Math.random() <= 0.1) {
			enemy.addStatus("paralyzed");
		}
	}
	
	private void Ice_Punch(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
		if (Math.random() <= 0.1) {
			enemy.addStatus("frozen");
		}
	}
	
	private void Ice_Fang(Pokemon enemy) {
		enemy.takeDamage(CalculateDmg(enemy));
		if (Math.random() <= 0.1) {
			enemy.addStatus("paralyzed");
		}
		if (Math.random() <= 0.1) {
			enemy.addStatus("flinched");
		}
	}
	
	public void useOn(Pokemon enemy) {
		instruction.accept(enemy);
	}
	
	private Consumer<Pokemon> getInstruction(){
		switch (this.move_ID) {
		case 2: return this::Growl;
		//3 Protect
		case 4: return this::Flamethrower;
		case 5: return this::Flame_Charge;
		case 6: return this::Hydro_Pump;
		//7 Hydro Canon
		//8 Leech Seed
		case 9: return this::Leaf_Blade;
		case 10: return this::Thunderbolt;
		case 11: return this::Ice_Punch;
		case 12: return this::Ice_Fang;
		default: return this::Quick_Attack;
		}
	}
	private int CalculateDmg(Pokemon enemy) {
		return power * user.getAtk()/enemy.getDef();
	}
	public String toString() {
		return String.format("Move ID: %d, Name: %s, Type: %s, Power: %d, Priority: %d", move_ID, move_name, type, power, priority);
	}
			
	
	
	
	
}
