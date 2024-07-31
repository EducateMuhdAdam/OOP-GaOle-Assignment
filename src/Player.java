 package src;

import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;

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
	public void CalculateScore(int addScore){
		 this.score += addScore;

		setScore(this.score);
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	 public void topScore(Player player){
		 HashMap<String, Integer> leaderboard = new HashMap<String, Integer>();
		 ArrayList<String[]> score_data = new ArrayList<String[]>();
		 try {
			 File textfile = new File(System.getProperty("user.dir") + "\\src\\topscorelist.csv");
			 Scanner data = new Scanner(textfile);
			 String[] line;
			 while (data.hasNextLine()) {
				 line = data.nextLine().split(",", 0);
				 if (line.length != 0)
				 	score_data.add(line);
			 }
			 data.close();
		 }
		 catch(FileNotFoundException e){
			 System.out.println("Score File Can't Be Read");
		 }

		 int score = player.getScore();

		 for (String[] log: score_data){
			 leaderboard.put(log[0], Integer.parseInt(log[1]));
		 }
		 leaderboard.put(player.getUsername(), player.getScore());

		 HashMap<String, Integer> newlead = sortByValue(leaderboard);
		 int i = 1;
		 for (Map.Entry<String, Integer> log: newlead.entrySet()){
			 if (i <= 5)
			 	System.out.printf("%d. %s - %d", i, log.getKey(), log.getValue());
			 i++;
		 }
		 System.out.println();
		 WriteLeaderboard(leaderboard);
	 }
	 public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
	 {
		 // Create a list from elements of HashMap
		 List<Map.Entry<String, Integer> > list =
				 new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

		 // Sort the list
		 Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
			 public int compare(Map.Entry<String, Integer> o1,
								Map.Entry<String, Integer> o2)
			 {
				 return (o1.getValue()).compareTo(o2.getValue());
			 }
		 });

		 // put data from sorted list to hashmap
		 HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		 for (Map.Entry<String, Integer> aa : list) {
			 temp.put(aa.getKey(), aa.getValue());
		 }
		 return temp;
	 }
	 public void WriteLeaderboard(HashMap<String, Integer> lead){
		 try {
			 File scorefile = new File("topscorelist.csv");
			 if (scorefile.createNewFile()) {
				 System.out.println("File created: " + scorefile.getName());
			 } else {
				 System.out.println("File already exists.");
			 }
			 FileWriter writer = new FileWriter(scorefile);
			 String pidgeon = "";
			 for (Map.Entry<String, Integer> line: lead.entrySet())
				 pidgeon += String.format("%s,%d\n",line.getKey(), line.getValue());
			 writer.write(pidgeon);
			 writer.close();
		 } catch (IOException e) {
			 System.out.println("An error occurred.");
			 e.printStackTrace();
		 }


	 }


}
