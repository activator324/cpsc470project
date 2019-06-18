package project.cpsc470;
import java.io.*;

abstract class PlayerStrategy implements Runnable
{
	private int bank1;
	protected Server server;
	protected Client client;
	protected String name;
	protected InputStreamReader in;
	protected BufferedReader br;
	protected PrintWriter pw;
	protected boolean isHost = false;
	protected String ipAddress;
	protected int portNumber;

	PlayerStrategy() { 
		bank1 = 100; 
	}

	abstract public boolean doesPlayerHit(String[] playerCards, String dealerUpCard); 
	abstract public int placeBet(int bank, String[] playedCards, int numCardsLeft);
	abstract void createServer(String name, String hostname, int portNumber, int numPlayers);
	abstract void connectToGame(String ipAddress, int portNumber);
	abstract String parseServerInput(String input);
	abstract void setNetworkVariables(String ipAddress, int portNumber);

	void setBank(int bank) { bank1 = bank; }
	int getBank() { return bank1; } 
	abstract void setName(String name);
	String getName() { return this.name;}
}
