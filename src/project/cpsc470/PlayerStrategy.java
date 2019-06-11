package project.cpsc470;

interface PlayerStrategy
{
	public boolean doesPlayerHit(String[] playerCards, String dealerUpCard); 
	public int placeBet(int bank, String[] playedCards, int numCardsLeft); 
}
