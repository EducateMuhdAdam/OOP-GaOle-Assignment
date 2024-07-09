import java.util.ArrayList;

public class Pokemon {
	private int hp, atk, spd, def, user_ID;
	public ArrayList<String> status = new ArrayList<String>();
	Moves move1;
	
	public Pokemon(int move_ID) {
		hp = 100;
		atk = 49;
		spd = 49;
		def = 49;
		user_ID = 1;
		move1 = new Moves(this, move_ID);
	}
	
	public void Attack1(Pokemon enemy) {
		move1.useOn(enemy);
	}
	
	
	
	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}

	public void decreaseSpeed(int spd) {
		this.spd -= spd;
	}
	
	public void increaseSpeed(int spd) {
		this.spd += spd;
	}
	
	public void decreaseAttack(int atk) {
		this.atk -= atk;
	}
	
	public void takeDamage(int dmg) {
		hp -= dmg;
	}
	
	public void addStatus(String status) {
		this.status.add(status);
	}
	
	public String toString() {
		return String.format("HP: %d Status:%s atk:%d", hp, status.toString(), atk);
	}
}
