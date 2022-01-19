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

@SuppressWarnings("unused")
public class BattleShip {
	static Pair head;
	static Pair tail;
	static boolean winnerDeclared;
	static boolean playerTurn;
	static volatile boolean targeting;
	static boolean destroyerPlaced, submarinePlaced, cruiserPlaced, battleshipPlaced, carrierPlaced;
	static volatile boolean allPlaced;
	static volatile boolean headPicked, tailPicked;
	static boolean badTarget;
	static volatile boolean difficultyPicked;
	static int AIDifficulty;
	static int timerSeconds = 0;
	static int timerMinutes = 0;
	static HashSet<Pair> nextTargets;
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
				//TODO GUI should change the label texts to indicate that [playerFiring] has already fired here
				badTarget = true;
				return false;
			}
			
			playerFiring.shotsTaken++;
			String shipHit = "";
			boolean sinkingHit = false;

			if(opponent.playerGrid[coordinates.row][coordinates.col] == '.') {
				//TODO GUI should change the label texts to indicate that [playerFiring] has missed
				//also change the GUI grid for the opponent to show the miss
				opponent.playerGrid[coordinates.row][coordinates.col] = 'O';
				playerTurn = playerTurn ? false : true;//invert the player's turn depending on if it's true or not
				playerFiring.misses++;
				//TODO change the JLabel for counting misses appropriately
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
					//TODO change the text of the JLabel to show that [playerFiring] has sunk [opponent]'s [shipHit]
					//also change the GUI grid for the opponent to show the hit
					if(opponent.sunkenShips == 5) {
						winnerDeclared = true;
						//TODO change the text of the JLabel to show that [playerFiring] has won the match
					}
				}
				else {
					//TODO change the text of the JLabel to show that [playerFiring] has hit [opponent]'s [shipHit]
				}
				playerTurn = playerTurn ? false : true;
				playerFiring.hits++;
				//TODO change the JLabel for counting hits appropriately
				return true;
			}
			return false;
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
	}
	
	static boolean validPlacement(Player player, char shipTypeChar, int shipSize) {
		//check for diagonals(for players) or out of bounds (for AI)
		if((head.row-tail.row != 0 && head.col-tail.col != 0) || (tail.row < 0 || tail.row > 9 || tail.col < 0 || tail.col > 9)) {
			return false;
		}
		
		if ((Math.abs(head.row - tail.row) == 0 && Math.abs(head.col - tail.col) != shipSize)//check if the head and tail are the correct distance from each other
			|| (Math.abs(head.row - tail.row) != shipSize && Math.abs(head.col - tail.col) == 0)) {
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
			// TODO GUI should also be updated correspondingly, perhaps change button background colours
			// but don't change the label themselves, since they're needed to find out what coordinates they're supposed to be
						
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
			// TODO GUI should also be updated correspondingly, perhaps change button background colours
			// but don't change the label themselves, since they're needed to find out what coordinates they're supposed to be

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
			tail.col = head.col - shipSize;
			break;
		case (1):
			tail.row = head.row;
			tail.col = head.col + shipSize;
			break;
		case (2):
			tail.row = head.row - shipSize;
			tail.col = head.col;
			break;
		case (3):
			tail.row = head.row + shipSize;
			tail.col = head.col;
			break;
		}
	}
	
	static boolean validTargets(Pair coordinates){//for the AI to check if the coordinate is a valid target
		if (coordinates.row < 0 || coordinates.row > 9 || coordinates.col < 0 || coordinates.col > 9
			|| human.playerGrid[coordinates.row][coordinates.col] != 'X'|| human.playerGrid[coordinates.row][coordinates.col] != 'O'){
			return false;
		}
		return true;
	}
	
	static void checkForShipPlacement(String action) {
		switch(action) {
		case("Place Destroyer"):
			if (!destroyerPlaced) {
				headPicked = false;
				tailPicked = false;
				while (!headPicked && !tailPicked) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
					}
				}
				if (validPlacement(human, 'd', 2)) {
					destroyerPlaced = true;
				} else {
					// TODO show on the JLabel that the placement is invalid
				}
			} else {
				// TODO show on the JLabel that they have already placed the ship
			}
			break;
		case("Place Submarine"):
			if (!submarinePlaced) {
				headPicked = false;
				tailPicked = false;
				while (!headPicked && !tailPicked) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
					}
				}
				if (validPlacement(human, 's', 2)) {
					submarinePlaced = true;
				} else {
					// TODO show on the JLabel that the placement is invalid
				}
			} else {
				// TODO show on the JLabel that they have already placed the ship
			}
			break;
		case("Place Cruiser"):
			if (cruiserPlaced) {
				headPicked = false;
				tailPicked = false;
				while (!headPicked && !tailPicked) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
					}
				}
				if (validPlacement(human, 'c', 3)) {
					cruiserPlaced = true;
				} else {
					// TODO show on the JLabel that the placement is invalid
				}
			} else {
				// TODO show on the JLabel that they have already placed the ship
			}
			break;
		case("Place Battleship"):
			if (!battleshipPlaced) {
				headPicked = false;
				tailPicked = false;
				while (!headPicked && !tailPicked) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
					}
				}
				if (validPlacement(human, 'b', 4)) {
					battleshipPlaced = true;
				} else {
					// TODO show on the JLabel that the placement is invalid
				}
			} else {
				// TODO show on the JLabel that they have already placed the ship
			}
			break;
		case("Place Carrier"):
			if (!carrierPlaced) {
				headPicked = false;
				tailPicked = false;
				while (!headPicked && !tailPicked) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
					}
				}
				if (validPlacement(human, 'a', 5)) {
					carrierPlaced = true;
				} else {
					// TODO show on the JLabel that the placement is invalid
				}
			} else {
				// TODO show on the JLabel that they have already placed the ship
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
	static JButton[] placeShips;
	
	static void initializeGUI() {
		placeShips = new JButton[5];
		placeShips[0] = new JButton("Place Destroyer");
		placeShips[1] = new JButton("Place Submarine");
		placeShips[2] = new JButton("Place Cruiser");
		placeShips[3] = new JButton("Place Battleship");
		placeShips[4] = new JButton("Place Carrier");
		
		for(int i=0;i<placeShips.length;i++) {
			// TODO this is just the actionlistener methods for now, but these buttons should be added to the middle panel later on
			placeShips[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();
					// TODO show on the JLabel that the user needs to pick a head and tail
					checkForShipPlacement(action);
				}
			});
		}// ship placement buttons end here
		
		
		
		
		
		//TODO still need to add difficulty buttons, JLabels, timer, grids, and the 3 Panels 
		
		// left Panel should be gridLayout, mid Panel could be boxLayout or flowLayout since it pretty much only has a few buttons and JLabels
		// right panel should also be gridLayout
	}
	// TODO create a static JFrame use the method above to initialize and add the components to the JFrame
	// MAKE SURE ALL THE COMPONENTS ARE STATIC, OTHERWISE THEY GET DELETO'D WHEN THE Initialize() METHOD ENDS
	// this method should also attach the actionListeners to all the buttons: placing ships, choosing AI difficulty
	// also including the player grid to help with ship placement and enemy grid to help with firing
	// Ask me (Ali) for questions regarding how to add an actionlistener to a specific button please, since it's very important to know how
	// if you want to make the GUI work with the code here
	
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
				//TODO set the text for the JLabel in the middle to [timerString], this will be done every second so it will be a proper timer
			}
		}, 0, 1000);
	}
	
	static void startGame() {
		human = new Player("Player");
		computer = new Player("Computer");
		computer.randomlyPlace();
		initializeGUI();
		startTimer();
		
		while(!allPlaced&&!difficultyPicked) {
			try {
				Thread.sleep(200);
			} 	
			catch (InterruptedException e) {
			}
		}
		if(coinToss()){
			playerTurn = true;
		}
		//TODO change the JLabel to indicate whether or not they won the cointoss
		
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
	
	/**
	 * code for the easy AI targeting algorithm
	 * name: easyAITargeting
	 * @param none
	 * @return void - procedure method
	 */
	static void easyAITargeting() {
		int randomRow = -1, randomCol = -1;
		Pair tragetCoord = new Pair(randomRow, randomCol);
		do{
			randomRow = (int)(Math.random()*10);
			randomCol = (int)(Math.random()*10);
			tragetCoord.row = randomRow;
			tragetCoord.col = randomCol;
		}while(!validTargets(tragetCoord));
		computer.fire(computer, human, tragetCoord);
	}

	/**
	 * code for the expert AI targeting algorithm
	 * name: expertAITargeting
	 * @param none
	 * @return void - procedure method
	 */
	static void expertAITargeting() {
		int targetRow = -1, targetCol = -1;
		Pair tragetCoord = new Pair(targetRow, targetCol);
		if(nextTargets.isEmpty()){
			do{
				targetRow = (int)(Math.random()*10);
				targetCol = (int)(Math.random()*10);
				tragetCoord.row = targetRow;
				tragetCoord.col = targetCol;
			}while(!validTargets(tragetCoord));
		}else{
			int i=0;
			for(Pair coord: nextTargets){
				if(i == nextTargets.size()){
					tragetCoord.col = coord.col;
					tragetCoord.row = coord.row;
				}
				i++;
			}
			nextTargets.remove(tragetCoord);
		}

		if(computer.fire(computer, human, tragetCoord)){
			if(validTargets(new Pair(targetRow+1, targetCol))){
				nextTargets.add(new Pair(targetRow+1, targetCol));
			}
			if(validTargets(new Pair(targetRow-1, targetCol))){
				nextTargets.add(new Pair(targetRow-1, targetCol));
			}
			if(validTargets(new Pair(targetRow, targetCol+1))){
				nextTargets.add(new Pair(targetRow, targetCol+1));
			}
			if(validTargets(new Pair(targetRow, targetCol-1))){
				nextTargets.add(new Pair(targetRow, targetCol-1));
			}
		}
	}

	public static void main(String[] args) {
		
	}
/* Some algorithms to keep in mind -------------------------------------------------------------------------------



actionperformed method for buttons on the opponent's grid(Event e){
	we can check if they are allowed to fire currently with: if(targeting == true){
		get the coordinate of the button pressed on the computer player's grid from its JButton Label, the coordinates should be called ButtonRow and ButtonCol respectively
		human.fire(computer, Pair{ButtonRow,ButtonCol})
		if(badTarget == true){
			targeting = false
			badTarget = false
		}
	}
}

actionperformed method for buttons on the human player grid(Event e){
	get the coordinates from the JButton label and store as ButtonRow, ButtonCol
	we can check if the user is picking a new set of head and tail by checking: if(headPicked && tailPicked){
		headPicked = false;
		tailPicked = false;
	}
	
	if(!headPicked){
		head = new Pair{ButtonRow,ButtonCol};
		headPicked = true;
	}
	else{
		tail = new Pair{ButtonRow,ButtonCol};
		tailPicked = true;
	}
}

actionperformed method for buttons on difficulty selection (Event e){
	if(difficultyPicked){
		show a message that they've already picked the difficulty
	}
	if(e.getActionCommand().equals("Easy")){
		AIDifficulty = 0;
	}
	else{
		AIDifficulty = 1;
	}
	difficultyPicked = true;
}
 */
}

