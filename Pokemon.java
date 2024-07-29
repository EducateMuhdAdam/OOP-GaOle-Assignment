public class Pokemon {
    private int pokemon_id;
    private String name;
    private String[] type;
    private Team team;
    private int hp_max;
    private int attack;
    private int defense;
    private int current_hp;
    
    private int current_attack;
    private int current_defense;
    private int max_speed;
    private int current_speed;
    private Status status_effect;
    private Moves move1;
    private Moves move2;

    public Pokemon(int pokemon_id, String name, String[] type, int attack, int current_attack, int defense, int max_speed, int hp_max, String moveID1, String moveID2) {
        this.pokemon_id = pokemon_id;
        this.name = name;
        this.type = type;
        this.current_hp = hp_max;
        this.attack = attack;
        this.current_attack = current_attack;
        this.defense = defense;
        this.current_defense = defense;
        this.max_speed = max_speed;
        this.current_speed = max_speed;
        this.hp_max = hp_max;
        move1 = new Moves(this, moveID1);
        move2 = new Moves(this, moveID2);

    }
    
    public Pokemon(int pokemon_id, String moveID1, String moveID2, Team team) {
    	this.pokemon_id = pokemon_id;
    	this.name = "Pikachu";
    	this.current_hp = 100;
        this.attack = 50;
        this.current_attack = 50;
        this.defense = 50;
        this.current_defense = 50;
        this.max_speed = 50;
        this.current_speed = 50;
        this.hp_max = 100;
        this.team = team;
        move1 = new Moves(this, moveID1);
        move2 = new Moves(this, moveID2);
        status_effect = new Status(this);
    }


	
    public int getPokemon_id() {
        return this.pokemon_id;
    }
    public String getName() {
        return this.name;
    }
    public String[] getType() {
        return this.type;
    }
    public Team getTeam() {
        return this.team;
    }
    public int getCurrent_hp() {
        return this.current_hp;
    }
    public int getCurrent_attack() {
    	return this.current_attack;
    }
    public int getAttack() {
        return this.attack;
    }
    public int getDefense() {
        return this.defense;
    }
    public int getCurrent_defense() {
        return this.current_defense;
    }
    public int getMax_speed() {
        return this.max_speed;
    }
    public int getCurrent_speed() {
        return this.current_speed;
    }
    public int getHp_max() {
        return this.hp_max;
    }
    public Moves getMove1() {
        return move1;
    }
    public Moves getMove2() {
        return move2;
    }
    public Status getStatus() {
    	return status_effect;
    }
    


    public void setType(String[] type) {
        this.type = type;
    }
    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }


    public void setCurrent_defense(int current_defense) {
        this.current_defense = current_defense;
    }

    public void setCurrent_speed(int current_speed) {
        this.current_speed = current_speed;
    }  
    public void changeSpeed(int speed) {
    	current_speed += speed;
    }
    
    public void changeAttack(int atk) {
    	current_attack += atk;
    }
    
    public void changeDefence(int def) {
    	current_attack += def;
    }
    
    public void takeDamage(int damage) {
    	System.out.printf("I TOOK %d DAMAGE!\n", damage); // temporary
    	current_hp -= damage;
    }

	@Override
	public String toString() {
		return "Pokemon [pokemon_id=" + pokemon_id + ", name=" + name + ", hp_max=" + hp_max + ", attack=" + attack
				+ ", defense=" + defense + ", current_hp=" + current_hp + ", current_attack=" + current_attack
				+ ", current_defense=" + current_defense + ", max_speed=" + max_speed + ", current_speed="
				+ current_speed + "]";
	}
    

}

