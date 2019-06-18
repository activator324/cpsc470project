package project.cpsc470;

import java.util.Random;
import java.util.Iterator;
import java.util.Arrays;

class Deck
{
	private static Deck instance;

	private static String[] deck;
	private static String[] playedCards;
	private Iterator<String> i;

	private int nextShoeIndex;
	private int numCardsLeft;

	public static synchronized Deck getSingleton()
	{
		if (instance == null)
			instance = new Deck();
		return instance;
	}

	private Deck() { this(52); }

	private Deck(int shoeSize)
	{
		deck = new String[shoeSize];
		playedCards = new String[shoeSize];
		String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		for (int s = 0; s < 4; s++)
		{
		    nextShoeIndex = ranks.length * s;
		    for (int r = 0; r < ranks.length; r++)
		    {
			deck[nextShoeIndex] = ranks[r];
			nextShoeIndex++;
		    }
		}
		shuffle(shoeSize);
	}

	private void shuffle(int shoeSize)
	{
		nextShoeIndex = 0;
		numCardsLeft = shoeSize;

		int[] randomArray = new int[52];
		for (int i = 0; i < randomArray.length; i++)
		{
		    randomArray[i] = i;
		}
		for (int i = 0; i < deck.length; i++)
		{
		    int randomNumber = (int)(Math.random() * 52); //random number between 0 and 51
		    int randomElement = randomArray[randomNumber];
		    while (randomElement == -1) //checks to see if random number already used in deck
		    {
			randomNumber = (int)(Math.random() * 52); // generate new random number
			randomElement = randomArray[randomNumber];
		    }
		    String swappedCard = deck[i];
		    deck[i] = deck[randomNumber];
		    deck[randomNumber] = swappedCard;
		    randomArray[randomNumber] = -1;
		}

		i = Arrays.asList(deck).iterator();

	}

	String getCard() 
	{ 
		if (!i.hasNext())
			shuffle(52);
		String card = i.next();

		playedCards[nextShoeIndex] = card;
		
		nextShoeIndex++;
		numCardsLeft--;
		
		return card;
		 
	}
	
	public void runGame()
	{
		
	}

	String[] getPlayedCards() { return playedCards; }

	int getNumCardsLeft() { return numCardsLeft; } 
}
