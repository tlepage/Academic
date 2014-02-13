//***************************************************************************//
// Thomas W. LePage xxx-xx-9586
// Class:   Client
// Purpose: To provide the functionality for the client to interact with the 
//          POP3 mail server
//***************************************************************************//
import java.net.*;
import java.io.*;
import javax.swing.*;

public class Client
{	
	private Socket 				socket;
	
	private BufferedReader 		input;
	
	private PrintWriter 		output;
	
	private String				host,
								userName,
								password,
								currentOutput;
	
	private JTextArea			guiWrite;
	
//**************************************************************************//
// Method:  Client()
// Purpose: to construct a client instance
// Input:   a reference to the GUI's message area to display messages
// Output:  none
//***************************************************************************//		
	public Client( JTextArea textArea )
	{
		host = userName = password = currentOutput = null;	
		guiWrite = textArea;
	}
	
//**************************************************************************//
// Method:  startClient()
// Purpose: to connect to the POP3 server
// Input:   Strings representing the host name, user name, and password
// Output:  none
//***************************************************************************//	
	protected void startClient( String hostServer, String user, String pwd )
	{
		host = hostServer;
		userName = user;
		password = pwd;
		
		try
		{
			// call connectToServer() method to start communication
			connectToServer();
		}
		catch( EOFException eofException )
		{
			JOptionPane.showMessageDialog( null, 
				"Client terminated application." );
			System.err.println( "Client terminated application" );
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null, "Error in starting client." );
			e.printStackTrace();
		}
	}

//**************************************************************************//
// Method:  getServerMessage()
// Purpose: retrieves the server's response and passes it out to a string,
//          also updates the message area
// Input:   BufferedReader representing the server's message
// Output:  a String representing the server response
//***************************************************************************//	
	public void getServerMessage( BufferedReader in ) throws IOException
	{
		String serverResponse;
		
		try
		{
			// get server's response
			serverResponse = in.readLine();
			
			// write server's response on message area
			guiWrite.append( serverResponse + "\n" );
		}
		catch( IOException e)
		{
			throw new IOException( e.toString() );
		}
		
	}

//**************************************************************************//
// Method:  connectToServer()
// Purpose: to connect to the server by establishing the socket and streams
// Input:   none
// Output:  none
//***************************************************************************//	
	protected void connectToServer() throws IOException
	{	
		try
		{
			// create socket to communicate with port 110
			socket = new Socket( InetAddress.getByName( host ), 110 );
	
			// create BufferedReader to store server messages
			input = new BufferedReader( 
				new InputStreamReader(socket.getInputStream() ));
			
			// create PrintWriter to send client messages
			output = new PrintWriter( 
				socket.getOutputStream(), true );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		// acquire the server's output
		getServerMessage(input);
		
		// send user name to the server
		output.println( "USER " + userName );
		
		// acquire the server's output
		getServerMessage(input);
		
		// send password to the server
		output.println( "PASS " + password );
		
		// acquire the server's output
		getServerMessage(input);
	}

//**************************************************************************//
// Method:  sendMessage()
// Purpose: to send a client message to the server
// Input:   String representing the message from the client
// Output:  none
//***************************************************************************//	
	protected void sendMessage( String message )
	{
		try
		{
			// send client message to server
			output.println( message );
			
			// acquire the server's response
			getServerMessage(input);
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null, "Error in sending message" );
			e.printStackTrace();
		}
	}

//**************************************************************************//
// Method:  getMail()
// Purpose: to acquire mail messages and display them on the message area
// Input:   an integer representing the mail number, a reference to the 
//          To: JTextField, a reference to the From: JTextField, and a 
//          reference to the Subject: JTextField
// Output:  none
//***************************************************************************//	
	protected void getMail( int i, JTextField to, 
							JTextField from, JTextField sub )
	{
		try
		{
			// send client message to server
			output.println("RETR " + i );
			
			// write client message to message area
			guiWrite.append( "\nRETR " + i );
			
			// acquire the server's response
			getServerMessage(input);
				
			// get the first line of the server's response
			String currentLine = input.readLine();
			
			// while the server's response doesn't contain a .
			while(!currentLine.equals("."))
			{
				// if line starts with To:
				if( currentLine.startsWith("To:"))
				{
					// write this to the To: text field
					to.setText(currentLine.substring(4));
					
					// read next line of server's response
					currentLine = input.readLine();
				}
				// if line starts with From:
				else if( currentLine.startsWith( "From:"))
				{
					// write this to the From: text field
					from.setText(currentLine.substring(6));
					
					// read next line of server's response
					currentLine = input.readLine();
				}
				// if line starts with Subject:
				else if( currentLine.startsWith( "Subject:"))
				{
					// write this to the Subject: text field
					sub.setText(currentLine.substring(9));
					
					// read next line of server's response
					currentLine = input.readLine();
				}
				// otherwise...
				else
				{
					// write current line to the message area
					guiWrite.append(currentLine + "\n" );
					
					// read next line of server's response
					currentLine = input.readLine();
				}
			}
			
			// delete the message after it has been read
			output.println( "DELE " + i );
			
			// write the client message to the message area
			guiWrite.append( "\nDELE " + i + "\n" );
			
			// acquire the response from the server
			getServerMessage(input);
				
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null, "Error in acquiring mail." );
			e.printStackTrace();
		}
	}

//**************************************************************************//
// Method:  listMail()
// Purpose: to show proper listing of mail in the mailbox
// Input:   String representing the message string from the client
// Output:  none
//***************************************************************************//	
	protected void listMail( String list )
	{
		try
		{
			// send message to output stream
			output.println( list );
		
			// acquire the server's response
			getServerMessage(input);
			
			// write message to message area in GUI
			guiWrite.append( list + "\n" );
			
			// get the first line of the server's response
			String currentLine = input.readLine();
			
			// while the line doesn't contain only a .
			while(!currentLine.equals("."))
			{
				// output the the line to the message area
				guiWrite.append( currentLine + "\n" );
				
				// get next line in the server's response
				currentLine = input.readLine();
			}
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null, "Error in listing mail." );
			e.printStackTrace();
		}
	}
}
