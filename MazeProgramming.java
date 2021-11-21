/*
* Name: James Tan
* Date: 11/11/2021
* Description: A program that auto creates or reads a maze from a file and finds all the paths from the start to the exit of the maze using recursion. 
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class MazeProgramming extends JFrame implements ActionListener {

    public static Stack<Pair> stack = new Stack<Pair>(); //stack to store the path
    public static Font fontbig = new Font("SansSerif", Font.BOLD, 30); //font for the maze
    public static Font fontmedium = new Font("SansSerif", Font.BOLD, 20); //font for the maze
    public static Font fontsmall = new Font("SansSerif", Font.BOLD, 15); //font for the maze
    public static File file; //file to read from
    public static char border, open, mouse, exit; //characters to be used in the maze
    public static char correctPath = '+'; //character to be used to show the correct path
    public static int row, coloumn, startr, startc, exitr, exitc; // integers for storing row and column of the maze, and start and exit's row and column
    public static int[] rx = { 0, 0, 1, -1 }; //array to store the row change in the 4 directions
    public static int[] ry = { 1, -1, 0, 0 };  //array to store the coloumn change in the 4 directions
    public static boolean validInput = false; //boolean to check if the users' input is valid
    public static Color brown = new Color(218, 165, 32); //color for brown 
    public static Color red = new Color(255, 51, 51); //color for red
    public static char[][] pathChar = new char[row][coloumn]; //char array to store the different characters in the path
    public static boolean[][] isVisited = new boolean[row][coloumn]; //boolean array to store 'true' if the cell has been visited
    public static boolean hasPath = false; //boolean to check if there is a path or not, so that the program can show the correct message on the screen
    public static JPanel choosePanel = new JPanel(); //panel to choose to read from a file or create a random maze
    public static JPanel fileMazePanel = new JPanel(); //panel to prompt user to enter the file name
    public static JPanel mazePanel = new JPanel(); //panel to show the maze lables with color 
    public static JPanel randomMazeEnter = new JPanel(); //panel to prompt user to enter the number of rows and columns for the random maze
    public static JPanel emptyPanel = new JPanel(); //empty panel to make the GUI look nice
    public static JPanel bottomFilePanel = new JPanel(); //panel that tells the user if there is a path or not, and ask user if they want to re-enter a file or go to choose panel
    public static JPanel bottomRandomPanel = new JPanel(); //panel that tells the user if there is a path or not, and ask user if they want to re-enter a dimention or go to choose panel
    public static JLabel[][] mapLable = new JLabel[row][coloumn]; //label array to display the maze with colors 
    public static JLabel descriptionLabel1, descriptionLabel2, noteLabel, mazeResultLable1, mazeResultLable2; //labels to tell the user what the program does and display wether or not there is a path
    public static JButton chooseFile, chooseRandom, submitDimention, proceed; //buttons to choose to read from a file or create a random maze, and to proceed to the next step if the user has entered the correct file name or dimention
    public static JButton reenterDimentonButton, reenterFileButton;  //buttons to re-enter the dimention or file name
    public static JButton findPath, homeButton, findPath1, homeButton1; //buttons to find the path or go back to the home screen
    public static JTextField enterRow, enterColoumn, enterFileName; //text fields to let user enter the number of rows and columns for the random maze, or enter the file name

    //method for a pop up window that tell user to enter the correct dimention or correct file
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE); //Java API that displays a message inside a specified frame
    }

    // recursive method that finds the path of the maze and displays it by taking in the row and column of the starting point 
    public static void findPath(int x, int y) {
        if (x == exitr && y == exitc) { // base case - if the current cell is the exit cell - that stops the recursion
            Stack<Pair> temp = (Stack<Pair>) stack.clone(); //clone the stack to store a path that leads to the exit
            while (!temp.isEmpty()) { //while the stack is not empty
                int pathx = temp.peek().fir; //get the first number - also the row - from the pair in the stack
                int pathy = temp.peek().sec; //get the second number - also the coloumn - from the pair in the stack
                if (pathChar[pathx][pathy] != exit && pathChar[pathx][pathy] != mouse) { //if the character in the path is not the exit or the mouse
                    pathChar[pathx][pathy] = correctPath; //change the character in pathChar to the correct path character  - '+' 
                }
                temp.pop(); //pop the pair from the stack each time it loops 
            }
            stack.pop(); //pop the last pair from the stack 
            isVisited[x][y] = false; //set the current cell to not visited
            return; //return back a step
        } else { //if the base case is not reached - continue the recursion
            for (int i = 0; i < 4; i++) { //loop through the 4 directions - up, down, left, right
                int dx = x + rx[i]; //change the row by the row change in the direction
                int dy = y + ry[i]; //change the row by the row change in the direction
                if (dx >= 0 && dx <= row - 1 && dy >= 0 && dy <= coloumn - 1 && pathChar[dx][dy] != border
                        && !isVisited[dx][dy] && pathChar[dx][dy] != mouse) { //if the cell is not a border, and the cell has not been visited, and the cell is not the mouse
                    isVisited[dx][dy] = true; //set the current cell to visited
                    stack.push(new Pair(dx, dy)); //push the current cell (the pair) into the stack
                    findPath(dx, dy); //call the method again to find the path
                }
            }
            stack.pop(); //pop the pair from the stack
            isVisited[x][y] = false; //set the current cell to not visited
            return; //return back a step
        }
    }

    // method that reads the maze from a file
    public static void getMapFromFile(String fileName) throws IOException {

        Scanner fs = new Scanner(file); // declare a scanner for scanning through the file
        row = fs.nextInt(); // record the number of rows
        coloumn = fs.nextInt(); // record the number of columns
        border = fs.next().charAt(0); // record the border character
        open = fs.next().charAt(0); // record the open character
        mouse = fs.next().charAt(0); // record the mouse character
        exit = fs.next().charAt(0); // record the exit character
        pathChar = new char[row][coloumn]; // identify the size of 2D char array which stores the characters in the maze
        mapLable = new JLabel[row][coloumn]; // identify the size of 2D label array that displays the maze with colors
        fs.close(); // close the file scanner to avoid issues with scanning differnt data types in the same file
        fs = new Scanner(file); // declare the scanner again
        for (int i = 0; i < 6; i++) { // loop through the 6 lines of the file that's already scanned
            fs.nextLine(); // skip the line
        }
        String line = ""; // declare a string to store a line read from the file 
        for (int r = 0; r < row; r++) { // go through each remaining line of the file
            line = fs.nextLine(); // read the line
            for (int c = 0; c < coloumn; c++) { // go through each character in the line
                pathChar[r][c] = line.charAt(c); // record the character on the line in the  2D char array
            }
        }
        fs.close(); // close the file scanner
    }

    // function to color the specified maze
    public static void colorMaze() {

        for (int r = 0; r < mapLable.length; r++) { // go through each row
            for (int c = 0; c < mapLable[0].length; c++) { // go through each column
                if (pathChar[r][c] == border) { // if the character corresponds to a border
                    mapLable[r][c] = new JLabel(String.valueOf(border)); // set the label to the border character
                    mapLable[r][c].setOpaque(true); // set the label to be opaque
                    mapLable[r][c].setBackground(brown); // set the background color to brown
                } else if (pathChar[r][c] == open) { // if the corresponds to a open path
                    mapLable[r][c] = new JLabel(String.valueOf(open)); // set the label to the open character
                    mapLable[r][c].setOpaque(true);  // set the label to be opaque
                    mapLable[r][c].setBackground(Color.yellow); // set the background color to yellow
                } else if (pathChar[r][c] == mouse) { // if the character corresponds to the starting point
                    mapLable[r][c] = new JLabel(String.valueOf(mouse));  // set the label to the mouse/starting point character
                    mapLable[r][c].setOpaque(true);  // set the label to be opaque
                    mapLable[r][c].setBackground(Color.gray); // set the background color to gray
                } else if (pathChar[r][c] == exit) { // if the character corresponds to the exit
                    mapLable[r][c] = new JLabel(String.valueOf(exit)); // set the label to the exit character
                    mapLable[r][c].setOpaque(true); // set the label to be opaque
                    mapLable[r][c].setBackground(Color.green); // set the background color to green
                } else if (pathChar[r][c] == correctPath) { // if the character corresponds to the correct path
                    mapLable[r][c] = new JLabel(String.valueOf(correctPath)); // set the label to the correct path character
                    mapLable[r][c].setOpaque(true); // set the label to be opaque
                    mapLable[r][c].setBackground(Color.cyan); // set the background color to cyan
                }
                mapLable[r][c].setHorizontalAlignment(SwingConstants.CENTER); // align the text in the label to the center
                mapLable[r][c].setFont(fontsmall); // set the font of the label to the 'small font'
                mazePanel.add(mapLable[r][c]); // add the label to the panel
            }
        }
    }

    // method that automatically generates the characters in the maze according to the sum of the row and column
    public static void generateRandomMaze() {

        Random rand = new Random(); // declare a random object
        for (int r = 0; r < pathChar.length; r++) { // go through each row
            for (int c = 0; c < pathChar[r].length; c++) { // go through each column
                int num = rand.nextInt(100) + 1; // generate a random number between 1 and 100
                if (r == 0 || c == 0 || r == pathChar.length - 1 || c == pathChar[0].length - 1) { // if the cell is on the border
                    pathChar[r][c] = 'B'; // set the character to a border
                } else if (row + coloumn <= 25) { // if the maze is small - row + coloumn <= 25
                    if (num <= 40) { // if the random number is less than or equal to 40
                        pathChar[r][c] = 'B'; // set the character to a border
                    } else { // if the random number is greater than 40
                        pathChar[r][c] = 'O'; // set the character to an open path
                    }
                } else{ // if the maze is large - row + coloumn > 25
                    if (num <= 50) { // if the random number is less than or equal to 50
                        pathChar[r][c] = 'B'; // set the character to a border
                    } else { // if the random number is greater than 50
                        pathChar[r][c] = 'O'; // set the character to an open path
                    }
                }
            }
        }

        boolean isUnique = true; // declare a boolean variable to check if the generated exits or starting points meets the requirements

        do {
            isUnique = true; // set the boolean variable to true
            int rMouse = rand.nextInt(pathChar.length); // generate a random number between 0 and the number of rows
            int cMouse = rand.nextInt(pathChar[0].length); // generate a random number between 0 and the number of columns
            int rExit = rand.nextInt(pathChar.length); // generate a random number between 0 and the number of rows
            int cExit = rand.nextInt(pathChar[0].length); // generate a random number between 0 and the number of columns
            if (rMouse == rExit && cMouse == cExit) { // if the generated starting point and exit point are the same
                isUnique = false; // set the boolean variable to false
            } else if (rExit > 0 && rExit < pathChar.length - 1 && cExit > 0 && cExit < pathChar[0].length - 1) { // if the generated exit point is not on the border
                isUnique = false; // set the boolean variable to false
            } else if ((rExit == 0 && cExit == 0) || (rExit == 0 && cExit == pathChar[0].length - 1) 
                    || (cExit == 0 && rExit == pathChar.length - 1) 
                    || (rExit == pathChar.length - 1 && cExit == pathChar[0].length - 1)) { // if the generated exit point is on a corner
                isUnique = false; // set the boolean variable to false
            } else if ((rMouse == 0 && cMouse == 0) || (rMouse == 0 && cMouse == pathChar[0].length - 1) 
                    || (cMouse == 0 && rMouse == pathChar.length - 1)
                    || (rMouse == pathChar.length - 1 && cMouse == pathChar[0].length - 1)) { // if the generated starting point is on a corner
                isUnique = false; // set the boolean variable to false
            }
            if (isUnique) { // if the boolean variable is true
                pathChar[rMouse][cMouse] = 'M'; // set the character to the starting point
                pathChar[rExit][cExit] = 'E'; // set the character to the exit
            }
        } while (!isUnique); // keep on generating the starting point and exit point until the boolean variable is true
    }

    // method that checks if the user entered a valid maze size 
    public static void enterValidNumber() throws NumberFormatException {

        boolean isValid = false; // declare a boolean variable to check if the user entered a valid number
        try { // try to catch the exception
            int tempr = Integer.parseInt(enterRow.getText()); // get the row from the text field and convert it to an integer
            int tempc = Integer.parseInt(enterColoumn.getText()); // get the coloumn from the text field and convert it to an integer
            if (tempr > 20 || tempc > 20 || tempr < 2 || tempc < 2) { // if the row or coloumn is less than 2 or greater than 20
                throw new NumberFormatException(); // throw the exception
            } else if (tempr < 2 && tempc < 3) { // if the row is less than 2 and the coloumn is less than 3
                throw new NumberFormatException(); // throw the exception
            } else if (tempr < 3 && tempc < 2) { // if the row is less than 3 and the coloumn is less than 2
                throw new NumberFormatException(); // throw the exception
            } else {
                isValid = true; // set the boolean variable to true if the above conditions are not met
            }
            if (isValid) { // if the boolean variable is true
                row = tempr; // set the global row to the user entered row
                coloumn = tempc; // set the global coloumn to the user entered coloumn
                validInput = true; // set the boolean for valid input to true
            }
        } catch (NumberFormatException e) { // if the exception is caught
            MazeProgramming.infoBox("Enter a valid dimention", "ERROR"); // display an error message through the pop-up window
        }
    }

    // constructor that creates the GUI
    public MazeProgramming() {

        setTitle("Maze Asignment"); // set the title of the frame
        setSize(1920, 1080); // set the size of the frame
        setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to full screen when the program is run
        setDefaultCloseOperation(EXIT_ON_CLOSE); // close and frame and program when the user clicks the close on the frame
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS); // create a box layout for the frame
        GridLayout choosePanelLayout = new GridLayout(2, 2); // create a grid layout for the choose panel
        descriptionLabel1 = new JLabel("Welcome to the maze path finder program"); // initialize label for the description
        descriptionLabel2 = new JLabel("Press a button of you choice to continue"); // initialize label for the description
        chooseFile = new JButton("Press to enter a file"); // button to let user enter a file
        chooseFile.addActionListener(this); // add an action listener to the button
        chooseFile.setFont(fontbig); // set the font of the button to a big font
        chooseRandom = new JButton("Press to auto generate a maze"); // button to let user auto generate a maze
        chooseRandom.addActionListener(this); // add an action listener to the button
        chooseRandom.setFont(fontbig); // set the font of the button to a big font
        descriptionLabel1.setHorizontalAlignment(SwingConstants.CENTER); // align text in the label to center
        descriptionLabel2.setHorizontalAlignment(SwingConstants.CENTER); // align text in the label to center
        descriptionLabel1.setFont(fontbig); // set the font of the label to a big font
        descriptionLabel2.setFont(fontbig); // set the font of the label to a big font
        descriptionLabel1.setOpaque(true); // set the label to be opaque
        descriptionLabel2.setOpaque(true); // set the label to be opaque
        descriptionLabel1.setBackground(Color.gray); // set the background of the label to gray
        descriptionLabel2.setBackground(Color.gray); // set the background of the label to gray
        choosePanel.setLayout(choosePanelLayout); // set the layout of the choose panel to a grid layout
        choosePanel.add(descriptionLabel1); // add the description label to the choose panel
        choosePanel.add(descriptionLabel2); // add the description label to the choose panel
        choosePanel.add(chooseFile); // add the choose file button to the choose panel
        choosePanel.add(chooseRandom); // add the choose random button to the choose panel

        // If the user want to enter the dimentions manually
        GridLayout randomPanelLayout = new GridLayout(2, 2); // create a grid layout for the random panel
        enterRow = new JTextField("Enter how many rows HERE "); // prompt user to enter how many rows in the this text field
        enterRow.setHorizontalAlignment(SwingConstants.CENTER); // align text in the text field to center
        enterColoumn = new JTextField("Enter how many coloumns HERE"); // prompt user to enter how many coloumns in the this text field
        enterColoumn.setHorizontalAlignment(SwingConstants.CENTER); // align text in the text field to center
        submitDimention = new JButton("Enter"); // button to let user submit the dimentions entered
        submitDimention.addActionListener(this); // add an action listener to the button
        noteLabel = new JLabel("Please enter a grid that is greater than 2x3, and smaller than 20x20"); // tell user to enter correct dimentions
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER); // align text in the label to center
        enterRow.setFont(fontmedium); // set the font of the text field to a medium font
        enterColoumn.setFont(fontmedium); 
        submitDimention.setFont(fontmedium); 
        noteLabel.setFont(fontsmall); 
        randomMazeEnter.add(enterRow); // add the text field to the random maze enter panel
        randomMazeEnter.add(enterColoumn); 
        randomMazeEnter.add(submitDimention); // add the submit button to the random maze enter panel
        randomMazeEnter.add(noteLabel);
        randomMazeEnter.setLayout(randomPanelLayout); // set the layout of the random maze enter panel to a grid layout
        reenterDimentonButton = new JButton("Re_Enter Dimention"); // button to let user re-enter the dimentions
        reenterDimentonButton.addActionListener(this); // add an action listener to the button
        homeButton = new JButton("Home Button"); // button to let user go back to the home screen
        homeButton.addActionListener(this);
        findPath = new JButton("Find Path"); // button to let user find the path of the maze 
        findPath.addActionListener(this);
        mazeResultLable1 = new JLabel("Maze Result Will be Shown HERE"); // label to show the result of the maze
        mazeResultLable1.setHorizontalAlignment(SwingConstants.CENTER); // align text in the label to center
        mazeResultLable1.setFont(fontmedium);
        mazeResultLable1.setOpaque(true);
        mazeResultLable1.setBackground(Color.gray);
        bottomRandomPanel.add(mazeResultLable1);
        bottomRandomPanel.add(findPath);
        bottomRandomPanel.add(homeButton);
        bottomRandomPanel.add(reenterDimentonButton);
        bottomRandomPanel.setLayout(new GridLayout(1, 4)); // set the layout of the bottom random panel to a grid layout
        bottomRandomPanel.setMaximumSize(new Dimension(20000, 20000)); // make the panel bigger so that the buttons and panels are visually pleasing

        // If the user chooses to load data from a file
        enterFileName = new JTextField("Enter a file name", 20); // prompt user to enter a file name
        enterFileName.setFont(fontmedium); // set the font of the text field to a medium font
        enterFileName.setHorizontalAlignment(SwingConstants.CENTER);
        proceed = new JButton("Proceed"); // button to let user proceed with the file name
        proceed.addActionListener(this);
        proceed.setFont(fontmedium);
        reenterFileButton = new JButton("Re_Enter File"); // button to let user re-enter the file name
        reenterFileButton.addActionListener(this);
        homeButton1 = new JButton("Home Button"); // button to let user go back to the home screen
        homeButton1.addActionListener(this);
        findPath1 = new JButton("Find Path"); // button to let user find the path of the maze
        findPath1.addActionListener(this);
        mazeResultLable2 = new JLabel("Maze Result Will be Shown HERE"); // label to show the result of the maze
        mazeResultLable2.setHorizontalAlignment(SwingConstants.CENTER);
        mazeResultLable2.setFont(fontmedium);
        mazeResultLable2.setOpaque(true);
        mazeResultLable2.setBackground(Color.gray);
        bottomFilePanel.add(mazeResultLable2);
        bottomFilePanel.add(findPath1);
        bottomFilePanel.add(homeButton1);
        bottomFilePanel.add(reenterFileButton);
        bottomFilePanel.setLayout(new GridLayout(1, 4)); // set the layout of the bottom file panel to a grid layout
        bottomFilePanel.setMaximumSize(new Dimension(20000, 20000));
        fileMazePanel.add(enterFileName);
        fileMazePanel.add(proceed);
        fileMazePanel.setLayout(new GridLayout(1, 2));
        fileMazePanel.setMaximumSize(new Dimension(20000, 20000));

        // seting most of the panel to be invisible except for the home/choose panel
        fileMazePanel.setVisible(false);
        randomMazeEnter.setVisible(false);
        mazePanel.setVisible(false);
        emptyPanel.setVisible(false);
        bottomRandomPanel.setVisible(false);
        bottomFilePanel.setVisible(false);

        // add all the panels to the frame
        setVisible(true);
        setLayout(boxLayout);
        add(choosePanel);
        add(fileMazePanel);
        add(randomMazeEnter);
        add(mazePanel);
        add(emptyPanel);
        add(bottomRandomPanel);
        add(bottomFilePanel);

    } // end of the constructor

    // action listener for the buttons
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand(); // get the command of the button - the word on the button 
        if (command.equals("Press to auto generate a maze")) {  // if the user choose to auto generate a maze
            choosePanel.setVisible(false); // make the choose panel invisible
            randomMazeEnter.setVisible(true); // make the random maze enter panel visible
            emptyPanel.setVisible(true); // make the empty panel visible
        }
        if (command.equals("Press to enter a file")) { // if the user choose to enter a file
            choosePanel.setVisible(false); // make the choose panel invisible
            fileMazePanel.setVisible(true); // make the file maze panel visible
            emptyPanel.setVisible(true); // make the empty panel visible
        }
        if (command.equals("Enter")) { // if the user choose to submit the dimentions of the maze
            validInput = false;  // set validInput to false at first 
            enterValidNumber(); // check if the input is valid, if so, set validInput to true
            if (validInput == true) { // if the input is valid
                border = 'B'; // set the border to be 'B'
                open = 'O'; // set the open to be 'O'
                mouse = 'M'; // set the mouse/starting point to be 'M'
                exit = 'E'; // set the exit to be 'E'
                mapLable = new JLabel[row][coloumn]; // initialize the dimentions of the label 2D array
                pathChar = new char[row][coloumn]; // initialize the dimentions of the char 2D array
                mazePanel.setLayout(new GridLayout(row, coloumn)); // set the layout of the maze panel to a grid layout
                generateRandomMaze(); // generate a random maze
                colorMaze(); // color the maze
                randomMazeEnter.setVisible(false); // make the random maze enter panel invisible
                emptyPanel.setVisible(false); // make the empty panel invisible
                mazePanel.setVisible(true); // make the maze panel visible
                bottomRandomPanel.setVisible(true); // make the bottom random panel visible
            }
        }
        if (command.equals("Proceed")) { // if the user choose to proceed/submit with the file name
            String fileName = enterFileName.getText(); // get the file name from the text field
            file = new File(fileName); // "create" a new file called 'file' with the file name
            
            if(file.isFile()){ // if the file is a file
                try {
                    Scanner fc = new Scanner(file); // create a scanner called to read the file
                    fc.nextInt(); // skip the first and second line of the file
                    fc.nextInt();
                    border = fc.next().charAt(0); // get the border from the file
                    open = fc.next().charAt(0); // get the open from the file
                    mouse = fc.next().charAt(0); // get the mouse/starting point from the file
                    exit = fc.next().charAt(0); // get the exit from the file
                    fc.close(); // close the file scanner 
                } catch (FileNotFoundException e2) { 
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
            
            if (file.isFile() && (border != '+' && open != '+' && mouse != '+' && exit != '+')) { // if the file exists and the file's legend is not the correct path indicator 
                try { 
                    fileMazePanel.setVisible(false); // make the file maze panel invisible
                    emptyPanel.setVisible(false); // make the empty panel invisible
                    mazePanel.setVisible(true); // make the maze panel visible
                    bottomFilePanel.setVisible(true); // make the bottom file panel visible
                    getMapFromFile(fileName); // get the map from the file
                    mazePanel.setLayout(new GridLayout(row, coloumn)); // set the layout of the maze panel to a grid layout
                    colorMaze(); // color the maze
                } catch (IOException e1) {
                    e1.printStackTrace(); // print the error on console
                }
            } else { // if the file does not exist
                MazeProgramming.infoBox("Enter a valid file name or one with correct legend", "ERROR"); // show an error message that tells user to enter a correct file name
            }
        }
        if (command.equals("Re_Enter Dimention")) { // if the user choose to re-enter the dimentions of the maze
            randomMazeEnter.setVisible(true); // make the random maze enter panel visible
            emptyPanel.setVisible(true); // make the empty panel visible
            mazePanel.setVisible(false); // make the maze panel invisible
            bottomRandomPanel.setVisible(false); // make the bottom random panel invisible
            mazePanel.removeAll(); // remove all the labels from the maze panel, so that the lables wont stack up
        }
        if (command.equals("Re_Enter File")) { // if the user choose to re-enter the file name
            fileMazePanel.setVisible(true); // make the file maze panel visible
            emptyPanel.setVisible(true); // make the empty panel visible
            mazePanel.setVisible(false); // make the maze panel invisible
            bottomFilePanel.setVisible(false); // make the bottom file panel invisible
            mazePanel.removeAll(); // remove all the labels from the maze panel, so that the lables wont stack up
        }
        if (command.equals("Home Button")) { // if the user choose to go back to the home/choose panel page
            choosePanel.setVisible(true); // make the choose panel visible
            mazePanel.setVisible(false); // make the maze panel invisible
            bottomFilePanel.setVisible(false); // make the bottom file panel invisible
            bottomRandomPanel.setVisible(false); // make the bottom random panel invisible
            mazePanel.removeAll(); // remove all the labels from the maze panel, so that the lables wont stack up
        }
        if (command.equals("Find Path")) { // if the user choose to find the path of the maze 
            hasPath = false; // set hasPath to false at first
            mazePanel.removeAll(); // remove all the labels from the maze panel, so that the lables that lead to the correct path can be re colored cyan
            isVisited = new boolean[row][coloumn]; // initialize the dimentions of the boolean 2D array
            for (int r = 0; r < row; r++) { // loop through the 2D char array
                for (int c = 0; c < coloumn; c++) {
                    if (pathChar[r][c] == mouse) { // if the char corrosponds to the mouse char
                        startr = r; // set the start row
                        startc = c; // set the start coloumn
                    }
                    if (pathChar[r][c] == exit) { // if the char corrosponds to the exit char
                        exitr = r; // set the exit row
                        exitc = c; // set the exit coloumn
                    }
                }
            }
            stack.push(new Pair(startr, startc)); // push the start position pair into the stack
            findPath(startr, startc); // calling the find path method
            for (int r = 0; r < row; r++) { // loop through the 2D char array
                for (int c = 0; c < coloumn; c++) {
                    if (pathChar[r][c] == correctPath) { // if the char array has a correct path char
                        hasPath = true; // set hasPath to true
                        break; // break out of the loop
                    }
                }
                if (hasPath == true || (startc == exitc - 1 && startr == exitr) || (startc == exitc + 1 && startr == exitr) ||
                 (startc == exitc && startr == exitr - 1) || (startc == exitc && startr == exitr + 1)) { // if the char array has a correct path char or if the start is beside the exit
                    mazeResultLable1.setText("There is a path from the mouse to the exit"); // tell that there is a path from the mouse to the exit
                    mazeResultLable1.setBackground(Color.green); // set the background of the maze result label to green
                    mazeResultLable2.setText("There is a path from the mouse to the exit");
                    mazeResultLable2.setBackground(Color.green);
                } else { // if the conditions are not met 
                    mazeResultLable1.setText("There is no path from the mouse to the exit"); // tell that there is no path from the mouse to the exit
                    mazeResultLable1.setBackground(red); // set the background of the maze result label to red
                    mazeResultLable2.setText("There is no path from the mouse to the exit");
                    mazeResultLable2.setBackground(red);
                }
            }
            colorMaze(); // color the maze
            // built in method to refresh the Jframe so that it can display the correct paths if there is any 
            invalidate();  
            validate(); 
            repaint();
        }
        if (command.equals("Home Button") || command.equals("Re_Enter Dimention") || command.equals("Re_Enter File")) {  // if the user choose to go back to the home/choose panel page, or to re-enter a file or dimentions
            mazeResultLable1.setText("Maze Result Will be Shown HERE"); // set the text of the maze result label to "Maze Result Will be Shown HERE"
            mazeResultLable2.setText("Maze Result Will be Shown HERE");
            mazeResultLable1.setBackground(Color.gray); // set the background of the maze result label to gray
            mazeResultLable2.setBackground(Color.gray);
        }
    } // end of actionPerformed method

    public static void main(String args[]) {

        MazeProgramming frame = new MazeProgramming(); // create a new frame
        Pair pair = new Pair(startr, startc); // create a new pair

    }  // end of main method
} // end of program
 