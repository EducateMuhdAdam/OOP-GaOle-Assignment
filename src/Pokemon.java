package src;

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
    
    private boolean isGen2OrLater;

    public Pokemon(int pokemon_id, String name, ArrayList<Type> type, int attack, int current_attack, int defense, int max_speed, int hp_max, String moveID1, String moveID2) {
        this.pokemon_id = pokemon_id;
        this.name = name;
        this.type = type;
        this.current_hp = hp_max;
        this.attack = attack;
        this.stage_attack = current_attack;
        this.defense = defense;
        this.stage_defense = defense;
        this.speed = max_speed;
        this.stage_speed = max_speed;
        this.hp_max = hp_max;
        move1 = new Moves(this, moveID1);
        move2 = new Moves(this, moveID2);

    }
    
    public Pokemon(int pokemon_id, Team team){
        this.pokemon_id = pokemon_id;
        this.team = team;
        this.status_effect = new Status(this);
        this.type = new ArrayList<Type>();
        
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
				move2 = new Moves(this, pokemonlog[9]);
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
		return pokemon_id;
	}

	public void setPokemon_id(int pokemon_id) {
		this.pokemon_id = pokemon_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getHp_max() {
		return hp_max;
	}

	public void setHp_max(int hp_max) {
		this.hp_max = hp_max;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getStage_attack() {
		return stage_attack;
	}

	public void setStage_attack(int stage_attack) {
		this.stage_attack = stage_attack;
	}

	public int getStage_defense() {
		return stage_defense;
	}

	public void setStage_defense(int stage_defense) {
		this.stage_defense = stage_defense;
	}

	public int getStage_speed() {
		return stage_speed;
	}

	public void setStage_speed(int stage_speed) {
		this.stage_speed = stage_speed;
	}

	public void setStatus_effect(Status status_effect) {
		this.status_effect = status_effect;
	}

	public Moves getMove1() {
		return move1;
	}

	public void setMove1(Moves move1) {
		this.move1 = move1;
	}

	public Moves getMove2() {
		return move2;
	}

	public void setMove2(Moves move2) {
		this.move2 = move2;
	}

	public boolean isGen2OrLater() {
		return isGen2OrLater;
	}

	public void setGen2OrLater(boolean isGen2OrLater) {
		this.isGen2OrLater = isGen2OrLater;
	}

	public ArrayList<Type> getType() {
		return type;
	}

	public Status getStatus() {
    	return status_effect;
    }
    
    public static double calculateStatMultiplier(int stage, boolean isGen2OrLater) {
        if (stage < -6 || stage > 6) {
            throw new IllegalArgumentException("Stage must be between -6 and 6");
        }
        if (isGen2OrLater) {
            return stage >= 0 ? (2.0 + stage) / 2.0 : 2.0 / (2.0 - stage);
        } else {
            switch (stage) {
                case -6: return 0.25;
                case -5: return 0.28;
                case -4: return 0.33;
                case -3: return 0.40;
                case -2: return 0.50;
                case -1: return 0.66;
                case 0: return 1.0;
                case 1: return 1.5;
                case 2: return 2.0;
                case 3: return 2.5;
                case 4: return 3.0;
                case 5: return 3.5;
                case 6: return 4.0;
                default: throw new IllegalArgumentException("Stage must be between -6 and 6");
            }
        }
    }

    public void setType(ArrayList<Type> type) {
        this.type = type;
    }
    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }
    public int getCurrent_hp() {
        return this.current_hp;
    }


    public int getCurrent_attack() {
    	return (int)(this.attack * Pokemon.calculateStatMultiplier(this.stage_attack, isGen2OrLater()));
    }
    
    public int getCurrent_defense() {
        return (int)(this.defense * Pokemon.calculateStatMultiplier(this.stage_defense, isGen2OrLater()));
    }
    
    public int getCurrent_speed() {
        return (int)(this.speed * Pokemon.calculateStatMultiplier(this.stage_speed, isGen2OrLater()));
    }

    public void changeSpeed(int spd) {
    	stage_speed += spd;
    }
    
    public void changeAttack(int atk) {
    	stage_attack += atk;
    }
    
    public void changeDefence(int def) {
    	stage_attack += def;
    }
    
    public void takeDamage(int damage) {
    	UI.displayDamage(this, damage);
    	current_hp -= damage;
    	UI.displayHealthBar(this);
    }

	@Override
	public String toString() {
		return "Pokemon [pokemon_id=" + pokemon_id + ", name=" + name + ", hp_max=" + hp_max + ", attack=" + attack
				+ ", defense=" + defense + ", current_hp=" + current_hp + ", current_attack=" + stage_attack
				+ ", current_defense=" + stage_defense + ", max_speed=" + speed + ", current_speed="
				+ stage_speed + "]";
	}
    

}

