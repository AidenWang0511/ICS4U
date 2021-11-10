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

public class AidenWangMazeAssignment extends JFrame implements ActionListener{

    public static char[][] maze = new char[20][20];
    public static boolean[][] visited = new boolean[20][20];
    public static boolean[][] check = new boolean[20][20];
    public static int maxR = 20;
    public static int maxC = 20;
    public static char barrierChar = 'B';
    public static char startChar = 'S';
    public static char exitChar = 'E';
    public static char openChar = 'O';

    JPanel[] fillPan = new JPanel[10];
    public static int panCounter = 0;
    JLabel[] fillLab = new JLabel[10];
    public static int labCounter = 0;
    JLabel warnLab = new JLabel("The dimension must be within the range 2x3 or 3x2 to 20x20, unqualified number wil turn red!");
    JPanel userInterface = new JPanel();
    JLabel rowPrompt = new JLabel("Enter the number of rows of the maze");
    JLabel colPrompt = new JLabel("Enter the number of columns of the maze");
    JTextField rowInput = new JTextField(10);
    JTextField colInput = new JTextField(10);
    JButton btnGenerate = new JButton("Generate");

    JPanel mazePanel = new JPanel();
    JLabel[][] mazeLabel = new JLabel[20][20];

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if(command.equals("Generate")){
            if(rowInput.getText().equals("")){
                rowInput.setBackground(Color.RED);

            }
            if(colInput.getText().equals("")){
                colInput.setBackground(Color.RED);
            }else{
                int tempRow = Integer.parseInt(rowInput.getText());
            }
        }
    }
    

    public AidenWangMazeAssignment() {
        fillInLabels();
        setTitle("Button Exercise");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,0));

        btnGenerate.addActionListener(this);

        userInterface.setLayout(new GridLayout(0,2));
        rowInput.setPreferredSize(new Dimension(60, 30));
        colInput.setPreferredSize(new Dimension(60, 30));
        rowPrompt.setFont(new Font("Comic Sans", Font.BOLD, 18));
        colPrompt.setFont(new Font("Comic Sans", Font.BOLD, 18));
        userInterface.add(rowPrompt);
        userInterface.add(rowInput);
        userInterface.add(colPrompt);
        userInterface.add(colInput);
        userInterface.add(warnLab);
        userInterface.add(btnGenerate);

        mazePanel.setLayout(new GridLayout(maxR, maxC));
        // label.setFont(new Font("Arial", Font.PLAIN, 30));
        // label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(userInterface);
        setVisible(true);

    }

    /**
     * prompt user for maze dimensions and randomly generate the maze as required
     * method name: mazeGen
     * @return void - procedure method
     */
    public void mazeGen(){
         
    }

    /**
     * initialize all fill in labels to ""
     * method name: fillInLabels
     * @return void - procedure method
     */
    public void fillInLabels(){
        for(int i = 0; i < 10; i++){
            fillLab[i] = new JLabel("");
        }
    }


    public static void main(String[] args) {
        AidenWangMazeAssignment frame = new AidenWangMazeAssignment();
    }

}
