/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.dao;

import com.mycompony.sinavendingmachin.dto.Inventory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Cna
 */
public class VendingMachinDaoFileImpl implements VendingMachinDao{
    private Map<String, Inventory> inventory = new HashMap<>();
    private final String VENDING_FILE;
    public static final String DELIMITER = "::";
    /**
     * first constructor for main Dao purpose using main file
     */
    public VendingMachinDaoFileImpl()
    {
        VENDING_FILE = "vending.txt";
    }
    /**
     * second constructor for test Dao purpose
     * @param vendingTextFile 
     */
    public VendingMachinDaoFileImpl(String vendingTextFile)
    {
        VENDING_FILE = vendingTextFile;
    }
    /**
     * add to the list, just for testing purposes
     * @param key
     * @param item 
     */
    @Override
    public void addForTestInventory(String key, Inventory item)
    {
        inventory.put(key, item);
    }
    /**
     * a function to get all available items from inventory 
     * and separate them from items with quantity of 0
     * @return list of available items output will be empty list if we don't have any item
     * @throws VendingMachinPersistenceException 
     */
    @Override
    public List<Inventory> getAllAvailableInventory() throws 
            VendingMachinPersistenceException
    {
        loadInventory();
        Collection<Inventory> values = inventory.values();
        return values.stream().filter((v)->Integer.parseInt(v.getQuantity())>0).collect(Collectors.toList());
    }
    /**
     * a function to get all items, whether they are available or not
     * @return list all inventory, creating Arraylist, but will return empty Arraylist if we don't have any
     * @throws VendingMachinPersistenceException 
     */
    @Override
    public List<Inventory> getAllInventory() throws 
            VendingMachinPersistenceException
    {
        loadInventory();
        return new ArrayList(inventory.values());
    }
    /**
     * a function to return the amount of credit in decimal, as negative or positive or zero
     * depends on user entry and price of item 
     * @param credit
     * @param userSelect
     * @return return negative, zero or positive value based on the input and price
     * @throws VendingMachinPersistenceException 
     */
    @Override
    public BigDecimal creditDifference(BigDecimal credit, String userSelect) throws
            VendingMachinPersistenceException
    {
        Inventory temp = inventory.get(userSelect);
        BigDecimal cost = new BigDecimal(temp.getCost());
        BigDecimal result = credit.subtract(cost);
        return result.setScale(2, RoundingMode.HALF_UP);
    }
    /**
     * a function to get user option and check the quantity and purchase the item accordingly
     * @param userSelect
     * @return return item which purchased, return null if quantity is equal or less than one
     * @throws VendingMachinPersistenceException 
     */
    @Override
    public Inventory purchaseItem(String userSelect) throws 
            VendingMachinPersistenceException
    {
        loadInventory();
        Inventory item = inventory.get(userSelect);
        int quantity = Integer.parseInt(item.getQuantity());
        if(quantity > 0)
        {
            quantity = quantity - 1;
            item.setQuantity(Integer.toString(quantity));
            writeInventory();
            return item;
        }
        return null;
    }
    /**
     * get inventory as text from a line of text in file and simply separate them in array
     * @param inventoryAsText
     * @return 
     */
    private Inventory unmarshallInventory(String inventoryAsText){

        String[] inventoryTokens = inventoryAsText.split(DELIMITER);

        String itemName = inventoryTokens[0];

        Inventory inventoryFromFile = new Inventory(itemName);

        inventoryFromFile.setQuantity(inventoryTokens[1]);

        inventoryFromFile.setCost(inventoryTokens[2]);

        return inventoryFromFile;
    }
    /**
     * read the file line by line to get each inventory item
     * @throws VendingMachinPersistenceException for file handling
     */
    private void loadInventory() throws VendingMachinPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinPersistenceException(
                    "-_- Could not load inventory data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentStudent holds the most recent Inventory unmarshalled
        Inventory currentInventory;
        // Go through VENDING_FILE line by line, decoding each line into an 
        // Inventory object by calling the unmarshallStudent method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Inventory
            currentInventory= unmarshallInventory(currentLine);

            // We are going to use the Inventory name as the map key for our Inventory object.
            // Put currentInventory into the map using Inventry name as the key
            inventory.put(currentInventory.getItemName(), currentInventory);
        }
        // close scanner
        scanner.close();
    }
    /**
     * a function to create a string which will be used to write to the file
     * @param aInventory
     * @return 
     */
    private String marshallInventory(Inventory aInventory){
     // Item name
        String inventoryAsText = aInventory.getItemName() + DELIMITER;

        // add the rest of the properties in the correct order:

        // Item quantity   
        inventoryAsText += aInventory.getQuantity() + DELIMITER;

        // Item Price
        inventoryAsText += aInventory.getCost();

        // We have now turned a Item to text! Return it!
        return inventoryAsText;
    }
     /**
     * Writes all Items in the Inventory out to a Vending_FILE.  See loadInventory
     * for file format.
     * 
     * @throws ClassRosterPersistenceException if an error occurs writing to the file
     */
    private void writeInventory() throws VendingMachinPersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_FILE));
        } catch (IOException e) {
            throw new VendingMachinPersistenceException(
                    "Could not save item data.", e);
        }

        String inventoryAsText;
        List<Inventory> inventoryList = this.getAllInventory();
        for (Inventory currentItem : inventoryList) {
            // turn an inventory into a String
            inventoryAsText = marshallInventory(currentItem);
            // write the inventory object to the file
            out.println(inventoryAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }
    
}
