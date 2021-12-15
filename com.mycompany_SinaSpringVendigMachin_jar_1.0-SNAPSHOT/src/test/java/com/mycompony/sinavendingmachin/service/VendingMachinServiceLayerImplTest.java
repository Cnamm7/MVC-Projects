/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompony.sinavendingmachin.service;

import com.mycompony.sinavendingmachin.dao.VendingMachinDaoStubImpl;
import com.mycompony.sinavendingmachin.dao.VendingMachinAuditDaoStubImpl;
import com.mycompony.sinavendingmachin.dao.VendingMachinAuditDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinPersistenceException;
import com.mycompony.sinavendingmachin.dto.Inventory;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Cna
 */
public class VendingMachinServiceLayerImplTest {
    private VendingMachinServiceLayer service;

    
    public VendingMachinServiceLayerImplTest() {
//    VendingMachinDao testDao = new VendingMachinDaoStubImpl();
//    VendingMachinAuditDao testAudit = new VendingMachinAuditDaoStubImpl();
//    Change change = new Change();
//    
//    service = new VendingMachinServiceLayerImpl(change, testDao, testAudit);
        ApplicationContext ctx = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
        service = 
            ctx.getBean("serviceLayer", VendingMachinServiceLayer.class);
    }    
    /**
     * test the first item and second item purchases
     */
    @Test
    public void testSetCreditAndPurchase() {
        BigDecimal credit = new BigDecimal("1.50");
        service.setCredit(credit);
        try
        {
            service.purchaseItem("firstInventory");
        }catch(VendingMachinNoItemInventoryException|
               VendingMachinInsufficientFundsException|
                VendingMachinPersistenceException e)
        {
            fail("purcahse was valid and no exception should have thrown.");
        }
        
        assertThrows(VendingMachinInsufficientFundsException.class,
        ()->{
            service.purchaseItem("secondInventory");
        });
            
    }
    /**
     * test all items function
     */           
    @Test
    public void testgetAll()
    {
        Inventory test1 = new Inventory("firstInventory");
        test1.setCost("1.02");
        test1.setQuantity("10");
        
        Inventory test2 = new Inventory("secondInventory");
        test2.setCost("15.01");
        test2.setQuantity("0");
        try{
            List<Inventory> available = service.getAllInventory();
            assertEquals(2, available.size());
            assertTrue(service.getAllInventory().contains(test1));
            assertTrue(service.getAllInventory().contains(test2));
            
        }catch(VendingMachinNoItemInventoryException|VendingMachinPersistenceException e)
        {
            fail("Should return all Items");
        }              
    }
    /**
     * test available list, note we added exit in service layer to the list to selected by the user
     */
    @Test
    public void testGetAvailable()
    {
        Inventory test = new Inventory("firstInventory");
        test.setCost("1.02");
        test.setQuantity("10");
        
        Inventory exit = new Inventory("Exit");
       try{
            List<Inventory> available = service.listAvailableItems();
            assertEquals(2, available.size(), "should return two because we add exit to the list in Service Layer");
            assertTrue(service.listAvailableItems().contains(test));
            assertTrue(service.listAvailableItems().contains(exit));
        }catch(VendingMachinNoItemInventoryException|VendingMachinPersistenceException e)
        {
            fail("Should return available Items");
        }  
    }
    /**
     * check the functionality and return of userChoiceItem in service layer
     */
    @Test
    public void testUserChoiceItem()
    {
        try{
            String name = service.userChoiceItem(1);
            assertEquals(name, "firstInventory", "The item which returned is our item which choosed from list of available items");
        }catch(VendingMachinPersistenceException|VendingMachinNoItemInventoryException e)
        {
            fail("no exception should throw since the index is in our list range");
        }
                
    }
    
}
