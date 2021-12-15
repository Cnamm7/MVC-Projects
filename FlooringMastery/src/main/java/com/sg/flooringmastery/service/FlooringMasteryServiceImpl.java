/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Cna
 */
public class FlooringMasteryServiceImpl implements FlooringMasteryService{
    private FlooringMasteryDao dao;
    public FlooringMasteryServiceImpl(FlooringMasteryDao dao)
    {
        this.dao = dao;
    }
    /**
     * a function to get the date and check the validity of date and if it was OK get the order list from
     * that date
     * @param date
     * @return list of orders based on date
     * @throws FlooringMasteryPersistenceException for file handling in dao
     * @throws FlooringMasteryInvalidEntryException for data entry
     */
    @Override
    public List<Order> listOfOrders(String date) throws FlooringMasteryPersistenceException, FlooringMasteryInvalidEntryException{
        List<Order> temp = null;
        try{
            temp = dao.listOfOrders(date);    
        }catch(FlooringMasteryPersistenceException e)
        {
            throw new FlooringMasteryInvalidEntryException("The date you entered is not valid");
        }
        
        return temp;
    }
    /**
     * a function to add to the list of validated, by checking if its null so user selected to discard
     * this
     * @param order
     * @throws FlooringMasteryInvalidEntryException for file handling in dao
     * @throws FlooringMasteryPersistenceException for data entry
     */
    @Override
    public void addToListOfValidated(Order order) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException{
        if(order == null)
        {
            throw new FlooringMasteryInvalidEntryException("The order Discarded!");
        }
        dao.addToListOfValidated(order);
    }
    /**
     * a function to add to edited list based on user choice of discarding or adding
     * @param order
     * @throws FlooringMasteryInvalidEntryException file handling in dao
     * @throws FlooringMasteryPersistenceException for data entry
     */
    @Override
    public void addToEditedList(Order order) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException
    {
        if(order == null)
        {
            throw new FlooringMasteryInvalidEntryException("The order Discarded!");
        }
        dao.EditedList(order, false);
    }
    /**
     * a function to receive the date and order number and validate these two and
     * get order based on them
     * @param listOfOrderAndDate
     * @return
     * @throws FlooringMasteryInvalidEntryException file handling in dao
     * @throws FlooringMasteryPersistenceException for entry
     */
    @Override
    public Order getOrderForEdit(List<String> listOfOrderAndDate)throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException
    {
        Order order = null;
        String date = listOfOrderAndDate.get(0);
        String orderNumString = listOfOrderAndDate.get(1);
        try{
        List<Order> orders = listOfOrders(date);
        
        int ordernumber = Integer.parseInt(orderNumString);
        if(ordernumber > orders.get(orders.size()-1).getOrderNumber())
        {
            throw new FlooringMasteryInvalidEntryException("The number you entered is not in Order number list!");            
        }
        for(Order o: orders)
        {
            if(o.getOrderNumber() == ordernumber)
            {
                order = o;
            }
        }
        }catch(FlooringMasteryPersistenceException e)
        {
            throw new FlooringMasteryInvalidEntryException("The date you entered is not valid and we dont have any order for that date!");
            
        }catch(NumberFormatException e)
        {
            throw new FlooringMasteryInvalidEntryException("The number you entered is not a valid number!");
        }
        order.setDate(date);
        return order;
    }
    /**
     * a function to get the info from user and create an order object based on user input
     * @param recieveInfoFromUser
     * @return order created
     * @throws FlooringMasteryInvalidEntryException for entry
     * @throws FlooringMasteryPersistenceException file handling in dao
     */
    @Override
    public Order showAndPrompUserForValidatedList(List<String> recieveInfoFromUser) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException
    {
        /*
        here we just validated info first
        */
        validateEntry(recieveInfoFromUser);
        Order temp;
        /*
        here we added date in edited option in the fifth element of list, so if its new order
        it will have 0 as order number before accepting by user, and if its existing order which edited
        it will atain the older order number to rewriting in the list
        */
        if(recieveInfoFromUser.size() == 5)
        {
            temp = new Order(0);    
        }else
        {            
            temp = new Order(Integer.parseInt(recieveInfoFromUser.get(5)));
        }
        
        temp.setCustomerName(recieveInfoFromUser.get(1));
        temp.getState().setStateAbbreviation(recieveInfoFromUser.get(2));
        temp.getProduct().setProductType(recieveInfoFromUser.get(3));
        temp.setArea(new BigDecimal(recieveInfoFromUser.get(4)));
        List<List<String>> allState = dao.getStates();
        for(List<String> state : allState)
        {
            if(temp.getState().getStateAbbreviation().compareToIgnoreCase(state.get(0)) == 0)
            {
                temp.getState().setStateName(state.get(1));
                temp.getState().setTaxRate(new BigDecimal(state.get(2)));
            }
        }
        List<List<String>> allProducts = dao.getProducts();
        for(List<String> product : allProducts)
        {
            if(temp.getProduct().getProductType().compareToIgnoreCase(product.get(0)) == 0)
            {
                temp.getProduct().setCostPerSquareFoot(new BigDecimal(product.get(1)));
                temp.getProduct().setLaborCostPerSquareFoot(new BigDecimal(product.get(2)));
            }
        }
        temp.setLaborCost(temp.getLaborCost());
        temp.setMaterialCost(temp.getMaterialCost());
        temp.setTax(temp.getTax());
        temp.setTotal(temp.getTotal());
        temp.setDate(recieveInfoFromUser.get(0));
        return temp;                
    }
    /**
     * a function to validating entry from user and throwing exception based on that
     * completed based on business goals
     * @param recieveInfoFromUser
     * @throws FlooringMasteryInvalidEntryException for entry
     * @throws FlooringMasteryPersistenceException file handling in dao
     */
    private void validateEntry(List<String> recieveInfoFromUser) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException
    {
        // the data entered in this order: date, customername, state, product type, area
        /*
        validating date
        checking whether its for past or future or valid entry
        */
        LocalDate ld;
        try
        {
            ld = LocalDate.parse(recieveInfoFromUser.get(0));    
        }catch(DateTimeParseException e)
        {
            throw new FlooringMasteryInvalidEntryException("The date you entered is not valid!");
        }                   
        LocalDate now = LocalDate.now();
        int result = ld.compareTo(now);
        if(result < 0)
        {
            throw new FlooringMasteryInvalidEntryException("The date you entered is for the past!");
        }
        /*
        validating name it should contain a-z A-Z and ',' and '.'
        */
        String temp = recieveInfoFromUser.get(1).trim().replaceAll("\\w" , "");
        temp = temp.replace(",", "");
        temp = temp.replace(".", "");
        temp = temp.replace(" ", "");
        if(temp.length() != 0)
        {
            throw new FlooringMasteryInvalidEntryException("The Name you entered is not valid!");
        }
        /*
        validating state
        */
        
        boolean stateValidity = dao.isStateValid(recieveInfoFromUser.get(2));
        if(stateValidity == false)
        {
            throw new FlooringMasteryInvalidEntryException("The State you entered is not in the list of taxes!");
        }

        /*
        Validating Area should be more than 100
        */
        BigDecimal area = new BigDecimal(recieveInfoFromUser.get(4));
        BigDecimal minimuOrderSize = new BigDecimal("100.00");
        if(area.compareTo(minimuOrderSize) == -1)
        {
            throw new FlooringMasteryInvalidEntryException("The area can't be negative or less than 100 sq ft value");
        }

    }
    /**
     * getting back the product names
     * @return list of product names
     * @throws FlooringMasteryPersistenceException for file handling by dao
     */
    @Override
    public List<String> listOfProduct() throws FlooringMasteryPersistenceException{
        return dao.getProductname();
    }
    /**
     * for removing order and check whether user answered yes or no to delete
     * @param showOrderDetail
     * @throws FlooringMasteryInvalidEntryException for entry
     * @throws FlooringMasteryPersistenceException for handling in dao
     */
    @Override
    public void removeThisOrder(Order showOrderDetail) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException{
        if(showOrderDetail == null)
        {
            throw new FlooringMasteryInvalidEntryException("The order didn't delete");
        }
        dao.EditedList(showOrderDetail, true);
    }
    /**
     * a function to add all existing report to one single file
     * @throws FlooringMasteryPersistenceException for handling in dao
     */
    @Override
    public void addAllReportsToBackup() throws FlooringMasteryPersistenceException {
        dao.addAllReportsToBackupFile();
    }
    
}
