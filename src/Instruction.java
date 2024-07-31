package src;

 import java.util.function.*;

public class Instruction {
	
	static public BiConsumer<Moves, Pokemon> Attack(int hits) {
		return( 		
				(move, enemy) -> {	
					for (int i=0;i < hits;i++) {
						enemy.takeDamage(move.CalculateDmg(move, enemy)); 
					}
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> Attack() {
		return( 		
				(move, enemy) -> {	
					enemy.takeDamage(move.CalculateDmg(move, enemy)); 
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
	
	static public BiConsumer<Moves, Pokemon> ChangeUserDefence(int diff) {
		return( 		
				(move, enemy) -> {	
					enemy.changeDefence(diff);
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
						UI.displayMessage(UI.colorFont("Hit!", UI.GREEN));
						enemy.takeDamage(move.CalculateDmg(move, enemy));
						move.setPower(move.getPower() + inc);
					}
					UI.displayMessage(UI.colorFont(String.format("It hit %d time(s)!", hits), UI.YELLOW));
					move.setPower(initPower);
				}
		);
	}
	
	
	
	static public BiConsumer<Moves, Pokemon> Drain(double perc) {
		return( 		
				(move, enemy) -> {	
					int damage = move.CalculateDmg(move, enemy);
					enemy.takeDamage(damage); 
					move.user.setCurrent_hp(move.user.getCurrent_hp() + (int)(damage * perc));
					UI.displayMessage("it drained some of its health!");
					UI.displayHealthBar(move.user);
						}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> SplitUserHealth(double perc) {
		return( 		
				(move, enemy) -> {	
					move.user.takeDamage((int)(move.user.getHp_max() * perc));
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ApplyEnemyStatus(Consumer<Status> ail) {
		return (move, enemy) -> {enemy.getStatus().AddStatus(ail);};
	}
	
	static public BiConsumer<Moves, Pokemon> ApplyUserStatus(Consumer<Status> ail) {
		return (move, enemy) -> {move.user.getStatus().AddStatus(ail);};
	}
	static public BiConsumer<Moves, Pokemon> RemoveUserStatus(Class<Status> ail) {
		return (move, enemy) -> {move.user.getStatus().RemoveStatus(ail);};
	}
	static public BiConsumer<Moves, Pokemon> ApplyUserStatusAction(Consumer<Status> ail, String act) {
		return (move, enemy) -> {move.user.getStatus().AddStatus(ail);
								 move.setMove_action(act);};
	}
	
	static public BiConsumer<Moves, Pokemon> MaxUserHealth() {
		return( 		
				(move, enemy) -> {	
					move.user.setCurrent_hp(move.user.getHp_max());
					UI.displayMessage("it restored it health to full!");
					UI.displayHealthBar(move.user);
				}
		);
	}
	static public BiConsumer<Moves, Pokemon> ChangeMoveAction(String s) {
		return(
				(move, enemy) -> {
					move.setMove_action(s);
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
	
	static public BiConsumer<Moves, Pokemon> ExecuteIf(BiFunction<Moves, Pokemon, Boolean> cond, BiConsumer<Moves, Pokemon> success) {
		return( 		
				(move, enemy) -> {	
					if (cond.apply(move, enemy)) success.accept(move, enemy);
				}
		);
	}
	
	static public BiConsumer<Moves, Pokemon> ExecuteIf(BiFunction<Moves, Pokemon, Boolean> cond, BiConsumer<Moves, Pokemon> success, BiConsumer<Moves, Pokemon> fail) {
		return( 		
				(move, enemy) -> {	
					if (cond.apply(move, enemy)) success.accept(move, enemy);
					else fail.accept(move, enemy);
				}
		);
	}
	static public BiConsumer<Moves, Pokemon> ExecuteIfNot(BiFunction<Moves, Pokemon, Boolean> cond, BiConsumer<Moves, Pokemon> success) {
		return(
				(move, enemy) -> {
					if (!cond.apply(move, enemy)) success.accept(move, enemy);
				}
		);
	}
	
	static public boolean ChanceCondition(double perc) {
		if (Math.random() <= perc) return true;
		else return false;
	}
	
	static public BiFunction<Moves, Pokemon, Boolean> EnemyStatusCondition(Class<?> ail) {
		return ( 
				(move, enemy) -> {
					for (Status s: enemy.getStatus().getStatusList()) {
						if (ail.isInstance(s)) return true;
					}
					return false;
				}
		);
	}
	
	static public BiFunction<Moves, Pokemon, Boolean> UserStatusCondition(Class<?> ail) {
		return ( 
				(move, enemy) -> {
					for (Status s: move.user.getStatus().getStatusList()) {
						if (ail.isInstance(s)) return true;
					}
					return false;
				}
		);
	}

	static public BiFunction<Moves, Pokemon, Boolean> UserAttackedCondition() {
		return (
				(move, enemy) -> {
					for (Moves m: gameMaster.getMainlog().get(gameMaster.getTurnCounter())){
						if (m.user.getTeam() != move.user.getTeam() && m.getMove_action().equalsIgnoreCase("self"))
							return true;
					}
					return false;
				}
		);
	}
	
}
	