package project.cpsc470;

import java.util.Random;
import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;

public class OldProject {

	/**
	 * @param args - not used
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		
		PlayerStrategy[] players = new PlayerStrategy[4];
		int numPlayers = 0;
		PlayerStrategy player1 = null;
		
		
		//Local or Online Multiplayer
		
		boolean OnlineOrLocal = false;
		boolean OnlineOrLocalCheck = false;
		
		Scanner input = new Scanner(System.in);
		
		
		System.out.println("Online or Local: ");
		String answer = input.nextLine();
		
		if(answer.equalsIgnoreCase("Online"))
		{
			OnlineOrLocalCheck = true;
			OnlineOrLocal = true;
		}
		else if(answer.equalsIgnoreCase("Local"))
		{
			OnlineOrLocalCheck = true;
			OnlineOrLocal = false;
		}
		else
			OnlineOrLocalCheck = false;
		
		while(!OnlineOrLocalCheck)
		{
			
			System.out.println("Online or Local: ");
			answer = input.nextLine();
			
			if(answer.equalsIgnoreCase("Online"))
			{
				OnlineOrLocalCheck = true;
				OnlineOrLocal = true;
			}
			else if(answer.equalsIgnoreCase("Local"))
			{
				OnlineOrLocal = false;
				OnlineOrLocalCheck = false;
			}
			else
				OnlineOrLocalCheck = false;
		}
		
		if(OnlineOrLocal)
		{
			//Get main player
			System.out.println("Enter class-name of player to create (i.e.: Player\"name\"):");
			String myClassName = scnr.next();
			myClassName = "project.cpsc470." + myClassName;
			
				
			try {
				player1 = (PlayerStrategy) Class.forName(myClassName).newInstance();
				player1.setName(myClassName);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Usage: ClassSimpleName");
			}
			
			System.out.println("Search for a Game or Host Game? (Host or Search):");
			
			answer = input.nextLine();
			
			boolean HostOrSearch = true;
			
			if(answer.equalsIgnoreCase("Host"))
			{
				HostOrSearch = false;
			}
			else if(answer.equalsIgnoreCase("Search"))
			{
				HostOrSearch = true;
			}
			
			String name = player1.getName();
			
			if (!HostOrSearch)
			{
				String hostname = null;
				int numberOfPlayers;
				
				System.out.println("Enter number of players to wait for: ");
				numberOfPlayers = input.nextInt();
				
				
				players[0] = player1;
				
				if(numberOfPlayers < 1 || numberOfPlayers > 4)
				{
					System.out.println("Not a valid number\nEnter number of players to wait for: ");
					numberOfPlayers = input.nextInt();
				}
				
				player1.createServer(name, hostname, 6000, numberOfPlayers);
			}
			else
			{
				System.out.println("Give ip address of host (x.x.x.x):");
				String ipAddress = input.nextLine();
				
				System.out.println("Searching for game....");
				
				player1.setNetworkVariables(ipAddress, 6000);
				
				Thread playerThread = new Thread(player1);
				
				playerThread.start();
			}
		}
		else
		{
			
			System.out.println("Enter number of players:");
			numPlayers = scnr.nextInt();
			
			players = new PlayerStrategy[numPlayers];
			
			//For each player in game, prompt user for name.
			for (int i = 0; i < players.length; i++)
			{
					System.out.println("Enter class-name of player to create (i.e.: Player \"name\"):");
					String myClassName = scnr.next();
					myClassName = "project.cpsc470." + myClassName;
				try {
					player1 = (PlayerStrategy) Class.forName(myClassName).newInstance();
					players[i] = player1;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					System.out.println("Usage: ClassSimpleName");
				}
			}
		}
		if (player1 == null)
			System.exit(1);

		ArrayList<PlayerStrategy> al = new ArrayList<PlayerStrategy>();
		for (PlayerStrategy p: players)
		{
			al.add(p);
		}
		
		int bank1;
		
		int numCardsLeft = Deck.getSingleton().getNumCardsLeft();
		String[] playedCards = Deck.getSingleton().getPlayedCards();

		while (numPlayers > 0) // Play game while there are still players 
		{	
			//Iterate through each player
			for (Iterator<PlayerStrategy> iterator = al.iterator(); iterator.hasNext();)
			{
				player1 = iterator.next();
				
				bank1 = player1.getBank();
				String name = player1.getClass().getSimpleName();
				System.out.println("It is " + name + "'s turn.");
				
				// place bets
				int bet1 = player1.placeBet(bank1, playedCards, numCardsLeft);
				System.out.println(name + " Bank = " + bank1);
				System.out.println(name + " bets " + bet1 + "\n==========");
			
				
				//deal initial cards
				String[] playerCards = createEmptyHand(52);
				String[] dealerCards = createEmptyHand(52);
				String dealerUpCard = "";
				int nextShoeIndex = 0;
				int nextPlayerHandIndex = 2;
				int nextDealerHandIndex = 2;
				playerCards[0] = Deck.getSingleton().getCard();
				dealerCards[0] = Deck.getSingleton().getCard();
				playerCards[1] = Deck.getSingleton().getCard();
				dealerCards[1] = Deck.getSingleton().getCard();
				//numCardsLeft -= 4;
				dealerUpCard = dealerCards[1];
				//nextShoeIndex = 4;	
				// player's hand
				boolean doesPlayerHit = true;
				while (doesPlayerHit) {
					printCurrentHandInfo(playerCards);
					doesPlayerHit = player1.doesPlayerHit(playerCards, dealerUpCard);
					if (doesPlayerHit) {
						playerCards[nextPlayerHandIndex] = Deck.getSingleton().getCard();
						nextPlayerHandIndex++;
						System.out.println(name + " hits.");
						//printCurrentHandInfo(playerCards);
					}
					else
						System.out.println(name + " stands.");
					int points = BlackjackRules.countPoints(playerCards);
					if (points > 21)
						break;
				}
				
				// dealer's hand
				boolean doesDealerHit = true;
				while (doesDealerHit) {
					printCurrentHandInfo(dealerCards);
					doesDealerHit = BlackjackRules.doesDealerHit(dealerCards);
					if (doesDealerHit) {
						dealerCards[nextDealerHandIndex] = Deck.getSingleton().getCard();
						nextDealerHandIndex++;
						System.out.println("Dealer hits.");
					}
					else
						System.out.println("Dealer stands.");
				}

				int dealerPoints = BlackjackRules.countPoints(dealerCards);
				int playerPoints = BlackjackRules.countPoints(playerCards);
				System.out.println("==========\nDealer has: " + dealerPoints);
				System.out.println(name + " has: " + playerPoints);
				
				// figure out winner - this is not correct for all cases, but it is close enough to test with		
				if (dealerPoints > playerPoints && dealerPoints < 22 
						|| playerPoints > 21) {
					System.out.println(name + " lost!");
					player1.setBank(bank1-=bet1);
				} else if (dealerPoints == 21) {// check dealer blackjack
					if (dealerCards[2]=="0") {
						if (playerPoints == 21 && playerCards[2]=="0") {
							System.out.println("Push game!");
						}
						else {
							System.out.println("Dealer has Blackjack!!!");
							player1.setBank(bank1-=bet1);
						}
					} else if (playerPoints == dealerPoints){
						System.out.println("Push game!");
					}
				} else if(playerPoints == 21 && playerCards[2]=="0") { // check player blackjack
					System.out.println(name + " has Blackjack!!!");
					player1.setBank(bank1+= 1.5*bet1);
				} else if (playerPoints == dealerPoints){
					System.out.println("Push game!");
				} else {
					System.out.println(name + " won!");
					player1.setBank(bank1+= bet1);
				}
				
				if (bank1 <= 0)
				{
					System.out.println(name + " bank = " + bank1);
					iterator.remove(); 
					numPlayers--;
				}
				System.out.println("==========");
			}
		}
		
	}

	/**
	 * @param handSize - the number of card slots in the hand
	 * @return an array of empty card slots
	 */
	private static String[] createEmptyHand(int handSize) {
		// create empty array
		String[] hand = new String[handSize];
		for (int i=0; i<handSize; i++) {
			hand[i] = "0";
		}
		return hand;
	}

