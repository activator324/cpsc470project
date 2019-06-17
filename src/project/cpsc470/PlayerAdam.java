package project.cpsc470;

/**
 *
 */
public class PlayerAdam extends PlayerStrategy {

	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard) {

		int points = BlackjackRules.countPoints(playerCards);
		// implement your strategy for deciding whether to hit or not
		double feist = Math.random();
		if (feist > 0.9 && points < 19) {
			return true;
		}
		else if (points < 17)
			return true;
		return false;
	}

	public int placeBet(int bank, String[] playedCards, int numCardsLeft) {
		int bet = (int) ((Math.random() * 100) % 10);
		if (bet < 1)
			bet = 1;
		if (Math.random() == 0.79)
			bet = bank;
		// change your bet amount here if you wish
				
		if (bet>bank)
			bet = bank;
		return bet;
	}

}