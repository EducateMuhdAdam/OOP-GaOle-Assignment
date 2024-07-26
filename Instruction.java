import java.util.ArrayList;
import java.util.function.*;

public class Instruction {
	public ArrayList<BiConsumer<Moves, Pokemon>> queue = new ArrayList<BiConsumer<Moves, Pokemon>>();
	
	public void Run(Moves move, Pokemon enemy) {
		for (int i=0;i < queue.size();i++) {
			queue.get(i).accept(move , enemy);
		}
	}
	
	public void Attack(int hits) {
		queue.add( 		
				(move, enemy) -> {	
					for (int i=0;i < hits;i++) {
						System.out.println("HAIIIYAH");
						enemy.takeDamage(move.CalculateDmg(enemy)); 
					}
				}
		);
	}
	
	public void Attack() {
		queue.add( 		
				(move, enemy) -> {	
					enemy.takeDamage(move.CalculateDmg(enemy)); 
				}
		);
	}
	
	public void ChangeUserSpeed(int diff) {
		queue.add( 		
				(move, enemy) -> {	
					move.user.changeSpeed(diff);
						}
		);
	}
	
	public void ChangeEnemySpeed(int diff) {
		queue.add( 		
				(move, enemy) -> {	
					enemy.changeSpeed(diff);
						}
		);
	}
	
	public void MultiAttack(int inc) {
		queue.add( 		
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
	
	public void Drain(double perc) {
		queue.add( 		
				(move, enemy) -> {	
					int damage = move.CalculateDmg(enemy);
					enemy.takeDamage(damage); 
					move.user.setCurrent_hp(move.user.getCurrent_hp() + (int)(damage * perc));
						}
		);
	}
	
}
	