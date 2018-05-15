package Animals;
/*
Individual Week 4 
Preston Belford
10/17/16
PRG421
Edward Spear

This class creates a collection of animals and saves them to a database that is loaded at program launch.
*/

/*
import static java.lang.System.*; // Static import of the
// System class methods.
// Now we can use just 'out'
// instead of System.out.
String url = "jdbc:derby://localhost:1521/BookSellerDB";
String user = "bookguy";
String pwd = "$3lleR";
try {
Connection conn =
DriverManager.getConnection(url, user, pwd); // Get Connection
Statement stmt = conn.createStatement(); // Create Statement
String query = "SELECT * FROM Customer";
ResultSet rs = stmt.executeQuery(query); // Execute Query
while (rs.next()) { // Process Results
out.print(rs.getInt("CustomerID") + " "); // Print Columns
out.print(rs.getString("FirstName") + " ");
out.print(rs.getString("LastName") + " ");
out.print(rs.getString("EMail") + " ");
out.println(rs.getString("Phone"));
}
} catch (SQLException se) { } // Catch SQLException
*/

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class AnimalArray {
    public static void main(String[] args)
    {//main program execution
        ArrayList<Animal> animalList = new ArrayList<Animal>(); // Create ArrayList
        Scanner input = new Scanner(System.in); 
        int menu = 0; // Declare menu variable
        System.out.println("This program will store animals for later access.");
        if (loadAnimals(animalList) == false) 
        {
            System.out.println("\nNo animals were loaded.");
        }
        do // Loop menu selection until exit
        {
            do // Correct invalid selection
            {
                // Print menu options and get selection
                System.out.println("\nPlease make a selection from the menu:");
                System.out.println("1. Add an animal to the list");
                System.out.println("2. Delete an animal in the list");
                System.out.println("3. Edit an animal in the list");
                System.out.println("4. Print the animal list");
                System.out.println("5. Quit the program\n");
                menu = input.nextInt();
                if (menu < 1 || menu > 5) 
                { // Check for invalid selection 
                    System.out.println("\nPlease make a valid select of 1-5."); //Print error
                }
            } 
            while (menu < 1 || menu > 5); // End invalid entry loop
            switch (menu) // Switch statement for menu selection
            {
            case 1:
                animalAdd(animalList);
                break;
            case 2:
                animalDelete(animalList);
                break;
            case 3:
                animalEdit(animalList);
                break;
            case 4:
                animalListPrint(animalList);
                break;
            case 5:
                break;
            default:
                break;
            }
        } 
        while (menu != 5); // End continue program
        if (saveAnimals(animalList) == false) 
        {
            System.out.println("\nAnimal list failed to save.\n");
        }
    }
    
    private static String dbURL = "jdbc:derby://localhost:1527/animalDB;create=true;user=zookeeper;password=funfun123";
    
    private static String tableName = "restaurants";
    // jdbc Connection
    
    private static Connection conn = null;
    
    private static Statement stmt = null;
    
    public static void animalAdd(ArrayList<Animal> animalList)
    {  // Method to add animal to ArrayList      
        animalList.add(getNewAnimal()); //Call method to collect a new animal, then add animal to arraylist
        System.out.println("\nThe requested animal has been added."); // Notify user
    }
    public static Animal getNewAnimal() 
    { // Method to collect a new animal
        Scanner scan = new Scanner(System.in);         
        // Get attributes
        System.out.println("\nWhat do you want to name the animal?");
        String name = scan.nextLine();
        System.out.println("\nWhat color is the animal?");
        String color = scan.nextLine();
        System.out.println("\nThe animal is a vertibrate. (true/false)");
        boolean vertibrate = scan.nextBoolean();
        System.out.println("\nThe animal can swim. (true/false)");
        boolean swim = scan.nextBoolean();
        return new Animal(name, color, vertibrate, swim); // Return animal
    }
    public static void animalDelete(ArrayList<Animal> animalList) 
    { // Method to remove animal from ArrayList
        int animalIndex = getLocateAnimal(animalList); // Call method to locate animal index
        if (animalIndex > -1) 
        { // if animal is returned
            animalList.remove(animalIndex); // Remove animal from ArrayList
            System.out.println("\nThe animal was removed."); // Notify user
        } 
        else 
        { // If default no animal is returned
            System.out.println("\nThe animal was not found."); // Notify user
        }
    }
    public static void animalEdit(ArrayList<Animal> animalList) 
    { // Method to edit animal in ArrayList
        int animalIndex = getLocateAnimal(animalList); //Call method to locate animal index
        if (animalIndex > -1) 
        { // if animal is returned
            Animal newSpecs = getNewAnimal(); //Create a temp animal and assign attributes
            animalList.get(animalIndex).setName(newSpecs.getName());
            animalList.get(animalIndex).setColor(newSpecs.getColor());
            animalList.get(animalIndex).setVertibrate(newSpecs.getVertibrate());
            animalList.get(animalIndex).setSwim(newSpecs.getCanSwim());
            System.out.println("Edit complete."); // Notify user
        } 
        else 
        { // If no animal is returned
            System.out.println("\nThe animal was found."); // Notify user
        }
    }
    public static int getLocateAnimal(ArrayList<Animal> animalList) 
    { // Method to locate animal in ArrayList
        Scanner input = new Scanner(System.in); 
        System.out.println("\nWhich animal are we working on?"); //request animal name
        String animalName = input.nextLine();
        int animalIndex = -1; 
        for (int i = 0; i < animalList.size(); i++) 
        { //search arraylist for the provided animal
            if (animalList.get(i).getName().equalsIgnoreCase(animalName)) 
            { 
                animalIndex = i; //update index
            }
        }
        return animalIndex;
    }
    public static void animalListPrint(ArrayList<Animal> animalList) 
    { // Method to print animals in ArrayList
        //<Entry<Sting, String, boolean, boolean>> itrr = animalList.entrySet();
        Iterator itr = animalList.iterator();
        int a = 0;
        if (animalList.size() > 0) 
        { 
            while(itr.hasNext())
            {
                //iterator prints out the animals. although I have no idea why itr.next() prints a reference.
                System.out.println(itr.next() + " " + animalList.get(a).getName() + " is a " + animalList.get(a).getColor() + " " +
                                  ((animalList.get(a).getVertibrate()) ? "vertibrate" : "invertibrate") + " that " + 
                                  ((animalList.get(a).getCanSwim()) ? "can" : "cannot") + " swim.\n");
                a = a+1;
            }
            //for (int i = 0; i < animalList.size();i++) 
            //{//Print each animal in the arraylist
            //   System.out.println(animalList.get(i).getName() + " is a " + animalList.get(i).getColor() + " " +
            //                      ((animalList.get(i).getVertibrate()) ? "vertibrate" : "invertibrate") + " that " + 
            //                      ((animalList.get(i).getCanSwim()) ? "can" : "cannot") + " swim.\n");
            //}
        } 
        else 
        {
            System.out.println("No animals found."); //Failure Feedback.
        }
    }
    public static boolean loadAnimals(ArrayList<Animal> animals) 
    { //method to load animals from file
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("Animals.dat")); //invoke reader
            String line; // Declare variable
            while ((line = br.readLine()) != null) //Loop through all files.
            {
                if (line != "") //Skip Blanks 
                {   
                    String parts[] = line.split(";"); //remove semicolons and writes each animal to the array.
                    animals.add(new Animal(parts[0], parts[1], (parts[2].equals("true")), (parts[3].equals("true")))); // Add animal
                }
            }
            return true;
        }
        catch (IOException e) //catch exception
        {
            return false;
        }
        
    }
    public static boolean saveAnimals(ArrayList<Animal> animals) {//method to Saves animal animals. to file
        try 
        {
            BufferedWriter output = new BufferedWriter(new FileWriter(new File("Animals.dat")));// invoke writer
            for (int i = 0; i < animals.size(); i++) 
            {//writes each animal in semicolon seperated file with each animal on a new line.
                output.write(animals.get(i).getName() + ";" + animals.get(i).getColor() + ";" + animals.get(i).getVertibrate() + ";" + animals.get(i).getCanSwim() + "\r\n");
            }
            output.close();
            return true;
        } 
        catch (IOException e) //catch exception
        {
            e.printStackTrace();
            return false;
        }
        
    }

}