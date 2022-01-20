package battleShipProject;
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

public class BattleShip {
	static Pair head = new Pair(-1,-1);
	static Pair tail = new Pair(-1,-1);
	static boolean winnerDeclared;
	static boolean playerTurn;
	static volatile boolean targeting;
	static boolean destroyerPlaced, submarinePlaced, cruiserPlaced, battleshipPlaced, carrierPlaced;
	static volatile boolean allPlaced;
	static boolean headPicked, tailPicked;
	static boolean badTarget;
	static volatile boolean difficultyPicked;
	static int AIDifficulty;
	static int timerSeconds = 0;
	static int timerMinutes = 0;
	static String curShipPlace = "NULL";
	static HashSet<Pair> nextTargets = new HashSet<Pair>();
	static Player human, computer;
	
	public static class Player{
		String playerName;
		Ship destroyer;
		Ship submarine;
		Ship cruiser;
		Ship battleship;
		Ship carrier;
		int sunkenShips;
		int shotsTaken;
		int hits;
		int misses;
		char[][] playerGrid;
		
		public Player(String playerName) {// player class constructor
			this.playerName = playerName;
			destroyer = new Ship(2);
			submarine = new Ship(2);
			cruiser = new Ship(3);
			battleship = new Ship(4);
			carrier = new Ship(5);
			sunkenShips = 0;
			shotsTaken = 0;
			hits = 0;
			misses = 0;
			playerGrid = new char[10][10];
			for (int i = 0; i < playerGrid.length; i++) {
				for (int j = 0; j < playerGrid[0].length; j++) {
					playerGrid[i][j] = '.';
				}
			}
		}

