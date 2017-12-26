package yahtzee;

import java.util.concurrent.ThreadLocalRandom;

public class Die {
	private int numSides;
	private int value;
	private boolean frozen = false;

	public Die(int numSides) {
		super();
		this.numSides = numSides;
		roll();
	}

	public Die() {
		super();
		this.numSides = 6;
		roll();
	}

	public int roll() {
		if (!frozen) {
			value = ThreadLocalRandom.current().nextInt(1, numSides + 1);
		}
		return value;
	}

	public int getValue() {
		return value;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void freeze() {
		frozen = true;
	}

	public void unfreeze() {
		frozen = false;
	}

}
