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

    public static char[][] maze = new char[20][20];
    public static boolean[][] visited = new boolean[20][20];
    public static boolean[][] check = new boolean[20][20];
    public static int maxR = 20;
    public static int maxC = 20;
    public static char barrierChar = 'B';
    public static char startChar = 'S';
    public static char exitChar = 'X';
    public static char openChar = 'O';

    public static JPanel uiPan = new JPanel();
    public static JLabel uiLabel = new JLabel("<html>Welcome to my a Maze ing game! Please Select an option to generate maze:<br/> <br/>Maximize screen for better user experience</html>");
    public static JButton btnUserGen = new JButton("Randomly Generate");
    public static JButton btnFileGen = new JButton("Generate Maze From File");
    
    public static JLabel warnLab = new JLabel("The dimension must be bigger than 2x3 or 3x2 and less than 20x20, unqualified number will turn ORANGE!");
    public static JPanel userGen = new JPanel();
    public static JLabel rowPrompt = new JLabel("Enter the number of rows of the maze");
    public static JLabel colPrompt = new JLabel("Enter the number of columns of the maze");
    public static JTextField rowInput = new JTextField(10);
    public static JTextField colInput = new JTextField(10);
    public static JButton btnGenerate = new JButton("Generate");

    public static JPanel fileGen = new JPanel();
    public static JLabel fileLab = new JLabel("Enter the file name of the maze:");
    public static JTextField fileInput = new JTextField(10);
    public static JButton btnFileGenerate = new JButton("Continue");
    public static String mazeFileName = new String();

    public static JPanel mazePanel = new JPanel();
    public static JLabel[][] mazeLabel = new JLabel[20][20];
    public static JPanel mazeUIPanel = new JPanel();
    public static JButton btnSolve = new JButton("Solve Maze");
    public static JButton btnHome = new JButton("Return Home");

    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();
        if(command.equals("Randomly Generate")){
            uiPan.setVisible(false);
            userGen.setVisible(true);
        }else if(command.equals("Generate Maze From File")){
            uiPan.setVisible(false);
            fileGen.setVisible(true);
            
        }else if(command.equals("Generate")){
            boolean isValid = false;
            isValid = dimensionPrompt();
            if(isValid){ //only generate the maze if the dimension is valid
                mazeGenGUI();
            }
        }else if(command.equals("Continue")){
            boolean isValid = false;
            isValid = filePrompt();
            if(isValid){ //only generate the maze if the dimension is valid
                fileGen.setVisible(false);
                try{
                    fileGenGUI();
                }catch(Exception e){
                    fileLab.setText("File not found! Please try again!!!");
                }
            }
        }else if(command.equals("Return Home")){
            returnHome();
        }
    }
    

    public AidenWangMazeAssignment() {
        setTitle("An A Maze ing Program");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        btnGenerate.addActionListener(this);
        btnSolve.addActionListener(this);
        btnHome.addActionListener(this);
        btnUserGen.addActionListener(this);
        btnFileGen.addActionListener(this);
        btnFileGenerate.addActionListener(this);

        uiPan.setLayout(new GridLayout(3,0));
        uiLabel.setHorizontalAlignment(SwingConstants.CENTER);
        uiLabel.setVerticalAlignment(SwingConstants.CENTER);
        uiLabel.setOpaque(true);
        uiLabel.setBackground(Color.lightGray);
        uiLabel.setFont(new Font("Comic Sans", Font.BOLD, 24));
        btnFileGen.setFont(new Font("Comic Sans", Font.BOLD, 24));
        btnUserGen.setFont(new Font("Comic Sans", Font.BOLD, 24));
        uiPan.add(uiLabel);
        uiPan.add(btnUserGen);
        uiPan.add(btnFileGen);

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

        fileGen.setLayout(new GridLayout(5,0));
        fileLab.setFont(new Font("Comic Sans", Font.BOLD, 20));
        fileLab.setHorizontalAlignment(SwingConstants.CENTER);
        fileInput.setPreferredSize(new Dimension(30, 30));
        btnFileGenerate.setFont(new Font("Comic Sans", Font.BOLD, 20));
        fileGen.add(fileLab);
        fileGen.add(fileInput);
        fileGen.add(btnFileGenerate);

        mazeUIPanel.setLayout(new GridLayout(1,2));
        btnHome.setFont(new Font("Comic Sans", Font.BOLD, 20));
        btnSolve.setFont(new Font("Comic Sans", Font.BOLD, 20));
        mazeUIPanel.add(btnHome);
        mazeUIPanel.add(btnSolve);

        add(uiPan);
        add(userGen);
        add(fileGen);
        add(mazePanel);
        add(mazeUIPanel);

        uiPan.setVisible(true);
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
            int row = Integer.parseInt(rowInput.getText());
            int col = Integer.parseInt(colInput.getText());
            boolean sizeCheck = (row>=2 && col>=3) || (row>=3 && col>=2); 
            if(!(row<=20) || !(col<=20) || !sizeCheck){
                throw new NumberFormatException();
            }else{
                rowInput.setBackground(Color.WHITE);
                colInput.setBackground(Color.WHITE);
                isValid = true;
            }
        }catch(NumberFormatException e1) {
            rowInput.setBackground(Color.ORANGE);//try catch so the whole section will turn orange if it's invalid input
            colInput.setBackground(Color.ORANGE);
        }
        if(isValid){
            maxC = Integer.parseInt(colInput.getText());
            maxR = Integer.parseInt(rowInput.getText());
        }
        return isValid;
    }

    /**
     * prompt user for maze dimensions and randomly generate the maze as required
     * method name: mazeGen
     * @return void - procedure method
     */
    public static void mazeGen(){
        //set the barriers
        for(int i=0; i<maxR; i++){
            maze[i][0] = barrierChar;
            maze[i][maxC-1] = barrierChar;
        }
        for(int i=0; i<maxC; i++){
            maze[0][i] = barrierChar;
            maze[maxR-1][i] = barrierChar;
        }

        Random rand = new Random();

        //special case where the maze is 3x2 or 2x3
        if(maxR==3 && maxC==2){
            int randNum = rand.nextInt(2);
            if(randNum == 0){
                maze[1][0] = startChar;
                maze[1][1] = exitChar;
            }else{
                maze[1][0] = exitChar;
                maze[1][1] = startChar;
            }
            return;
        }else if(maxR==2 && maxC==3){
            int randNum = rand.nextInt(2);
            if(randNum == 0){
                maze[0][1] = startChar;
                maze[1][1] = exitChar;
            }else{
                maze[0][1] = exitChar;
                maze[1][1] = startChar;
            }
            return;
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
            int randRow = rand.nextInt(maxR);
            int randCol = rand.nextInt(maxC);
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
                //the section below is not part of the algorithm, but is used to increase the chance of the maze being solvable
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
                if(maze[i][j] == startChar || maze[i][j] ==openChar){ //if it's the starting position or an open space, skip it
                    continue;
                }
                if(maze[i][j+1] == exitChar || maze[i][j-1] == exitChar || maze[i+1][j] == exitChar || maze[i-1][j] == exitChar){ //not part of algorithm
                    maze[i][j] = openChar; //if the cell is next to the exit, set it to openChar so there's a higher chance of finding a path
                    continue;
                }
                randNum = rand.nextInt(100);
                if(randNum <= 60){ //60% chance of being a open path
                    maze[i][j] = openChar;
                }else{
                    maze[i][j] = barrierChar;
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
        mazePanel.setLayout(new GridLayout(maxR, maxC));
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazeLabel[i][j] = new JLabel("" + maze[i][j]);
                mazeLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setVerticalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setFont(new Font("Comic Sans", Font.BOLD, 20));
                mazeLabel[i][j].setOpaque(true);
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
                mazePanel.add(mazeLabel[i][j]);
            }
        }
        mazePanel.setVisible(true);
        mazeUIPanel.setLayout(new GridLayout(1,2));
        mazeUIPanel.add(btnHome);
        mazeUIPanel.add(btnSolve);
        mazeUIPanel.setVisible(true);
    }

    /**
     * Regenerate the maze
     * method name: returnHome
     * @return void - procedure method
     */
    public static void returnHome(){
        mazePanel.setVisible(false);
        mazeUIPanel.setVisible(false);
        uiPan.setVisible(true);
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazePanel.remove(mazeLabel[i][j]);
                maze[i][j] = ' ';
            }
        }
    }

    /**
     * prompt file check if file is valid
     * method name: filePrompt
     * @return boolean - if the file is valid or not
     */
    public static boolean filePrompt(){
        mazeFileName = fileInput.getText();
        File file = new File(mazeFileName);
        if(!file.isFile()){
            fileLab.setOpaque(true);
            fileLab.setBackground(Color.RED);
            fileLab.setText("Invalid File!!! Please try again!");
            return false;
        }else{
            fileLab.setOpaque(true);
            fileLab.setBackground(Color.white);
            fileLab.setText("Enter the file name of the maze:");
            return true;
        }
    }

    /**
     * read the file and store the data into the maze array
     * method name: fileGenGUI
     * @return void - procedure method
     */
    public static void fileGenGUI() throws Exception{
        File mazeFile = new File(mazeFileName);
        Scanner in = new Scanner(mazeFile);
        maxR = in.nextInt();
        maxC = in.nextInt();
        barrierChar = in.next().charAt(0);
        openChar = in.next().charAt(0);
        startChar = in.next().charAt(0);
        exitChar = in.next().charAt(0);
        in.nextLine();//clear scanner
        for(int i = 0; i < maxR; i++){
            String line = in.nextLine();
            for(int j = 0; j < maxC; j++){
                maze[i][j] = line.charAt(j);
            }
        }
        in.close();
        mazePanel.setLayout(new GridLayout(maxR, maxC));
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazeLabel[i][j] = new JLabel("" + maze[i][j]);
                mazeLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setVerticalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setFont(new Font("Comic Sans", Font.BOLD, 20));
                mazeLabel[i][j].setOpaque(true);
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
                mazePanel.add(mazeLabel[i][j]);
            }
        }
        mazePanel.setVisible(true);
        mazeUIPanel.setVisible(true);

    }

    public static void main(String[] args) {
        AidenWangMazeAssignment frame = new AidenWangMazeAssignment();
    }

}
