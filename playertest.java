import java.util.ArrayList;

public class playertest {

	public static void main(String[] args) {
		 //create an ArrayList for pokemon_list
        ArrayList<String> pokemon_list = new ArrayList<>();
        pokemon_list.add("Pikachoochoo");
        pokemon_list.add("Charizzmander");
        pokemon_list.add("Bulbasaurus");
        
        //create an instance of Player
        Player player1 = new Player("awesomeusername", 0, pokemon_list);
        
        //set pokemon_list of player1 to pokemon_list given here
        player1.setPokemon_list(pokemon_list);
        
        //write to CSV file
        player1.pokemonToCSV("pokemon_list.csv");

	}

}
