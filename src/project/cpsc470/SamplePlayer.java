/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project.cpsc470;

/**
 *
 */
public class SamplePlayer implements PlayerStrategy {

	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		// implement your strategy for deciding whether to hit or not

		return false;
	}

	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		int bet = 10;
		// change your bet amount here if you wish
				
		if (bet>bank)
			bet = bank;
		return bet;
	}

}
