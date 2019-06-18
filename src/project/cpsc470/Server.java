package project.cpsc470;
import java.io.*;
import java.net.*;
import java.util.*;

/*
 * 
 */
public class Server implements Runnable{
	
	private final int portNumber = 6000;
	private String serverName;
	private String ipAddress;
	int numPlayers;
	
	private Hashtable<Socket, String> players = new Hashtable<Socket, String>();
	private ServerSocket ss;
	private Socket s;
	
	private BufferedReader br;
	private PrintWriter pw;
	private InputStreamReader in;
	
	private Deck deck = Deck.getSingleton();
	
	public Server(String name, String hostname, int numberOfPlayers)
	{
		this.serverName = name;
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.ipAddress = ip.getHostAddress();
		System.out.println("Hosting at address: " + this.ipAddress);
		this.numPlayers = numberOfPlayers;
		
		try {
			ss = new ServerSocket(portNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public Server(String name, String hostname)
	{
		this.serverName = name;
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.ipAddress = ip.getHostAddress();
		System.out.println("Hosting at address: " + this.ipAddress);
		
		try {
			ss = new ServerSocket(6000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			in = new InputStreamReader(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			pw = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		br = new BufferedReader(in);
	}

	/*
	 * Keeps the server running and updating between players.
	 */
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("Waiting for players....");
		
		for(int i = 0; i < numPlayers; i++)
		{
			
			try {
				Socket player = ss.accept();
				waitForPlayer(player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//System.out.println("All Players Connected.");
		sendToAllPlayers("All Players Connected.");
		sendToAllPlayers("\n");
		sendToAllPlayers("********************");
		sendToAllPlayers("Starting game....");
		
		sendToAllPlayers("At the table:");
		
		for(Socket connectionID : players.keySet())
		{
			sendToAllPlayers(players.get(connectionID));
		}
		
		sendToAllPlayers("********************");
		int pot = 0;
	
		int numCardsLeft = Deck.getSingleton().getNumCardsLeft();
		String[] playedCards = Deck.getSingleton().getPlayedCards();
		
		int[] playerBanks = new int[4];
		
		for(Socket connectionID : players.keySet())
		{
			String playerID = players.get(connectionID);
			
			try {
				pw = new PrintWriter(connectionID.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				in = new InputStreamReader(connectionID.getInputStream());
				br = new BufferedReader(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			pw.println(players.get(connectionID));
			pw.println("Bet");
			pw.flush();
			
			int playerBet = 0;
			
			try {
				playerBet = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println(playerID + " Bet " + playerBet);
			
			
			
			
		}
		while(true)
		{
			
		}
		
	}
	
	/*
	 * This should wait until a player is connected
	 */
	public void waitForPlayer(Socket player) throws IOException
	{
		
		in = new InputStreamReader(player.getInputStream());
		
		br = new BufferedReader(in);
		
		String playerName = br.readLine();
		
		players.put(player, playerName);
		
		System.out.println(playerName + " Connected");
		
	}
	
	public String getIP()
	{
		return this.ipAddress;
	}
	
	public int getPortNumber()
	{
		return this.portNumber;
	}
	
	void sendToAllPlayers(String input)
	{
		for(Socket connectionID : players.keySet())
		{
			try {
				pw = new PrintWriter(connectionID.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pw.println("notification");
			pw.println(input);
			pw.println(" ");
			pw.flush();
			
		}
	}
}
