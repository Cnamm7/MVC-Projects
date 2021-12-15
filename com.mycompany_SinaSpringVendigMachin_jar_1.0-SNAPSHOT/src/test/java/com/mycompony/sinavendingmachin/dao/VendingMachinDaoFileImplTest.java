/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompony.sinavendingmachin.dao;

import com.mycompony.sinavendingmachin.dto.Inventory;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Cna
 */
public class VendingMachinDaoFileImplTest {
    
    VendingMachinDao testDao;
    
    public VendingMachinDaoFileImplTest() {
    }
    /**
     * create a file for our test
     * @throws Exception 
     */
    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "testDao.txt";        
        new FileWriter(testFile);
        testDao = new VendingMachinDaoFileImpl(testFile);
    }

    /**
     * check the get all inventory and get available functions
     * @throws Exception 
     */
    @Test
    public void testGetAllInventoryAndGetAllAvailable() throws Exception{
        Inventory item1 = new Inventory("item1");
        item1.setCost("1.5");
        item1.setQuantity("4");
        
        Inventory item2 = new Inventory("item2");
        item2.setCost("2.4");
        item2.setQuantity("1");

        Inventory item3 = new Inventory("item3");
        item3.setCost("3.8");
        item3.setQuantity("0");        
        
        testDao.addForTestInventory(item1.getItemName(), item1);
        testDao.addForTestInventory(item2.getItemName(), item2);
        testDao.addForTestInventory(item3.getItemName(), item3);
        
        List<Inventory> getAll = testDao.getAllInventory();
        
        assertNotNull(getAll, "The list of Inventories must not null");
        assertEquals(3, getAll.size(),"List of students should have 2 students.");
        
        assertTrue(testDao.getAllInventory().contains(item1));
        assertTrue(testDao.getAllInventory().contains(item2));
        assertTrue(testDao.getAllInventory().contains(item3));
        
        List<Inventory> getAvailable = testDao.getAllAvailableInventory();
        
        assertNotNull(getAvailable, "The List of Available must not null");
        assertEquals(2, getAvailable.size(),"List of students should have 2 students.");
        
        assertTrue(testDao.getAllAvailableInventory().contains(item1));
        assertTrue(testDao.getAllAvailableInventory().contains(item2));
        assertFalse(testDao.getAllAvailableInventory().contains(item3));
    }
    /**
     * check the credit difference functionality
     * @throws Exception 
     */
    @Test
    public void testcreditDifference() throws Exception
    {
        Inventory item1 = new Inventory("item1");
        item1.setCost("1.5");
        item1.setQuantity("4");
        
        testDao.addForTestInventory(item1.getItemName(), item1);
        
        BigDecimal UserEnteredMoney1 = new BigDecimal("1.5");
        BigDecimal UserEnteredMoney2 = new BigDecimal("7.4");
        BigDecimal UserEnteredMoney3 = new BigDecimal("0.5");
        
        BigDecimal difference1 = testDao.creditDifference(UserEnteredMoney1, "item1");
        BigDecimal difference2 = testDao.creditDifference(UserEnteredMoney2, "item1");
        BigDecimal difference3 = testDao.creditDifference(UserEnteredMoney3, "item1");
        
        BigDecimal result1 = new BigDecimal("0.00");
        BigDecimal result2 = new BigDecimal("5.90");
        BigDecimal result3 = new BigDecimal("-1.00");
        
        assertEquals(difference1, result1, "expecting to be equal");
        assertEquals(difference2, result2, "expecting to be equal");
        assertEquals(difference3, result3, "expecting to be equal");       
    }
    /**
     * test purchase item function in dao
     * @throws Exception 
     */
    @Test
    public void testPurchaseItem() throws Exception
    {
        Inventory item1 = new Inventory("item1");
        item1.setCost("1.5");
        item1.setQuantity("4");
        
        Inventory item2 = new Inventory("item2");
        item2.setCost("1.5");
        item2.setQuantity("0");
        
        testDao.addForTestInventory(item1.getItemName(), item1);

        Inventory returnedItem = testDao.purchaseItem("item1");
        assertEquals(returnedItem, item1, "expecting to be equal");
        assertThrows(NullPointerException.class,
            ()->{
                testDao.purchaseItem("item2");
            });
    }
    
}
