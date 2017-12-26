package yahtzee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Scoring {
	private Cup cup;
	private Set<Integer> scoreIdxSet = new HashSet<>(13);

	private Integer ones = null;
	private Integer twos = null;
	private Integer threes = null;
	private Integer fours = null;
	private Integer fives = null;
	private Integer sixes = null;
	private int subTotal = 0;

	private Integer threeOfAKind = null;
	private Integer fourOfAKind = null;
	private Integer smallStraight = null;
	private Integer largeStraight = null;
	private Integer fullHouse = null;
	private Integer chance = null;
	private Integer yahtzee = null;
	private int yahtzeeCount = 0;
	private int yahtzeeBonus = 0;

	private int score = 0;

	public Scoring(Cup cup) {
		this.cup = cup;
	}

	public void printScoreCard() {
		System.out.println("________Score Card________");
		System.out.println("1.  Ones:    " + printScore(ones));
		System.out.println("2.  Twos:    " + printScore(twos));
		System.out.println("3.  Threes:  " + printScore(threes));
		System.out.println("4.  Fours:   " + printScore(fours));
		System.out.println("5.  Fives:   " + printScore(fives));
		System.out.println("6.  Sixes    " + printScore(sixes));
		System.out.println("Subtotal:    " + subTotal);
		System.out.println("7.  3K:      " + printScore(threeOfAKind));
		System.out.println("8.  4K:      " + printScore(fourOfAKind));
		System.out.println("9.  Small    " + printScore(smallStraight));
		System.out.println("10. Large:   " + printScore(largeStraight));
		System.out.println("11. FH:      " + printScore(fullHouse));
		System.out.println("12. Chance:  " + printScore(chance));
		System.out.println("13. Yahtzee: " + printScore(yahtzee));
		System.out.println("Total:       " + score);
		System.out.println("__________________________\n");
	}

	public void roll() {
		cup.shake();
		cup.printDice();
	}

	private void updateScoreCard() {
		subTotal = (ones == null ? 0 : ones) + (twos == null ? 0 : twos) + (threes == null ? 0 : threes)
				+ (fours == null ? 0 : fours) + (fives == null ? 0 : fives) + (sixes == null ? 0 : sixes);
		score = (threeOfAKind == null ? 0 : threeOfAKind) + (fourOfAKind == null ? 0 : fourOfAKind)
				+ (smallStraight == null ? 0 : smallStraight) + (largeStraight == null ? 0 : largeStraight)
				+ (fullHouse == null ? 0 : fullHouse) + (chance == null ? 0 : chance) + (yahtzee == null ? 0 : yahtzee)
				+ yahtzeeBonus + subTotal;
		if (subTotal >= 63) {
			score += 35;
		}

	}

	private int totalDice() {
		int diceTotal = 0;
		for (Die die : cup.getDice()) {
			diceTotal += die.getValue();
		}
		return diceTotal;
	}

	private String printScore(Integer toScore) {
		if (toScore == null) {
			return "";
		}
		return toScore.toString();
	}

	private boolean checkForYahtzee() {
		Set<Integer> faceValues = new HashSet<>(5);
		for (Die die : cup.getDice()) {
			faceValues.add(die.getValue());
		}
		return faceValues.size() == 1;
	}

	private Map<Integer, Integer> buildFaceValueMap() {
		Map<Integer, Integer> map = new HashMap<>(5);
		int faceValue;
		for (Die die : cup.getDice()) {
			faceValue = die.getValue();
			if (map.containsKey(faceValue)) {
				map.put(faceValue, map.get(faceValue) + 1);
			} else {
				map.put(faceValue, 1);
			}
		}
		return new TreeMap<>(map);
	}

	public boolean score(Integer scoreIdx) {
		boolean scored = false;
		if (scoreIdx > 0 && scoreIdx <= 13) {

			if (checkForYahtzee() && yahtzeeCount > 1) {
				yahtzeeBonus += 100;
				return true;
			}

			if (!scoreIdxSet.contains(scoreIdx)) {
				Map<Integer, Integer> map;
				Collection<Integer> values;
				List<Integer> keyList;
				switch (scoreIdx) {
				case 1:
					ones = 0;
					for (Die die : cup.getDice()) {
						if (die.getValue() == 1) {
							ones += 1;
						}
					}
					break;

				case 2:
					twos = 0;
					for (Die die : cup.getDice()) {
						if (die.getValue() == 2) {
							twos += 2;
						}
					}
					break;

				case 3:
					threes = 0;
					for (Die die : cup.getDice()) {
						if (die.getValue() == 3) {
							threes += 3;
						}
					}
					break;

				case 4:
					fours = 0;
					for (Die die : cup.getDice()) {
						if (die.getValue() == 4) {
							fours += 4;
						}
					}
					break;

				case 5:
					fives = 0;
					for (Die die : cup.getDice()) {
						if (die.getValue() == 5) {
							fives += 5;
						}
					}
					break;

				case 6:
					sixes = 0;
					for (Die die : cup.getDice()) {
						if (die.getValue() == 6) {
							sixes += 6;
						}
					}
					break;

				case 7:
					threeOfAKind = 0;
					map = buildFaceValueMap();
					values = map.values();
					if (values.contains(3) || values.contains(4)) {
						threeOfAKind = totalDice();
					}
					break;

				case 8:
					fourOfAKind = 0;
					map = buildFaceValueMap();
					values = map.values();
					if (values.contains(4)) {
						fourOfAKind = totalDice();
					}
					break;

				case 9:
					smallStraight = 0;
					map = buildFaceValueMap();
					keyList = new ArrayList<>(new TreeSet<>(map.keySet()));
					if ((Collections.indexOfSubList(keyList, Arrays.asList(1, 2, 3, 4)) != -1)
							|| (Collections.indexOfSubList(keyList, Arrays.asList(2, 3, 4, 5)) != -1)
							|| (Collections.indexOfSubList(keyList, Arrays.asList(3, 4, 5, 6)) != -1)) {
						smallStraight = 30;
					}
					break;

				case 10:
					largeStraight = 0;
					map = buildFaceValueMap();
					keyList = new ArrayList<>(new TreeSet<>(map.keySet()));
					if ((Collections.indexOfSubList(keyList, Arrays.asList(1, 2, 3, 4, 5)) != -1)
							|| (Collections.indexOfSubList(keyList, Arrays.asList(2, 3, 4, 5, 6)) != -1)) {
						largeStraight = 40;
					}
					break;

				case 11:
					map = buildFaceValueMap();
					values = map.values();
					fullHouse = 0;
					if (values.size() == 2 && values.contains(2) && values.contains(3)) {
						fullHouse = 25;
					}
					break;

				case 12:
					chance = 0;
					chance = totalDice();
					break;

				case 13:
					if (checkForYahtzee()) {
						yahtzee = 50;
						yahtzeeCount += 1;
					} else {
						yahtzee = 0;
					}
					break;

				default:
					break;
				}

				scoreIdxSet.add(scoreIdx);
				updateScoreCard();
				scored = true;

			} else {
				System.out.println("You have already scored option " + scoreIdx + "...");
			}
		}
		printScoreCard();
		if (scoreIdxSet.size() == 13) {
			System.out.println("Game over... final score: " + score + "\nEnter 'G' for a new game!");
		}
		return scored;
	}
}
