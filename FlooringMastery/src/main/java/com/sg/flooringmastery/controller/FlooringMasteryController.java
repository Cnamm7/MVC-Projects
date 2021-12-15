/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.service.FlooringMasteryInvalidEntryException;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.view.FlooringMasteryView;

/**
 *
 * @author Cna
 */
public class FlooringMasteryController {
    /**
     * creating just two variables of service and view and get all functions from dao and userIO
     * throw them
     */
    private FlooringMasteryService service;
    private FlooringMasteryView view;
    
    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }
    /**
     * menu selection
     */
    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            try {
                    menuSelection = getMenuSelection();

                    switch (menuSelection) {
                        case 1:
                            listOrders();
                            break;
                        case 2:
                            addAnOrder();
                            break;
                        case 3:
                            editAnOrder();
                            break;
                        case 4:
                            removeAnOrder();
                            break;
                        case 5:
                            ExportAllOrder();
                            break;                            
                        case 6:
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();                    
                    }

                } catch (FlooringMasteryPersistenceException | FlooringMasteryInvalidEntryException e) {
                    view.displayErrorMessage(e.getMessage());
            }            
        }
        exitMessage();
    }
    /**
     * a function to get the menu selection choice
     * @return 
     */
    int getMenuSelection()
    {
        return view.printMenuAndGetUserChoice();
    }
    /**
     * a function to list orders for user by getting date
     * @throws FlooringMasteryPersistenceException for file handling
     * @throws FlooringMasteryInvalidEntryException for user entry
     */
    void listOrders() throws FlooringMasteryPersistenceException, FlooringMasteryInvalidEntryException
    {
        view.displaylistOfOrdersBanner();
        view.printOrders(service.listOfOrders(view.getDate()));
    }
    /**
     * showing exit message
     */
    void exitMessage()
    {
        view.displayExitMessage();
    }
    /**
     * showing unknown command
     */
    void unknownCommand()
    {
        view.displayUnknownCommand();
    }
    /**
     * use to handle add an order for user, check whether entry has error and if so prompt user to enter
     * again
     * @throws FlooringMasteryInvalidEntryException for file handling
     * @throws FlooringMasteryPersistenceException for entry handling
     */
    void addAnOrder() throws FlooringMasteryInvalidEntryException, FlooringMasteryPersistenceException{
        view.displayAddAnOrderBanner();        
        boolean hasError = true;
        do
        {
            try{
                service.addToListOfValidated(view.showOrderDetail(service.showAndPrompUserForValidatedList(view.recieveInfoFromUser(service.listOfProduct())), false));
                hasError = false;
            }catch(FlooringMasteryInvalidEntryException e)
            {
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
            
        }while(hasError == true);
    }
    /**
     * a function to handle editing an order for user, if it has error in entry prompt user to enter again
     */
    private void editAnOrder() {
        view.displayEditAnOrderBanner();
        boolean hasError = true;
        do
        {
            try{
                service.addToEditedList(view.showOrderDetail(service.showAndPrompUserForValidatedList(view.PrintAndReciveNewEdit(service.getOrderForEdit(view.getOrderNumAndDate()), service.listOfProduct())), false));
                hasError = false;
            }catch(FlooringMasteryInvalidEntryException | FlooringMasteryPersistenceException e )
            {
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
            
        }while(hasError == true);
        
    }
    /**
     * a function to handle removing from a list, if it has error prompt the user to enter again
     */
    private void removeAnOrder() {
        view.displayRemoveAnOrderBanner();
        boolean hasError = true;
        do
        {
            try{
                service.removeThisOrder(view.showOrderDetail(service.getOrderForEdit(view.getOrderNumAndDate()), true));
                hasError = false;
            }catch(FlooringMasteryInvalidEntryException | FlooringMasteryPersistenceException e )
            {
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
            
        }while(hasError == true);        
    }
    /**
     * a function to export all orders from files and write it to the file
     * @throws FlooringMasteryPersistenceException 
     */
    private void ExportAllOrder() throws FlooringMasteryPersistenceException {
        view.exportAllOrderBanner();
        service.addAllReportsToBackup();
        view.allFilesSaved();
    }
   
}
