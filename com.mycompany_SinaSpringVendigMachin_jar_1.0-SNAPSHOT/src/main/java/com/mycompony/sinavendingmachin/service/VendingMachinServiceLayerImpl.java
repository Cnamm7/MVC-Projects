/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.service;

import com.mycompony.sinavendingmachin.dao.VendingMachinAuditDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinPersistenceException;
import com.mycompony.sinavendingmachin.dto.Inventory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  service layer class for handling business logic
 * @author Cna
 */
public class VendingMachinServiceLayerImpl implements VendingMachinServiceLayer{
    private BigDecimal credit;
    VendingMachinDao dao;
    Change change;
    VendingMachinAuditDao audit;
    /**
     * constructor for service layer which use change audit and Dao as objects
     * @param change
     * @param dao
     * @param audit 
     */
    public VendingMachinServiceLayerImpl(Change change, VendingMachinDao dao, VendingMachinAuditDao audit)
    {
        this.dao = dao;
        this.change = change;
        this.audit = audit;        
    }
    /**
     * for setting credit
     * @param credit 
     */
    @Override
    public void setCredit(BigDecimal credit)
    {
        this.credit = credit;
    }
    /**
     * a function to convert the credit to int after getting it as big decimal from user,
     * the purpose is after purchase and subtracting price and credit in big decimal, this will
     * return the money in cent to change class for calculating the number of coins owe to user
     * @param credit
     * @return
     * @throws VendingMachinInsufficientFundsException
     * @throws VendingMachinPersistenceException 
     */
    private int getCreditToInt(BigDecimal credit) throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException {
        this.credit = credit.setScale(2, RoundingMode.HALF_UP);
        BigDecimal multiplicand = new BigDecimal("100");
        return this.credit.multiply(multiplicand).intValue();
    }
    /**
     * a function to get the remaining number in int and return the change to user,
     * used in purchase item function
     * @param userInputToInt
     * @return 
     */
    private Change getChange(int userInputToInt)
    {
        this.change.ChangeMoney(userInputToInt);
        return change;
    }
    /**
     * a function to list available item and throw error if no item available to choose
     * appending exit option into the list for exiting the program in this level
     * @return list of available items
     * @throws VendingMachinNoItemInventoryException
     * @throws VendingMachinPersistenceException 
     */
    @Override
    public List<Inventory> listAvailableItems() throws VendingMachinNoItemInventoryException, VendingMachinPersistenceException {
        List<Inventory> list = new ArrayList<>();
            
        Inventory exit = new Inventory("Exit");
        list = dao.getAllAvailableInventory();
        if(list.isEmpty())
        {
            throw new VendingMachinNoItemInventoryException("No Item is Available for now, Try again Later");
        }
        list.add(exit);
        return list;
    }
    /**
     * get the user choice in available items menu, based on our business plan we needed to just
     * show and available items which are in our inventory, so this function just act as get the
     * available item choice form user and pass it to view methods
     * @param userChoice
     * @return
     * @throws VendingMachinPersistenceException for file handling
     * @throws VendingMachinNoItemInventoryException for no item available handling
     */
    @Override
    public String userChoiceItem(int userChoice) throws VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        userChoice = userChoice - 1;

        List<Inventory> availableInventory = listAvailableItems();

        return availableInventory.get(userChoice).getItemName();
    }
    /**
     * a function to get all inventory
     * @return list of all
     * @throws VendingMachinPersistenceException for file handling
     */
    @Override
    public List<Inventory> getAllInventory() throws 
            VendingMachinPersistenceException
    {
            return dao.getAllInventory();     
    }
    /**
     * a function to check the credit which got from credit difference in Dao
     * and return if its can be purchased or not based on the answer
     * @param CreditDifference
     * @return
     * @throws VendingMachinPersistenceException
     * @throws VendingMachinInsufficientFundsException 
     */
    private BigDecimal checkCredit(BigDecimal CreditDifference) throws VendingMachinPersistenceException, VendingMachinInsufficientFundsException
    {
        BigDecimal Zero = new BigDecimal("0.00");
        boolean check = true;
        while(check)
        {
            if(CreditDifference.compareTo(Zero) == -1)
           {
               throw new VendingMachinInsufficientFundsException("The fund is insufficient, You had put " + this.credit + " into the machin");
           }else
           {
               check = false; 
           }   
        }
        return CreditDifference;

    }
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
    @Override
    public Map<String, Integer> purchaseItem(String userSelect) throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException {
        Inventory recieved = null;
        try{
        recieved = dao.purchaseItem(userSelect);}
        catch(NullPointerException e){
            throw new VendingMachinNoItemInventoryException("No item remained.");
        }

        Map <String, Integer> changes = new HashMap<>();
        Change change = getChange(getCreditToInt(checkCredit(dao.creditDifference(credit, userSelect))));
        changes.put("Quarters" ,change.getToQuarter());
        changes.put("Dimes", change.getToDime());
        changes.put("Nickels", change.getToNickel());
        changes.put("Pennies", change.getToPenny());
        audit.writeAuditEntry(
            "Someone bought " + recieved.getItemName()+ " from Machin.");
        return changes;
    }
    
}