	/**
	 * @param shoeSize - the number of cards in the "shoe". This is usually a multiple of 52
	 * @return an array of cards representing a deck of the specified number of cards
	 */
	private static String[] createNewdeck(int shoeSize) {

		String[] deck = new String[shoeSize];
		Random random = new Random();
		for (int i=0; i<shoeSize; i++) {
			int rNum = random.nextInt(13);
			switch (rNum) {
			case 0:
				deck[i] = "K";
				break;
			case 1:
				deck[i] = "A";
				break;
			case 2:
				deck[i] = "2";
				break;
			case 3:
				deck[i] = "3";
				break;
			case 4:
				deck[i] = "4";
				break;
			case 5:
				deck[i] = "5";
				break;
			case 6:
				deck[i] = "6";
				break;
			case 7:
				deck[i] = "7";
				break;
			case 8:
				deck[i] = "8";
				break;
			case 9:
				deck[i] = "9";
				break;
			case 10:
				deck[i] = "10";
				break;
			case 11:
				deck[i] = "J";
				break;
			case 12:
				deck[i] = "Q";
				break;
			}
		}
		return deck;
	}
	
	/**
	 * @param cards - an array of cards to be printed
	 */
	private static void printCurrentHandInfo(String[] cards) {
		
		int totalPoints = BlackjackRules.countPoints(cards);
		System.out.print("\nCurrent Hand\nCards:");
		for (String card : cards) {
			if (!card.equals("0")) {
				System.out.print(" " + card);
			}
		}
		System.out.println("\nPoints: " + totalPoints);
	}
}
