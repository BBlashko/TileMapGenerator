package bblashko.levelgenerator.main;

//Author: Brett A. Blashko
//Date Created: April 17, 2014
//Last Modified: October 13, 2014

//Purpose: Set up a JFrame for user input. Basic launcher to get needed information about the level being generated. 
//		   Once Generate! is pushed call Generator class


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Launcher extends JPanel {
	//variables for JPanel and generator
	private static final long serialVersionUID = 1L;
	protected JFrame frame;
	
	//Fonts
	public Font generateFont;
	private Font titleFont;
	private Font secondFont;
	
	//file attributes
	public int levelHeight;
	public int levelWidth;
	public int tilesetWidth;
	public int tilesetHeight;
	private String tileWidth;
	private String tileHeight;
	private String destination;
	private String levelSource;
	private String tilesetSource;
	
	//Vertical Box layout
	private Box box;
	
	//declare JPanels
	private JPanel panel;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;	
	
	//declare JLabels
	private JLabel lbltitle;
	private JLabel lblsecondTitle;
	private JLabel lblemptyString;
	private JLabel lblemptyString2;
	private JLabel lblcomment;
	private JLabel lblauthor;
	private JLabel lblTileWidth;
	private JLabel lblTileHeight;
	private JLabel lblDestinationFile;
	private JLabel lblLevelSourceFile;
	private JLabel lblTileSetSourceFile;
	private JLabel lblHelp;

	//declare button
	private JButton generate;

	//declare textfields
	private JTextField tfTileWidth;
	private JTextField tfTileHeight;
	private JTextField tfDestinationFile;
	private JTextField tfLevelSourceFile;
	private JTextField tfTileSetSourceFile;
	
	//Generator variables
	public static int dWidth;
	public static int dHeight;
	public static int dTileWidth;
	public static int dTileHeight;
	public static int dTilesetHeight;
	public static int dTilesetWidth;
	
	//Generator files
	public static File fDestination;
	public static File fLevelSource;
	public static File fTileSetSource;
	
	//File containing images
	private File tsdirectory;
	private File lmdirectory;
	
	//list of images in the directories above
	private File[] tsList;
	private File[] lmList;
	
	//names of files above
	private String[] tsliststring;
	private String[] lmliststring;

	private int i;
	private int checkInputs;
	
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox1;	
	
	//constructor
	public Launcher(JFrame frame){
		super();
		setFocusable(true);
		requestFocus();
		this.frame = frame;
		init();
	}
	//when reseting program call this function first.
	public void setLFrame(){
		frame.setSize(400, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(this);
	}
	//Initialize of variables declared a
	public void init(){
		i = 0;
		checkInputs = 0;
		setDirectories();
		setFonts();
		setJLabels();
		setJButton();
		setJTextFields();
		setJPanels();
		setBox();
		addListeners();
		
		//add box and lblauthor to JPanel
		add(box, BorderLayout.NORTH);
		add(lblauthor, BorderLayout.SOUTH);
		System.out.println("init() completed\n");
	}
	//load the names of the LevelMap and TileSet images into Comboboxes
	public void setDirectories(){
		comboBox = new JComboBox<String>();
		comboBox1 = new JComboBox<String>();
		
		lmdirectory = new File("Resources/LevelMaps");
		tsdirectory = new File("Resources/TileSets");
		
		tsList = tsdirectory.listFiles();
		tsliststring = new String[tsList.length];
		for (int i = 0; i < tsList.length; ++i){
			tsliststring[i] = tsList[i].getName();
			comboBox.addItem(tsliststring[i]);
		}
		lmList = lmdirectory.listFiles();
		lmliststring = new String[lmList.length];
		for (int i = 0; i < lmList.length; ++i){
			lmliststring[i] = lmList[i].getName();
			comboBox1.addItem(lmliststring[i]);
		}
		System.out.println("Directories set");
	}
	//set all panels
	public void setJPanels(){
		panel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		
		panel.add(lblTileWidth);
		panel.add(tfTileWidth);
		panel2.add(lblTileHeight);
		panel2.add(tfTileHeight);
		panel3.add(lblTileSetSourceFile);
		panel3.add(comboBox);
		panel4.add(lblLevelSourceFile);
		panel4.add(comboBox1);
		panel5.add(lblDestinationFile);
		panel5.add(tfDestinationFile);		
		panel6.add(lblHelp);
		System.out.println("JPanels set");
	}
	//set fonts
	public void setFonts(){
		titleFont = new Font("Verdana", 1, 38);
		secondFont = new Font("Verdana", 2, 28);
		generateFont = new Font("Verdana", 0, 16);
		System.out.println("Fonts Set");
	}
	//set Labels
	public void setJLabels(){
		lbltitle = new JLabel("Dafon's World:");
		lbltitle.setFont(titleFont);
		lbltitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		lblsecondTitle = new JLabel("Level Generator");
		lblsecondTitle.setFont(secondFont);
		lblsecondTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	
		lblemptyString = new JLabel(" ");
		lblemptyString.setFont(secondFont);
		
		lblemptyString2 = new JLabel(" ");
		lblemptyString2.setFont(secondFont);
		
		lblauthor = new JLabel("Created by Brett A. Blashko", SwingConstants.RIGHT);
		
		lblcomment = new JLabel("** Please you enter all of the fields below **");
		lblcomment.setForeground(Color.red);
		lblcomment.setAlignmentX(CENTER_ALIGNMENT);
		
		lblTileWidth = new JLabel("Tile Width:     ");
		lblTileHeight = new JLabel("Tile Height:    ");
		
		lblDestinationFile = new JLabel("TileMap Destination Filename (.txt):");
		lblLevelSourceFile = new JLabel("Level Source Filename (.png):");
		lblTileSetSourceFile = new JLabel("TileSet Source Filename (.png):");
	
		lblHelp = new JLabel();
		lblHelp.setText("**One or more fields have been filled in incorrectly**");
		lblHelp.setForeground(Color.red);
		lblHelp.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		System.out.println("JLabels set");
	}
	//set the generate button
	public void setJButton(){
		generate = new JButton("Generate!");
		generate.setAlignmentX(CENTER_ALIGNMENT);
		generate.setLayout(null);
		generate.setPreferredSize(new Dimension(600, 40));
		generate.setFont(generateFont);
	}
	//set all textfields for user input
	public void setJTextFields(){
		tfTileWidth = new JTextField("", 12);
		tfTileHeight = new JTextField("", 12);
		tfDestinationFile = new JTextField("", 12);
		tfLevelSourceFile = new JTextField("", 12);
		tfTileSetSourceFile = new JTextField("", 12);	
		System.out.println("JTextFields set");
	}
	//add necessary components to the box
	public void setBox(){
		box = Box.createVerticalBox();
		box.add(lbltitle);
		box.add(lblsecondTitle);
		box.add(lblemptyString);
		box.add(lblcomment);
		box.add(panel);
		box.add(panel2);
		box.add(panel3);
		box.add(panel4);
		box.add(panel5);
		box.add(lblemptyString2);
		box.add(generate);
		box.add(lblHelp).setVisible(false);
		System.out.println("Box set");
	}
	//initialize the Listeners
	public void addListeners(){
		comboBox.addActionListener(new comboBoxListener());
		comboBox1.addActionListener(new comboBoxListener());
		generate.addActionListener(new ButtonPressedListener());
		tfTileWidth.addFocusListener(new tfTextListener());
		tfTileHeight.addFocusListener(new tfTextListener());
		tfDestinationFile.addFocusListener(new tfTextListener());
		tfLevelSourceFile.addFocusListener(new tfTextListener());
		tfTileSetSourceFile.addFocusListener(new tfTextListener());
		System.out.println("Listeners created");
	}
	//Listener for the LevelMap and TileSet image. set by user input
	public class comboBoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == comboBox){
				i = 2;
				String s = "Resources/TileSets/" + comboBox.getItemAt(comboBox.getSelectedIndex());
				fTileSetSource = new File(s);
				comboBox.setBackground(Color.green);
				checkInputs++;
			}
			if(e.getSource() == comboBox1){
				i = 3;
				String s = "Resources/LevelMaps/" + comboBox1.getItemAt(comboBox1.getSelectedIndex());
				fLevelSource = new File(s);
				comboBox1.setBackground(Color.green);
				checkInputs++;
			}
		}
	}
	//text listener to initilize tileheight tilewidth and the destination of the TileMap.txt file
	//checks for correct user input by calling stringType(s)
	public class tfTextListener implements FocusListener{
		public void focusGained(FocusEvent e) {
			if(e.getSource() == tfTileWidth){
				tfTileWidth.setBackground(Color.white);
				i = 0;
			}
			if(e.getSource() == tfTileHeight){
				tfTileHeight.setBackground(Color.white);
				i = 1;
			}
			if(e.getSource() == tfDestinationFile){
				tfDestinationFile.setBackground(Color.white);
				i = 4;
			}
		}
		public void focusLost(FocusEvent e) {

			if(e.getSource() == tfTileWidth){
				tileWidth = tfTileWidth.getText();
				stringType(tileWidth);
			}
			if(e.getSource() == tfTileHeight){
				tileHeight = tfTileHeight.getText();
				stringType(tileHeight);
			}
			if(e.getSource() == tfTileSetSourceFile){
				tilesetSource = tfTileSetSourceFile.getText();
				stringType(tilesetSource);
			}
			
			if(e.getSource() == tfLevelSourceFile){
				levelSource = tfLevelSourceFile.getText();
				stringType(levelSource);
			}
			if(e.getSource() == tfDestinationFile){
				destination = tfDestinationFile.getText();
				stringType(destination);
			}
		}
	}

	public void stringType(String str) {
		int length = 0;
		length = str.length();
		//check if inputs for tilewidth and tileheight are numbers.
		if (i >= 0 && i < 2) {
			try {
				//if a number set variables and set the color to green
				int d = (int)Double.parseDouble(str);
				if(i == 0){
					dTileWidth = d;
					tfTileWidth.setBackground(Color.green);
					checkInputs++;
					
				}else if(i == 1){
					dTileHeight = d;
					tfTileHeight.setBackground(Color.green);
					checkInputs++;

				}
				System.out.println("Entry is valid number");
				
				
			} catch (NumberFormatException nfe) {
				System.out.println("Entry is not valid number");
				//if not numbers set background to red
				if(i == 0){
					tfTileWidth.setBackground(Color.red);
				}else if(i == 1){
					tfTileHeight.setBackground(Color.red);
				}
			}
		} else {
			//if input is a string check to make sure it has correct file extensions.
			length = str.length();
			if (str.matches("[a-zA-Z._0-9]+") && length >= 4 && length != 0) {
				if(i == 2){
					if (str.substring(length - 4, length).equals(".png")) {
						System.out.println("Entry is a valid String");
						String file = ("Resources/TileSets/" + str);
						fTileSetSource = new File(file);
						if(fTileSetSource.exists()) { 
							tfTileSetSourceFile.setBackground(Color.green);
							checkInputs++;
						}else{
							tfTileSetSourceFile.setBackground(Color.red);
						}
					}
				}else if(i == 3){
					if (str.substring(length - 4, length).equals(".png")) {
						System.out.println("Entry is a valid String");
						String file = ("Resources/LevelMaps/" + str);
						fLevelSource  = new File(file);
						if(fLevelSource.exists()) { 
							tfLevelSourceFile.setBackground(Color.green);
							checkInputs++;
						}else{
							tfLevelSourceFile.setBackground(Color.red);
						}
					}
				}else if(i == 4){
					if (str.substring(length - 4, length).equals(".txt")) {
						System.out.println("Entry is a valid String");
						tfDestinationFile.setBackground(Color.green);
						String file = ("Resources/TileMaps/" + str);
						fDestination = new File(file);
						checkInputs++;
					}
				}
			} else {
				System.out.println("Entry is not a valid String");
				if(i == 2){
						tfTileSetSourceFile.setBackground(Color.red);
				}else if(i == 3){
						tfLevelSourceFile.setBackground(Color.red);
				}else if(i == 4){
						tfDestinationFile.setBackground(Color.red);
					
				}
			}
		}
	}
	//Listen for when the Generate JButton is pressed
	//once pressed start the Generator.
	public class ButtonPressedListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(checkInputs >= 5){
				new Generator(frame);
			}else{
				//not all fields were correctly filled out.
				//make the error message visible
				box.getComponent(11).setVisible(true);
				frame.repaint();				
			}
		}
	}	
}
