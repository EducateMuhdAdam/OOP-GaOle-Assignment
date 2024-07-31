package src;

import java.util.ArrayList;

public class Player {
	private String username;
	private int score;
	private int[] pokemon_list;
	
	
	//constructor
	public Player(String username, int score, int[] pokemon_list) {
		this.username = username;
		this.score = score;
		this.pokemon_list = new int[0];
	}
	
	
	//
	public String getUsername() {
		return username;
	}
	
	public int getScore() {
		return score;
	}
	public int[] getPokemon_list() {
		return pokemon_list;
	}
	
	public void addPokemon(int id){

		int new_array[] = new int[pokemon_list.length + 1];

		if(pokemon_list.length == 0){
			new_array[0] = id;
		}
		else {
			for (int i = 0; i < pokemon_list.length; i++) {
				new_array[i] = pokemon_list[i];
			}
		}
		new_array[pokemon_list.length] = id;


		pokemon_list = new_array;
		System.out.println(UI.colorFont("Success!!!", UI.GREEN));
		System.out.println();
	}
	//
	public void CalculateScore(Pokemon pokemon){
		this.score += 1 * pokemon.getCurrent_attack() * pokemon.getGrade()* pokemon.getCurrent_speed();

		setScore(this.score);
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setPokemon_list(int[] pokemon_list) {
		this.pokemon_list = pokemon_list;
	}

}
