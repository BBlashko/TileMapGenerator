package bblashko.levelgenerator.main;

//Author: Brett A. Blashko
//Date Created: April 17, 2014
//Last Modified: October 13, 2014

//Purpose: Compares each pixel in each tile of the tileset image with each pixel of each tile of the LevelMap image. If a
//         match is found output it to a TileMap.txt file. Else if mismatched print to screen in red or yellow depending on
//         number of mismatched pixels.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Generator extends Launcher{
	private static final long serialVersionUID = 1L;
	
	//counters and variables
	private boolean tilesuccess = false;
	private int highestcheck = 0;
	private int hightilenum = 0;
	private int check;
	private int count = 0;
	private int missed = 0;
	private int lvlnumCols;
	private int lvlnumRows;
	private int tsnumCols;
	private int tsnumRows;
	
	//needed image and image[]
	public BufferedImage[] tiles;
	private BufferedImage currentTile;
	private BufferedImage currentTiles;
	private BufferedImage tileset;
	private BufferedImage levelset;
	
	//outpute writer to file
	public static PrintWriter output;
	
	//ArrayLits for the colors of each pixel in each tile
	private ArrayList<Color[][]> tilesetcolors;
	private ArrayList<Color[][]> levelcolors;

	//attributes for the Styled Document
	private StyledDocument doc;
	private SimpleAttributeSet success;
	private SimpleAttributeSet failed;
	private SimpleAttributeSet possible;
	private SimpleAttributeSet file;
	
	//Visual variables
	private JButton Reset;
	private JTextPane textpane;
	private JScrollPane scrollpane;
	
	//contructor
	public Generator(JFrame frame) {
		super(frame);
		initG();
		doc = textpane.getStyledDocument();
		
		try {
			//output number and strings generated
			doc.insertString(0, "Starting...\n", null);
			System.out.println("Output file = " + fDestination);
			output = new PrintWriter(fDestination);
			setTileSet();
			setLevel();
			tileComparison();
			output.close();
			doc.insertString(doc.getLength(), "\nNumber of unmatched tiles = " + missed, null);
			doc.insertString(doc.getLength(), "\n\nGenerator has Finished press \"Reset\" to get back to launcher", null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("number of <100% matched tiles is " + missed);
		System.out.println("Comparison has finished");
		System.out.println("Waiting for Exit prompt or Reset prompt...");
		
		
	}
	//initialize variables
	public void initG(){
		System.out.println("Starting...");
		frame.setSize(new Dimension(800, 500));
		frame.setLocationRelativeTo(null);
		frame.getContentPane().removeAll();
		
		//initialize textpane and scrollpane for visual output to screen
		textpane = new JTextPane();
		scrollpane = new JScrollPane(textpane);
		scrollpane.setPreferredSize(new Dimension(700, 400));
		
		//initialize button to restart the Launcher
		Reset = new JButton("Reset!");
		Reset.setAlignmentX(CENTER_ALIGNMENT);
		Reset.setLayout(null);
		Reset.setFont(generateFont);
		Reset.addActionListener(new ButtonPressedListener());
		
		//Add components to JPanel
		frame.add(scrollpane, BorderLayout.CENTER);
		frame.add(Reset, BorderLayout.PAGE_END);
		
		//initalize attributes for StyledDocument
		success = new SimpleAttributeSet();
		failed = new SimpleAttributeSet();
		possible = new SimpleAttributeSet();
		file = new SimpleAttributeSet();

		//set Colors of attributes
		StyleConstants.setForeground(success, Color.green);
		StyleConstants.setForeground(failed, Color.red);
		StyleConstants.setForeground(possible, Color.yellow);
		StyleConstants.setForeground(file, Color.blue);

		//initialize arraylist for tile pixels
		tilesetcolors = new ArrayList<Color[][]>();
		levelcolors = new ArrayList<Color[][]>();
	}
	//Compare each tile of Tileset with each tile of LevelMap by comparing each pixel in both files
	//if 100% match output tile number to txt file and output green tile number to screen
	//else if almost exact match output number to txt file and output yellow tile number to screen
	//else not a match and output "!" to txt file and output red "!" to screen
	public void tileComparison() {
		try {
			//write information of files that are generated
			doc.insertString(doc.getLength(), "Starting TileComparison...\n", null);
			output.println(lvlnumCols);
			output.println(lvlnumRows);
			doc.insertString(doc.getLength(),"Number of Level Columns = " + lvlnumCols + "\n" , null);
			doc.insertString(doc.getLength(),"Number of Level Rows = " + lvlnumRows + "\n" , null);
			
			doc.insertString(doc.getLength(),"Outputed TileMap to ", null);
			doc.insertString(doc.getLength(), fDestination + " \n",file);
 
			doc.insertString(doc.getLength(),"check the output below. \n\n", null);
			
			doc.insertString(doc.getLength(),"GREEN", success);
			doc.insertString(doc.getLength()," = tile was successfully found in tileset\n", null);

			doc.insertString(doc.getLength(),"RED", failed);
			doc.insertString(doc.getLength()," = tile was not found in tileset\n", null);
			
			doc.insertString(doc.getLength(),"YELLOW", possible);
			doc.insertString(doc.getLength()," = tile was almost an exact match to a tile in the tileset (Consider checking)\n\n\n", null);

			doc.insertString(doc.getLength(), lvlnumCols + "\n", null);
			doc.insertString(doc.getLength(), lvlnumRows + "\n", null);
			
			check = 0;
			for (int i = 0; i < levelcolors.size(); i++) {
				tilesuccess = false;
				//tile from LevelMap
				Color[][] leveltile = new Color[dTileWidth][dTileHeight];
				leveltile = levelcolors.get(i);
				for (int j = 0; j < tilesetcolors.size(); j++) {
					check = 0;
					
					//tile from TileSet
					Color[][] tilesettile = new Color[dTileWidth][dTileHeight];
					tilesettile = tilesetcolors.get(j);
					
					//check how many pixels match
					for (int x = 0; x < dTileWidth; x++) {
						for (int y = 0; y < dTileHeight; y++) {
							if (leveltile[x][y].equals(tilesettile[x][y])) {
								check++;
							} else {
								break;
							}
						}
					}
					//if 100% match output number of tile to file and green number of tile to screen
					if (check == (dTileWidth * dTileHeight)) {
						tilesuccess = true;
						count++;
						
						//decides where to put a nextline after a number is outputed
						if (count >= lvlnumCols) {
							
							output.println(j + " ");
							doc.insertString(doc.getLength(), j + " \n", success);
							
							count = 0;
						} else {
						
							output.print(j + " ");
							doc.insertString(doc.getLength(),j + " " , success);
						}
						break;
					}
					
					if (check > highestcheck) {
						highestcheck = check;
						hightilenum = j;
					}

				}
				//if tiles are not a 100% match find out how close of a match and output correct color to screen
				//ouput number of "!" to file.
				if (tilesuccess == false) {
					if (highestcheck > dTileWidth * dTileHeight - 100) {
						count++;
						if (count >= lvlnumCols) {
							output.println(hightilenum + " ");
							
							doc.insertString(doc.getLength(),hightilenum + " \n" , possible);
							count = 0;
						} else {
							output.print(hightilenum + " ");
							doc.insertString(doc.getLength(),hightilenum + " " , possible);
						}
					}
					else{
						count++;
						if (count >= lvlnumCols) {
							output.println("! ");
							doc.insertString(doc.getLength(), "! \n" , failed);
							count = 0;
						}else{
							output.print("! ");
							doc.insertString(doc.getLength(),"! " , failed);

						}
					}
					//number of not 100% matched tiles
					System.out.println("tile " + i	+ " is not an exact match. Check = " + highestcheck + " closest tile = " + hightilenum);
					missed++;
				}
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//load all the tiles in the Level Map
	public void setLevel() throws BadLocationException {
		try {
			doc.insertString(doc.getLength(), "Loading LevelMap...", null);
			levelset = ImageIO.read(fLevelSource);
			levelWidth = levelset.getWidth();
			levelHeight = levelset.getHeight();
			
			tiles = new BufferedImage[levelWidth*levelHeight];
			lvlnumCols = levelWidth/dTileWidth;
			lvlnumRows = levelHeight/dTileHeight;
			
			for (int i = 0; i < lvlnumRows; i++) {
				for (int j = 0; j < lvlnumCols; j++) {

					currentTile = levelset.getSubimage(j * dTileHeight, i * dTileWidth,
							dTileWidth, dTileHeight);
					Color[][] tilecolor = new Color[dTileWidth][dTileHeight];

					for (int x = 0; x < dTileHeight; x++) {
						for (int y = 0; y < dTileWidth; y++) {
							tilecolor[x][y] = new Color(
									currentTile.getRGB(x, y));
						}
					}
					levelcolors.add(tilecolor);
				}
			}
			System.out.println("levelcolors INITIALIZED, # of tiles = "
					+ levelcolors.size());
		} catch (Exception e) {
			e.printStackTrace();
			doc.insertString(doc.getLength(),"FAILED\n" , failed);
		}
		doc.insertString(doc.getLength(), "SUCCESS\n", success);
	}
	//Load all the tiles in the Tileset
	public void setTileSet() throws BadLocationException {

		try {
			doc.insertString(doc.getLength(), "Loading TileSet...", null);
			tileset = ImageIO.read(fTileSetSource);
			
			tilesetWidth = tileset.getWidth();
			tilesetHeight = tileset.getHeight();
			
			tsnumCols = tilesetHeight/dTileHeight;
			tsnumRows = tilesetWidth/dTileWidth;
			
			for (int i = 0; i < tsnumCols; i++) {
				for (int j = 0; j < tsnumRows; j++) {

					currentTiles = tileset.getSubimage(j * dTileWidth, i * dTileHeight, dTileWidth, dTileHeight);
					Color[][] tilecolor = new Color[dTileWidth][dTileHeight];

					for (int x = 0; x < dTileHeight; x++) {
						for (int y = 0; y < dTileWidth; y++) {
							tilecolor[x][y] = new Color(currentTiles.getRGB(x,y));
						}
					}
					tilesetcolors.add(tilecolor);
				}
			}
			System.out.println("tilesetcolors INITIALIZED, # of tiles = "
					+ tilesetcolors.size());
		} catch (Exception e) {
			e.printStackTrace();
			doc.insertString(doc.getLength(), "FAILED\n", failed);
		}
		doc.insertString(doc.getLength(),"SUCCESS\n" , success);
	}
	public class ButtonPressedListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			frame.getContentPane().removeAll();
			setLFrame();
			new Launcher(frame);
		}
	}	
}
