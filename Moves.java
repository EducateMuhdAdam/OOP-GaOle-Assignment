import java.util.*;
import java.util.function.*;
import java.io.*;
import java.lang.Math;

public class Moves{
	Pokemon user;
	private int power, accuracy, priority;
	private String  move_ID, type, move_name, move_action;
	private Instruction instruction = new Instruction();
	
	
	public Moves(Pokemon user, String move_ID){
		this.move_ID = move_ID;
		this.user = user;
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
		initInstruction(move_ID);
	}
	
	private void initInstruction(String moveID) {
		switch(moveID){
			case "NM1": //Protect
				{break;}
			case "NM2": 
				{instruction.Attack(); break;}
			case "NM3":
				{instruction.Attack(); break;}
			case "NM4":
				{instruction.Attack(); break;} //Target 30% Flinch
			case "FR1":
				{instruction.Attack(); break;} //Target 10% burn
			case "FR2":
				{instruction.Attack(); instruction.ChangeUserSpeed(1); break;} 
			case "FR3":
				{instruction.Attack(); break;} //Target 10% burn
			case "FR4":
				{instruction.Attack(); break;} //Target 10% burn, 10% flinch
			case "FR5":
				{instruction.Attack(); break;}
			case "WT1":
				{instruction.Attack(); break;}
			case "WT2":
				{instruction.Attack(); break;} //Recharge
			case "WT3":
				{instruction.Attack(); break;} // 20% Confused
			case "WT4":
				{instruction.MultiAttack(15); break;}
			case "WT5":
				{instruction.Attack(); instruction.ChangeEnemySpeed(-1); break;}
			case "WT6":
				{instruction.Attack(); break;}
			case "WT7":
				{instruction.Attack(); break;}
			case "GS1":
				{instruction.Drain(0.5); break;}
			case "GS2":
				{instruction.Attack(); break;}
			case "GS3":
				{instruction.Attack(); break;} //Flinch 30%
			case "GS4":
				{instruction.Attack(); break;}
			case "GS":
				{ break;}
			default:
				break;
		}
	}
	
	
	public void UseOn(Pokemon enemy) {
		instruction.Run(this, enemy);
	}
	
	
	
	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int CalculateDmg(Pokemon enemy) {
		return power * user.getCurrent_attack()/enemy.getCurrent_defense();
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
