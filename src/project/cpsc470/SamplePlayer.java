/**
 * CPSC 110
 * Mar 1, 2017
 * I pledge
 * @author George
 */
package project.cpsc470;

import java.io.IOException;

/**
 *
 */
public class SamplePlayer extends PlayerStrategy {

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

	@Override
	void createServer(String name, String hostname, int portNumber, int numPlayers) {
		// TODO Auto-generated method stub
		System.out.println("Creating server....");
		
		server = new Server(name, hostname, numPlayers);
		
		System.out.println("Server created");
		
		
	
		
	}

	@Override
	void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	void connectToGame(String ipAddress, int portNumber) {
		// TODO Auto-generated method stub
		this.client = new Client(this.name, "localhost", portNumber);
		
		this.client.sendInput(this.name);
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	String parseServerInput(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void setNetworkVariables(String ipAddress, int portNumber) {
		// TODO Auto-generated method stub
		
	}

}
