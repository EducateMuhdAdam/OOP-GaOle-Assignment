import Moves;
public class Pokemon {
    private int pokemon_id;
    private String name;
    private String[] type;
    private int current_hp;
    private int attack;
    private int defense;
    private int current_defense;
    private int max_speed;
    private int current_speed;
    private int hp_max;
    private String status_effect;

    public Pokemon(int pokemon_id, String name, String[] type, int attack, int defense, int max_speed, int hp_max, String status_effect, int moveID1, int moveID2) {
        this.pokemon_id = pokemon_id;
        this.name = name;
        this.type = type;
        this.current_hp = hp_max;
        this.attack = attack;
        this.defense = defense;
        this.current_defense = defense;
        this.max_speed = max_speed;
        this.current_speed = max_speed;
        this.hp_max = hp_max;
        this.status_effect = status_effect;
        Moves move1 = new Moves(this, moveID1);
        Moves move2 = new Moves(this, moveID2);

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
    public int getCurrent_hp() {
        return this.current_hp;
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
    public String getStatus_effect() {
        return this.status_effect;
    }
    public Moves getMoves() {
        return this.move1.getMoves(moveID);
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

    public void setStatus_effect(String status_effect) {
        this.status_effect = status_effect;
    }


}
