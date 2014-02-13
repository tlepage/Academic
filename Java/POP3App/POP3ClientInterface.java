//***************************************************************************//
// Thomas W. LePage xxx-xx-9586
// Class:   POP3ClientInterface
// Purpose: To provide the functionality for the Graphical User Interface
//***************************************************************************//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class POP3ClientInterface extends JFrame
{
	private JTextField 		serverNameField,
						  	userNameField,
						   	fromField,
						   	toField,
						   	subjectField,
						   	messageField;
	
	private JPasswordField 	passwordField;
	
	private JLabel			serverNameLabel,
							userNameLabel,
							passwordLabel,
							fromLabel,
							toLabel,
							subjectLabel,
							messageLabel;
						
	protected JTextArea 	messageArea;
	
	private JPanel  		messagePanel;
	
	private JButton			receiveButton,
							quitButton,
							sendButton;
		
	private Client			client;			
	
	public POP3ClientInterface()
	{
		super( "POP3 Mail Client" );
		
		// initialize text fields
		serverNameField = new JTextField(20);
		//serverNameField.setText("gamma2.uta.edu");
		userNameField = new JTextField(20);
		userNameField.setText("twl9586");
		passwordField = new JPasswordField(20);
		passwordField.setText("02158245");
		fromField = new JTextField(25);
		toField = new JTextField(25);
		subjectField = new JTextField(25);
		messageField = new JTextField(24);
		messageField.setEditable(false);
		
		// initialize labels
		serverNameLabel = new JLabel( "POP3 Server:" );
		userNameLabel = new JLabel( "User Name:   " );
		passwordLabel = new JLabel( "Password:     " );
		fromLabel = new JLabel( "     From:" );
		toLabel = new JLabel( "       To:   " );
		subjectLabel = new JLabel( "Subject:" );
		messageLabel = new JLabel( "Message:" );
		
		// initialize text area
		messageArea = new JTextArea(20, 29);
		messageArea.setWrapStyleWord( true );
		messageArea.setLineWrap( true );
		messageArea.setAutoscrolls( true );
		messageArea.setCaretColor( Color.BLUE );
		
		// initialize scroll panel
		messagePanel = new JPanel();
		messagePanel.setLayout( new FlowLayout() );
		messagePanel.add( new JScrollPane( messageArea ), BorderLayout.CENTER );
	
		// initialize buttons
		receiveButton = new JButton( "     Receive     " );
		receiveButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e) 
				{
					// if a field is left blank
					if( !checkFields() )
					{
						JOptionPane.showMessageDialog( null, 
							"Please fill in all fields" );
					}
					else
					{
						// create a client instance
						client.startClient(
							serverNameField.getText(),
							userNameField.getText(),
							passwordField.getText() );
							
						serverNameField.setEditable( false );
						userNameField.setEditable( false );
						passwordField.setEditable( false );
						receiveButton.setEnabled( false );
						messageField.setEditable(true);
						sendButton.setEnabled(true);
					}
				}
			}
		);
		
		// initialize quit button
		quitButton = new JButton( "     Quit     " );
		quitButton.addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					// close program when pressed
					System.exit(0);
				}
			}
		);
		
		// initialize send button
		sendButton = new JButton( "     Send     " );
		sendButton.setEnabled(false);
		sendButton.addActionListener(
			
			new ActionListener()
			{
				public void actionPerformed( ActionEvent e )
				{
					// if message field is not blank
					if( !messageField.equals("") )
					{
						// if message is RETR message
						if( messageField.getText().startsWith("RETR"))
						{
							// get text in message field
							String text = messageField.getText();
							
							// tokenize the String
							StringTokenizer tk = new StringTokenizer(text);
							
							// get first token
							text = tk.nextToken();
							
							// second token is message number, so acquire this
							int i = Integer.parseInt(tk.nextToken());
							
							// call getMail method
							client.getMail(i, toField, fromField, subjectField);
						}
						// if message is a LIST message
						else if( messageField.getText().equals("LIST"))
						{
							// get text in message field
							String text = messageField.getText();
							
							// call listMail method
							client.listMail(text);
						}
						// for any other type of message
						else
						{
							// write message to message area
							messageArea.append( "\n" + messageField.getText() 
								+ "\n" );
							
							// call sendMethod method
							client.sendMessage( messageField.getText() );
						}
						
						// clear message field
						messageField.setText("");
					}
					
				} 
			}
		);
		
		// setup container and add components
		Container container = getContentPane();
		container.setLayout( new FlowLayout() );
		
		container.add( serverNameLabel );
		container.add( serverNameField );
		
		container.add( userNameLabel );
		container.add( userNameField );
		
		container.add( passwordLabel );
		container.add( passwordField );
		
		container.add( receiveButton );
		container.add( sendButton );
		container.add( quitButton );
		
		container.add( fromLabel );
		container.add( fromField );
		
		container.add( toLabel );
		container.add( toField );
		
		container.add( subjectLabel );
		container.add( subjectField );
		
		container.add( messageLabel );
		container.add( messageField );
		
		container.add( messagePanel );
		
		// set size and make visible
		setSize( 350, 580 );
		setResizable(false);
		setVisible( true );
		
		client = new Client( messageArea );
	}
	
//**************************************************************************//
// Method:  displayClientMessages()
// Purpose: to display all messages sent from client on to the message area
// Input:   String representing the message from the client
// Output:  none
//***************************************************************************//	
	protected void displayClientMessages( String message )
	{
		// write to message area
		messageArea.append( "\n" + message + "\n" );
		
		// set the caret position to end of last line
		messageArea.setCaretPosition( messageArea.getText().length());
	}

//**************************************************************************//
// Method:  displayClientMessages()
// Purpose: to display all messages sent from client on to the message area
// Input:   String representing the message from the client
// Output:  none
//***************************************************************************//
	private boolean checkFields()
	{
		boolean rt = true;
		
		// if either field is empty
		if( serverNameField.getText().equals("") || 
			userNameField.getText().equals("") ||
			passwordField.getText().equals("") )
		{
			// set flag to false
			rt = false;
		}
		
		// return flag status
		return rt;
	}
	
	public static void main( String args[] )
	{
		POP3ClientInterface app = new POP3ClientInterface();
		app.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}	