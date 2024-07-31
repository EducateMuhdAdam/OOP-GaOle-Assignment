package src;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Status {
	public Pokemon user;
	private ArrayList<Status> status_list = new ArrayList<Status>();
	private int endon;

	
	
	
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

	public int getEndon() {
		return endon;
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
		CheckTurnStatus();
	}

	public void CheckTurnStatus(){
		ArrayList<Status> status_end= new ArrayList<Status>();
		for (Status s: status_list){
			if (s.getEndon() <= gameMaster.getTurnCounter()){
				status_end.add(s);
			}
		}
		for (Status s: status_end){
			System.out.println("Status Removed");
			this.RemoveStatus(s);
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

		public int getEndon(){
			return endon;
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
		public int getEndon(){
			return endon;
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
		public int getEndon(){
			return endon;
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
				UI.displayAction(user, "hit itself in its confusion");
				gameMaster.skipTurn(user);
			}
			
		}
		public int getEndon(){
			return endon;
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
			UI.displayAction(user, "too frozen to move!");
			gameMaster.skipTurn(user);
		}
		public int getEndon(){
			return endon;
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
				UI.displayAction(user, "is fully paralyzed");
				gameMaster.skipTurn(user);
			}
		}
		@Override
		public double spdMult() {
			return 0.5;
		}
		public int getEndon(){
			return endon;
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
			UI.displayAction(user, "is fast asleep");
			gameMaster.skipTurn(user);
		}
		public int getEndon(){
			return endon;
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
			UI.displayAction(user, "is recovering from flinching");
			gameMaster.skipTurn(user);
		}
		public int getEndon(){
			return endon;
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
		public int getEndon(){
			return endon;
		}
	}
	
	class DmgBuff extends Status{
		int endon;
		public DmgBuff(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter();
			UI.displayAction(user, "is now stronger");
		}
		@Override
		public double dmgMult() {
			return 2;
		}
		public int getEndon(){
			return endon;
		}
	}
	
	class Fly extends Status{
		int endon;
		Moves move;
		public Fly(Pokemon user) {
			super(user);
			endon = gameMaster.getTurnCounter() + 1;
			for (int i = 1; i <= gameMaster.getMainlog().size();i++){
				if (i == gameMaster.getTurnCounter()){
					for (Moves m : gameMaster.getMainlog().get(i)){
						if (m.user.equals(this.user)) move = m;
					}
				}

			}

			UI.displayAction(user, "flew up in the air");
		}
		@Override
		public boolean PreventAttack() {
			return true;
		}
		@Override
		public void Endstep(){
				gameMaster.addTurn(move);
		}

		public int getEndon(){
			return endon;
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
		public int getEndon(){
			return endon;
		}
	}
	
	class Recharge extends Status{
		int endon;
		Moves move;
		public Recharge(Pokemon user) {
			super(user);
			for (int i = 1; i <= gameMaster.getMainlog().size();i++){
				if (i == gameMaster.getTurnCounter()){
					for (Moves m : gameMaster.getMainlog().get(i)){
						if (m.user.equals(this.user)) move = m;
					}
				}

			}
			endon = gameMaster.getTurnCounter() + 1;
		}
		@Override
		public void Upkeep() {
			UI.displayAction(user, "is recharging...");
			gameMaster.skipTurn(user);
		}
		@Override
		public void Endstep(){
			gameMaster.addTurn(move);
		}
		public int getEndon(){
			return endon;
		}
	}
