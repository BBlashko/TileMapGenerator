package bblashko.levelgenerator.main;

//Author: Brett A. Blashko
//Date Created: April 17, 2014
//Last Modified: October 13, 2014

//Purpose: Read in 2 image files, a Tileset png, and a LevelMap png to create a TileMap text file for my 2D 
//       Java game (Dafon's World). Assists making new levels faster and easier.
//		 Resources need to be in a directory names "Resources" in the same directory as the .jar file
//		 In the Resources directory needs to be sub directories named "TileSets", "TileMaps" and "LevelMaps".


import javax.swing.JFrame;

public class Window {
	private static JFrame frame;
	
	public static void main(String [] args){
		//Create JFrame
		frame = new JFrame("Dafon's World: Level Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 500);
		frame.setContentPane(new Launcher(frame));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
}
