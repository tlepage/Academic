/*
 * Created on Nov 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package project;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BrowsePage extends JFrame 
{
	final int tabNumber = 16;
	protected OrderTest order;
	protected Connection connection;
	
	public BrowsePage() 
	{
		super( "Browse RAPS" );
		
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:final";
		String userName = "";
		String password = "";
		
		Box boxes[] = new Box[tabNumber];	
		JButton buttons[] = new JButton[tabNumber];
		final String query[] = new String[tabNumber];
		final Statement statements[] = new Statement[tabNumber];
		final ResultSet resultSets[] = new ResultSet[tabNumber];
		final String allQuery = "SELECT * FROM PART";
		
		for( int i = 0; i < tabNumber; i++ )
		{
			statements[i] = null;
			boxes[i] = Box.createHorizontalBox();
		}
		for( int i = 0; i < tabNumber; i++ )
		{
			buttons[i] = new JButton("Submit");
			
		}
		for( int i = 0; i < tabNumber; i++ )
		{
			
			boxes[i].add( buttons[i] );
		}
		
		final JTabbedPane tabs = new JTabbedPane(
			JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );
			
		tabs.addTab( "Brakes and Traction Control", boxes[0]  );
		tabs.addTab( "Climate Control", boxes[1] );
		tabs.addTab( "Collision, Body Parts, and Hardware", boxes[2] );
		tabs.addTab( "Drivetrain", boxes[3] );
		tabs.addTab( "Electrical", boxes[4] );
		tabs.addTab( "External Engine", boxes[5] );
		tabs.addTab( "Fuel Delivery", boxes[6] );
		tabs.addTab( "Internal Engine", boxes[7] );
		tabs.addTab( "PowerTrain and Engine Management", boxes[8] );
		tabs.addTab( "Routine Maintenance", boxes[9] );
		tabs.addTab( "Starting, Charging, and Tune-up", boxes[10] );
		tabs.addTab( "Suspension", boxes[11] );
		tabs.addTab( "Tools and Manuals", boxes[12] );
		tabs.addTab( "Accessories", boxes[13] );
		tabs.addTab( "Vehicle Cross-Reference", boxes[14] );
		tabs.addTab( "All Parts", boxes[15] );
		
		getContentPane().add( tabs );
		
		buttons[0].addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					query[0] = "SELECT pnumber, vnumber, name, cost, " +						"condition, description FROM PART WHERE category " +						"LIKE '" + 1 + "'";
					new BrowseTable( query[0], tabs.getTitleAt(0) );
				}
			}
		);
		buttons[1].addActionListener(
		
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					query[1] = "SELECT pnumber, vnumber, name, cost, " +
						"condition, description FROM PART WHERE category " +
						"LIKE '" + 2 + "'";
					new BrowseTable( query[1], tabs.getTitleAt(1) );
				}
			}
		);
		buttons[2].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[2] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 3 + "'";
							new BrowseTable( query[2], tabs.getTitleAt(2) );
						}
					}
				);
		buttons[3].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[3] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 4 + "'";
							new BrowseTable( query[3], tabs.getTitleAt(3) );
						}
					}
				);
		buttons[4].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[4] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 5 + "'";
							new BrowseTable( query[4], tabs.getTitleAt(4) );
						}
					}
				);
		buttons[5].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[5] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 6 + "'";
							new BrowseTable( query[5], tabs.getTitleAt(5) );
						}
					}
				);
		buttons[6].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[6] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 7 + "'";
							new BrowseTable( query[6], tabs.getTitleAt(6) );
						}
					}
				);
		buttons[7].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[7] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 8 + "'";
							new BrowseTable( query[7], tabs.getTitleAt(7) );
						}
					}
				);
		buttons[8].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[8] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 9 + "'";
							new BrowseTable( query[8], tabs.getTitleAt(8) );
						}
					}
				);
		buttons[9].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[9] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 10 + "'";
							new BrowseTable( query[9], tabs.getTitleAt(9) );
						}
					}
				);
		buttons[10].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[10] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 11 + "'";
							new BrowseTable( query[10], tabs.getTitleAt(10) );
						}
					}
				);
		buttons[11].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[11] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 12 + "'";
							new BrowseTable( query[11], tabs.getTitleAt(11) );
						}
					}
				);
		buttons[12].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[12] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 13 + "'";
							new BrowseTable( query[12], tabs.getTitleAt(12) );
						}
					}
				);
		buttons[13].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[13] = "SELECT pnumber, vnumber, name, cost, " +
								"condition, description FROM PART WHERE category " +
								"LIKE '" + 14 + "'";
							new BrowseTable( query[13], tabs.getTitleAt(13) );
						}
					}
				);
		buttons[14].addActionListener(
		
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							query[14] = "SELECT vnumber, make, year, model, " +								"engine, description FROM VEHICLE";
							new BrowseTable( query[14], tabs.getTitleAt(14) );
						}
					}
				);
		buttons[15].addActionListener(
		
					new ActionListener()
					{		
						public void actionPerformed(ActionEvent e)
						{
							query[15] = "SELECT pnumber, vnumber, name, condition," +								" description FROM PART";
							new BrowseTable( query[15], tabs.getTitleAt(15) );
						}
					}
				);
		
		setSize( 500, 300 );
		setVisible( true );
		setResizable( false );
	}
}


