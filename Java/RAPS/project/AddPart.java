package project;

import java.awt.*;
import java.sql.*;

import java.awt.event.*;
import javax.swing.*;

public class AddPart extends JFrame
{
	static final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	static final String DB_URL = "jdbc:odbc:final";

	private Connection connection;
	private Statement statement;
	private boolean connectstatus;

	private JTextField TFsku, TFprice, TFcondition, TFname, TFdesc, 
		TFvnumber, TFimage, TFcategory;
	private JTextField TFqty;
	private String Sname, Sdesc, Simage, Svnumber;
	private JComboBox CBstatus;
	private String status[] = {"NEW", "OLD"};
	private int Iqty, Icategory, Isku;
	private double Dprice;
	private boolean bsubmit;
	protected AddPart addPart;
	protected int store;
	public AddPart( int storeNumber )
	{
		super("ADD NEW PART");
		
		store = storeNumber;
		addPart = this;
		connectstatus = false;
		JLabel Lsku = new JLabel("Part Number");
		JLabel Lqty = new JLabel("Quantity");
		JLabel Lprice = new JLabel("Price $");
		JLabel Lname = new JLabel("Name");

		CBstatus = new JComboBox(status);
		CBstatus.setMaximumRowCount(2);
		JLabel Lvnumber = new JLabel("Vehicle Number");
		JLabel Lcategory = new JLabel("Category");
		JLabel Limage = new JLabel("Image");

		JLabel Ldesc = new JLabel("Description");

		JButton Bsubmit = new JButton("Submit");
        JButton Bcancel = new JButton("Cancel");

		Bsubmit.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent event )
			{
				int Ierror = 0;
				String Serror = "";
				bsubmit = true;

				try
				{
					Ierror = 0;
					if(TFsku.getText().equals("") || TFqty.getText().equals("")
						|| TFprice.getText().equals("") || TFname.getText().equals("")
						|| TFdesc.getText().equals("") || TFvnumber.getText().equals("")
						|| TFcategory.getText().equals(""))
					{
						throw new Exception();
					}

					Ierror = 1;
					if(!TFsku.getText().matches("\\d{6}"))
					{
						throw new Exception();
					}
					Isku = Integer.parseInt(TFsku.getText());

					Ierror = 2;
					Iqty = Integer.parseInt(TFqty.getText());
					if(Iqty < 0 || Iqty > 1000)
					{
						throw new Exception();
					}

					Ierror = 3;
			
					Dprice = Double.parseDouble(TFprice.getText());
					if( Dprice <= 0 )
					{
						throw new Exception();
					}
					
					Ierror = 4;
				
					if(!TFname.getText().matches("(([a-zA-Z]+|[a-zA-Z]+\\s+[a-zA-Z]*)+\\s+[a-zA-Z]*)") &&
						!TFname.getText().matches("[a-zA-Z]+|[a-zA-Z]+\\s+[a-zA-Z]*"))
					{
						throw new Exception();
					}
					Sname = TFname.getText();

					Ierror = 5;
					Svnumber = TFvnumber.getText();

					Ierror = 6;
					Icategory = Integer.parseInt(TFcategory.getText());

					Ierror = 7;
					Simage = TFimage.getText();

					Ierror = 8;
					Sdesc = TFdesc.getText();

					Class.forName(JDBC_DRIVER);
					connection = DriverManager.getConnection(DB_URL);
					connectstatus = true;
					statement = connection.createStatement();

					Ierror = 9;
					ResultSet resultSet = statement.executeQuery("SELECT " +						"pnumber FROM PART");
					while(resultSet.next())
					{
						if(resultSet.getInt("pnumber") == Isku)
						{
							throw new Exception();
						}
					}

					Ierror = 10;
					boolean eval = false;
					ResultSet resultSet2 = statement.executeQuery("SELECT vnumber " +
						"FROM VEHICLE");
					while(resultSet2.next() && !eval)
					{
						if(resultSet2.getString("vnumber").equals(Svnumber))
						{
							eval = true;
						}
					}
					if(!eval)
					{
						throw new Exception();
					}

					Ierror = 11;
					eval = false;
					ResultSet resultSet3 = statement.executeQuery("SELECT category " +
						"FROM CATEGORY");
					while(resultSet3.next() && !eval)
					{
						if(resultSet3.getInt("category") == Icategory)
						{
							eval = true;
						}
					}
					if(!eval)
					{
						throw new Exception();
					}
					Ierror = 12;
					statement.close();
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
					ResultSet updatePart = statement.executeQuery(
						"SELECT * FROM PART");
					updatePart.moveToInsertRow();
					updatePart.updateInt("pnumber", Isku);
					updatePart.updateString("vnumber", Svnumber);
					updatePart.updateInt("category", Icategory);
					updatePart.updateString("name", Sname);
					updatePart.updateDouble("cost", Dprice);
					updatePart.updateString("condition", status[CBstatus.getSelectedIndex()]);
					updatePart.updateString("image", Simage);
					updatePart.updateString("description", Sdesc);
					updatePart.insertRow();
					
					ResultSet updatePart2 = statement.executeQuery( 
						"SELECT * FROM INVENTORY");
					updatePart2.moveToInsertRow();
					updatePart2.updateInt( "store", store );
					updatePart2.updateInt( "pnumber", Isku );
					updatePart2.updateInt( "quantity", Iqty );
					updatePart2.insertRow();
					
					JOptionPane.showMessageDialog(null, "New Part Added", "Message",
						JOptionPane.INFORMATION_MESSAGE);
					addPart.hide();
				}
				catch(ClassNotFoundException cnfe)
				{
					System.err.println("Error loading " + JDBC_DRIVER);
				}
				catch(SQLException sqle)
				{
					System.err.println(sqle);
				}
				catch(Exception x)
				{
					bsubmit = false;
					switch(Ierror)
					{
						case 0:
							JOptionPane.showMessageDialog(null,
								"Please fill all fields","ERROR", JOptionPane.ERROR_MESSAGE);
							break;
						case 1:
							JOptionPane.showMessageDialog(null,
								"Enter valid 6 digit SKU","ERROR", JOptionPane.ERROR_MESSAGE);
							TFsku.setText("");
							break;
						case 2:
							JOptionPane.showMessageDialog(null,
								"Enter a valid Quantity","ERROR", JOptionPane.ERROR_MESSAGE);
							TFqty.setText("");
							break;
						case 3:
							JOptionPane.showMessageDialog(null,
								"Enter a valid Price","ERROR", JOptionPane.ERROR_MESSAGE);
							TFprice.setText("");
							break;

						case 4:
							JOptionPane.showMessageDialog(null,
								"Enter a valid Name","ERROR", JOptionPane.ERROR_MESSAGE);
							TFname.setText("");
							break;
						case 5:
							JOptionPane.showMessageDialog(null,
								"Enter a valid Vehicle Number","ERROR", 
								JOptionPane.ERROR_MESSAGE);
							break;
						case 6:
							JOptionPane.showMessageDialog(null,
								"Enter a valid Category Number (eg 1..12)","ERROR", 
								JOptionPane.ERROR_MESSAGE);
							TFdesc.setText("");
							break;
						case 7:
							JOptionPane.showMessageDialog(null,
								"Enter a valid Image or none","ERROR", 
								JOptionPane.ERROR_MESSAGE);
							TFdesc.setText("");
							break;
						case 8:
							JOptionPane.showMessageDialog(null,
								"Enter a valid description (eg. Motor for Honda)","ERROR", 
								JOptionPane.ERROR_MESSAGE);
							TFdesc.setText("");
							break;
						case 9:
	 						JOptionPane.showMessageDialog(null,
								"Part already exists","ERROR", JOptionPane.ERROR_MESSAGE);
							break;
						case 10:
							JOptionPane.showMessageDialog(null,
								"Vehicle number does not exist","ERROR", 
								JOptionPane.ERROR_MESSAGE);
							break;
						case 11:
							JOptionPane.showMessageDialog(null,
								"Category does not exist","ERROR", JOptionPane.ERROR_MESSAGE);
							break;
						default:
							JOptionPane.showMessageDialog(null,
								"One or more fields contain invalid text","ERROR", 
								JOptionPane.ERROR_MESSAGE);
							break;
					}

				}
				finally
				{	
					try
					{
						if(connectstatus)
						{
							statement.close();
							connection.close();
						}
					}
					catch(SQLException sqlException)
					{
						sqlException.printStackTrace();
					}
				}
			}
		});

		Bcancel.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent event )
			{
				bsubmit = false;
				TFsku.setText("");
				TFqty.setText("");
				TFprice.setText("");
				TFname.setText("");
				TFvnumber.setText("");
				TFcategory.setText("");
				TFimage.setText("");
				TFdesc.setText("");
				JOptionPane.showMessageDialog(null,
				"Add Part Cancelled","MESSAGE", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		TFsku = new JTextField(6);
		TFqty = new JTextField(3);
		TFprice = new JTextField(10);
		TFname = new JTextField(10);
		TFvnumber = new JTextField(8);
		TFcategory = new JTextField(2);
		TFimage = new JTextField(10);
		TFdesc = new JTextField(20);

		JPanel sku = new JPanel();
		JPanel qty = new JPanel();
		JPanel price = new JPanel();
		JPanel name = new JPanel();
		JPanel vnumber = new JPanel();
		JPanel category = new JPanel();
		JPanel image = new JPanel();
		JPanel desc = new JPanel();

		sku.add(Lsku);
		sku.add(TFsku);
		qty.add(Lqty);
		qty.add(TFqty);
		price.add(Lprice);
		price.add(TFprice);
		name.add(Lname);
		name.add(TFname);
		vnumber.add(Lvnumber);
		vnumber.add(TFvnumber);
		category.add(Lcategory);
		category.add(TFcategory);
		image.add(Limage);
		image.add(TFimage);
		desc.add(Ldesc);
		desc.add(TFdesc);

		JPanel submit = new JPanel();
		submit.add(Bsubmit);

		JPanel cancel = new JPanel();
		cancel.add(Bcancel);

		Container container = getContentPane();
		container.setLayout( new FlowLayout());
		container.add(sku);
		container.add(qty);
		container.add(price);
		container.add(name);
		container.add(vnumber);
		container.add(category);
		container.add(image);
		container.add(CBstatus);
		container.add(desc);
		container.add(submit);
		container.add(cancel);

	
		setSize(1000, 800);
		setVisible(true);
	}

}