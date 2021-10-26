/*
File Name: AidenWangISP
Author: Aiden Wang
Date: Jan 17th, 2020
Description: An online shop with GUI
 */

//import required things
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class AidenWangISP extends JFrame implements ActionListener{
    //Creates the main panels
    JPanel panHome = new JPanel();
    JPanel panManager = new JPanel();
    JPanel panCustomer = new JPanel();
    JPanel panCheckOut = new JPanel();

    //create sll the arrays and variables needed that's not part of the GUI
    public static double price[] = new double[10];
    public static double discount[] = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5};//for randomization
    public static double revenue = 0;
    public static Random rand = new Random();


    // declare GUI Components for panHome
    JLabel welcome = new JLabel("Welcome To My Booger Shop! Please pick one of the following:");
    JButton btnExit = new JButton("Exit");
    JButton btnCustomer = new JButton("Customer");
    JButton btnManager = new JButton("Manager");

    //declare GUI components for panManager
    JPanel panManagerTable = new JPanel();
    JPanel panManagerTitle = new JPanel();
    JLabel managerHeader = new JLabel("Hi Manager, You can add products, change original names, change stock, and change the price.", JLabel.CENTER);
    //these are public static because I want to access it from all places
    public static JTextField productNameTF[] = new JTextField[10];
    public static JTextField priceTF[] = new JTextField[10];
    public static JTextField stockTF[] = new JTextField[10];
    JLabel managerProductName = new JLabel("Products Name");
    JLabel managerPrice = new JLabel("Price($)");
    JLabel managerStock = new JLabel("Stock");
    JButton btnManagerExit = new JButton("Save & Exit Manager Page");
    JLabel managerWarning = new JLabel("Invalid information will turn ORANGE!");
    JLabel managerRevenue = new JLabel("Revenue: $" + revenue);

    //declare GUI components for panCustomer
    JPanel panCustomerTable = new JPanel();
    JPanel panCustomerTitle = new JPanel();
    public static JLabel productNameLB[] = new JLabel[10];
    public static JLabel priceLB[] = new JLabel[10];
    public static JTextField quantityTF[] = new JTextField[10];
    JLabel customerHeader = new JLabel("Hello, Please enter the amount you want to buy in the box beside each products:", JLabel.CENTER);
    JButton btnCustomerExit = new JButton("Cancel Order");
    JButton btnCheckout = new JButton(("Check Out"));
    public static JLabel image[] = new JLabel[10];
    JLabel customerWarning = new JLabel("Invalid quantity & out of stock item will turn orange", JLabel.CENTER);

    //declare GUI components for panCheckOut
    JLabel provinceCheckLB = new JLabel("Please choose your province:", JLabel.CENTER);
    public static String province[] = {"ON & Manitoba", "New Brunswick & Newfoundland/Labrador & Nova Scotia & PEI & Quebec", "Alberta & NorthWest Territories & Nunavut & Yukon", "British Columbia", "Susketchewan"};
    public static final JComboBox<String> provinceCheckCB = new JComboBox<String>(province);
    JLabel discountCheckLB = new JLabel("Would you like to pay $10 for a 50% -- 100% discount?", JLabel.CENTER);
    public static String discountCheck[] = {"Yes", "No"};
    public static final JComboBox<String> discountCheckCB = new JComboBox<String>(discountCheck);
    JButton btnConfirmed = new JButton("Confirmed");

    //declare GUI component for panCustomerExit
    JPanel panCustomerExit = new JPanel();
    public static JLabel goodByeLB = new JLabel("", JLabel.CENTER);
    JButton btnReturnToHome = new JButton("Return To Home Page");

    //Declare Images and set up their sizes
    public static ImageIcon booger = new ImageIcon(new ImageIcon("Booger.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    public static ImageIcon DNE = new ImageIcon(new ImageIcon("DNE.jpg").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

    public AidenWangISP() {
        //set up the main Windows/frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));//make sure everything always centers
        setTitle("Booger Shop"); // Create a window with a title
        setSize(1080, 1920); // set the window size, better if maximized

        //Creates all the button listeners and combo box listeners
        btnCustomer.addActionListener(this);
        btnExit.addActionListener(this);
        btnManager.addActionListener(this);
        btnManagerExit.addActionListener(this);
        btnCheckout.addActionListener(this);
        btnCustomerExit.addActionListener(this);
        btnConfirmed.addActionListener(this);
        btnReturnToHome.addActionListener(this);
        provinceCheckCB.addActionListener(this);
        discountCheckCB.addActionListener(this);

        // all layouts are created together with the panels

        //setting up the panHome panel
        panHome.setLayout(new BoxLayout(panHome, BoxLayout.PAGE_AXIS));
        welcome.setFont(new Font("Comic Sans", Font.BOLD, 24));//this changes the font
        panHome.add(welcome);//this is how you can add things to a panel
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);//align it at the center
        for (JButton buttonHome : new JButton[] {btnCustomer, btnManager, btnExit}) {//uses this for loop to set up and standerize three buttons
            panHome.add(Box.createVerticalStrut(30));
            buttonHome.setFont(new Font("Arial", Font.PLAIN, 20));
            buttonHome.setAlignmentX(Component.CENTER_ALIGNMENT);
            panHome.add(buttonHome);
        }
        add(panHome);//add to the Frame

        //set up for panManager
        panManager.setLayout(new BoxLayout(panManager, BoxLayout.PAGE_AXIS));
        panManager.add(panManagerTitle);
        managerHeader.setFont(new Font("Comic Sans", Font.BOLD, 18));
        panManagerTitle.setLayout(new BoxLayout(panManagerTitle, BoxLayout.PAGE_AXIS));
        panManagerTitle.add(managerHeader);
        panManager.add(panManagerTable);
        panManagerTable.setLayout(new GridLayout(12,3));//grid layout to ensure better user experience
        panManagerTable.add(managerProductName);
        panManagerTable.add(managerPrice);
        panManagerTable.add(managerStock);
        for(int i=0; i<10; i++){//use this for loop to set up all the grid
            productNameTF[i] = new JTextField("");
            panManagerTable.add(productNameTF[i]);
            priceTF[i] = new JTextField("");
            panManagerTable.add(priceTF[i]);
            stockTF[i] = new JTextField("");
            panManagerTable.add(stockTF[i]);
        }
        panManagerTable.add(managerWarning);
        panManagerTable.add(managerRevenue);
        panManagerTable.add(btnManagerExit);
        managerHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(panManager);
        panManager.setVisible(false);

        //set up for panCustomer
        panCustomer.setLayout(new BoxLayout(panCustomer, BoxLayout.PAGE_AXIS));
        panCustomer.add(panCustomerTitle);
        panCustomerTitle.setLayout(new BoxLayout(panCustomerTitle, BoxLayout.PAGE_AXIS));
        panCustomerTitle.add(customerHeader);
        customerHeader.setFont(new Font("Comic Sans", Font.BOLD, 18));
        panCustomer.add(panCustomerTable);
        panCustomerTable.setLayout(new GridLayout(11,4));
        for(int i=0; i<10; i++){//use for loop to set up the grid for the customer
            image[i] = new JLabel(DNE,JLabel.CENTER);//this image is for pictures that's not a booger
            panCustomerTable.add(image[i]);
            productNameLB[i] = new JLabel(productNameTF[i].getText());
            panCustomerTable.add(productNameLB[i]);
            if(price[i] != 0) {//use a if statement so if there's no price being entered, prices won't print 0
                priceLB[i] = new JLabel("" + priceTF[i].getText());
                panCustomerTable.add(priceLB[i]);
            }else{
                priceLB[i] = new JLabel("");
                panCustomerTable.add(priceLB[i]);
            }
            quantityTF[i] = new JTextField("0");
            panCustomerTable.add(quantityTF[i]);
        }
        panCustomerTable.add(customerWarning);
        panCustomerTable.add(btnCustomerExit);
        panCustomerTable.add(btnCheckout);
        add(panCustomer);
        panCustomer.setVisible(false);

        //set up for panCheckOut
        panCheckOut.setLayout(new GridLayout(6,1));
        panCheckOut.add(provinceCheckLB);
        provinceCheckLB.setFont(new Font("Comic Sans", Font.BOLD, 18));
        panCheckOut.add(provinceCheckCB);//use a combobox to let the customer select provinces
        panCheckOut.add(discountCheckLB);
        discountCheckLB.setFont(new Font("Comic Sans", Font.BOLD, 18));
        panCheckOut.add(discountCheckCB);//uses combobox to let the customer choose
        panCheckOut.add(btnConfirmed);
        btnConfirmed.setFont(new Font("Comic Sans", Font.BOLD, 18));
        add(panCheckOut);
        panCheckOut.setVisible(false);

        //panCustomerExit setup
        panCustomerExit.setLayout(new GridLayout(2,1));
        panCustomerExit.add(goodByeLB);
        goodByeLB.setFont(new Font("Comic Sans", Font.BOLD, 18));
        panCustomerExit.add(btnReturnToHome);
        add(panCustomerExit);
        panCustomerExit.setVisible(false);

        setVisible(true);

    }
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();//used for detecting  which button got pressed

        //these actions are mainly for hiding other panels and showing the target one
        if(command.equals("Exit")){
            dispose();//closes everything
        }
        if(command.equals("Manager")){//when going into manager, this is a method called getInfoFromFile that was entered and saved from the previous time
            panHome.setVisible(false);
            panCheckOut.setVisible(false);
            panCustomer.setVisible(false);
            getInfoFromFile();
            managerRevenue.setText("Revenue: $" + revenue);//updating the revenue, putting it here because it caused some wierd error
            panManager.setVisible(true);
        }
        if(command.equals("Save & Exit Manager Page")){
            //this uses a boolean so when save and exit, if something is wrong it won't return to home screen until you fix it(try catch)
            boolean end = addInfoToFile();//this add to file is also updating new things managers have put in and save it for the next time
            if(end) {
                panCheckOut.setVisible(false);
                panCustomer.setVisible(false);
                panManager.setVisible(false);
                addInfoToFile();
                panHome.setVisible(true);
            }else{
                //nothing happens if inputs are invalid
            }
        }
        if(command.equals("Customer")){
            panCheckOut.setVisible(false);
            panHome.setVisible(false);
            panManager.setVisible(false);
            getInfoFromFile();
            panCustomer.setVisible(true);
        }
        if(command.equals("Check Out")){
            boolean check = checkForInvalidQuantity();
            if(check) {
                panHome.setVisible(false);
                panManager.setVisible(false);
                panCustomer.setVisible(false);
                panCheckOut.setVisible(true);
            }else {

            }
        }
        if(command.equals("Cancel Order")){
            panCheckOut.setVisible(false);
            panCustomer.setVisible(false);
            panManager.setVisible(false);
            panHome.setVisible(true);
        }
        if(command.equals("Confirmed")){
            panCheckOut.setVisible(false);
            panCustomer.setVisible(false);
            panManager.setVisible(false);
            calculateFinalPrice();//by clicking confirmed it will run this method to calculate the final prices
            panCustomerExit.setVisible(true);
        }
        if(command.equals("Return To Home Page")){
            panCheckOut.setVisible(false);
            panCustomer.setVisible(false);
            panManager.setVisible(false);
            panCustomerExit.setVisible(false);
            panHome.setVisible(true);
        }
    }

    public static boolean addInfoToFile() throws NumberFormatException{
        //this methods saves new information for next time and ensures no invalid input
        boolean end = true;
        try {//uses try catch so it will only end if end = true
            File information = new File("information.txt");//creates the file
            PrintWriter writer = new PrintWriter(information);//creates a scanner of the file
            int stockInput = 0;
            double priceInput = 0;
            int i=0;
            while(i<10 && !productNameTF[i].getText().equals("")){//going through all the products that has a name
                writer.println(productNameTF[i].getText());
                //here's an if statement that will be able to change pictures depends on the products name
                if(productNameTF[i].getText().contains("booger") || productNameTF[i].getText().contains("Booger")){
                    image[i].setIcon(booger);
                }else{
                    image[i].setIcon(DNE);
                }
                    try {
                        priceInput = Double.parseDouble(priceTF[i].getText());
                        if(!(priceInput>0)){
                            throw new NumberFormatException();
                        }else{
                            priceTF[i].setBackground(Color.WHITE);
                        }
                    }catch(NumberFormatException e1) {
                        priceTF[i].setBackground(Color.ORANGE);//try catch so the whole section will turn orange if it's invalid input
                        end = false;
                    }
                writer.println(priceInput);
                    try{
                        stockInput = Integer.parseInt(stockTF[i].getText());
                        if(!(stockInput>=0)){
                            throw new NumberFormatException();
                        }else{
                            stockTF[i].setBackground(Color.white);
                        }
                    }catch(NumberFormatException e){
                        stockTF[i].setBackground(Color.ORANGE);//try catch so the whole section will turn orange if it's invalid input
                        end = false;
                    }
                writer.println(stockInput);
                i++;

            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return end;
    }

    public static void getInfoFromFile(){
        //this methods use scanner to scan all the informatino in the saved files to display onto both the manager and customer grids
        try {
            File information = new File("information.txt");//accessing the files
            Scanner fileScanner = new Scanner(information);//create a file scanner
            String nameInput;
            int stockInput;
            double priceInput;
            int i=0;
            while (fileScanner.hasNextLine()) {
                    nameInput = fileScanner.nextLine();
                    productNameLB[i].setText(nameInput);//set bothe TF and LB for panCustomer and panManager
                    productNameTF[i].setText(nameInput);
                    priceInput = Double.parseDouble(fileScanner.nextLine());
                    priceLB[i].setText("" + priceInput);
                    priceTF[i].setText("" + priceInput);
                    stockInput = Integer.parseInt(fileScanner.nextLine());
                    stockTF[i].setText("" + stockInput);
                    i++;
            }
            fileScanner.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double finalPrice = 0;
    public static void calculateFinalPrice(){
        //This method is used to calculate the total price
        finalPrice = 0;
        int number;
        int i=0;
        while(i<10 && !productNameLB[i].getText().equals("")){//it first takes all the quantity of each of the products the customer entered
            int stock = Integer.parseInt(stockTF[i].getText());
            number = Integer.parseInt(quantityTF[i].getText());
            if(number>stock){//use a if statement to check if it's over the stock limit
                finalPrice+=stock*Double.parseDouble(priceLB[i].getText());
                stockTF[i].setText("" + 0);
            }else{
                finalPrice+=number*Double.parseDouble(priceLB[i].getText());
                stock = stock-number;
                stockTF[i].setText("" + stock);
            }
            i++;
            addInfoToFile();//save the new stock to the files
        }
        if(provinceCheckCB.getSelectedItem().equals("ON & Manitoba")){//an if statement to chack which country the customer is in and determine the tax rate with the combobox
            finalPrice = finalPrice*1.13;
        }else if(provinceCheckCB.getSelectedItem().equals("New Brunswick & Newfoundland/Labrador & Nova Scotia & PEI & Quebec")){
            finalPrice = finalPrice*1.15;
        }else if(provinceCheckCB.getSelectedItem().equals("Alberta & NorthWest Territories & Nunavut & Yukon")){
            finalPrice = finalPrice*1.05;
        }else if(provinceCheckCB.getSelectedItem().equals("British Columbia")){
            finalPrice = finalPrice*1.12;
        }else if(provinceCheckCB.getSelectedItem().equals("Susketchewan")){
            finalPrice = finalPrice*1.11;
        }

        int randomDiscount;
        if(discountCheckCB.getSelectedItem().equals("Yes")){//ask the customer for lucky discount, the $10 paid to get this discount won's be taxed
            randomDiscount = rand.nextInt(6);
            finalPrice = finalPrice*discount[randomDiscount] +10;
            revenue += finalPrice;//add up the revenue, it will reset to 0 after you reopen the program
            goodByeLB.setText("Congrats, you have just got a %" + (discount[randomDiscount] *100) + " discount, the final bill is: $" + finalPrice  + ", Have a nice day :)");
        }else{
            revenue+=finalPrice;
            goodByeLB.setText("The final bill is: $" + finalPrice + ", Have a nice day :)");
        }
    }

    public static boolean checkForInvalidQuantity() throws NumberFormatException{
        //this is the try catch for invalid quanity to buy
        boolean check = true;
        int i=0;
        int number;
        int stock = 0;
        while(i<10 && !productNameLB[i].getText().equals("")) {//it first takes all the quantity of each of the products the customer entered
            try {
                number = Integer.parseInt(quantityTF[i].getText());
                stock = Integer.parseInt(stockTF[i].getText());
                if(!(number>=0) || stock-number<0){
                    throw new NumberFormatException();
                }else{
                    quantityTF[i].setBackground(Color.WHITE);
                }
            }catch(NumberFormatException e) {
                quantityTF[i].setBackground(Color.ORANGE);//try catch so the whole section will turn orange if it's invalid input
                check = false;
            }
            i++;
        }
        return check;
    }

    //Main method
    public static void main(String[] args) {
        AidenWangISP frame1 = new AidenWangISP();  //Start the GUI
    }

}