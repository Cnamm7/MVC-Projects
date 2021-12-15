/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.util.List;

/**
 *
 * @author Cna
 */
public interface FlooringMasteryDao {
    /**
     * should be a function to get back list of orders based on date entry by user
     * @param date
     * @return list of orders
     * @throws FlooringMasteryPersistenceException for file handling
     */
    List<Order> listOfOrders(String date) throws FlooringMasteryPersistenceException;
    /**
     * should be a function to just add to the list after validating in service
     * @param order
     * @throws FlooringMasteryPersistenceException for file handling
     */
    public void addToListOfValidated(Order order) throws FlooringMasteryPersistenceException;    
    /**
     * a function to return the states info to service for validating
     * @return list of states
     * @throws FlooringMasteryPersistenceException for file handling
     */
    public List<List<String>> getStates() throws FlooringMasteryPersistenceException;
    /**
     * a function to return the products info to service for validating
     * @return list of products
     * @throws FlooringMasteryPersistenceException for file handling
     */
    public List<List<String>> getProducts() throws FlooringMasteryPersistenceException;
    /**
     * a function to validate state from file and answer to service
     * @param state
     * @return true or false
     * @throws FlooringMasteryPersistenceException for file handling
     */
    public boolean isStateValid(String state)throws FlooringMasteryPersistenceException;
    /**
     * a function to get products name in a list by using stream
     * @return list of products name
     * @throws FlooringMasteryPersistenceException 
     */    
    public List<String> getProductname() throws FlooringMasteryPersistenceException;
    /**
     * a function to pass the edited order to write to file, isForRemoving is just for distinguishing
     * between normal edit or removing the user choice
     * @param order
     * @param isForRemoving
     * @throws FlooringMasteryPersistenceException 
     */
    public void EditedList(Order order, boolean isForRemoving) throws FlooringMasteryPersistenceException;
    /**
     * a function to list all files in orders folder and write them back to backup list
     * @throws FlooringMasteryPersistenceException 
     */
    public void addAllReportsToBackupFile() throws FlooringMasteryPersistenceException;
    


    
}
