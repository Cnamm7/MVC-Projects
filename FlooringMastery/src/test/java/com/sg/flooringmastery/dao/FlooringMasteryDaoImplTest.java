/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class for testing dao
 * @author Cna
 */
public class FlooringMasteryDaoImplTest {
    FlooringMasteryDao testDao;
    public FlooringMasteryDaoImplTest() {
    }
    /**
     * first create an empty file and then an order object and write that to file
     * for dao purposes, here we need to have a file with info to test our functions
     * @throws Exception 
     */
    @BeforeEach
    public void setUp() throws Exception{
        String fileName = "Orders/Orders_01011111.txt";
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        
        int orderId = 0;
        Order order = new Order(orderId);
        order.setArea(new BigDecimal("0.00"));
        order.setCustomerName("s");
        order.setLaborCost(new BigDecimal("0.00"));
        order.setMaterialCost(new BigDecimal("0.00"));
        order.setTax(new BigDecimal("0.00"));
        order.setTotal(new BigDecimal("0.00"));        
        order.setDate("1111-01-01");
        order.getState().setStateAbbreviation("TX");
        order.getState().setStateName("Texas");
        order.getState().setTaxRate(new BigDecimal("4.45"));
        order.getProduct().setCostPerSquareFoot(new BigDecimal("2.25"));
        order.getProduct().setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        order.getProduct().setProductType("Carpet");
        
        out.println("empty");        
        out.println(order.getOrderNumber()+","
        + order.getCustomerName()+","
        + order.getState().getStateAbbreviation()+","
        + order.getState().getTaxRate()+","
        + order.getProduct().getProductType()+","
        + order.getArea()+","
        + order.getProduct().getCostPerSquareFoot()+","
        + order.getProduct().getLaborCostPerSquareFoot()+","
        + order.getMaterialCost() + ","
        + order.getLaborCost() + ","
        + order.getTax() + ","
        + order.getTotal());
        out.flush();
        out.close();
        
        testDao = new FlooringMasteryDaoImpl(fileName);
    }
    /**
     * a function to simply clear data from test file so it cant have effect on our app
     * @throws Exception 
     */
    @AfterEach
    public void tearDown() throws Exception{
        String fileName = "Orders/Orders_01011111.txt";
        new PrintWriter(new FileWriter(fileName));
    }
 
    /**
     * a function to test when we enter the valid or invalid date and receive the right answer form our function
     * @throws Exception 
     */
    @Test
    public void testGetBackOrderListBasedOnDateEntered() throws Exception{
        
        List<Order> list = testDao.listOfOrders("1111-01-01");
        assertEquals(list.size(), 1);
        
        try
        {
            testDao.listOfOrders("2020-01-10");
            fail("Expected Persistence Exception was not thrown.");
        }catch(FlooringMasteryPersistenceException e)
        {
            return;
        }
        
        
    }
    /**
     * a function to check loading and validating the info of product and state groups
     * @throws Exception 
     */
    @Test
    public void testLoadAndValidateProductAndState() throws Exception{
        List<List<String>> products = testDao.getProducts();
        assertEquals(products.size(), 4, "Should be equal to number of products in file");
        List<List<String>> states = testDao.getStates();
        assertEquals(states.size(), 4, "Should be equal to number of products in file");
        boolean isValid = testDao.isStateValid("tes");
        assertEquals(isValid, false);
        List<String> productsName = testDao.getProductname();
        assertTrue(productsName.contains("Wood"), "Wood is in the list, expected to be true");
    }
    /**
     * the function to check and test what will happen when we add or delete or edit or files
     * @throws Exception 
     */
    @Test
    public void testCheckAddingOrDeletingOrEditingAndValidating() throws Exception{
        int orderId = 1;
        Order order = new Order(orderId);
        order.setArea(new BigDecimal("0.00"));
        order.setCustomerName("s");
        order.setLaborCost(new BigDecimal("0.00"));
        order.setMaterialCost(new BigDecimal("0.00"));
        order.setTax(new BigDecimal("0.00"));
        order.setTotal(new BigDecimal("0.00"));        
        order.setDate("1111-01-01");
        order.getState().setStateAbbreviation("TX");
        order.getState().setStateName("Texas");
        order.getState().setTaxRate(new BigDecimal("4.45"));
        order.getProduct().setCostPerSquareFoot(new BigDecimal("2.25"));
        order.getProduct().setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        order.getProduct().setProductType("Carpet");
        List<Order> list = testDao.listOfOrders("1111-01-01");
        assertEquals(list.size(), 1, "Should be 1 that read from file");
        testDao.addToListOfValidated(order);
        list = testDao.listOfOrders("1111-01-01");
        assertEquals(list.size(), 2, "Should add to the list of validated");
        testDao.EditedList(order, true);
        list = testDao.listOfOrders("1111-01-01");
        assertEquals(list.size(), 1, "Should be 1 cause we removed 1 from list by setting boolean to true");
    }
    /**
     * simply try the writing proccess of exporting to one file if it throw any error or not
     */
    @Test
    public void testCheckReadAllOrdersAndAddSingleFile()
    {
        try{
            testDao.addAllReportsToBackupFile();
        }catch(FlooringMasteryPersistenceException e)
        {
            fail("Should not throw any exception");
        }
        
    }

}
