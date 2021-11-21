/*
File Name: AidenWangISP
Author: Aiden Wang
Date: Nov 10, 2021
Description: Maze generator and solver
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.Flow;

public class AidenWangMazeAssignment extends JFrame implements ActionListener{

    public static char[][] maze = new char[20][20]; //2d array to store the char of the maze for each cell
    public static boolean[][] vis = new boolean[20][20]; //2D array used in recursion to keep track of visited cells
    public static boolean[][] check = new boolean[20][20]; //2D array used in recursion to keep track of cells that lead to exit
    public static int maxR = 20; //stores max row
    public static int maxC = 20; //stores max column
    public static char barrierChar = 'B'; //stores barrier char
    public static char startChar = 'S'; //stores start char
    public static char exitChar = 'X'; //stores exit char
    public static char openChar = 'O'; //stores open char
    public static int startR = 0; //stores the row the mouse starts at
    public static int startC = 0; //stores the column the mouse starts at

    public static JPanel homePan = new JPanel(); //panel for the home screen
    //label to display instructions for user
    public static JLabel uiLabel = new JLabel("<html>Welcome to my a Maze ing game! Please Select an option to generate maze:<br/> <br/>Maximize screen for better user experience</html>");
    public static JButton btnUserGen = new JButton("Randomly Generate"); //button to randomly generate maze
    public static JButton btnFileGen = new JButton("Generate Maze From File"); //button to generate maze from file
    
    public static JPanel userGen = new JPanel(); //panel for user generated maze
    public static JLabel warnLab = new JLabel("The dimension must be bigger than 2x3 or 3x2 and less than 20x20, unqualified number will turn ORANGE!"); //label to display instructions for user
    public static JLabel rowPrompt = new JLabel("Enter the number of rows of the maze"); //label to display instructions for user
    public static JLabel colPrompt = new JLabel("Enter the number of columns of the maze"); //label to display instructions for user
    public static JTextField rowInput = new JTextField(10); //textfield for user to enter the number of rows
    public static JTextField colInput = new JTextField(10); //textfield for user to enter the number of columns
    public static JButton btnGenerate = new JButton("Generate"); //button to start generating the maze

    public static JPanel fileGen = new JPanel(); //panel for file generated maze
    public static JLabel fileLab = new JLabel("Enter the file name of the maze:"); //label to display instructions for user
    public static JTextField fileInput = new JTextField(10); //textfield for user to enter the file name
    public static JButton btnFileGenerate = new JButton("Continue"); //button to start generating the maze
    public static String mazeFileName = new String(); //stores the file name of the maze

    public static JPanel mazePanel = new JPanel(); //panel for displaying and solving the maze
    public static JLabel[][] mazeLabel = new JLabel[20][20]; //2D array of labels to display the maze
    public static JPanel mazeUIPanel = new JPanel(); //panel to display the UI elements
    public static JButton btnSolve = new JButton("Solve Maze"); //button to solve the maze
    public static JButton btnHome = new JButton("Return Home"); //button to return to home screen
    public static JLabel mazeLab = new JLabel("Pick an option:"); //label to display instructions for user

    public void actionPerformed(ActionEvent event){ //action performed for each button
        String command = event.getActionCommand();
        if(command.equals("Randomly Generate")){ //if user wants to randomly generate maze, the only visible panel should be userGen 
            homePan.setVisible(false);
            userGen.setVisible(true);
        }else if(command.equals("Generate Maze From File")){ //if user wants to generate maze from file, the only visible panel should be fileGen
            homePan.setVisible(false);
            fileGen.setVisible(true);
            
        }else if(command.equals("Generate")){ //when click the generate button, the user input will be checked and the maze will be generated
            boolean isValid = false;
            isValid = dimensionPrompt();
            if(isValid){ //only generate the maze if the dimension is valid
                mazeGenGUI();//starts to generate and display maze
            }
        }else if(command.equals("Continue")){ //when click the continue button, the file name will be checked and the maze will be generated
            boolean isValid = false;
            isValid = filePrompt(); //methods that prompts the file name and return a boolean to check if it is valid
            if(isValid){ //only continue if the file is valid
                fileGen.setVisible(false);
                try{
                    fileGenGUI(); //starts to generate and display maze
                }catch(Exception e){
                    fileLab.setText("File not found! Please try again!!!");
                }
            }
        }else if(command.equals("Return Home")){ //when click the return home button, the home screen will be displayed
            returnHome(); //method that returns to home screen
        }else if(command.equals("Solve Maze")){ //when click the solve button, the maze will be solved
            boolean isSolved = solveMaze(startR, startC); //method that solves the maze and returns a boolean to check if it is solved
            if(isSolved){
                //if the maze is solved, it will upadte the UI to display the solution
                mazeLab.setText("Maze Solved!");
                mazeLab.setOpaque(true);
                mazeLab.setBackground(Color.GREEN);
                //nested for loop that update all cells that lead to exit to blue and '+'
                for(int i=0; i<maxR; i++){
                    for(int j=0; j<maxC; j++){
                        if(check[i][j] && maze[i][j] != startChar){
                            mazeLabel[i][j].setBackground(new Color(173,216,230));
                            mazeLabel[i][j].setText("+");
                        }
                    }
                }
            }else{
                //if the maze is not solved, it will upadte the UI to inform the user it's unsolvable
                mazeLab.setText("Maze Unsolvable!");
                mazeLab.setOpaque(true);
                mazeLab.setBackground(Color.RED);
            }
        }
    }
    

    public AidenWangMazeAssignment() {
        //set up the basic settings of the JFrame
        setTitle("An A Maze ing Program");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //creating action listener for every button
        btnGenerate.addActionListener(this);
        btnSolve.addActionListener(this);
        btnHome.addActionListener(this);
        btnUserGen.addActionListener(this);
        btnFileGen.addActionListener(this);
        btnFileGenerate.addActionListener(this);

        //set up the home screen
        homePan.setLayout(new GridLayout(3,0));
        uiLabel.setHorizontalAlignment(SwingConstants.CENTER);
        uiLabel.setVerticalAlignment(SwingConstants.CENTER);
        uiLabel.setOpaque(true);
        uiLabel.setBackground(Color.lightGray);
        uiLabel.setFont(new Font("Comic Sans", Font.BOLD, 24));
        btnFileGen.setFont(new Font("Comic Sans", Font.BOLD, 24));
        btnUserGen.setFont(new Font("Comic Sans", Font.BOLD, 24));
        homePan.add(uiLabel);
        homePan.add(btnUserGen);
        homePan.add(btnFileGen);
        
        //set up the user generated maze screen
        userGen.setLayout(new GridLayout(5,2));
        rowInput.setPreferredSize(new Dimension(30, 30));
        colInput.setPreferredSize(new Dimension(30, 30));
        rowPrompt.setFont(new Font("Comic Sans", Font.BOLD, 18));
        colPrompt.setFont(new Font("Comic Sans", Font.BOLD, 18));
        userGen.add(rowPrompt);
        userGen.add(rowInput);
        userGen.add(colPrompt);
        userGen.add(colInput);
        userGen.add(warnLab);
        userGen.add(btnGenerate);

        //set up the file generated maze screen
        fileGen.setLayout(new GridLayout(5,0));
        fileLab.setFont(new Font("Comic Sans", Font.BOLD, 20));
        fileLab.setHorizontalAlignment(SwingConstants.CENTER);
        fileInput.setPreferredSize(new Dimension(30, 30));
        btnFileGenerate.setFont(new Font("Comic Sans", Font.BOLD, 20));
        fileGen.add(fileLab);
        fileGen.add(fileInput);
        fileGen.add(btnFileGenerate);

        //set up the maze UI panel
        mazeUIPanel.setLayout(new GridLayout(0,3));
        btnHome.setFont(new Font("Comic Sans", Font.BOLD, 20));
        btnSolve.setFont(new Font("Comic Sans", Font.BOLD, 20));
        mazeLab.setFont(new Font("Comic Sans", Font.BOLD, 20));
        mazeLab.setHorizontalAlignment(SwingConstants.CENTER);
        mazeUIPanel.add(mazeLab);
        mazeUIPanel.add(btnHome);
        mazeUIPanel.add(btnSolve);

        //add all the panels to the JFrame
        add(homePan);
        add(userGen);
        add(fileGen);
        add(mazePanel);
        add(mazeUIPanel);

        //initialize the JFrame so only the home screen is visible
        homePan.setVisible(true);
        userGen.setVisible(false);
        fileGen.setVisible(false);
        mazePanel.setVisible(false);
        mazeUIPanel.setVisible(false);
        setVisible(true);

    }

    /**
     * This method is called from within the constructor to assist the process of prompting dimensions.
     * method name: dimensionPrompt
     * @param: none
     * @return: boolean - returns if the input is valid or not
     */
    public static boolean dimensionPrompt() throws NumberFormatException{
        boolean isValid = false;
        try {
            //get user's input from the textfield
            int row = Integer.parseInt(rowInput.getText());
            int col = Integer.parseInt(colInput.getText());
            boolean sizeCheck = (row>=2 && col>=3) || (row>=3 && col>=2); //boolean to check if the input is valid and within the range
            if(!(row<=20) || !(col<=20) || !sizeCheck){
                throw new NumberFormatException(); //if the input is not valid, throw exception
            }else{
                //if the input is valid, update labels
                rowInput.setBackground(Color.WHITE);
                colInput.setBackground(Color.WHITE);
                isValid = true;
            }
        }catch(NumberFormatException e1) {
            rowInput.setBackground(Color.ORANGE); //try catch so the whole section will turn orange if it's invalid input
            colInput.setBackground(Color.ORANGE); //try catch so the whole section will turn orange if it's invalid input
        }
        if(isValid){
            //if the input is valid, update the global variables to store the max row and column
            maxC = Integer.parseInt(colInput.getText());
            maxR = Integer.parseInt(rowInput.getText());
        }
        return isValid; //return if the input is valid or not
    }

    /**
     * prompt user for maze dimensions and randomly generate the maze as required
     * method name: mazeGen
     * @return void - procedure method
     */
    public static void mazeGen(){
        //set the barriers for the left and right walls
        for(int i=0; i<maxR; i++){
            maze[i][0] = barrierChar;
            maze[i][maxC-1] = barrierChar;
        }
        //set the barriers for the top and bottom walls
        for(int i=0; i<maxC; i++){
            maze[0][i] = barrierChar;
            maze[maxR-1][i] = barrierChar;
        }

        Random rand = new Random(); //create a random object

        //special case where the maze is 3x2 or 2x3
        if(maxR==3 && maxC==2){
            int randNum = rand.nextInt(2);
            //only 2 orientations are possible
            if(randNum == 0){
                maze[1][0] = startChar;
                maze[1][1] = exitChar;
                startR = 1;
                startC = 0;
            }else{
                maze[1][0] = exitChar;
                maze[1][1] = startChar;
                startR = 1;
                startC = 1;
            }
            return; //return to end the method
        }else if(maxR==2 && maxC==3){
            int randNum = rand.nextInt(2);
            //only 2 orientations are possible
            if(randNum == 0){
                maze[0][1] = startChar;
                maze[1][1] = exitChar;
                startR = 0;
                startC = 1;
            }else{
                maze[0][1] = exitChar;
                maze[1][1] = startChar;
                startR = 1;
                startC = 1;
            }
            return; //return to end the method
        }

        //randmoly generate 1-4 inclusive for the side the exit is on
        int randNum = rand.nextInt(4) + 1; //1&2 is on the horizontal side, 3&4 is on the vertical side
        if(maxR == 2){
            int randCol = rand.nextInt(maxC-2) + 1;//generate a random column number
            if(randNum == 1){
                maze[0][randCol] = exitChar;//set the exit on the horizontal side
            }else{
                maze[maxR-1][randCol] = exitChar;//set the exit on the horizontal side
            }
        }else if(maxC == 2){
            int randRow = rand.nextInt(maxR-2) + 1;//generate a random row number
            if(randNum == 3){
                maze[randRow][0] = exitChar;//set the exit on the vertical side
            }else{
                maze[randRow][maxC-1] = exitChar;//set the exit on the vertical side
            }
        }else if(randNum <= 2){
            int randCol = rand.nextInt(maxC-2) + 1;//generate a random column number
            if(randNum == 1){
                maze[0][randCol] = exitChar;//set the exit on the horizontal side
            }else{
                maze[maxR-1][randCol] = exitChar;//set the exit on the horizontal side
            }
        }else{
            int randRow = rand.nextInt(maxR-2) + 1;//generate a random row number
            if(randNum == 3){
                maze[randRow][0] = exitChar;//set the exit on the vertical side
            }else{
                maze[randRow][maxC-1] = exitChar;//set the exit on the vertical side
            }
        }

        //do while loop generating starting position, excluding the 4 corners and exit position
        do{
            //generate a random row and column number
            int randRow = rand.nextInt(maxR);
            int randCol = rand.nextInt(maxC);
            //if the random position is one of the 4 corners or the exit position, continue to regenerate
            if(randRow==0 && randCol==0){
                continue;
            }else if(randRow==0 && randCol==maxC-1){
                continue;
            }else if(randRow==maxR-1 && randCol==0){
                continue;
            }else if(randRow==maxR-1 && randCol==maxC-1){
                continue;
            }else if(maze[randRow][randCol] == exitChar){
                continue;
            }else{ //if it's not a corner, set the starting position to startChar
                maze[randRow][randCol] = startChar;
                startR = randRow;
                startC = randCol;
                //the section below is not part of the algorithm, but it is added to increase the chance of the maze being solvable
                //the 4 cells around the starting position are set the open char
                if(randRow+1<maxR-1 && maze[randRow+1][randCol] != exitChar && randCol!=0 && randCol!=maxC-1){
                    maze[randRow+1][randCol] = openChar;
                }
                if(randRow-1>=1 && maze[randRow-1][randCol] != exitChar && randCol!=0 && randCol!=maxC-1){
                    maze[randRow-1][randCol] = openChar;
                }
                if(randCol+1<maxC-1 && maze[randRow][randCol+1] != exitChar && randRow!=0 && randRow!=maxR-1){
                    maze[randRow][randCol+1] = openChar;
                }
                if(randCol-1>=1 && maze[randRow][randCol-1] != exitChar && randRow!=0 && randRow!=maxR-1){
                    maze[randRow][randCol-1] = openChar;
                }
                break;
            }
        }while(true); //do while loop that will only break when it finds a valid starting position

        //nested for loop to randomly generate the middle section of the maze(excluding the borders and starting position)
        for(int i=1; i<maxR-1; i++){
            for(int j=1; j<maxC-1; j++){
                if(maze[i][j] == startChar || maze[i][j] == openChar){ //if it's the starting position or an open space, skip it
                    continue;
                }
                if(maze[i][j+1] == exitChar || maze[i][j-1] == exitChar || maze[i+1][j] == exitChar || maze[i-1][j] == exitChar){ //not part of algorithm
                    maze[i][j] = openChar; //if the cell is next to the exit, set it to openChar so there's a higher chance of finding a path
                    continue;
                }
                randNum = rand.nextInt(100);
                
                if(maxC >= 19 || maxR >= 19){ //if the maze is too big, the chance of a barrier cell is increased
                    if(randNum <= 45){ //45% chance of being a open path
                        maze[i][j] = openChar;
                    }else{
                        maze[i][j] = barrierChar;
                    }
                }else if(maxC >= 10 && maxR >= 10){
                    if(randNum <= 50){ //50% chance of being a open path
                        maze[i][j] = openChar;
                    }else{
                        maze[i][j] = barrierChar;
                    }

                }else{
                    if(randNum <= 65){ //65% chance of being a open path
                        maze[i][j] = openChar;
                    }else{
                        maze[i][j] = barrierChar;
                    }
                }
            }
        }

    }

    /**
     * maze generator for the GUI
     * method name: mazeGenGUI
     * @return void - procedure method
     */
    public static void mazeGenGUI(){
        userGen.setVisible(false); //hide the userInterface panel
        mazeGen(); //call mazeGen method to generate maze
        mazePanel.setLayout(new GridLayout(maxR, maxC)); //set the layout of the mazePanel to a grid layout with the user entered dimensions
        for(int i = 0; i < maxR; i++){ //nested for loop to update and add each label to the mazePanel
            for(int j = 0; j < maxC; j++){
                mazeLabel[i][j] = new JLabel("" + maze[i][j]);
                mazeLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setVerticalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setFont(new Font("Comic Sans", Font.BOLD, 20));
                mazeLabel[i][j].setOpaque(true);
                //set the color of the label based on the character
                if(maze[i][j] == barrierChar){
                    mazeLabel[i][j].setBackground(new Color(102, 51, 0));
                }
                else if(maze[i][j] == startChar){
                    mazeLabel[i][j].setBackground(Color.GRAY);
                }
                else if(maze[i][j] == exitChar){
                    mazeLabel[i][j].setBackground(Color.GREEN);
                }
                else if(maze[i][j] == openChar){
                    mazeLabel[i][j].setBackground(Color.YELLOW);
                }
                mazePanel.add(mazeLabel[i][j]); //add the label to the mazePanel
            }
        }
        //make the maze and UI panels visible
        mazePanel.setVisible(true);
        mazeUIPanel.setLayout(new GridLayout(1,2));
        mazeUIPanel.add(btnHome);
        mazeUIPanel.add(btnSolve);
        mazeUIPanel.setVisible(true);
    }

    /**
     * return back to the home panel
     * method name: returnHome
     * @return void - procedure method
     */
    public static void returnHome(){
        //set all the chars to the default letters
        startChar = 'S';
        exitChar = 'X';
        openChar = 'O';
        barrierChar = 'B';
        //make the home panel the only visible panel
        mazePanel.setVisible(false);
        mazeUIPanel.setVisible(false);
        //change the text and color of some labels to make sure when the user sees that label again, its the same as when it was first
        mazeLab.setText("Pick an option:");
        mazeLab.setBackground(Color.WHITE);
        homePan.setVisible(true);
        //nested for loop to remove all the labels from the mazePanel so it will not interfere with the next maze
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazePanel.remove(mazeLabel[i][j]);
                maze[i][j] = ' ';
                //reset the 2D arrays used to keep track of the path
                vis[i][j] = false;
                check[i][j] = false;
            }
        }
    }

    /**
     * prompt file check if file is valid
     * method name: filePrompt
     * @return boolean - if the file is valid or not
     */
    public static boolean filePrompt(){
        mazeFileName = fileInput.getText(); //get the file name from the text field
        File file = new File(mazeFileName); //create a new file object with the file name
        if(!file.isFile()){ //if the file does not exist, warn the user
            fileLab.setOpaque(true);
            fileLab.setBackground(Color.RED);
            fileLab.setText("Invalid File!!! Please try again!");
            return false; //return false if the file is invalid
        }else{ //if the file exists, set the UI elements back to normal
            fileLab.setOpaque(true);
            fileLab.setBackground(Color.white);
            fileLab.setText("Enter the file name of the maze:");
            return true; //return true if the file is valid
        }
    }

    /**
     * read the file and store the data into the maze array
     * method name: fileGenGUI
     * @return void - procedure method
     */
    public static void fileGenGUI() throws Exception{
        File mazeFile = new File(mazeFileName); //create a new file object with the file name
        Scanner in = new Scanner(mazeFile); //create a new scanner object to read the file
        maxR = in.nextInt(); //get the number of rows from the file
        maxC = in.nextInt(); //get the number of columns from the file
        barrierChar = in.next().charAt(0); //get the barrier character from the file
        openChar = in.next().charAt(0); //get the open character from the file 
        startChar = in.next().charAt(0); //get the start character from the file
        exitChar = in.next().charAt(0); //get the exit character from the file
        in.nextLine();//clear scanner
        //nested for loop to read the maze from the file and store it into the 2D array
        for(int i = 0; i < maxR; i++){
            String line = in.nextLine(); //get the next line of the file
            for(int j = 0; j < maxC; j++){ //stores each character of the line into the 2D array
                maze[i][j] = line.charAt(j); 
                if(maze[i][j] == startChar){ //marks the start position of the maze
                    startR = i;
                    startC = j;
                }
            }
        }
        in.close(); //close the scanner
        mazePanel.setLayout(new GridLayout(maxR, maxC)); //set the layout of the mazePanel to a grid layout with the user entered dimensions
        //nested for loop to update and add each label to the mazePanel
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazeLabel[i][j] = new JLabel("" + maze[i][j]);
                mazeLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setVerticalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setFont(new Font("Comic Sans", Font.BOLD, 20));
                mazeLabel[i][j].setOpaque(true);
                //set the color of the label based on the character
                if(maze[i][j] == barrierChar){
                    mazeLabel[i][j].setBackground(new Color(102, 51, 0));
                }
                else if(maze[i][j] == startChar){
                    mazeLabel[i][j].setBackground(Color.GRAY);
                }
                else if(maze[i][j] == exitChar){
                    mazeLabel[i][j].setBackground(Color.GREEN);
                }
                else if(maze[i][j] == openChar){
                    mazeLabel[i][j].setBackground(Color.YELLOW);
                }
                mazePanel.add(mazeLabel[i][j]); //add the label to the mazePanel
            }
        }
        //make the maze and UI panels visible
        mazePanel.setVisible(true);
        mazeUIPanel.setVisible(true);
    }

    /**
     * recursive algorithm that solves the maze
     * method name: solveMaze
     * @param curR - current row of the maze that this method is on
     * @param curC - current column of the maze that this method is on
     * @return boolean - returns if the position curR, curC can lead to exit or not
     */
    public static boolean solveMaze(int curR, int curC){
        if(curR < 0 || curR >= maxR || curC < 0 || curC >= maxC){ //base case 1, if the position is out of bounds, return false
            return false;
        }

        if(maze[curR][curC] == exitChar || check[curR][curC]){ //base case 2, if the position is the exit or can lead to the exit already, return true
            return true;
        }

        if(maze[curR][curC] == barrierChar){ //base case 3, if the position is a barrier, return false
            return false;
        }

        if(vis[curR][curC]){ //base case 4, if the position has been visited, return false
            return false;
        }else{
            vis[curR][curC] = true; //mark the position as visited if it was not visited before
        }

        //recursive calls to iterate through all possible directions
        boolean top = solveMaze(curR - 1, curC); //check if the position above can lead to the exit
        boolean bottom = solveMaze(curR + 1, curC); //check if the position below can lead to the exit
        boolean left = solveMaze(curR, curC - 1); //check if the position to the left can lead to the exit
        boolean right = solveMaze(curR, curC + 1); //check if the position to the right can lead to the exit

        vis[curR][curC] = false; // after checking all possible directions, mark the position as not visited so the spot can be visited again for other paths
        
        //if any of the recursive calls is true, mark current position as true for the check array, and return true
        if(top || bottom || left || right){
            check[curR][curC] = true;
            return true;
        }
        
        //if none of the recursive calls is true, return false
        return false;
        
    }

    public static void main(String[] args) {
        AidenWangMazeAssignment frame = new AidenWangMazeAssignment();
    }

}