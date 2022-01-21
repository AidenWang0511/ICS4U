/*
 * Authors: Ali, Aiden, James, David, Daniel
 * Date: Jan. 17
 * The program to run the battleship board game
 */
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class BattleShip {
	static Pair head = new Pair(-1,-1);// Pair objects used to track ship head/tail placements
	static Pair tail = new Pair(-1,-1);
	static boolean winnerDeclared;//to check if there's a winner
	static boolean playerTurn;//to check if it's the player's turn
	static volatile boolean targeting;//volatile boolean that determines if the player is allowed to fire, it is volatile to allow changes to it to affect the main thread's constant while loop
	static boolean destroyerPlaced, submarinePlaced, cruiserPlaced, battleshipPlaced, carrierPlaced;//boolean to keep track of which ships are placed
	static volatile boolean allPlaced;//volatile boolean that determines if the all the ships on the player's side are placed, it is true only when all the boolean above are true
	static boolean headPicked, tailPicked;//to check if the head and tail have been picked by the player
	static boolean badTarget;//to check if the player's target is invalid
	static volatile boolean difficultyPicked;//to check if they've selected one of the difficulties
	static int AIDifficulty;//to determine which difficulty the player picked
	static int timerSeconds = 0;//timer variables
	static int timerMinutes = 0;
	static String curShipPlace = "NULL";//to track what ship is currently being placed
	static HashSet<Pair> nextTargets = new HashSet<Pair>();//used by the AI to check for optimal targets
	static Player human, computer;//player objects for both the human player and computer
	
	public static class Player{
		String playerName;//to keep track of which player this is
		Ship destroyer;//each player gets 5 ships respectively
		Ship submarine;
		Ship cruiser;
		Ship battleship;
		Ship carrier;
		int sunkenShips;//to keep track of how many of the player's own ships are sunk
		int shotsTaken;//to keep track of how many shits, hits, and misses
		int hits;
		int misses;
		char[][] playerGrid;//to keep track of the current state of the player's grid
		
		public Player(String playerName) {// player class constructor
			this.playerName = playerName;
			destroyer = new Ship(2);
			submarine = new Ship(3);
			cruiser = new Ship(3);
			battleship = new Ship(4);
			carrier = new Ship(5);
			sunkenShips = 0;
			shotsTaken = 0;
			hits = 0;
			misses = 0;
			playerGrid = new char[10][10];//initialize the char array
			for (int i = 0; i < playerGrid.length; i++) {
				for (int j = 0; j < playerGrid[0].length; j++) {
					playerGrid[i][j] = '.';// period indicates an empty space in the char array
				}
			}
		}
		/**
		 * method for the AI's ship placement 
		 * name: randomlyPlace()
		 * @param none
		 * @return void - procedure method
		 */
		void randomlyPlace() {
			do {
				Random rand = new Random();//pick two random integers between 0-9 for the head
				head.row = rand.nextInt(10);
				head.col = rand.nextInt(10);

				int randOrientation = rand.nextInt(4);//pick an integer between 0-3 to determine which orientation the tail will be
				pickRandomTail(randOrientation, 5);//call the method to pick a random tail
				if (validPlacement(computer, 'a',5)) {//check if the placement is valid with the randomly picked head and tail 
					carrierPlaced = true;//set the ship being placed to true
				}
			} while (carrierPlaced == false);// the do-while loop breaks after a valid placement

			do {
				Random rand = new Random();//same cases below but with other ships
				head.row = rand.nextInt(10);
				head.col = rand.nextInt(10);

				int randOrientation = rand.nextInt(4);
				pickRandomTail(randOrientation, 4);
				if (validPlacement(computer, 'b',4)) {
					battleshipPlaced = true;
				}
			} while (battleshipPlaced == false);

			do {
				Random rand = new Random();
				head.row = rand.nextInt(10);
				head.col = rand.nextInt(10);

				int randOrientation = rand.nextInt(4);
				pickRandomTail(randOrientation, 3);
				if (validPlacement(computer, 'c',3)) {
					cruiserPlaced = true;
				}
			} while (cruiserPlaced == false);

			do {
				Random rand = new Random();
				head.row = rand.nextInt(10);
				head.col = rand.nextInt(10);

				int randOrientation = rand.nextInt(4);
				pickRandomTail(randOrientation, 3);
				if (validPlacement(computer, 's',3)) {
					submarinePlaced = true;
				}
			} while (submarinePlaced == false);

			do {
				Random rand = new Random();
				head.row = rand.nextInt(10);
				head.col = rand.nextInt(10);

				int randOrientation = rand.nextInt(4);
				pickRandomTail(randOrientation, 2);
				if (validPlacement(computer, 'd',2)) {
					destroyerPlaced = true;
				}
			} while (destroyerPlaced == false);

			destroyerPlaced = false;//after all the ships are placed, reset the ship placement booleans to false so that it can be used for the human player
			submarinePlaced = false;
			cruiserPlaced = false;
			battleshipPlaced = false;
			carrierPlaced = false;

			File printTo = new File("shipLocation.txt");//initialize a file object to print the shipLocations to
			try {//try-catch to handle the printwriter
				PrintWriter output = new PrintWriter(printTo);//initialize the printwriter to the file object
				for (int r = 0; r < 10; r++) {//use a nested for loop to go through the 10x10 2d char array and write the chars to the file
					for (int c = 0; c < 10; c++) {
						output.print(playerGrid[r][c]);
					}
					output.println();//newline to format
				}
				output.close();//close the PrintWriter
			} catch (FileNotFoundException e) {
			}
		}
		
		/**
		 * method to handle firing on opponents, used by both the human and computer player
		 * name: fire()
		 * @param 
		 * playerFiring - the player that fired the shot
		 * opponent - the opposing player
		 * coorindates - the Pair with the row and column of where they're firing
		 * @return boolean
		 */
		boolean fire(Player playerFiring, Player opponent, Pair coordinates) {
			if (opponent.playerGrid[coordinates.row][coordinates.col] == 'X'||opponent.playerGrid[coordinates.row][coordinates.col] == 'O'){//check if they have already fired here
				status.setText("You have already fired here");
				badTarget = true;//set badtarget to true so that the player doesn't lose their turn
				return false;//return so that the player can try again
			}
			
			playerFiring.shotsTaken++;//increment the shotsTaken of the firing player
			String shipHit = "";//initialize a string that will contain the ship name
			boolean sinkingHit = false;//initialize a boolean that determines if the hit will sink the ship
			
			if(opponent.playerGrid[coordinates.row][coordinates.col] == '.') {//if the opponent's grid is empty
				status.setText(playerFiring.playerName+ " has missed");
				if(playerFiring.playerName.equals("Player")) {//if the player firing is the human
					rightGrid[coordinates.row][coordinates.col].setBackground(Color.RED);//change the colour of the AI's grid
				}
				else {//otherwise, it's the computer firing
					playerButton[coordinates.row][coordinates.col].setBackground(Color.GREEN);//change the colour of the human player's grid
				}
				opponent.playerGrid[coordinates.row][coordinates.col] = 'O';//set the player grid to O to indicate a miss
				playerTurn = !playerTurn;//invert the player's turn depending on if it's true or not
				playerFiring.misses++;//increment the miss counter
				playMissSound();//call the method to play the miss sound
			} 
			else {//otherwise, they hit something
				if(playerFiring.playerName.equals("Player")) {
					rightGrid[coordinates.row][coordinates.col].setBackground(Color.GREEN);//change the AI's grid if the player firing is human
				}
				else {
					playerButton[coordinates.row][coordinates.col].setBackground(Color.RED);//change the human's grid if it's the computer firing
				}
				
				switch (opponent.playerGrid[coordinates.row][coordinates.col]) {//switch case to determine what they it
				case ('d'):
					shipHit = "destroyer";
					opponent.destroyer.partsHit++;
					if (opponent.destroyer.partsHit == opponent.destroyer.shipSize) {//if all the parts of the ship are hit, it is sunk
						sinkingHit = true;
						for(Pair p:opponent.destroyer.location) {//go through the locations the ship takes up
							if(opponent.playerName.equals("Computer")) {//if the player getting hit is the computer
								rightGrid[p.row][p.col].setBackground(Color.gray);//set the JButton on their side to gray
							}
							else {//otherwise it is the human player
								playerButton[p.row][p.col].setBackground(Color.gray);//set the JButton on the human side to gray
							}
						}
						opponent.sunkenShips++;
					}
					break;
				case ('s')://same cases as above, but for the different ship types
					shipHit = "submarine";
					opponent.submarine.partsHit++;
					if (opponent.submarine.partsHit == opponent.submarine.shipSize) {
						sinkingHit = true;
						for(Pair p:opponent.submarine.location) {
							if(opponent.playerName.equals("Computer")) {
								rightGrid[p.row][p.col].setBackground(Color.gray);
							}
							else {
								playerButton[p.row][p.col].setBackground(Color.gray);
							}
						}
						opponent.sunkenShips++;
					}
					break;
				case ('c'):
					shipHit = "cruiser";
					opponent.cruiser.partsHit++;
					if (opponent.cruiser.partsHit == opponent.cruiser.shipSize) {
						sinkingHit = true;
						for(Pair p:opponent.cruiser.location) {
							if(opponent.playerName.equals("Computer")) {
								rightGrid[p.row][p.col].setBackground(Color.gray);
							}
							else {
								playerButton[p.row][p.col].setBackground(Color.gray);
							}
						}
						opponent.sunkenShips++;
					}
					break;
				case ('b'):
					shipHit = "battleship";
					opponent.battleship.partsHit++;
					if (opponent.battleship.partsHit == opponent.battleship.shipSize) {
						sinkingHit = true;
						for(Pair p:opponent.battleship.location) {
							if(opponent.playerName.equals("Computer")) {
								rightGrid[p.row][p.col].setBackground(Color.gray);
							}
							else {
								playerButton[p.row][p.col].setBackground(Color.gray);
							}
						}
						opponent.sunkenShips++;
					}
					break;
				case ('a'):
					shipHit = "carrier";
					opponent.carrier.partsHit++;
					if (opponent.carrier.partsHit == opponent.carrier.shipSize) {
						sinkingHit = true;
						for(Pair p:opponent.carrier.location) {
							if(opponent.playerName.equals("Computer")) {
								rightGrid[p.row][p.col].setBackground(Color.gray);
							}
							else {
								playerButton[p.row][p.col].setBackground(Color.gray);
							}
						}
						opponent.sunkenShips++;
					}
					break;
				}
				opponent.playerGrid[coordinates.row][coordinates.col] = 'X';//set opponent's playergrid to X to indicate a hit
				if(sinkingHit) {//if the hit sunk their ship
					status.setText(playerFiring.playerName+" has sunk "+opponent.playerName+"'s "+shipHit);//show it on the JLabel what ship has been sunk
					playSinkSound();//play the ship sinking sound
					if(opponent.sunkenShips == 5) {//if all 5 ships have been sunk
						winnerDeclared = true;//there is a winner
						status.setText(playerFiring.playerName+" has won the match");//change the JLabel to show who won the match
					}
				}
				else {//otherwise, it's a normal hit
					playFireSound();//play the firing sound
					status.setText(playerFiring.playerName+" has hit "+opponent.playerName+"'s "+shipHit);//show what ship was hit on the JLabel
				}
				playerTurn = !playerTurn;//invert the playerTurn
				playerFiring.hits++;
				return true;//return true so the AI knows it hit
			}
			return false;//return false so that the AI knows it missed
		}
	}
	
	/**
	 * code for the easy AI targeting algorithm
	 * name: easyAITargeting
	 * @param none
	 * @return void - procedure method
	 */
	static void easyAITargeting() {
		int randomRow = -1, randomCol = -1;
		Pair targetCoord = new Pair(randomRow, randomCol);
		do {
			randomRow = (int) (Math.random() * 10);//generate two random numbers between 0-9 for the row and column for the target
			randomCol = (int) (Math.random() * 10);
			targetCoord.row = randomRow;
			targetCoord.col = randomCol;
		} while (!validTargets(targetCoord));//do-while loop breaks when a valid target is generated
		computer.fire(computer, human, targetCoord);//fire at the target
	}

	/**
	 * code for the expert AI targeting algorithm
	 * name: expertAITargeting
	 * @param none
	 * @return void - procedure method
	 */
	static void expertAITargeting() {
		int targetRow = -1, targetCol = -1;
		Pair targetCoord = new Pair(targetRow, targetCol);
		if (nextTargets.isEmpty()) {//if there's no optimal targets
			do {
				targetRow = (int) (Math.random() * 10);
				targetCol = (int) (Math.random() * 10);
				targetCoord.row = targetRow;
				targetCoord.col = targetCol;
			} while (!validTargets(targetCoord));//same as easy AI for picking random targets
		} 
		else {//otherwise, focus on the optimal targets
			int i = 0;//initalize a counter to help remove the last element of the hashset
			for (Pair coord : nextTargets) {//go through the pairs in the hashset
				i++;
				if (i == nextTargets.size()) {//if this is the last element in the hashset
					targetCoord.col = coord.col;//set the target coordinates to the pair
					targetCoord.row = coord.row;
				}
			}
			nextTargets.remove(targetCoord);//remove it from the hashset
		}
		
		if (computer.fire(computer, human, targetCoord)) {//fire at the target, if it returned true, check for adjacent tiles 
			if (validTargets(new Pair(targetCoord.row + 1, targetCoord.col))) {//if the adjacent tile is a valid target
				nextTargets.add(new Pair(targetCoord.row + 1, targetCoord.col));//add it to the hashset
			}
			if (validTargets(new Pair(targetCoord.row - 1, targetCoord.col))) {
				nextTargets.add(new Pair(targetCoord.row - 1, targetCoord.col));
			}
			if (validTargets(new Pair(targetCoord.row, targetCoord.col + 1))) {
				nextTargets.add(new Pair(targetCoord.row, targetCoord.col + 1));
			}
			if (validTargets(new Pair(targetCoord.row, targetCoord.col - 1))) {
				nextTargets.add(new Pair(targetCoord.row, targetCoord.col - 1));
			}
		}
	}

	public static class Ship {//ship class to keep track of the ships's details
		int shipSize;//length of the ship
		int partsHit;//how many parts are hit
		Pair[] location;//the location it takes up

		public Ship(int shipSize) {//Ship constructor
			this.shipSize = shipSize;
			this.partsHit = 0;
			location = new Pair[shipSize];
		}
	}

	public static class Pair {//pair class to keep track of coordinates (row/columns)
		int row;
		int col;

		public Pair(int row, int col) {//Pair constructor
			this.row = row;
			this.col = col;
		}
		@Override
		public int hashCode() {//overridden hashcode method to facilitate java container methods such as .contains or .containsAll
			return Objects.hash(col, row);
		}
		@Override
		public boolean equals(Object obj) {//overridden equals method similar to the hashcode method
			if (this == obj)//if both objects point to the same memory location
				return true;
			if (obj == null)//if the object being compared is null
				return false;
			if (getClass() != obj.getClass())//if the object classes aren't equal
				return false;
			Pair other = (Pair) obj; //create a copy of obj
			return col == other.col && row == other.row;//compare if the primitives are equal
		}
	}
	/**
	 * method to check if the placement is valid as well as placing the ships if they are valid
	 * name: validPlacement()
	 * @param 
	 * player - the player trying to place their ship
	 * shipTypeChar - the character used to represent the ship type
	 * shipSize - to determine how long the ship is
	 * @return boolean
	 */
	static boolean validPlacement(Player player, char shipTypeChar, int shipSize) {
		//check for diagonals(for players) or out of bounds (for AI)
		if((head.row-tail.row != 0 && head.col-tail.col != 0) || (tail.row < 0 || tail.row > 9 || tail.col < 0 || tail.col > 9)) {
			return false;
		}
		
		if ((Math.abs(head.row - tail.row) == 0 && Math.abs(head.col - tail.col) != (shipSize-1))//check if the head and tail are the correct distance from each other
			|| (Math.abs(head.row - tail.row) != (shipSize-1) && Math.abs(head.col - tail.col) == 0)) {
			return false;
		}
		
		if (head.row - tail.row == 0) {//if the placement is horizontal
			int begin = Math.min(head.col, tail.col);//find the head and tail of the ship (although both is interchangable in a for loop)
			int end = Math.max(head.col, tail.col);

			for (int i = begin; i <= end; i++) {// check if there's any obstructions from head to tail
				if (player.playerGrid[head.row][i] != '.') {//if the grid is not empty
					return false;
				}
			}
			
			if (player.playerName.equals("Player")) {//if the human player is placing the ship
				for (int i = begin; i <= end; i++) {
					playerButton[head.row][i].setBackground(Color.CYAN);//set the buttons from head to tail to cyan
				}
			}
			
			for (int i = begin; i <= end; i++) {//go through from head to tail in the playergrid
				player.playerGrid[head.row][i] = shipTypeChar;//set the playerGrid to the shipTypeChar to indicate which ship has been placed at this coordinate
			}
			
			switch (shipTypeChar) {//switch-case for the shipTypeChar to determine which ship it is
			case ('d'):
				for (int i = begin; i <= end; i++) {//add all the coordinates between head and tail to the pair array
					player.destroyer.location[i - begin] = new Pair(head.row, i);
				}
				break;
			case ('s'):
				for (int i = begin; i <= end; i++) {//same case for all the different ship types below
					player.submarine.location[i - begin] = new Pair(head.row, i);
				}
				break;
			case ('c'):
				for (int i = begin; i <= end; i++) {
					player.cruiser.location[i - begin] = new Pair(head.row, i);
				}
				break;
			case ('b'):
				for (int i = begin; i <= end; i++) {
					player.battleship.location[i - begin] = new Pair(head.row, i);
				}
				break;
			case ('a'):
				for (int i = begin; i <= end; i++) {
					player.carrier.location[i - begin] = new Pair(head.row, i);
				}
				break;
			}
		}
		
		else {//otherwise, the placement is vertical
			int begin = Math.min(head.row, tail.row);//same case as above but for a vertical head and tail
			int end = Math.max(head.row, tail.row);
			for (int i = begin; i <= end; i++) {// check if there's any obstructions
				if (player.playerGrid[i][head.col] != '.') {
					return false;
				}
			}
			
			if (player.playerName.equals("Player")) {
				for (int i = begin; i <= end; i++) {
					playerButton[i][head.col].setBackground(Color.CYAN);
				}
			}
			
			for (int i = begin; i <= end; i++) {
				player.playerGrid[i][head.col] = shipTypeChar;
			}
			switch (shipTypeChar) {
			case ('d'):
				for (int i = begin; i <= end; i++) {//same case as above
					player.destroyer.location[i - begin] = new Pair(i, head.col);
				}
				break;
			case ('s'):
				for (int i = begin; i <= end; i++) {
					player.submarine.location[i - begin] = new Pair(i, head.col);
				}
				break;
			case ('c'):
				for (int i = begin; i <= end; i++) {
					player.cruiser.location[i - begin] = new Pair(i, head.col);
				}
				break;
			case ('b'):
				for (int i = begin; i <= end; i++) {
					player.battleship.location[i - begin] = new Pair(i, head.col);
				}
				break;
			case ('a'):
				for (int i = begin; i <= end; i++) {
					player.carrier.location[i - begin] = new Pair(i, head.col);
				}
				break;
			}
		}
		return true;//return true since the placement is valid
	}
	
	/**
	 * method to pick the tail randomly depending on the orientation
	 * name: pickRandomTail()
	 * @param 
	 * randOrientation - a random integer between 0-3 what determines what orientation the tail will be in
	 * shipSize - to determine how long the ship is
	 * @return void - procedure method
	 */
	static void pickRandomTail(int randOrientation, int shipSize) {
		switch (randOrientation) {//switch-case to check for which number it is, and randomly pick the tail [shipSize-1] tiles away
		case (0):
			tail.row = head.row;
			tail.col = head.col - shipSize-1;
			break;
		case (1):
			tail.row = head.row;
			tail.col = head.col + shipSize-1;
			break;
		case (2):
			tail.row = head.row - shipSize-1;
			tail.col = head.col;
			break;
		case (3):
			tail.row = head.row + shipSize-1;
			tail.col = head.col;
			break;
		}
	}
	
	/**
	 * method to check if the coordinate is a valid target for the AI
	 * name: validTargets()
	 * @param 
	 * coordinates - the Pair with the row and column to check
	 * @return boolean
	 */
	static boolean validTargets(Pair coordinates){
		if (coordinates.row < 0 || coordinates.row > 9 || coordinates.col < 0 || coordinates.col > 9
			|| human.playerGrid[coordinates.row][coordinates.col] == 'X'|| human.playerGrid[coordinates.row][coordinates.col] == 'O'){
			return false;
			//if it is out of bounds or already fired here, return false
		}
		return true;
	}
	
	/**
	 * method used in the actionPerformed method to check for the ship placement when a button is clicked
	 * name: checkForShipPlacement()
	 * @param 
	 * action - the label of the button indicating what they're trying to place
	 * @return void - procedure method
	 */
	static void checkForShipPlacement(String action) {
		switch(action) {//switch-case for action to determine which button it is
		case("Place Destroyer (1x2)"):
			if (!destroyerPlaced) {//if the destroyer hasn't been placed yet
				if(!headPicked || !tailPicked) {
					return;//return if both the head and tail haven't been picked
				}
				if (validPlacement(human, 'd', 2)) {//if the placement is valid with the selected head and tail
					destroyerPlaced = true;
					status.setText("You have placed a Destroyer");
					placeShips[0].setVisible(false);//remove the button for the ship after it has been placed
				} else {
					status.setText("Placement is invalid");//otherwise show that the placement is invalid
				}
			}
			break;
		case("Place Submarine (1x3)")://same case for the different ship types
			if (!submarinePlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 's', 3)) {
					submarinePlaced = true;
					status.setText("You have placed a Submarine");
					placeShips[1].setVisible(false);
				} else {
					status.setText("Placement is invalid");
				}
			}
			break;
		case("Place Cruiser (1x3)"):
			if (!cruiserPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'c', 3)) {
					cruiserPlaced = true;
					status.setText("You have placed a Cruiser");
					placeShips[2].setVisible(false);
				} else {
					status.setText("Placement is invalid");
				}
			}
			break;
		case("Place Battleship (1x4)"):
			if (!battleshipPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'b', 4)) {
					battleshipPlaced = true;
					status.setText("You have placed a Battleship");
					placeShips[3].setVisible(false);
				} else {
					status.setText("Placement is invalid");
				}
			}
			break;
		case("Place Carrier (1x5)"):
			if (!carrierPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'a', 5)) {
					carrierPlaced = true;
					status.setText("You have placed a Carrier");
					placeShips[4].setVisible(false);
				} else {
					status.setText("Placement is invalid");
				}
			} else {
				status.setText("You have already placed a Carrier");
			}
			break;
		}
		if (destroyerPlaced && submarinePlaced && cruiserPlaced && battleshipPlaced && carrierPlaced) {//if all the ships are placed
			allPlaced = true;//change the volatile boolean
		}
	}
	
