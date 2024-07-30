package src;

import java.util.*;
import java.util.function.*;
import java.io.*;
import java.lang.Math;

public class Moves{
	Pokemon user;
	private int power, accuracy, priority, stage_accuracy;
	private String  move_ID, move_name, move_action;
	private ArrayList<BiConsumer<Moves, Pokemon>> inst = new ArrayList<BiConsumer<Moves, Pokemon>>();
	private Type type;
	
	
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
	
	//TO-DO Reformat the accuracy for stages
	
	private void initInstruction(String moveID) {
		switch(moveID){
			case "NM1": //self
				{Collections.addAll(inst, 
						Instruction.ApplyEnemyStatus(Status::Protect)); //Check for reoccurence
			break;}
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus(Status::Flinch))
						);
				break;} 
			case "FR1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Burn))
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Burn))
						); 
				break;} 
			case "FR4":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Burn)),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Flinch))
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
						Instruction.Attack(),
						Instruction.ApplyUserStatus(Status::Recharge)); 
				break;}
			case "WT3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.2), Instruction.ApplyEnemyStatus(Status::Confuse))
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus(Status::Flinch))
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Paralyze))
						); 
				break;}
			case "ET2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ApplyEnemyStatus(Status::Paralyze)
						); 
				break;}
			case "ET3":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus(Status::Paralyze))
						); 
				break;}
			case "IC1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Freeze))
						); 
				break;} //Freeze 10%
			case "IC2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Freeze)),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Flinch))
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus(Status::Poison))
						); //Poison 30%
				break;}
			case "PS2":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.5), Instruction.ApplyEnemyStatus(Status::BPoison))); //BPoison 50%
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
			case "FL1": //self
				{Collections.addAll(inst, 
						Instruction.ApplyUserStatus(Status::SpdBuff));
				break;}
			case "FL2":
				{Collections.addAll(inst, 
						Instruction.ExecuteIf(Instruction.UserStatusCondition(Fly.class), Instruction.Attack(), Instruction.ApplyUserStatus(Status::Fly))); //T1 Fly Then Attacks T2
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Confuse))
						);
				break;}
			case "PC2": //self
				{Collections.addAll(inst, 
						Instruction.MaxUserHealth(),
						Instruction.ApplyUserStatus(Status::Sleep)
						); 
				break;}
			case "PC3":
				{Collections.addAll(inst, 
						Instruction.ApplyEnemyStatus(Status::Sleep)); 
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Confuse))
						);
				break;}
			case "BG3":
				{Collections.addAll(inst, 
						Instruction.Attack(2)); 
				break;}
			case "RC1":
				{Collections.addAll(inst, 
						Instruction.Attack(),
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.1), Instruction.ApplyEnemyStatus(Status::Flinch))
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
						Instruction.ExecuteIf(Instruction.EnemyStatusCondition(Sleep.class), Instruction.SplitUserHealth(0.25))); //if sleep
				break;}
			case "DG1": //self
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
						Instruction.ExecuteIf(Instruction.ChanceCondition(0.3), Instruction.ApplyEnemyStatus(Status::Flinch))
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
						Instruction.ChangeUserDefence(2)); //self
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
		UI.displayAttack(this);
		if (this.getCurrentAccuracy() >= (Math.random() * 100) + 1)
			for (int i=0;i < inst.size();i++) {
				inst.get(i).accept(this, enemy);
			}
		else UI.displayAction(user, "missed");
	}
	
	
	
	public int getPower() {
		return power;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public Type getType() {
		return type;
	}
	
	
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getMove_action() {
		return move_action;
	}

	public void setMove_action(String move_action) {
		this.move_action = move_action;
	}

	public void setPower(int power) {
		this.power = power;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getCurrentAccuracy() {
		return (int)(accuracy * CalculateStageAccuracy(stage_accuracy));
	}
	
	public void changeAccuracy(int num) {
		stage_accuracy += num;
	}

	public int CalculateDmg(Moves move,Pokemon enemy) {
		return (int)(power * user.getCurrent_attack()/enemy.getCurrent_defense() * user.getStatus().getdmgMult() * Type.CheckTypeMult(move, enemy));
	}
	
	public static int CalculateDmg(int power, Pokemon user, Pokemon enemy) {
		return power * user.getCurrent_attack()/enemy.getCurrent_defense();
	}
	
	public static double CalculateStageAccuracy(int stage) {
		return stage >= 0 ? (3.0 + stage) / 3.0 : 3.0 / (3.0 - stage);
	}
	
	private Type CheckType(String moveID) {
		switch (moveID.toLowerCase().substring(0, 2)){
		case "nm": return Type.Normal;
		case "fr": return Type.Fire;
		case "wt": return Type.Water;
		case "gs": return Type.Grass;
		case "et": return Type.Electric;
		case "ic": return Type.Ice;
		case "ft": return Type.Fighting;
		case "ps": return Type.Poison;
		case "gd": return Type.Ground;
		case "fl": return Type.Flying;
		case "pc": return Type.Psychic;
		case "bg": return Type.Bug;
		case "rc": return Type.Rock;
		case "gh": return Type.Ghost;
		case "dg": return Type.Dragon;
		case "dr": return Type.Dark;
		case "st": return Type.Steel;
		case "fy": return Type.Fairy;
		default: {
			System.out.println("Type Error, Defaulting to Normal");
			return Type.Normal;
		}
		}
	}
			
	
	
	
	
}
