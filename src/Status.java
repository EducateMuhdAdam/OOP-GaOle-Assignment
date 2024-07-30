package src;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Status {
	public Pokemon user;
	public ArrayList<Status> status_list = new ArrayList<Status>();

	
	
	
	public Status(Pokemon user) {
		this.user = user;
	}
	
	
	//Initiate Child------------------------------------
	public void Burn() {
		status_list.add(new Burn(user));
	}
	
	public void Poison() {
		status_list.add(new Poison(user));
	}
	
	public void BPoison() {
		status_list.add(new BPoison(user));
	}
	
	public void Confuse() {
		status_list.add(new Confuse(user));
	}
	
	public void Freeze() {
		status_list.add(new Freeze(user));
	}
	
	public void Paralyze() {
		status_list.add(new Paralyze(user));
	}
	
	public void Sleep() {
		status_list.add(new Sleep(user));
	}
	
	public void Flinch() {
		status_list.add(new Flinch(user));
	}
	
	public void SpdBuff() {
		status_list.add(new SpdBuff(user));
	}
	public void DmgBuff() {
		status_list.add(new DmgBuff(user));
	}
	public void Fly() {
		status_list.add(new Fly(user));
	}
	public void Protect() {
		status_list.add(new Protect(user));
	}
	public void Recharge() {
		status_list.add(new Recharge(user));
	}
	
	//--------------------------------------------------
	
	//Default Child Methods-----------------------------
	
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
	
	public boolean PreventAttack() {
		return false;
	}
	//--------------------------------------------------
	
	//Status Class Methods------------------------------
	
	public void AddStatus(Consumer<Status> s) {
		s.accept(this);
	}
	
	public void RemoveStatus(Status s) {
		for (int i = 0; i < status_list.size(); i++) {
			if (status_list.get(i).equals(s)) {
				status_list.remove(i);
			}
		}
	}
	
	public void RemoveStatus(Class<?> ail) {
		for (int i = 0; i < status_list.size(); i++) {
			if (ail.isInstance(status_list.get(i))) {
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

	public boolean CheckPrevention() {
		for (Status s: status_list) {
			if (s.PreventAttack()) return true;
		}
		return false;
	}
	
	public ArrayList<Status> getStatusList() {
		return status_list;
	}
	
	//--------------------------------------------------
}

// ---------------------- Child Classes ------------------------------------
	class Burn extends Status{
		int endon;
		public Burn(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayStatusChange(user, "Burning");
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
		int endon;
		public Poison(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayStatusChange(user, "Poisoned");
		}
		@Override
		public void Endstep() {
			user.takeDamage(user.getHp_max() / 16);
			
		}
	}
	
	class BPoison extends Status{
		int endon;
		int inc;
		public BPoison(Pokemon user) {
			super(user);
			inc = 0;
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayStatusChange(user, "Badly Poisoned");
		}
		@Override
		public void Endstep() {
			inc += 1;
			user.takeDamage(user.getHp_max() * inc/ 16);
			
		}
	}
	
	class Confuse extends Status{
		int endon;
		public Confuse(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayStatusChange(user, "Confused");
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
		int endon;
		public Freeze(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 999;
			UI.displayStatusChange(user, "Frozen");
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
		int endon;
		public Paralyze(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayStatusChange(user, "Paralyzed");
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
	
	class Sleep extends Status{
		int endon;
		public Sleep(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayStatusChange(user, "Sleeping");
		}
		@Override
		public void Upkeep() {
			//skip user's turn
			System.out.println("skip turn");
		}
	}
	
	class Flinch extends Status{
		int endon;
		public Flinch(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter();
			UI.displayAction(user, "Flinched");
		}
		@Override
		public void Upkeep() {
			//skip user's turn
			System.out.println("skip turn");
		}
	}
	
	class SpdBuff extends Status{
		int endon;
		public SpdBuff(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayAction(user, "is now faster");
		}
		@Override
		public double spdMult() {
			return 2;
		}
	}
	
	class DmgBuff extends Status{
		int endon;
		public DmgBuff(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 4;
			UI.displayAction(user, "is now stronger");
		}
		@Override
		public double dmgMult() {
			return 2;
		}
	}
	
	class Fly extends Status{
		int endon;
		public Fly(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 1;
			UI.displayAction(user, "flew up in the air");
		}
		@Override
		public boolean PreventAttack() {
			return true;
		}
	}
	
	class Protect extends Status{
		int endon;
		public Protect(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter();
			UI.displayAction(user, "protected itself");
		}
		@Override
		public boolean PreventAttack() {
			return true;
		}
	}
	
	class Recharge extends Status{
		int endon;
		public Recharge(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 1;
			UI.displayAction(user, "is recharging");
		}
		@Override
		public void Upkeep() {
			//skip turn
		}
	}
