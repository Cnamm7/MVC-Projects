/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompony.sinavendingmachin.service;

import com.mycompony.sinavendingmachin.dao.VendingMachinPersistenceException;
import com.mycompony.sinavendingmachin.dto.Inventory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Cna
 */
public interface VendingMachinServiceLayer {
    /**
     * for setting credit
     * @param credit 
     */
    public void setCredit(BigDecimal credit);
    /**
     * List all available items in the vending machine
     * @return list of available
     * @throws VendingMachinNoItemInventoryException for no item remaining
     * @throws VendingMachinPersistenceException for IO exception part from Dao
     */
    public List<Inventory> listAvailableItems() throws 
            VendingMachinNoItemInventoryException, VendingMachinPersistenceException;
    /**
     * a function to get all inventory items
     * @return list of all inventory objects
     * @throws VendingMachinNoItemInventoryException
     * @throws VendingMachinPersistenceException 
     */
    public List<Inventory> getAllInventory() throws 
            VendingMachinNoItemInventoryException, VendingMachinPersistenceException;
    /**
     * purchase item based on the selection, handling most of business requirements
     * check whether credit is sufficient and if the item is available, then call purchase
     * function from Dao and then use change class to get back the money owe to the user
     * @param userSelect as the item name
     * @return a map of coins type and numbers which should be returned to the user
     * @throws VendingMachinInsufficientFundsException for handling insufficient money
     * @throws VendingMachinPersistenceException for handling general IO exception and handling
     * @throws VendingMachinNoItemInventoryException for handling no item remaining
     */
    public Map<String, Integer> purchaseItem(String userSelect)throws 
            VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException;
    /**
     * get the user choice in available items menu, based on our business plan we needed to just
     * show and available items which are in our inventory, so this function just act as get the
     * available item choice form user and pass it to view methods
     * @param userChoice
     * @return name of item
     * @throws VendingMachinPersistenceException for file handling
     * @throws VendingMachinNoItemInventoryException for no item available handling
     */
    public String userChoiceItem(int userChoice) throws VendingMachinPersistenceException, VendingMachinNoItemInventoryException;
}
