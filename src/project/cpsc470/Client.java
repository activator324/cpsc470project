package project.cpsc470;
import java.io.IOException;
import java.io.*;
import java.net.*;

public class Client {

	private String clientName;
	private int portNumber;
	private Socket sock;
	private ServerSocket server;
	private PrintWriter pw;
	private InputStreamReader in;
	private BufferedReader br;
	
	public Client(String clientName, String ipAddress, int portNumber)
	{
		this.portNumber = portNumber;
		this.clientName = clientName;
		
		
		try {
			//This should accept the socket of the Player Host
			sock = new Socket(ipAddress, portNumber);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in = new InputStreamReader(sock.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		br = new BufferedReader(in);
		
		try {
			pw = new PrintWriter(sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pw.println(this.clientName);
		pw.flush();
	}
	
	
	
	public void sendInput(String input)
	{
		if(this.sock.isConnected())
		{
			
			pw.println(input);
			pw.flush();
		}
	}
	
	public Socket getSocket()
	{
		return this.sock;
	}
	
	public boolean connected()
	{
		if(this.sock.isConnected() == true)
		{
			return true;
		}
		else if(this.sock.isConnected() == false)
		{
			return false;
		}
		else
		{
			System.out.println("Ultra Failed");
			return false;
		}
	}
	
	public String serverInput()
	{
		
		String input = "none";
		
		try {
			input = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(input.equalsIgnoreCase(this.clientName))
		{
			try {
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (input.equalsIgnoreCase("notification"))
		{
			
			
			while(!input.equalsIgnoreCase(" "))
			{
				try {
					input = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(input.equalsIgnoreCase(" "))
					break;
				else
					System.out.println(input);
			}
		}
		
		
		return input;
	}
}
