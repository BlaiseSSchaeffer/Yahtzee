package yahtzee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DieTest {
	int numSides = 6;
	Die die = new Die(numSides);

	@Test
	public void testNumSides() {
		int value = die.getValue();
		assertTrue(value <= numSides && value > 0);
	}

	@Test
	public void testRoll() {
		int value = die.getValue();
		int newValue = die.roll();
		while (value == newValue) {
			newValue = die.roll();
		}
		assertFalse(value == newValue);
	}

	@Test
	public void testFreezing() {
		assertFalse(die.isFrozen());

		die.freeze();
		assertTrue(die.isFrozen());
		int value = die.getValue();
		int newValue = die.roll();
		newValue = die.roll();
		newValue = die.roll();
		assertTrue(value == newValue);

		die.unfreeze();
		assertFalse(die.isFrozen());
	}

}
