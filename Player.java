import java.util.ArrayList;
import java.util.Formatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
	
	
	//write to csv file
	public void pokemonToCSV(String filePath) {
        try (Formatter output = new Formatter(filePath)) {
            for (String pokemon: pokemon_list) {
                output.format("%s \n", pokemon);
            }
            System.out.println("Pokemon List file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
