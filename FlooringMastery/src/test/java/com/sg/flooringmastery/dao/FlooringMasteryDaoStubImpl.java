/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * a stub of dao for service test
 * @author Cna
 */
public class FlooringMasteryDaoStubImpl implements FlooringMasteryDao{
    public Order order;
    public FlooringMasteryDaoStubImpl()
    {
        int orderId = 0;
        order = new Order(orderId);
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
    }
    
    public FlooringMasteryDaoStubImpl(Order test)
    {
        this.order = test;
    }
    
    @Override
    public List<Order> listOfOrders(String date) throws FlooringMasteryPersistenceException {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        return orders;
    }

    @Override
    public void addToListOfValidated(Order order) throws FlooringMasteryPersistenceException {
        List<Order> orders = listOfOrders("");
        orders.add(order);        
    }

    @Override
    public List<List<String>> getStates() throws FlooringMasteryPersistenceException {
        List<List<String>> states = new ArrayList<>();
        List<String> state = new ArrayList<>();
        state.add("TX");
        state.add("Texas");
        state.add("4.45");
        states.add(state);
        return states;
    }

    @Override
    public List<List<String>> getProducts() throws FlooringMasteryPersistenceException {
        List<List<String>> products = new ArrayList<>();
        List<String> product = new ArrayList<>();
        product.add("Carpet");
        product.add("2.25");
        product.add("2.10");
        products.add(product);
        return products;
    }

    @Override
    public boolean isStateValid(String state) throws FlooringMasteryPersistenceException {
        return this.getStates().get(0).get(0).equals(state);
    }

    @Override
    public List<String> getProductname() throws FlooringMasteryPersistenceException {
        List<String> products = new ArrayList<>();
        String product = this.getProducts().get(0).get(0);
        products.add(product);
        return products;
    }

    @Override
    public void EditedList(Order order, boolean isForRemoving) throws FlooringMasteryPersistenceException {
        int orderId = 1;
        order = new Order(orderId);
        order.setArea(new BigDecimal("0.00"));
        order.setCustomerName("sNew");
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
        List<Order> orders = listOfOrders("");
        if(isForRemoving == true)
        {
            orders.clear();
        }else
        {
            orders.clear();
            orders.add(order);
        }
        
    }

    @Override
    public void addAllReportsToBackupFile() throws FlooringMasteryPersistenceException {
        //empty because it tested in Dao
        
    }
    
}