/*
* GUI Components ------------------------------------------------------------------------------------------------------------------------
*/
	static JFrame mainFrame;
	static JPanel rightPanel;
	static JButton[] placeShips;
	static JButton easyDiff, expertDiff;
	static JButton[][] rightGrid = new JButton[10][10];
	static JButton[][] playerButton = new JButton[10][10];
	static JPanel midPanel;
	static JPanel leftPanel;
	static JLabel playerShots = new JLabel("Player's Shots: ");
	static JLabel playerHits = new JLabel("Player's Hits: ");
	static JLabel playerMisses = new JLabel("Player's Misses: ");
	static JLabel computerShots = new JLabel("Computer's Shots: ");
	static JLabel computerHits = new JLabel("Computer's Hits: ");
	static JLabel computerMisses = new JLabel("Computer's Misses: ");
	static JLabel timer = new JLabel("0:00");
	static JLabel status = new JLabel("");
	
	/**
	 * method to initialize the GUI and add all the components to the JFrame
	 * name: initializeGUI()
	 * @param none
	 * @return void - procedure method
	 */
	static void initializeGUI() {

		mainFrame = new JFrame();
		mainFrame.setLayout(new BorderLayout());//mainframe has borderlayout
		mainFrame.setTitle("Battleship");//Title
		mainFrame.setSize(1920,600);//window size	
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//program will terminate when the frame is closed

		rightPanel = new JPanel();//panel on the right side, responsible for the enemy grid
		rightPanel.setLayout(new GridLayout(10,10));

		midPanel = new JPanel();//panel in the middle, responsible for the info
		midPanel.setLayout(new GridLayout(20,0));
		midPanel.add(timer);
		
		leftPanel = new JPanel();//panel on the left side, responsible for the player grid
		leftPanel.setLayout(new GridLayout(10,10));
		
		placeShips = new JButton[5];//initalize the array of 5 buttons to place each 5 of the ships
		placeShips[0] = new JButton("Place Destroyer (1x2)");
		placeShips[1] = new JButton("Place Submarine (1x3)");
		placeShips[2] = new JButton("Place Cruiser (1x3)");
		placeShips[3] = new JButton("Place Battleship (1x4)");
		placeShips[4] = new JButton("Place Carrier (1x5)");
		for(int i=0;i<placeShips.length;i++) {//go through all 5 of the ship placement buttons
			placeShips[i].addActionListener(new ActionListener() {//add actionlisteners to all of these buttons
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();//get the button label
					status.setText("Pick a head and tail for the ship");//set the status to tell the user what to do
					curShipPlace = action;//set the current ship being placed to the label
					headPicked = false;//set both head and tail picked to false so the user can pick a fresh pair
					tailPicked = false;
					checkForShipPlacement(action);//call the method to check for the ship placement
				}
			});
			placeShips[i].setVisible(false);//make the buttons invisible at first
			midPanel.add(placeShips[i]);//add the buttons to the midPanel
		}
		easyDiff = new JButton("Easy AI");//button to pick easy difficulty
		easyDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AIDifficulty = 0;//set AIDifficulty to 0
				difficultyPicked = true;//change the volatile boolean
				for(JButton b:placeShips) {
					b.setVisible(true);//make all the ship placement buttons visible after selecting difficulty
				}
				easyDiff.setVisible(false);//make the difficulty buttons invisible 
				expertDiff.setVisible(false);
				status.setText("You have picked Easy difficulty");
				
			}
		});
		expertDiff = new JButton("Expert AI");//button to pick expert difficulty
		expertDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//same case as easy difficulty
				AIDifficulty = 1;//set AIDifficulty to 1 instead
				difficultyPicked = true;
				for(JButton b:placeShips) {
					b.setVisible(true);
				}
				easyDiff.setVisible(false);
				expertDiff.setVisible(false);
				status.setText("You have picked Expert difficulty");
			}
		});
		//add all the buttons and JLabels to the mid Panel with some spacing
		midPanel.add(Box.createRigidArea(new Dimension(0,30)));
		midPanel.add(easyDiff);
		midPanel.add(expertDiff);
		midPanel.add(Box.createRigidArea(new Dimension(0,30)));
		midPanel.add(playerShots); 
		midPanel.add(playerHits);
		midPanel.add(playerMisses);
		midPanel.add(computerShots);
		midPanel.add(computerHits);
		midPanel.add(computerMisses);
		midPanel.add(Box.createRigidArea(new Dimension(0,30)));
		midPanel.add(status);

		
		for (int i = 0; i < rightGrid.length; i++) {
			for (int j = 0; j < rightGrid.length; j++) {//go through the 2d JButton array to initialize them
				rightGrid[i][j] = new JButton((Character.toString('A' + i)) + Integer.toString(j + 1));
				//set the Label depending on the i and j counters to match with the battleship coordinate format
				rightPanel.add(rightGrid[i][j]);//add the button to the right grid
				rightGrid[i][j].setOpaque(true);
				rightGrid[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(e.getActionCommand().length()<2){//if the button is no longer a coordinate
							return;
						} 
						if (targeting == true) {//if the human player is allowed to target
							String label = e.getActionCommand();//get the button label
							int ButtonRow = label.charAt(0) - 'A';
							int ButtonCol = Integer.parseInt(label.substring(1)) - 1;//get the row and column from the button Label
							Pair p = new Pair(ButtonRow, ButtonCol);
							human.fire(human, computer, p);//fire at the coordinate the player clicked
						}
						targeting = false;//set the targeting to false after they've fired
						if (badTarget == true) {//if it was a invalid target
							targeting = true;//set targeting back to true so they don't lose their turn
							badTarget = false;//set badtarget back to false
						}
					}});
				
				//same case for the playerButton side
				playerButton[i][j] = new JButton((Character.toString('A' + i)) + Integer.toString(j + 1));
				playerButton[i][j].setOpaque(true);
				leftPanel.add(playerButton[i][j]);
				playerButton[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String action = e.getActionCommand();
						if (e.getActionCommand().length() < 2||allPlaced) {
							return;
						}
						// get the coordinates from the JButton and store as ButtonRow, ButtonCol
						int ButtonRow = action.charAt(0) - 'A';
						int ButtonCol = Integer.parseInt(action.substring(1)) - 1;
						if (headPicked && tailPicked) {//make the player pick a fresh pair
							headPicked = false;
							tailPicked = false;
						}
						if (!headPicked) {//if head hasnt been picked, they're picking a head
							head = new Pair(ButtonRow, ButtonCol);
							headPicked = true;
						} else {//otherwise, they're picking a tail
							tail = new Pair(ButtonRow, ButtonCol);
							tailPicked = true;
							checkForShipPlacement(curShipPlace);//call this method again since both the head and tail have been picked now
						}
					}
				});
			}
		}
		
		//add all the panels to the mainFrame
		mainFrame.add(leftPanel, BorderLayout.LINE_START);
		mainFrame.add(midPanel,BorderLayout.CENTER);
		mainFrame.add(rightPanel, BorderLayout.LINE_END);
		mainFrame.setVisible(true);
	}
	
	/**
	 * method to do a cointoss and returns a true/false with a 50/50 of each
	 * name: coinToss()
	 * @param none
	 * @return boolean
	 */
	static boolean coinToss() {
		Random rand = new Random();
		return rand.nextInt(2) == 1;//returns true if the random number is 1, false if 0
		
	}
	
	/**
	 * method to start the timer
	 * name: startTimer()
	 * @param none
	 * @return void - procedure method
	 */
	static void startTimer() {
		Timer time = new Timer();//initialize a new timer
		time.scheduleAtFixedRate(new TimerTask() {//schedule a timer task every second
			@Override
			public void run() {
				if (!winnerDeclared) {//if the winner hasn't been declared, the timer will keep incrementing
					timerSeconds++;//increment seconds
					if (timerSeconds == 60) {//if it has reached 60 second count
						timerSeconds = 0;//set it back to 0
						timerMinutes++;//increment the minute count by 1
					}
					//format timerSeconds and timerMinutes into a string
					String timerString = Integer.toString(timerMinutes) + ":" + Integer.toString(timerSeconds / 10) + Integer.toString(timerSeconds % 10);
					timer.setText(timerString);//set the timer JLabel to the timerString 

					//update the JLabel for tracking shots/misses/hits every second
					playerShots.setText(human.playerName + "'s Shots: " + human.shotsTaken);
					playerHits.setText(human.playerName + "'s Hits: " + human.hits);
					playerMisses.setText(human.playerName + "'s Misses: " + human.misses);
					computerShots.setText(computer.playerName + "'s Shots: " + computer.shotsTaken);
					computerHits.setText(computer.playerName + "'s Hits: " + computer.hits);
					computerMisses.setText(computer.playerName + "'s Misses: " + computer.misses);
				}
			}
		}, 0, 1000);
	}
	
	/**
	 * method to play "BGM.wav" and loop it
	 * name: startBGM()
	 * @param none
	 * @return void - procedure method
	 */
	static void startBGM() {
		Timer time = new Timer();//initialize a timer
		time.scheduleAtFixedRate(new TimerTask() {//schedule a timer task to play the BGM every 232 seconds --> BGM track length
			@Override
			public void run() {
				String soundName = "BGM.wav";    
				AudioInputStream audioInputStream = null;
				try {
					audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
				} catch (UnsupportedAudioFileException | IOException e1) {
					e1.printStackTrace();
				}
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e2) {
					e2.printStackTrace();
				}
				try {
					clip.open(audioInputStream);
				} catch (LineUnavailableException | IOException e1) {
					e1.printStackTrace();
				}
				clip.start();
			}
		}, 0, 232000);
	}
	
	/**
	 * method to play "miss.wav"
	 * name: playMissSound()
	 * @param none
	 * @return void - procedure method
	 */
	static void playMissSound() {
		String soundName = "miss.wav";    
		AudioInputStream audioInputStream = null;
		Clip clip = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e2) {
			e2.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException | IOException e1) {
			e1.printStackTrace();
		}
		clip.start();
	}
	/**
	 * method to play "sinkShip.wav"
	 * name: playSinkSound()
	 * @param none
	 * @return void - procedure method
	 */
	static void playSinkSound() {
		String soundName = "sinkShip.wav";    
		AudioInputStream audioInputStream = null;
		Clip clip = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e2) {
			e2.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException | IOException e1) {
			e1.printStackTrace();
		}
		clip.start();
	}
	
	/**
	 * method to play "fireGuns.wav"
	 * name: playFireSound()
	 * @param none
	 * @return void - procedure method
	 */
	static void playFireSound() {
		String soundName = "fireGuns.wav";//set the string to the filename
		AudioInputStream audioInputStream = null;
		Clip clip = null;//initialize clip
		
		//try-catches to handle the audio methods
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		}

		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e2) {
			e2.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException | IOException e1) {
			e1.printStackTrace();
		}
		clip.start();
	}
	
	/**
	 * method to run the game
	 * name: startGame()
	 * @param none
	 * @return void - procedure method
	 */
	static void startGame() {
		human = new Player("Player");//initialize the human and computer Players
		computer = new Player("Computer");
		computer.randomlyPlace();//call the method to randomly place ships for the AI
		initializeGUI();//call the gui to initialize and add all the components to the mainFrame
		startBGM();//start the BGM

		while(!allPlaced||!difficultyPicked) {//wait for the player to pick the difficulty and place their ships
			try {
				Thread.sleep(200);
			} 	
			catch (InterruptedException e) {
			}
		}
		if(coinToss()){//if the cointoss returns true
			playerTurn = true;//the player will get the first turn
			status.setText("You won the cointoss, the first turn is yours");
		}
		else {//otherwise, the computer goes first
			status.setText("You lost the cointoss, the enemy goes first");
		}
		
		startTimer();//start the timer
		
		while(!winnerDeclared){//while there isn't a winner yet, the while loop will keep the game logic running
			try {
				Thread.sleep(1000);//add a delay between each turn
			} catch (InterruptedException e1) {
			}
			if(playerTurn){//if it's the player's turn
				targeting = true;//let the player target
				while(targeting) {//infinitely loop while the player is allowed to target (wait for the player to pick a target)
					try {
						Thread.sleep(200);
					}	 	
					catch (InterruptedException e) {//the sleep will be interrupted when 'targeting' changes as it is volatile
					}
				}
			}
			else{//otherwise, let the computer target the human player
				if(AIDifficulty == 0){//0 indicates easy, 1 indicates expert
					easyAITargeting();
				}
				else{
					expertAITargeting();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		startGame();//call the method to start the game
	}
}
