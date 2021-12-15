/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.model.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Cna
 */
public interface FlooringMasteryService {
    /**
     * for getting list of products
     * @return list of products name
     * @throws FlooringMasteryPersistenceException for file handling of dao
     */
    public List<String> listOfProduct() throws FlooringMasteryPersistenceException;
    /**
     * getting list of order from dao by using date
     * @param date
     * @return list of orders
     * @throws FlooringMasteryPersistenceException for file handling of dao
     * @throws FlooringMasteryInvalidEntryException for user entry
     */
    public List<Order> listOfOrders(String date) throws FlooringMasteryPersistenceException, FlooringMasteryInvalidEntryException;
    /**
     * a function to add to list after validating
     * @param order
     * @throws FlooringMasteryInvalidEntryException for file handling of dao
     * @throws FlooringMasteryPersistenceException for user entry
     */
    public void addToListOfValidated(Order order) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException;
    /**
     * a function to add to edited list
     * @param order
     * @throws FlooringMasteryInvalidEntryException for file handling in dao
     * @throws FlooringMasteryPersistenceException for user entry
     */
    public void addToEditedList(Order order) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException;
    /**\
     * for getting info from user and create the order object based on those entry and return the order
     * @param recieveInfoFromUser
     * @return order created
     * @throws FlooringMasteryInvalidEntryException for file handling in dao
     * @throws FlooringMasteryPersistenceException for user entry
     */
    public Order showAndPrompUserForValidatedList(List<String> recieveInfoFromUser) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException;
    /**
     * a function to get the order based on order date and order number
     * @param listOfOrderAndDate
     * @return order based on info
     * @throws FlooringMasteryInvalidEntryException for file handling in dao
     * @throws FlooringMasteryPersistenceException for user entry
     */
    public Order getOrderForEdit(List<String> listOfOrderAndDate) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException;
    /**
     * for removing order from list
     * @param showOrderDetail
     * @throws FlooringMasteryInvalidEntryException
     * @throws FlooringMasteryPersistenceException 
     */
    public void removeThisOrder(Order showOrderDetail) throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException;
    /**
     * for getting backup from all files
     * @throws FlooringMasteryPersistenceException 
     */
    public void addAllReportsToBackup() throws FlooringMasteryPersistenceException;
    
}
