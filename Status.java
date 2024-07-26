import java.util.ArrayList;

public class Status {
	public Pokemon user;
	public ArrayList<Status> status_list = new ArrayList<Status>();

	
	
	
	public Status(Pokemon user) {
		this.user = user;
	}
	
	public void Burn() {
		status_list.add(new Burn(user));
	}
	
	public void Poison() {
		status_list.add(new Poison(user));
	}
	
	public double dmgMult() {
		return 1;
	}
	
	public double spdMult() {
		return 1;
	}
	
	public void Upkeep() {
		
	}
	
	public void Endstep() {
		
	}
	
	public void RemoveStatus(Status s) {
		for (int i = 0; i < status_list.size(); i++) {
			if (status_list.get(i).equals(s)) {
				status_list.remove(i);
			}
		}
	}
	
	public void RemoveBurn() {
		for (int i = 0; i < status_list.size(); i++) {
			if (status_list.get(i) instanceof Burn) {
				status_list.remove(i);
			}
		}
	}
	
	public double getdmgMult() {
		double dmgMult = 1;
		for (Status s: status_list) {
			dmgMult *= s.dmgMult();
		}
		return dmgMult;
	}
	
	public double getspdMult() {
		double spdMult = 1;
		for (Status s: status_list) {
			spdMult *= s.spdMult();
		}
		return spdMult;
	}
	
	public void CheckUpkeep() {
		for (Status s: status_list) {
			s.Upkeep();
		}
	}
	
	public void CheckEndstep() {
		for (Status s: status_list) {
			s.Endstep();
		}
	}
}
	class Burn extends Status{

		public Burn(Pokemon user) {
			super(user);
			System.out.println("I am burning");
		}
		@Override
		public void Endstep() {
			user.takeDamage(user.getHp_max() / 16);
			
		}
		@Override
		public double dmgMult() {
			return 0.5;
		}
	
	}
	
	class Poison extends Status{
		public Poison(Pokemon user) {
			super(user);
			System.out.println("I am poisoned");
		}
		@Override
		public void Endstep() {
			user.takeDamage(user.getHp_max() / 16);
			
		}
	}
	
	class Confuse extends Status{
		public Confuse(Pokemon user) {
			super(user);
			System.out.println("I am confused");
		}
		@Override
		public void Upkeep() {
			if (Math.random() <= 0.5) {
				user.takeDamage(Moves.CalculateDmg(40, user, user));
				//skip user's turn
			}
			
		}
	}
	
	class Freeze extends Status{
		public Freeze(Pokemon user) {
			super(user);
			System.out.println("I am frozen");
		}
		@Override
		public void Upkeep() {
			if (Math.random() <= 0.2) {
				super.RemoveStatus(this);
			}
			//else skip user's turn
			System.out.println("skip turn");
		}
		
		
	}
	
	class Paralyze extends Status{
		public Paralyze(Pokemon user) {
			super(user);
			System.out.println("I am paralyzed");
		}
		@Override
		public void Upkeep() {
			if (Math.random() <= 0.5) {
				//skip user's turn
				System.out.println("skip turn");
			}
		}
		@Override
		public double spdMult() {
			return 0.5;
		}
	}
