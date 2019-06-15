package project.cpsc470;

abstract class PlayerStrategy
{
	private int bank1;

	PlayerStrategy() { bank1 = 100; }

	abstract public boolean doesPlayerHit(String[] playerCards, String dealerUpCard); 
	abstract public int placeBet(int bank, String[] playedCards, int numCardsLeft); 

	void setBank(int bank) { bank1 = bank; }
	int getBank() { return bank1; } 
}
