/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.dao;

import com.mycompony.sinavendingmachin.dao.VendingMachinDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinPersistenceException;
import com.mycompony.sinavendingmachin.dto.Inventory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cna
 */
public class VendingMachinDaoStubImpl implements VendingMachinDao{
    public Inventory firstInventory;
    public Inventory secondInventory;
    
    public VendingMachinDaoStubImpl()
    {
        firstInventory = new Inventory("firstInventory");
        firstInventory.setCost("1.02");
        firstInventory.setQuantity("10");
        
        secondInventory = new Inventory("secondInventory");
        secondInventory.setCost("15.01");
        secondInventory.setQuantity("0");
        
    }
    public VendingMachinDaoStubImpl(Inventory testInventory)
    {
        this.firstInventory = testInventory;
    }
    @Override
    public List<Inventory> getAllAvailableInventory() throws VendingMachinPersistenceException {
        List<Inventory> available = new ArrayList();
        available.add(firstInventory);
        return available;
    }

    @Override
    public List<Inventory> getAllInventory() throws VendingMachinPersistenceException {
        List<Inventory> available = new ArrayList();
        available.add(firstInventory);
        available.add(secondInventory);
        return available;
    }

    @Override
    public BigDecimal creditDifference(BigDecimal userInput, String UserSelect) throws VendingMachinPersistenceException {
        if(UserSelect.equals("firstInventory"))
        {
            return new BigDecimal("0.48");
        }else if(UserSelect.equals("secondInventory"))
        {
            return new BigDecimal("-13.51");
        }else
        {
            return new BigDecimal("0.00");
        }
    }

    @Override
    public Inventory purchaseItem(String userSelect) throws VendingMachinPersistenceException {
        if(userSelect.equals("firstInventory"))
        {
            return firstInventory;
        }
        return null;
    }

    @Override
    public void addForTestInventory(String key, Inventory item) {
        //doing nothing here
    }
    
}
