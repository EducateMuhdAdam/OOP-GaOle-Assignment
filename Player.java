import java.util.ArrayList;

public class Player {
	private String username;
	private int score;
	private ArrayList<String> pokemon_list;
	
	
	//constructor
	public Player(String username, int score, ArrayList<String> pokemon_list) {
		this.username = username;
		this.score = score;
		this.pokemon_list = new ArrayList<String>();
	}
	
	
	//getters
	public String getUsername() {
		return username;
	}
	
	public int getScore() {
		return score;
	}
	public ArrayList<String> getPokemon_list() {
		return pokemon_list;
	}
	
	
	//setters
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setPokemon_list(ArrayList<String> pokemon_list) {
		this.pokemon_list = pokemon_list;
	}

}
