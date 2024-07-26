import java.util.ArrayList;
import java.util.function.*;

public class Instruction {
	
	static public BiConsumer<Moves, Pokemon> Attack(int hits) {
		return( 		
				(move, enemy) -> {	
					for (int i=0;i < hits;i++) {
						System.out.println("HAIIIYAH");
						enemy.takeDamage(move.CalculateDmg(enemy)); 
					}
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> Attack() {
		return( 		
				(move, enemy) -> {	
					enemy.takeDamage(move.CalculateDmg(enemy)); 
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeUserSpeed(int diff) {
		return( 		
				(move, enemy) -> {	
					move.user.changeSpeed(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeEnemySpeed(int diff) {
		return( 		
				(move, enemy) -> {	
					enemy.changeSpeed(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeUserAttack(int diff) {
		return( 		
				(move, enemy) -> {	
					move.user.changeAttack(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeEnemyAttack(int diff) {
		return( 		
				(move, enemy) -> {	
					enemy.changeAttack(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeEnemyDefence(int diff) {
		return( 		
				(move, enemy) -> {	
					enemy.changeDefence(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeEnemyAccuracy(int diff) {
		return( 		
				(move, enemy) -> {	
					enemy.getMove1().changeAccuracy(diff);
					enemy.getMove2().changeAccuracy(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ChangeUserDefence(int diff) {
		return( 		
				(move, enemy) -> {	
					enemy.changeDefence(diff);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> MultiAttack(int inc) { //Hits 2-5 times, 1/8 to hit 5 or 4 times, 3/8 to hit 3 or 2 times
		return( 		
				(move, enemy) -> {	
					int hits = 2;
					int randNm = (int)(Math.random() * 8);
					int initPower = move.getPower();
							
					if (randNm == 8) hits = 5;
					else if (randNm == 7) hits = 4;
					else if (randNm >= 4) hits = 3;
							
					for (int i=0;i < hits;i++) {
						System.out.println("Bonk");
						enemy.takeDamage(move.CalculateDmg(enemy));
						move.setPower(move.getPower() + inc);
					}
					move.setPower(initPower);
				}
		);
	}
	
	
	
	static public BiConsumer<Moves, Pokemon> Drain(double perc) {
		return( 		
				(move, enemy) -> {	
					int damage = move.CalculateDmg(enemy);
					enemy.takeDamage(damage); 
					move.user.setCurrent_hp(move.user.getCurrent_hp() + (int)(damage * perc));
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> SplitUserHealth(double perc) {
		return( 		
				(move, enemy) -> {	
					System.out.println("OUCH!");
					move.user.takeDamage((int)(move.user.getHp_max() * perc));
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ApplyEnemyStatus(String ail) {
		switch (ail.toLowerCase()) {
			case "brn": return (move, enemy) -> {enemy.getStatus().Burn();};
			case "psn": return (move, enemy) -> {enemy.getStatus().Poison();};
			case "cnf": return (move, enemy) -> {enemy.getStatus().Confuse();};
			case "frz": return (move, enemy) -> {enemy.getStatus().Freeze();};
			case "prl": return (move, enemy) -> {enemy.getStatus().Paralyze();};
			case "slp": return (move, enemy) -> {enemy.getStatus().Sleep();};
			case "flc": return (move, enemy) -> {enemy.getStatus().Flinch();};
			case "spd": return (move, enemy) -> {enemy.getStatus().SpdBuff();};
			default: {
				System.out.println("Error: Status Instruction not Applied");
				return (move, enemy) -> {};
			}
		}
	}
	
	static public BiConsumer<Moves, Pokemon> ApplyUserStatus(String ail) {
		switch (ail.toLowerCase()) {
			case "brn": return (move, enemy) -> {move.user.getStatus().Burn();};
			case "psn": return (move, enemy) -> {move.user.getStatus().Poison();};
			case "cnf": return (move, enemy) -> {move.user.getStatus().Confuse();};
			case "frz": return (move, enemy) -> {move.user.getStatus().Freeze();};
			case "prl": return (move, enemy) -> {move.user.getStatus().Paralyze();};
			case "slp": return (move, enemy) -> {move.user.getStatus().Sleep();};
			case "flc": return (move, enemy) -> {move.user.getStatus().Flinch();};
			case "spd": return (move, enemy) -> {move.user.getStatus().SpdBuff();};
			default: {
				System.out.println("Error: Status Instruction not Applied");
				return (move, enemy) -> {};
			}
		}
	}
	
	static public BiConsumer<Moves, Pokemon> MaxUserHealth() {
		return( 		
				(move, enemy) -> {	
					move.user.setCurrent_hp(move.user.getHp_max());
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ExecuteIf(boolean cond, BiConsumer<Moves, Pokemon> success, BiConsumer<Moves, Pokemon> fail) {
		return( 		
				(move, enemy) -> {	
					if (cond) success.accept(move, enemy);
					else fail.accept(move, enemy);
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ExecuteIf(boolean cond, BiConsumer<Moves, Pokemon> success) {
		return( 		
				(move, enemy) -> {	
					if (cond) success.accept(move, enemy);
				}
		);
	}
	
	static public boolean ChanceCondition(double perc) {
		if (Math.random() <= perc) return true;
		else return false;
	}
	
}
	