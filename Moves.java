import java.util.*;
import java.util.function.*;
import java.io.*;
import java.lang.Math;

public class Moves{
	Pokemon user;
	private int power, accuracy, priority;
	private String  move_ID, type, move_name, move_action;
	private Consumer<Pokemon> instruction = t -> {}; //dummy instruction, waiting assignment
	
	
	public Moves(Pokemon user, String move_ID){
		this.move_ID = move_ID;
		this.user = user;
		instruction = getInstruction();		
		ArrayList<String[]> move_data = new ArrayList<String[]>();
		
		try {
			File textfile = new File(System.getProperty("user.dir") + "\\src\\movelist.csv");
			Scanner data = new Scanner(textfile);
			while (data.hasNextLine()) {
				move_data.add(data.nextLine().split(",", 0));
			}
			data.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Move File Can't Be Read");
		}
		for (String[] moveLog : move_data) {
			if (moveLog[0].equals(move_ID)) {
				move_name = moveLog[1];
				type = CheckType(this.move_ID);
				power = Integer.parseInt(moveLog[2]);
				accuracy = Integer.parseInt(moveLog[3]);
				move_action = moveLog[4];
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
		//3 Protect
		case "FR1": return this::Flamethrower;
		case "FR2": return this::Flame_Charge;
		case "WT1": return this::Hydro_Pump;
		//7 Hydro Canon
		//8 Leech Seed
		case "ET1": return this::Thunderbolt;
		case "IC1": return this::Ice_Punch;
		case "IC2": return this::Ice_Fang;
		default: return this::Quick_Attack;
		}
	}
	private int CalculateDmg(Pokemon enemy) {
		return power * user.getAtk()/enemy.getDef();
	}
	private String CheckType(String moveID) {
		switch (moveID.toLowerCase().substring(0, 2)){
		case "nm": return "Normal";
		case "fr": return "Fire";
		case "wt": return "Water";
		case "gs": return "Grass";
		case "et": return "Electric";
		case "ic": return "Ice";
		case "ft": return "Fighting";
		case "ps": return "Poison";
		case "gd": return "Ground";
		case "fl": return "Flying";
		case "pc": return "Psychic";
		case "bg": return "Bug";
		case "rc": return "Rock";
		case "gh": return "Ghost";
		case "dr": return "Dragon";
		case "st": return "Steel";
		case "fy": return "Fairy";
		default: return "Error";
		}
	}
	public String toString() {
		return String.format("Move ID: %s, Name: %s, Type: %s, Power: %d, Accuracy: %d,Priority: %d", move_ID, move_name, type, power, accuracy, priority);
	}
			
	
	
	
	
}
