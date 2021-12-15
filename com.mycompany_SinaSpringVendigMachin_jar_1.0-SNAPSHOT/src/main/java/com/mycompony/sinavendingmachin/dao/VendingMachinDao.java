/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompony.sinavendingmachin.dao;

import com.mycompony.sinavendingmachin.dto.Inventory;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Cna
 */
public interface VendingMachinDao {
    /**
     * a function to get all available items from inventory 
     * and separate them from items with quantity of 0
     * @return list of available items in a list
     * @throws VendingMachinPersistenceException for file handling
     */
    public List<Inventory> getAllAvailableInventory() throws VendingMachinPersistenceException;
    /**
     * a function to get all items, whether they are available or not
     * @return list of all items
     * @throws VendingMachinPersistenceException for file handling
     */
    public List<Inventory> getAllInventory() throws 
            VendingMachinPersistenceException;
    /**
     * a function to return the amount of credit in decimal, as negative or positive or zero
     * depends on user entry and price of item
     * @param userInput
     * @param UserSelect
     * @return difference in BigDecimal
     * @throws VendingMachinPersistenceException 
     */
    public BigDecimal creditDifference(BigDecimal userInput, String UserSelect) throws
            VendingMachinPersistenceException;
    /**
     * a function to get user option and check the quantity and purchase the item accordingly
     * @param userSelect
     * @return inventory item
     * @throws VendingMachinPersistenceException for file handling
     */
    public Inventory purchaseItem(String userSelect) throws VendingMachinPersistenceException;
    /**
     * a function which used just in test case for adding items to map using this function
     * @param key
     * @param item 
     */
    public void addForTestInventory(String key, Inventory item);
}
