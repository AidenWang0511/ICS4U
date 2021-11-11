/*
File Name: AidenWangISP
Author: Aiden Wang
Date: Nov 10, 2021
Description: Maze generator and solver
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    
    public static JLabel warnLab = new JLabel("The dimension must be bigger than 2x3 or 3x2 and less than 20x20, unqualified number will turn ORANGE!");
    public static JPanel userInterface = new JPanel();
    public static JLabel rowPrompt = new JLabel("Enter the number of rows of the maze");
    public static JLabel colPrompt = new JLabel("Enter the number of columns of the maze");
    public static JTextField rowInput = new JTextField(10);
    public static JTextField colInput = new JTextField(10);
    public static JButton btnGenerate = new JButton("Generate");


    public static JPanel mazePanel = new JPanel();
    public static JLabel[][] mazeLabel = new JLabel[20][20];
    public static JPanel mazeUIPanel = new JPanel();
    public static JButton btnSolve = new JButton("Solve Maze");
    public static JButton btnRegen = new JButton("Generate Again");

    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();
        if(command.equals("Generate")){
            boolean isValid = false;
            isValid = dimensionPrompt();
            if(isValid){ //only generate the maze if the dimension is valid
                mazeGenGUI();
            }
        }else if(command.equals("Generate Again")){
            regenMaze();
        }
    }
    

    public AidenWangMazeAssignment() {
        setTitle("An A Maze ing Program");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        btnGenerate.addActionListener(this);
        btnSolve.addActionListener(this);
        btnRegen.addActionListener(this);


        userInterface.setLayout(new GridLayout(5,2));
        rowInput.setPreferredSize(new Dimension(30, 30));
        colInput.setPreferredSize(new Dimension(30, 30));
        rowPrompt.setFont(new Font("Comic Sans", Font.BOLD, 18));
        colPrompt.setFont(new Font("Comic Sans", Font.BOLD, 18));
        warnLab.setFont(new Font("Comic Sans", Font.BOLD, 18));
        userInterface.add(rowPrompt);
        userInterface.add(rowInput);
        userInterface.add(colPrompt);
        userInterface.add(colInput);
        userInterface.add(warnLab);
        userInterface.add(btnGenerate);


        add(userInterface);
        add(mazePanel);
        add(mazeUIPanel);
        userInterface.setVisible(true);
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
        boolean isValid = false;;
        if(rowInput.getText().equals("")){
            rowInput.setBackground(Color.ORANGE);
        }
        if(colInput.getText().equals("")){
            colInput.setBackground(Color.ORANGE);
        }
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
        if(randNum <= 2){
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
        userInterface.setVisible(false); //hide the userInterface panel
        mazeGen(); //call mazeGen method to generate maze
        mazePanel.setLayout(new GridLayout(maxR, maxC));
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazeLabel[i][j] = new JLabel("" + maze[i][j]);
                mazeLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setVerticalAlignment(JLabel.CENTER);
                mazeLabel[i][j].setFont(new Font("Comic Sans", Font.BOLD, 30));
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
        mazeUIPanel.add(btnRegen);
        mazeUIPanel.add(btnSolve);
        mazeUIPanel.setVisible(true);
    }

    /**
     * Regenerate the maze
     * method name: regenMaze
     * @return void - procedure method
     */
    public static void regenMaze(){
        mazePanel.setVisible(false);
        mazeUIPanel.setVisible(false);
        userInterface.setVisible(true);
        for(int i = 0; i < maxR; i++){
            for(int j = 0; j < maxC; j++){
                mazePanel.remove(mazeLabel[i][j]);
                maze[i][j] = ' ';
            }
        }
    }

    public static void main(String[] args) {
        AidenWangMazeAssignment frame = new AidenWangMazeAssignment();
    }

}
