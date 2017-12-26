package yahtzee;

import java.util.ArrayList;
import java.util.List;

public class Cup {
	private List<Die> dice;
	private int numDice;

	public Cup(int numDice, int numSides) {
		super();
		this.numDice = numDice;
		dice = new ArrayList<>(numDice);
		for (int i = 0; i < numDice; i++) {
			dice.add(new Die(numSides));
		}
		shake();
	}

	public Cup() {
		super();
		this.numDice = 5;
		dice = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			dice.add(new Die());
		}
		shake();
	}

	public void shake() {
		for (Die die : dice) {
			die.roll();
		}
		printDice();
	}

	public void freezeDie(int idx) {
		if (idx >= 0 && idx < numDice) {
			dice.get(idx).freeze();
		}
	}

	public void unfreezeDie(int idx) {
		if (idx >= 0 && idx < numDice) {
			dice.get(idx).unfreeze();
		}
	}

	public void unfreezeAllDice() {
		for (Die die : dice) {
			die.unfreeze();
		}
	}

	public void printDice() {
		for (Die die : dice) {
			System.out.print(die.getValue() + "\t");
		}
		System.out.println("");
		for (Die die : dice) {
			System.out.print((die.isFrozen() ? "-" : " ") + "\t");
		}
		System.out.println("\n");
	}

	public List<Die> getDice() {
		return dice;
	}

}