		void randomlyPlace() {
			do {
				Random rand = new Random();
				head.row = rand.nextInt(10);
				head.col = rand.nextInt(10);

				int randOrientation = rand.nextInt(4);
				pickRandomTail(randOrientation, 5);
				if (validPlacement(computer, 'a',5)) {
					carrierPlaced = true;
				}
			} while (carrierPlaced == false);

			do {
				Random rand = new Random();
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
				pickRandomTail(randOrientation, 2);
				if (validPlacement(computer, 's',2)) {
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

			destroyerPlaced = false;
			submarinePlaced = false;
			cruiserPlaced = false;
			battleshipPlaced = false;
			carrierPlaced = false;

			File printTo = new File("shipLocation.txt");
			try {
				PrintWriter output = new PrintWriter(printTo);
				for (int r = 0; r < 10; r++) {
					for (int c = 0; c < 10; c++) {
						output.print(playerGrid[r][c]);
					}
					output.println();
				}
				output.close();
			} catch (FileNotFoundException e) {
			}
		}
		boolean fire(Player playerFiring, Player opponent, Pair coordinates) {
			if (opponent.playerGrid[coordinates.row][coordinates.col] == 'X'||opponent.playerGrid[coordinates.row][coordinates.col] == 'O'){
				status.setText("You have already fired here");
				badTarget = true;
				return false;
			}
			
			playerFiring.shotsTaken++;
			String shipHit = "";
			boolean sinkingHit = false;
			

			if(opponent.playerGrid[coordinates.row][coordinates.col] == '.') {
				status.setText(playerFiring.playerName+ " has missed");
				if(playerFiring.playerName=="Player") {
					rightGrid[coordinates.row][coordinates.col].setBackground(Color.RED);
				}
				else {
					playerButton[coordinates.row][coordinates.col].setBackground(Color.GREEN);
				}
				opponent.playerGrid[coordinates.row][coordinates.col] = 'O';
				playerTurn = playerTurn ? false : true;//invert the player's turn depending on if it's true or not
				playerFiring.misses++;
			} else {
				switch (opponent.playerGrid[coordinates.row][coordinates.col]) {
				case ('d'):
					shipHit = "destroyer";
					opponent.destroyer.partsHit++;
					if (opponent.destroyer.partsHit == opponent.destroyer.shipSize) {
						sinkingHit = true;
						opponent.sunkenShips++;
					}
					break;
				case ('s'):
					shipHit = "submarine";
					opponent.submarine.partsHit++;
					if (opponent.submarine.partsHit == opponent.submarine.shipSize) {
						sinkingHit = true;
						opponent.sunkenShips++;
					}
					break;
				case ('c'):
					shipHit = "cruiser";
					opponent.cruiser.partsHit++;
					if (opponent.cruiser.partsHit == opponent.cruiser.shipSize) {
						sinkingHit = true;
						opponent.sunkenShips++;
					}
					break;
				case ('b'):
					shipHit = "battleship";
					opponent.battleship.partsHit++;
					if (opponent.battleship.partsHit == opponent.battleship.shipSize) {
						sinkingHit = true;
						opponent.sunkenShips++;
					}
					break;
				case ('a'):
					shipHit = "carrier";
					opponent.carrier.partsHit++;
					if (opponent.carrier.partsHit == opponent.carrier.shipSize) {
						sinkingHit = true;
						opponent.sunkenShips++;
					}
					break;
				}
				opponent.playerGrid[coordinates.row][coordinates.col] = 'X';
				if(sinkingHit) {
					status.setText(playerFiring.playerName+" has sunk "+opponent.playerName+"'s"+shipHit);
					if(opponent.sunkenShips == 5) {
						winnerDeclared = true;
						status.setText(playerFiring.playerName+" has won the match");
					}
				}
				else {
					status.setText(playerFiring.playerName+" has hit "+opponent.playerName+"'s "+shipHit);
				}
				if(playerFiring.playerName=="Player") {
					rightGrid[coordinates.row][coordinates.col].setBackground(Color.GREEN);
				}
				else {
					playerButton[coordinates.row][coordinates.col].setBackground(Color.RED);
				}
				playerTurn = playerTurn ? false : true;
				playerFiring.hits++;
				return true;
			}
			return false;
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
			randomRow = (int) (Math.random() * 10);
			randomCol = (int) (Math.random() * 10);
			targetCoord.row = randomRow;
			targetCoord.col = randomCol;
		} while (!validTargets(targetCoord));
		computer.fire(computer, human, targetCoord);
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
		if (nextTargets.isEmpty()) {
			do {
				targetRow = (int) (Math.random() * 10);
				targetCol = (int) (Math.random() * 10);
				targetCoord.row = targetRow;
				targetCoord.col = targetCol;
			} while (!validTargets(targetCoord));
		} else {
			int i = 0;
			for (Pair coord : nextTargets) {
				i++;
				if (i == nextTargets.size()) {
					targetCoord.col = coord.col;
					targetCoord.row = coord.row;
				}
			}
			nextTargets.remove(targetCoord);
		}
		
		if (computer.fire(computer, human, targetCoord)) {
			if (validTargets(new Pair(targetCoord.row + 1, targetCoord.col))) {
				nextTargets.add(new Pair(targetCoord.row + 1, targetCoord.col));
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

	public static class Ship {
		int shipSize;
		int partsHit;

		public Ship(int shipSize) {
			this.shipSize = shipSize;
			this.partsHit = 0;
		}
	}

	public static class Pair {
		int row;
		int col;

		public Pair(int row, int col) {
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
	
	static boolean validPlacement(Player player, char shipTypeChar, int shipSize) {
		//check for diagonals(for players) or out of bounds (for AI)
		if((head.row-tail.row != 0 && head.col-tail.col != 0) || (tail.row < 0 || tail.row > 9 || tail.col < 0 || tail.col > 9)) {
			return false;
		}
		
		if ((Math.abs(head.row - tail.row) == 0 && Math.abs(head.col - tail.col) != (shipSize-1))//check if the head and tail are the correct distance from each other
			|| (Math.abs(head.row - tail.row) != (shipSize-1) && Math.abs(head.col - tail.col) == 0)) {
			return false;
		}
		
		if (head.row - tail.row == 0) {
			int begin = Math.min(head.col, tail.col);
			int end = Math.max(head.col, tail.col);

			for (int i = begin; i <= end; i++) {// check if there's any obstructions
				if (player.playerGrid[head.row][i] != '.') {
					return false;
				}
			}
			
			if (player.playerName.equals("Player")) {
				for (int i = begin; i <= end; i++) {
					playerButton[head.row][i].setBackground(Color.CYAN);
				}
			}
			
			for (int i = begin; i <= end; i++) {
				player.playerGrid[head.row][i] = shipTypeChar;
			}
		}
		
		else {
			int begin = Math.min(head.row, tail.row);
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
		}
		return true;
	}
	
	static void pickRandomTail(int randOrientation, int shipSize) {
		switch (randOrientation) {
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
	
	static boolean validTargets(Pair coordinates){//for the AI to check if the coordinate is a valid target
		if (coordinates.row < 0 || coordinates.row > 9 || coordinates.col < 0 || coordinates.col > 9
			|| human.playerGrid[coordinates.row][coordinates.col] == 'X'|| human.playerGrid[coordinates.row][coordinates.col] == 'O'){
			return false;
		}
		return true;
	}
	
	static void checkForShipPlacement(String action) {
		switch(action) {
		case("Place Destroyer"):
			if (!destroyerPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'd', 2)) {
					destroyerPlaced = true;
					status.setText("You have placed a Destroyer");
				} else {
					status.setText("Placement is invalid");
				}
			} else {
				status.setText("You have already placed a destroyer");
			}
			break;
		case("Place Submarine"):
			if (!submarinePlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 's', 2)) {
					submarinePlaced = true;
					status.setText("You have placed a Submarine");
				} else {
					status.setText("Placement is invalid");
				}
			} else {
				status.setText("You have already placed a submarine");
			}
			break;
		case("Place Cruiser"):
			if (!cruiserPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'c', 3)) {
					cruiserPlaced = true;
					status.setText("You have placed a Cruiser");
				} else {
					status.setText("Placement is invalid");
				}
			} else {
				status.setText("You have already placed a Cruiser");
			}
			break;
		case("Place Battleship"):
			if (!battleshipPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'b', 4)) {
					battleshipPlaced = true;
					status.setText("You have placed a Battleship");
				} else {
					status.setText("Placement is invalid");
				}
			} else {
				status.setText("You have already placed a Battleship");
			}
			break;
		case("Place Carrier"):
			if (!carrierPlaced) {
				if(!headPicked || !tailPicked) {
					return;
				}
				if (validPlacement(human, 'a', 5)) {
					carrierPlaced = true;
					status.setText("You have placed a Carrier");
				} else {
					status.setText("Placement is invalid");
				}
			} else {
				status.setText("You have already placed a Carrier");
			}
			break;
		}
		if (destroyerPlaced && submarinePlaced && cruiserPlaced && battleshipPlaced && carrierPlaced) {
			allPlaced = true;
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
	static JLabel timer = new JLabel("");
	static JLabel status = new JLabel("");
	
	static void initializeGUI() {

		mainFrame = new JFrame();
		mainFrame.setLayout(new BorderLayout());//mainframe has borderlayout
		mainFrame.setTitle("Battleship");//Title
		mainFrame.setSize(1500,1000);//window size	
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//program will terminate when the frame is closed

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(10,10));

		midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		midPanel.add(timer);
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(10,10));
		
		placeShips = new JButton[5];
		placeShips[0] = new JButton("Place Destroyer");
		placeShips[1] = new JButton("Place Submarine");
		placeShips[2] = new JButton("Place Cruiser");
		placeShips[3] = new JButton("Place Battleship");
		placeShips[4] = new JButton("Place Carrier");
		for(int i=0;i<placeShips.length;i++) {
			placeShips[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();
					status.setText("Pick a head and tail for the ship");
					curShipPlace = action;
					headPicked = false;
					tailPicked = false;
					checkForShipPlacement(action);
				}
			});
			midPanel.add(placeShips[i]);
		}
		easyDiff = new JButton("Easy AI");
		easyDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AIDifficulty = 0;
				difficultyPicked = true;
			}
		});
		expertDiff = new JButton("Expert AI");
		expertDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AIDifficulty = 1;
				difficultyPicked = true;
			}
		});
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
			for (int j = 0; j < rightGrid.length; j++) {
				rightGrid[i][j] = new JButton((Character.toString('A' + i)) + Integer.toString(j + 1));
				rightPanel.add(rightGrid[i][j]);
				rightGrid[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(e.getActionCommand().length()<2){
							return;
						} 
						if (targeting == true) {
							String label = e.getActionCommand();
							int ButtonRow = label.charAt(0) - 'A';
							int ButtonCol = Integer.parseInt(label.substring(1)) - 1;
							Pair p = new Pair(ButtonRow, ButtonCol);
							human.fire(human, computer, p);
						}
						targeting = false;
						if (badTarget == true) {
							targeting = true;
							badTarget = false;
						}
					}});
				playerButton[i][j] = new JButton((Character.toString('A' + i)) + Integer.toString(j + 1));
				playerButton[i][j].setOpaque(true);
				leftPanel.add(playerButton[i][j]);
				playerButton[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String action = e.getActionCommand();
						if (e.getActionCommand().length() < 2) {
							return;
						}
						// get the coordinates from the JButton and store as ButtonRow, ButtonCol
						int ButtonRow = action.charAt(0) - 'A';
						int ButtonCol = Integer.parseInt(action.substring(1)) - 1;
						if (headPicked && tailPicked) {
							headPicked = false;
							tailPicked = false;
						}
						if (!headPicked) {
							head = new Pair(ButtonRow, ButtonCol);
							headPicked = true;
						} else {
							tail = new Pair(ButtonRow, ButtonCol);
							tailPicked = true;
							checkForShipPlacement(curShipPlace);
						}
					}
				});
			}
		}
		
		mainFrame.add(leftPanel, BorderLayout.LINE_START);
		mainFrame.add(midPanel,BorderLayout.CENTER);
		mainFrame.add(rightPanel, BorderLayout.LINE_END);
		mainFrame.setVisible(true);
	}
	
	static boolean coinToss() {
		Random rand = new Random();
		return rand.nextInt(2) == 1 ? true : false;//returns true if the random number is 1, false if 0
		
	}
	static void startTimer() {
		Timer time = new Timer();
		time.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timerSeconds++;
				if(timerSeconds==60) {
					timerSeconds = 0;
					timerMinutes++;
				}
				String timerString = Integer.toString(timerMinutes)+":"+Integer.toString(timerSeconds/10)+Integer.toString(timerSeconds%10);
				timer.setText(timerString);
				
				playerShots.setText(human.playerName+"'s Shots: "+human.shotsTaken);
				playerHits.setText(human.playerName+"'s Hits: "+human.hits);
				playerMisses.setText(human.playerName+"'s Misses: "+human.misses);
				computerShots.setText(computer.playerName+"'s Shots: "+computer.shotsTaken);
				computerHits.setText(computer.playerName+"'s Hits: "+computer.hits);
				computerMisses.setText(computer.playerName+"'s Misses: "+computer.misses);
			}
		}, 0, 1000);
	}
	
	static void startGame() {
		human = new Player("Player");
		computer = new Player("Computer");
		computer.randomlyPlace();
		initializeGUI();
		startTimer();
		
		while(!allPlaced||!difficultyPicked) {
			try {
				Thread.sleep(200);
			} 	
			catch (InterruptedException e) {
			}
		}
		if(coinToss()){
			playerTurn = true;
			status.setText("You won the cointoss, the first turn is yours");
		}
		else {
			status.setText("You lost the cointoss, the enemy goes first");
		}
		
		while(!winnerDeclared){
			if(playerTurn){
				targeting = true;
				while(targeting) {
					try {
						Thread.sleep(200);
					}	 	
					catch (InterruptedException e) {
					}
				}
			}
			else{
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
		startGame();
	}
}
