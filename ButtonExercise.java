/**
 * Name: Aiden Wang
 * Date: Oct 28, 2021
 * Description: Create 100 JButtons and add them to a JPanel of 10 by 10.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ButtonExercise extends JFrame implements ActionListener {
    JButton[][] buttons = new JButton[10][10];
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Button Pressed");
    boolean[][] buttonPressed = new boolean[10][10];

    public void actionPerformed(ActionEvent e) {
        //update label when button is pressed
        String command = e.getActionCommand();
        int butNum = Integer.parseInt(command) - 1;
        int row = butNum / 10;
        int col = butNum % 10;
        butNum++;
        if(!buttonPressed[row][col]) {
            label.setText("Button " + butNum + " was pressed");
            buttonPressed[row][col] = true;
            //change color of button
            buttons[row][col].setBackground(Color.RED);
        }
        else {
            label.setText("Button " + butNum + " was already pressed");
        }

    }

    public ButtonExercise() {
        setTitle("Button Exercise");
        setSize(1100, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        panel.setLayout(new GridLayout(10, 10));
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new JButton("" + (i * 10 + j + 1));
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        add(label);
        add(panel);
        setVisible(true);

    }

    public static void main(String[] args) {
        ButtonExercise frame = new ButtonExercise();
    }

}
