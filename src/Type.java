package src;

 public enum Type {
	Normal,
	Fire,
	Water,
	Grass,
	Electric,
	Ice,
	Fighting,
	Poison,
	Ground,
	Flying,
	Psychic,
	Bug,
	Rock,
	Ghost,
	Dragon,
	Dark,
	Steel,
	Fairy;
	
	public static double CheckTypeMult(Moves move,Pokemon enemy) {
		double[][] table = {
				{1,1,1,1,1,1,1,1,1,1,1,1,0.5,0,1,1,0.5,1},
				{1,0.5,0.5,2,1,2,1,1,1,1,1,2,0.5,1,0.5,1,2,1},
				{1,2,0.5,0.5,1,1,1,1,2,1,1,1,2,1,0.5,1,1,1},
				{1,0.5,2,0.5,1,1,1,0.5,2,0.5,1,0.5,2,1,0.5,1,0.5,1},
				{1,1,2,0.5,0.5,1,1,1,0,2,1,1,1,1,0.5,1,1,1},
				{1,0.5,0.5,2,1,0.5,1,1,2,2,1,1,1,1,2,1,0.5,1},
				{2,1,1,1,1,2,1,0.5,1,0.5,0.5,0.5,2,0,1,2,2,0.5},
				{1,1,1,2,1,1,1,0.5,0.5,1,1,1,0.5,0.5,1,1,0,2},
				{1,2,1,0.5,2,1,1,2,1,0,1,0.5,2,1,1,1,2,1},
				{1,1,1,2,0.5,1,2,1,1,1,1,2,0.5,1,1,1,0.5,1},
				{1,1,1,1,1,1,2,2,1,1,0.5,1,1,1,1,0,0.5,1},
				{1,0.5,1,2,1,1,0.5,0.5,1,0.5,2,1,1,0.5,1,2,0.5,0.5},
				{1,2,1,1,1,2,0.5,1,0.5,2,1,2,1,1,1,1,0.5,1},
				{0,1,1,1,1,1,1,1,1,1,2,1,1,2,1,0.5,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,0.5,0},
				{1,1,1,1,1,1,0.5,1,1,1,2,1,1,2,1,0.5,1,0.5},
				{1,0.5,0.5,1,0.5,2,1,1,1,1,1,1,2,1,1,1,0.5,2},
				{1,0.5,1,1,1,1,2,0.5,1,1,1,1,1,1,2,2,0.5,1}
		};
		double[] line = table[move.getType().ordinal()];
		double mult = 1;
		for (Type t: enemy.getType()) {
			mult *= line[t.ordinal()];
		}
		if (mult == 0){
			System.out.println(UI.colorFont("No effect!!", UI.YELLOW));
		} else if (mult > 0 && mult < 1) {
			System.out.println(UI.colorFont("It's not very effective...", UI.YELLOW));
		} else if (mult >= 2) {
			System.out.println(UI.colorFont("It's super effective!!", UI.GREEN));
		}
		System.out.println(UI.colorFont(String.format("Multiplier: " + mult), UI.CYAN));
		System.out.println();
		return mult;
	}
}
