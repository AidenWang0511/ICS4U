import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUILayoutL2 extends JFrame{
    
    JButton okButton, yesButton, noButton;

    public GUILayoutL2(){
        setTitle("Layout Demo L2");
        setSize(400,300);

        // create components here
		okButton = new JButton("OK");
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        
        BoxLayout boxLayout1 = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
        setLayout(boxLayout1);
        

        // FlowLayout flowLayout1 = new FlowLayout(FlowLayout.CENTER);
        // setLayout(flowLayout1);

        // GridLayout gridLayout1 = new GridLayout(2,2);
        // setLayout(gridLayout1);

        add(yesButton);
        add(noButton);
        add(okButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        GUILayoutL2 myFrame1 = new GUILayoutL2();
    }
}
