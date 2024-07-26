import java.util.*;
import java.util.function.*;
import java.io.*;
import java.lang.Math;

public class Moves{
	Pokemon user;
	private int power, accuracy, priority;
	private String  move_ID, type, move_name, move_action;
	private ArrayList<BiConsumer<Moves, Pokemon>> inst = new ArrayList<BiConsumer<Moves, Pokemon>>();
	
	
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
	
	public String getMove_name() {
		return move_name;
	}
	
	private void initInstruction(String moveID) {
		switch(moveID){
			case "NM1": //Protect
				{break;}
			case "NM2": 
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "NM3":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "NM4":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus("flc"))
						);
				break;} 
			case "FR1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("brn"))
						);
				break;} 
			case "FR2":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ChangeUserSpeed(1)); 
				break;} 
			case "FR3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("brn"))
						); 
				break;} 
			case "FR4":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("brn")),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("flc"))
								); 
				break;}
			case "FR5":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "WT1":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "WT2":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;} //Recharge
			case "WT3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.2), Instruction.ApplyEnemyStatus("cnf"))
						); 
				break;}
			case "WT4":
				{Collections.addAll(inst, 
						Instruction.MultiAttack(15)); 
				break;}
			case "WT5":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ChangeEnemySpeed(-1)); 
				break;}
			case "WT6":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "WT7":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "GS1":
				{Collections.addAll(inst,
						Instruction.Drain(0.5)); 
				break;}
			case "GS2":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "GS3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus("flc"))
						);
				break;}
			case "GS4":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "GS5":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "GS6":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ChangeUserAttack(-2)); 
				break;}
			case "ET1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("prl"))
						); 
				break;}
			case "ET2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ApplyEnemyStatus("prl")
						); 
				break;}
			case "ET3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus("prl"))
						); 
				break;}
			case "IC1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("frz"))
						); 
				break;} //Freeze 10%
			case "IC2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("frz")),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("flc"))
						); 
				break;}
			case "FT1":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ChangeEnemySpeed(-1)); 
				break;} 
			case "FT2":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ChangeEnemyDefence(-1)); 
				break;}
			case "FT3":
				{Collections.addAll(inst, 
						Instruction.Attack(), 
						Instruction.ChangeUserSpeed(-1)); 
				break;}
			case "FT4":
				{Collections.addAll(inst, 
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.5), Instruction.Attack(), Instruction.SplitUserHealth(0.5)));
				break;}
			case "PS1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus("psn"))
						); //Poison 30%
				break;}
			case "PS2":
				{Collections.addAll(inst, 
						Instruction.Attack()); //BPoison 50%
				break;}
			case "GD1":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "GD2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ChangeEnemySpeed(-1)); 
				break;}
			case "GD3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ChangeEnemyAccuracy(-10)); 
				break;}
			case "FL1":
				{Collections.addAll(inst, 
						Instruction.ApplyUserStatus("spd"));
				break;}
			case "FL2":
				{Collections.addAll(inst, 
						null); //T1 Fly Then Attacks T2
				break;}
			case "FL3":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "FL4":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "PC1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("cnf"))
						);
				break;}
			case "PC2":
				{Collections.addAll(inst, 
						Instruction.MaxUserHealth(),
						Instruction.ApplyUserStatus("slp")
						); 
				break;}
			case "PC3":
				{Collections.addAll(inst, 
						Instruction.ApplyEnemyStatus("slp")); 
				break;}
			case "PC4":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ChangeEnemyDefence(-1)));
				break;}
			case "BG1":
				{Collections.addAll(inst, 
						Instruction.Drain(0.5)); 
				break;}
			case "BG2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("cnf"))
						);
				break;}
			case "BG3":
				{Collections.addAll(inst, 
						Instruction.Attack(2)); 
				break;}
			case "RC1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus("flc"))
						);
				break;}
			case "RC2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.5), Instruction.ChangeUserSpeed(2))); 
				break;}
			case "GH1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.2), Instruction.ChangeEnemyDefence(-1))); 
				break;}
			case "GH2":
				{Collections.addAll(inst, 
						Instruction.ExecuteIf(true, Instruction.SplitUserHealth(0.25))); //if sleep
				break;}
			case "DG1":
				{Collections.addAll(inst, 
						Instruction.ChangeUserAttack(2),
						Instruction.ChangeUserSpeed(2));
				break;}
			case "DG2":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "DR1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus("flc"))
						); 
				break;}
			case "DR2":
				{Collections.addAll(inst, 
						null); // Does double damage if user was attacked
				break;}
			case "DR3":
				{Collections.addAll(inst, 
						Instruction.Attack()); 
				break;}
			case "ST1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ChangeEnemyDefence(-1))); 
				break;}
			case "ST2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ChangeUserAttack(1))); 
				break;}
			case "ST3":
				{Collections.addAll(inst, 
						Instruction.ChangeUserDefence(2)); 
				break;}
			case "FY1":
				{Collections.addAll(inst, 
						Instruction.ChangeEnemyAttack(-1)); 
				break;}
			case "FY2":
				{Collections.addAll(inst, 
						Instruction.ChangeEnemyAttack(-2)); 
				break;}
			case "FY3":
				{Collections.addAll(inst, 
						Instruction.Drain(0.75)); 
				break;}
			default:
				break;
		}
	}
	
	
	public void UseOn(Pokemon enemy) {
		for (int i=0;i < inst.size();i++) {
			inst.get(i).accept(this, enemy);
		}
	}
	
	
	
	public int getPower() {
		return power;
	}
	
	public void changeAccuracy(int num) {
		accuracy += num;
	}
	
	public void setPower(int power) {
		this.power = power;
	}

	public int CalculateDmg(Pokemon enemy) {
		return (int)(power * user.getCurrent_attack()/enemy.getCurrent_defense() * user.getStatus().getdmgMult());
	}
	
	public static int CalculateDmg(int power, Pokemon user, Pokemon enemy) {
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
