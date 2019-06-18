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
	
	@Override
	void createServer(String name, String hostname, int portNumber, int numPlayers) {
		// TODO Auto-generated method stub
		
		isHost = true;
		
		this.ipAddress = hostname;
		this.portNumber = portNumber;
		
		System.out.println("Creating server....");
		
		server = new Server(name, hostname, numPlayers);
		
		System.out.println("Server created");
		
		Thread t1 = new Thread(this.server);
		
		
		
		Thread t2 = new Thread(this);
		
		t1.start();
		t2.start();
		
		
	}

	@Override
	void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	void connectToGame(String ipAddress, int portNumber) {
		// TODO Auto-generated method stub
		
		
		this.client = new Client(this.name, ipAddress, portNumber);
	
		
		
	}
	
	public String parseServerInput(String input)
	{
		if(input.equalsIgnoreCase("Bet"))
		{
			
			return "20";
		}
		
		return "0";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		connectToGame(this.ipAddress, this.portNumber);
		
		
		
		while(!this.client.connected())
		{
			
		}
		
		String serverResponse = "none";
		
		while(!serverResponse.equalsIgnoreCase("end"))
		{
			serverResponse = this.client.serverInput();
			
			if(!serverResponse.equalsIgnoreCase("none"))
			{
				//System.out.println(serverResponse);
				String response = parseServerInput(serverResponse);
				
				this.client.sendInput(response);
			}
			
			serverResponse = "none";
		}
		
		
		
		
	}
	
	void setNetworkVariables(String ipAddress, int portNumber)
	{
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}
	
	

}