/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.ui;

import com.mycompony.sinavendingmachin.dto.Inventory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Cna
 */
public class VendingMachinView {
    private UserIO io;
    /**
     * constructor for view class with IO object
     * @param io 
     */
    public VendingMachinView(UserIO io) {
        this.io = io;
    }
    /**
     * iterate throw whole list items and show them to the user
     * @param listOfAllItems 
     */
    public void printAllMenu(List<Inventory> listOfAllItems)
    {
        int n = 1;
        io.print("Main Menu");
        for(Inventory item: listOfAllItems)
        {
            io.print(n + "- " + item.getItemName() + " Quantity: " + item.getQuantity() + " Cost: " + item.getCost());
            n++;
        }
    }
    /**
     * get the credit from user
     * @return 
     */
    public BigDecimal userCreditEntered()
    {
        BigDecimal min = new BigDecimal("0.00");
        BigDecimal max = new BigDecimal("100.00");
        BigDecimal userInput = io.readBigDecimal("Please Enter your money in Dollar between 0 and 100 dolor", min, max);
        return userInput; 
    }
    /**
     * give user the option to exit at first level
     */
    public void Exit()
    {        
        boolean check = true;
        while(check)    
        {
            String exit = io.readString("please write exit to exit the program or enter to continue..");
            if(exit.toLowerCase().equals("exit"))
            {
                displayExitBanner();
                System.exit(0);
            }else if(exit.equals(""))
            {
                System.out.println("Continue to the menu");
                check = false;
            }else
            {
                System.out.println("please enter a valid input");
            }    
        }
    }
    /**
     * get the user choice between available items and give user the ability to exit
     * @param listOfAvailableItems
     * @return 
     */
    public int userChoice(List<Inventory> listOfAvailableItems)
    {
        int n = 1;
        io.print("Available Items Menu");
        for(int i = 0; i < (listOfAvailableItems.size()-1); i++)
        {
            io.print(n + "- " + listOfAvailableItems.get(i).getItemName() + " Quantity: " + listOfAvailableItems.get(i).getQuantity() + " Cost: " + listOfAvailableItems.get(i).getCost());
            n++;
        } 
        io.print(n + "- EXIT ");
        return io.readInt("Now Select from the Menu", 1, listOfAvailableItems.size());
    }
    /**
     * get the map of changes and show them to the user
     * @param change 
     */
    public void displayChange(Map<String, Integer> change)
    {
        io.print("Here is your change!");
        io.print("You will get back " + change.get("Quarters") + " Quarters");
        io.print("You will get back " + change.get("Dimes") + " Dimes");
        io.print("You will get back " + change.get("Nickels") + " Nickels");
        io.print("You will get back " + change.get("Pennies") + " Pennies");
    }
    /**
     * display the choice of user
     */
    public void displayChocolateBanner()
    {
        io.print("You choose Chocolate");
    }
    /**
     * display the choice of user
     */
    public void displayWaterBanner()
    {
        io.print("You choose Water");
    }
    /**
     * display the choice of user
     */
    public void displaySandwichBanner()
    {
        io.print("You choose Sandwich");
    }
    /**
     * display the choice of user
     */
    public void displayCocaColaBanner()
    {
        io.print("You choose CocaCola");
    }
    /**
     * display the choice of user
     */
    public void displayPringlesBanner()
    {
        io.print("You choose Pringles");
    }
    /**
     * display the choice of user
     */
    public void displaySpritBanner()
    {
        io.print("You choose Sprit");
    }
    /**
     * display the choice of user
     */
    public void displayCHEETOZBanner()
    {
        io.print("You choose CHEETOZ");
    }
    /**
     * display the choice of user
     */
    public void displayBreadBanner()
    {
        io.print("You choose Bread");
    }
    /**
     * display goodbye banner
     */
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    /**
     * display unknown banner
     */
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    /**
     * display error banner and message
     * @param errorMsg 
     */
    public void displayErrorMessage(String errorMsg) {
    io.print("=== ERROR ===");
    io.print(errorMsg);
    }

}
