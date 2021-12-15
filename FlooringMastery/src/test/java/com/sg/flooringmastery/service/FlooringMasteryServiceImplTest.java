/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryDaoStubImpl;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Cna
 */
public class FlooringMasteryServiceImplTest {
    private FlooringMasteryService service;
    public FlooringMasteryServiceImplTest() {
        FlooringMasteryDao dao = new FlooringMasteryDaoStubImpl();
        service = new FlooringMasteryServiceImpl(dao);
    }
    /**
     * a function to test getting the list of product functionality
     */
    @Test
    public void testGettingListOfProduct(){
        try{
        List<String> products = service.listOfProduct();
        assertEquals(products.size(), 1, "it should return a list of one product object");
        }catch(FlooringMasteryPersistenceException e)
        {
            fail("it should not throw any exception");
        }
    }
    /**
     * test getting list of order and check the data of the default order in our stub dao to check
     * if it added correctly or not
     */
    @Test
    public void testGettingListOfOrders() {
        String fileName = "";
        try{
            List<Order> orders = service.listOfOrders(fileName);
            assertEquals(orders.get(0).getCustomerName(), "s", "asserting that it contains the test order");
            assertEquals(orders.size(), 1, "asserting that the size is just one based on entry to list");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            fail("it should not throw any error");
        }
    }
    /**
     * a function to check what will function of adding answer when we enter null or object
     */
    @Test
    public void testAddingToListOfValidated() {
        Order order = new Order(1);
        Order orderNull = null;
        try{
            service.addToListOfValidated(order);
            
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            fail("it should not throw any error and should just add");
        }
        try{
            service.addToListOfValidated(orderNull);
            fail("it should throw the order discarded");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
    }
    /**
     * similar to adding to list check whether function perform correct based on user choice of
     * losing or saving orders data
     */
    @Test
    public void testAddingToEditedList() {
        Order order = new Order(1);
        Order orderNull = null;
        try{
            service.addToEditedList(order);
            
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            fail("it should not throw any error and should just add");
        }
        try{
            service.addToEditedList(orderNull);
            fail("it should throw the order discarded");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
    }
    /**
     * testing the most important function in test and check whether it validate and calculate correctly
     * the data which entered or not
     */
    @Test
    public void testShowAndPrompUserForValidatedList() {

        List<String> info = new ArrayList<>();
        info.add("2022-12-12");
        info.add("s");
        info.add("TX");
        info.add("Carpet");
        info.add("100.00");
        try{
            Order order = service.showAndPrompUserForValidatedList(info);
            // it should be calculated and be equal to LaborCost = (Area * LaborCostPerSquareFoot)
            assertEquals(order.getLaborCost(), new BigDecimal("210.00"), "it should be correct based on calculation");
            // it should be calculated and be equal to MaterialCost = (Area * CostPerSquareFoot)
            assertEquals(order.getMaterialCost(), new BigDecimal("225.00"), "it should be correct based on calculation");
            // it should be calculated and be equal to Tax = (MaterialCost + LaborCost) * (TaxRate/100)
            // Tax rates are stored as whole numbers            
            assertEquals(order.getTax(), new BigDecimal("19.36"), "it should be correct based on calculation");
            // it should be calculated and be equal to Total = (MaterialCost + LaborCost + Tax)
            assertEquals(order.getTotal(), new BigDecimal("454.36"), "it should be correct based on calculation");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            fail("it should not throw any exception");
        }
        info.remove(0);        
        info.add(0, "2000-12-12");
        try{
            service.showAndPrompUserForValidatedList(info);    
            fail("it should throw exception cause date is not valid!");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
        info.remove(0);
        info.add(0,"2022-12-12");
        
        info.remove(1);        
        info.add(1, "@#?");
        try{
            service.showAndPrompUserForValidatedList(info);    
            fail("it should throw exception cause name is not valid!");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
        info.remove(2);        
        info.add(2, "tex");
        try{
            service.showAndPrompUserForValidatedList(info);    
            fail("it should throw exception cause state is not valid!");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
        info.remove(2);        
        info.add(2, "TX");
        
        info.remove(3);        
        info.add(3, "asf");
        try{
            service.showAndPrompUserForValidatedList(info);    
            fail("it should throw exception cause prodict is not valid!");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
        info.remove(3);        
        info.add(3, "Carpet");
        
        info.remove(4);        
        info.add(4, "80.00");
        try{
            service.showAndPrompUserForValidatedList(info);    
            fail("it should throw exception cause area is not valid!");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
    }
    /**
     * test get order for edit based on validated entry, so if its not valid it should throw exception
     */
    @Test
    public void testGetOrderForEdit() {
        List<String> numAndDate= new ArrayList<>();
        numAndDate.add("2022-12-12");
        numAndDate.add("0");
        try{
            Order order = service.getOrderForEdit(numAndDate);
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            fail("it should not throw exception!");
        }
        
        numAndDate.remove(1);
        numAndDate.add(1,"asdas");
        try{
            Order order = service.getOrderForEdit(numAndDate);
            fail("it should throw exception! invalid order number!");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }        
    }
    /**
     * test remove order based on validated entry, so if its not valid it should throw exception
     */   
    @Test
    public void TestRemoveThisOrder() {
        Order orderNull = null;
        Order orderNotNull = new Order(0);
        try{
            service.removeThisOrder(orderNull);
            fail("it should throw exception and show the order didn't deleted");
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            return;
        }
        try{
            service.removeThisOrder(orderNotNull);
            
        }catch(FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e)
        {
            fail("it should not throw exception!");
        }
    }

}
