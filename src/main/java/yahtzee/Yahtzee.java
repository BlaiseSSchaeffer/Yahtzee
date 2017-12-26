package yahtzee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Yahtzee {
	private static Cup cup;
	private static Scoring scoring;
	private static int rollCount = 1;

	private static void newGame() {
		rollCount = 1;
		cup = new Cup(5, 6);
		scoring = new Scoring(cup);
		printHelp();
		scoring.printScoreCard();
		cup.printDice();
	}

	private static void roll() {
		if (rollCount < 3) {
			rollCount += 1;
			cup.shake();
		} else {
			System.out.println("You have already rolled three times, it's time to score!");
		}
	}

	private static void score(int scoreIdx) {
		if (scoring.score(scoreIdx)) {
			rollCount = 1;
			cup.unfreezeAllDice();
			cup.shake();
		}
	}

	private static void printHelp() {
		System.out.println("To roll enter 'R'.");
		System.out.println("To score enter 'S' followed by the option to score.");
		System.out.println("To freeze a die enter 'F' followed by the index of the die.");
		System.out.println("To unfreeze a die enter 'U' followed by the index of the die.");
		System.out.println("To start a new game enter 'G'.");
		System.out.println("To print the dice, enter 'PD'.");
		System.out.println("To print the score card enter 'PS'.");
		System.out.println("To show this menu enter 'H'.");
		System.out.println("");
	}

	private static void readInput(String input) {
		try {
			String[] parts = input.split(" ");
			String key = parts[0];
			if (key.equalsIgnoreCase("g")) {
				newGame();
			} else if (key.equalsIgnoreCase("f")) {
				for (int i = 1; i < parts.length; i++) {
					cup.freezeDie(Integer.valueOf(parts[i]));
				}
				cup.printDice();
			} else if (key.equalsIgnoreCase("u")) {
				for (int i = 1; i < parts.length; i++) {
					cup.unfreezeDie(Integer.valueOf(parts[i]));
				}
				cup.printDice();
			} else if (key.equalsIgnoreCase("r")) {
				roll();
			} else if (key.equalsIgnoreCase("s")) {
				score(Integer.valueOf(parts[1]));
			} else if (key.equalsIgnoreCase("pd")) {
				cup.printDice();
			} else if (key.equalsIgnoreCase("ps")) {
				scoring.printScoreCard();
			} else if (key.equalsIgnoreCase("h") || key.equalsIgnoreCase("help") || key.equalsIgnoreCase("--help")) {
				printHelp();
			} else {
				System.out.println("Input not recognized... ");
			}
		} catch (Exception e) {
			System.out.println("Failure while readind input, try again!");
		}
	}

	public static void main(String[] args) throws IOException {
		newGame();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input;
		while (!(input = reader.readLine()).equalsIgnoreCase("q")) {
			readInput(input);
		}
	}
}
