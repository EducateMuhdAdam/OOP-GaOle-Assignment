import java.io.*;
import java.util.*;

public class Pokemon {
    private int pokemon_id;
    private String name;
    private ArrayList<Type> type;
    private Team team;
    private int grade;
    
    private int hp_max;
    private int attack;
    private int defense;
    private int speed;
    
    private int current_hp;
    private int stage_attack;
    private int stage_defense;
    private int stage_speed;
    
    private Status status_effect;
    private Moves move1;
    private Moves move2;
    
    public Pokemon(int pokemon_id, Team team){
        this.pokemon_id = pokemon_id;
        this.team = team;
        this.status_effect = new Status(this);
        
        ArrayList<String[]> pokemon_data = new ArrayList<String[]>();
		
		try {
			File textfile = new File(System.getProperty("user.dir") + "\\src\\Set01.csv");
			Scanner data = new Scanner(textfile);
			while (data.hasNextLine()) {
				pokemon_data.add(data.nextLine().split(",", 0));
			}
			data.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Pokemon File Can't Be Read");
		}
        for (String[] pokemonlog : pokemon_data) {
			if (pokemonlog[0].equals(Integer.toString(pokemon_id))) {
				name = pokemonlog[1];
				type = new ArrayList<Type>();
				for (String t: pokemonlog[2].split("/", 0)) {
					type.add(InitType(t));
				}
				grade = Integer.parseInt(pokemonlog[3]);
				hp_max = Integer.parseInt(pokemonlog[4]);
				current_hp = Integer.parseInt(pokemonlog[4]);
				attack = Integer.parseInt(pokemonlog[5]);
                defense = Integer.parseInt(pokemonlog[6]);
                speed = Integer.parseInt(pokemonlog[7]);
                move1 = new Moves(this, pokemonlog[8]); 
			}
		}
        stage_attack = 0;
        stage_defense = 0;
        stage_speed = 0;
		
    }
    
    private Type InitType(String type) {
		switch (type.toLowerCase()){
		case "normal": return Type.Normal;
		case "fire": return Type.Fire;
		case "water": return Type.Water;
		case "grass": return Type.Grass;
		case "electric": return Type.Electric;
		case "ice": return Type.Ice;
		case "fighting": return Type.Fighting;
		case "poison": return Type.Poison;
		case "ground": return Type.Ground;
		case "flying": return Type.Flying;
		case "psychic": return Type.Psychic;
		case "bug": return Type.Bug;
		case "rock": return Type.Rock;
		case "ghost": return Type.Ghost;
		case "dragon": return Type.Dragon;
		case "dark": return Type.Dark;
		case "steel": return Type.Steel;
		case "fairy": return Type.Fairy;
		default: {
			System.out.println("Type Error, Defaulting to Normal");
			return Type.Normal;
		}
		}
	}

	
    public int getPokemon_id() {
        return this.pokemon_id;
    }
    public String getName() {
        return this.name;
    }
    public ArrayList<Type> getType() {
        return this.type;
    }
    public Team getTeam() {
        return this.team;
    }
    public int getCurrent_hp() {
        return this.current_hp;
    }
    public int getCurrent_attack() {
    	return attack;
    }
    public int getAttack() {
        return this.attack;
    }
    public int getDefense() {
        return this.defense;
    }
    public int getCurrent_defense() {
        return this.defense;
    }
    public int getMax_speed() {
        return this.speed;
    }
    public int getCurrent_speed() {
        return this.speed;
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
    


    public void setType(ArrayList<Type> type) {
        this.type = type;
    }
    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }


    public void setCurrent_defense(int stage_defense) {
        this.stage_defense = stage_defense;
    }

    public void setCurrent_speed(int stage_speed) {
        this.stage_speed = stage_speed;
    }  
    public void changeSpeed(int speed) {
    	stage_speed += speed;
    }
    
    public void changeAttack(int atk) {
    	stage_attack += atk;
    }
    
    public void changeDefence(int def) {
    	stage_attack += def;
    }
    
    public void takeDamage(int damage) {
    	System.out.printf("I TOOK %d DAMAGE!\n", damage); // temporary
    	current_hp -= damage;
    }

	@Override
	public String toString() {
		return "Pokemon [pokemon_id=" + pokemon_id + ", name=" + name + ", hp_max=" + hp_max + ", attack=" + attack
				+ ", defense=" + defense + ", current_hp=" + current_hp + ", current_attack=" + stage_attack
				+ ", current_defense=" + stage_defense + ", max_speed=" + speed + ", current_speed="
				+ stage_speed + "]";
	}
    

}

