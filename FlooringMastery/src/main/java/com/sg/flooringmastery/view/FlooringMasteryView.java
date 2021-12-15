/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.view;

import com.sg.flooringmastery.model.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * view class for interacting with user
 * @author Cna
 */
public class FlooringMasteryView {
    UserIO io;
    public FlooringMasteryView(UserIO io)
    {
        this.io = io;
    }
    /**
     * printing menu
     * @return 
     */
    public int printMenuAndGetUserChoice()
    {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        
        return io.readInt("Please select from the menu");
    }

    /**
     * a function to get date
     * @return string of date in format of year-month-day
     */
    public String getDate()
    {
        io.print("Please enter the date of order in the flowing options");
        String year = io.readString("Please enter year:");
        String month = io.readString("Please enter month:");
        String day = io.readString("Please enter day:");
        //LocalDate date = LocalDate.parse(month+day+year);
        String date = year + "-" + month + "-" + day;
        //System.out.println(test);
        return date;
        //return "06012013";
    }
    /**
     * a function to get order number
     * @return 
     */
    public String getOrder()
    {
        String order = io.readString("Please Enter the Order number: ");
        return order;
    }
    /**
     * a function to get order number and date together
     * @return 
     */
    public List<String> getOrderNumAndDate()
    {
        String date = getDate();
        String order = getOrder();
        List<String> OrderDate = new ArrayList<>();
        OrderDate.add(date);
        OrderDate.add(order);
        return OrderDate;
    }
    /**
     * a function to print list of orders
     * @param listOfOrders 
     */
    public void printOrders(List<Order> listOfOrders) {
        io.print("OrderNumber\t"
                + "CustomerName\t"
                + "State\t"
                + "TaxRate\t"
                + "ProductType\t"
                + "Area\t\t"
                + "CostPerSquareFoot\t"
                + "LaborCostPerSquareFoot\t"
                + "MaterialCost\t"
                + "LaborCost\t"
                + "Tax\t"
                + "Total");
        for(Order order: listOfOrders)
        {
            String orderDetail = order.toString();
            io.print(orderDetail);
        }
        io.readString("Please hit enter to continue.");
        
    }
    /**
     * a function to display error message
     * @param errorMsg 
     */
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
        io.readString("Please hit enter to continue.");
    }
    /**
     * a function to display exit message
     */
    public void displayExitMessage() {
        io.print("Thank you for trying our program!");
    }
    /**
     * a function to show unknown command
     */
    public void displayUnknownCommand() {
        io.print("Unknown Command!!!!");
    }
    /**
     * a function to show banner of list of orders
     */
    public void displaylistOfOrdersBanner() {
        io.print("List of orders for entered date");
    }
    /**
     * a function to show banner of adding an order
     */
    public void displayAddAnOrderBanner() {
        io.print("Adding New Order");
    }
    /**
     * a function to receive info from user and get back the list of string
     * in the following order: date, name, state, product, area
     * @param product
     * @return 
     */
    public List<String> recieveInfoFromUser(List<String> product)
    {
        String date = this.getDate();
        String customerName = io.readString("Please enter customer full name:");
        String state = io.readString("Please enter the state Abreviation:");
        io.print("Product List");
        int n = 1;
        for(String s : product)
        {
            io.print(n + "- " + s);
            n++;
        }
        int userChoice = io.readInt("Please choose from options and enter the number of your choice: ", 1 , product.size());
        String productType = product.get(userChoice - 1);
        String area = io.readString("Please enter the area:");
        List<String> temp = new ArrayList<>();
        temp.add(date);
        temp.add(customerName);
        temp.add(state);
        temp.add(productType);
        temp.add(area);
        return temp;
    }
    /**
     * function to show user the order which need to be edited or deleted before performing action on it
     * @param showAndPrompUserForValidatedList
     * @param forRemoving
     * @return order
     */
    public Order showOrderDetail(Order showAndPrompUserForValidatedList, boolean forRemoving) {
        io.print("CustomerName\t"
                + "State\t"
                + "TaxRate\t"
                + "StateAbbreviation\t"
                + "ProductType\t"
                + "Area\t\t"
                + "CostPerSquareFoot\t"
                + "LaborCostPerSquareFoot\t\t"
                + "MaterialCost\t"
                + "LaborCost\t"
                + "Tax\t"
                + "Total");
        io.print(showAndPrompUserForValidatedList.getCustomerName() + "\t\t"
                + showAndPrompUserForValidatedList.getState().getStateAbbreviation() + "\t" 
                + showAndPrompUserForValidatedList.getState().getTaxRate() + "\t"
                + showAndPrompUserForValidatedList.getState().getStateAbbreviation() + "\t\t\t"
                + showAndPrompUserForValidatedList.getProduct().getProductType() + "\t\t"
                + showAndPrompUserForValidatedList.getArea() + "\t\t"
                + showAndPrompUserForValidatedList.getProduct().getCostPerSquareFoot() + "\t\t\t"
                + showAndPrompUserForValidatedList.getProduct().getLaborCostPerSquareFoot() + "\t\t\t\t"
                + showAndPrompUserForValidatedList.getMaterialCost() + "\t\t"
                + showAndPrompUserForValidatedList.getLaborCost() + "\t\t"
                + showAndPrompUserForValidatedList.getTax() + "\t"
                + showAndPrompUserForValidatedList.getTotal() + "\t");
        String answer;
        if(forRemoving == false)
        {
            answer = io.readStringYesOrNo("Save the order? Please enter yes/no.");  

        }else
        {
            answer = io.readStringYesOrNo("Remove the order? Please enter yes/no.");
        } 
        if(answer.toLowerCase().equals("yes"))
        {   
            
            if(forRemoving == false){io.print("The order saved!");}
            else{io.print("The order removed!");}
            return showAndPrompUserForValidatedList;
        }else
        {
            return null;
        }

    }
    /**
     * a function to print the info of old order info and get the new entry from the user if
     * its empty, just put the old one instead
     * @param orderForEdit
     * @param product
     * @return list of answer in the following order:
     * date, name, state, product type, area, old order number
     */
    public List<String> PrintAndReciveNewEdit(Order orderForEdit, List<String> product) {
        String customerName = io.readString("Please enter customer full name(" + orderForEdit.getCustomerName() + "):");
        String state = io.readString("Please enter the state abbreviation(" + orderForEdit.getState().getStateAbbreviation() + ") :");
        io.print("Product List");
        int n = 1;
        for(String s : product)
        {
            io.print(n + "- " + s);
            n++;
        }
        int userChoice = io.readInt("Please choose from options and enter the number of your choice(" + orderForEdit.getProduct().getProductType() + "): ", 1 , product.size());
        String productType = product.get(userChoice - 1);
        String area = io.readString("Please enter the area(" + orderForEdit.getArea() + "):");        
        List<String> temp = new ArrayList<>();
        temp.add(orderForEdit.getDate());
        temp.add(customerName);
        temp.add(state);
        temp.add(productType);
        temp.add(area);        
        String OldOrderNum = Integer.toString(orderForEdit.getOrderNumber());
        temp.add(OldOrderNum);
        for(int i = 1; i < temp.size()-1; i++)
        {
            if(temp.get(i).equals(""))
            {
                switch(i)
                {
                    case 1:
                    temp.set(1, orderForEdit.getCustomerName());
                    break;
                    case 2:
                    temp.set(2, orderForEdit.getState().getStateAbbreviation());    
                    break;
                    case 3:
                    temp.set(3, orderForEdit.getProduct().getProductType());    
                    break;
                    case 4:
                    temp.set(4, orderForEdit.getArea().toString());    
                    break;                   
                }
            }
        }
        return temp;
    }
    /**
     * display edit banner
     */
    public void displayEditAnOrderBanner() {
        io.print("Edit Order");
    }
    /**
     * display remove banner
     */
    public void displayRemoveAnOrderBanner() {
        io.print("Remove Order");
    }
    /**
     * display export banner
     */
    public void exportAllOrderBanner() {
        io.print("All Order Export");
    }
    /**
     * display all file saved
     */
    public void allFilesSaved() {
       io.print("All reports backed up!!!");
       io.readString("Please Enter to Continue..");
    }

}
