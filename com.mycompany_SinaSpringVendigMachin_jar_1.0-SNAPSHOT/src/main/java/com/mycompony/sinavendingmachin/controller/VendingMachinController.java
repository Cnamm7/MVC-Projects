/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.controller;

import com.mycompony.sinavendingmachin.dao.VendingMachinPersistenceException;
import com.mycompony.sinavendingmachin.service.VendingMachinInsufficientFundsException;
import com.mycompony.sinavendingmachin.service.VendingMachinNoItemInventoryException;
import com.mycompony.sinavendingmachin.service.VendingMachinServiceLayer;
import com.mycompony.sinavendingmachin.ui.VendingMachinView;

/**
 *
 * @author Cna
 */
public class VendingMachinController {
    private VendingMachinView view;
    private VendingMachinServiceLayer service;
    
    public VendingMachinController(VendingMachinServiceLayer service, VendingMachinView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        String menuSelection = "";
        try {
                while (keepGoing) {
                    
                    getCreditAmount();
                    menuSelection = getMenuSelection();

                    switch (menuSelection) {
                        case "Sandwich":
                            purchaseSandwich();
                            break;
                        case "Water":
                            purchaseWater();
                            break;
                        case "CocaCola":
                            purchaseCocaCola();                           
                            break;
                        case "Pringles":
                            purchasePringles();
                            break;
                        case "Sprit":
                            purchaseSprit();                            
                            break;                            
                        case "CHEETOS":
                            purchaseCHEETOS();                            
                            break;                            
                        case "Chocolate":
                            purchaseChocolate();
                            break;                            
                        case "Bread":
                            purchaseBread();
                            break;                           
                        case "Exit":
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();
                    }

                }
                exitMessage();
            } catch (VendingMachinPersistenceException | VendingMachinNoItemInventoryException | VendingMachinInsufficientFundsException e) {
                view.displayErrorMessage(e.getMessage());
            }
    }
    private void getCreditAmount() throws VendingMachinNoItemInventoryException, VendingMachinPersistenceException
    {   view.printAllMenu(service.getAllInventory());
        view.Exit();
        service.setCredit(view.userCreditEntered());    
    }
    private String getMenuSelection() throws VendingMachinNoItemInventoryException, VendingMachinPersistenceException
    {
        return service.userChoiceItem(view.userChoice(service.listAvailableItems()));
    }
    
    private void purchaseChocolate() throws VendingMachinPersistenceException, VendingMachinInsufficientFundsException, VendingMachinNoItemInventoryException
    {
        view.displayChocolateBanner();
        view.displayChange(service.purchaseItem("Chocolate"));
    }
    
    private void purchaseCocaCola() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displayCocaColaBanner();
        view.displayChange(service.purchaseItem("CocaCola"));
    }
    private void purchaseSprit() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displaySpritBanner();
        view.displayChange(service.purchaseItem("Sprit"));
    }
    private void purchasePringles() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displayPringlesBanner();
        view.displayChange(service.purchaseItem("Pringles"));
    }
    private void purchaseCHEETOS() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displayCHEETOZBanner();
        view.displayChange(service.purchaseItem("CHEETOS"));
    }
    private void purchaseBread() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displayBreadBanner();
        view.displayChange(service.purchaseItem("Bread"));
    }
    private void purchaseSandwich() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displaySandwichBanner();
        view.displayChange(service.purchaseItem("Sandwich"));
    }
    private void purchaseWater() throws VendingMachinInsufficientFundsException, VendingMachinPersistenceException, VendingMachinNoItemInventoryException
    {
        view.displayWaterBanner();
        view.displayChange(service.purchaseItem("Water"));
    }
    private void unknownCommand()
    {
        view.displayUnknownCommandBanner();
    }
    private void exitMessage()
    {
        view.displayExitBanner();
    }
}
