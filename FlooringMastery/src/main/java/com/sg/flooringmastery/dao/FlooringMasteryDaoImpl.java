/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * data access object implementation using one map for orders and two lists for holding states and
 * products
 * @author Cna
 */
public class FlooringMasteryDaoImpl implements FlooringMasteryDao{
    private final Map<Integer , Order> orders = new HashMap<>();
    private final List<List<String>> states = new ArrayList<>();
    private final List<List<String>> products = new ArrayList<>();
    public static final String DELIMITER = ",";
    private String fileName;
    private String ORDER_FILE;
    private final String PRODUCTS_FILE = "Data/Products.txt";
    private final String STATE_FILE = "Data/Taxes.txt";
    /**
     * default constructor just for using in normal callings, the file name assigned empty because
     * it will be assigned in calling functions
     */
    public FlooringMasteryDaoImpl()
    {
        ORDER_FILE = "";
    }
    /**
     * a constructor used in testing
     * @param fileName 
     */
    public FlooringMasteryDaoImpl(String fileName)
    {
        ORDER_FILE = fileName;
    }
    /**
     * a function to get the date and change the format of date string to the format of saved files,
     * and then loading and returning the list of orders value
     * @param date
     * @return
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public List<Order> listOfOrders(String date) throws FlooringMasteryPersistenceException{
        String[] temp = date.split("-");
        String ProccessedDate = temp[1]+temp[2]+temp[0];
        ORDER_FILE = "Orders/Orders_" + ProccessedDate + ".txt";
        fileName = ORDER_FILE;
        loadOrderList(fileName);
        return new ArrayList(orders.values());                
    }
    /**
     * a function to load states from file
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private void loadState() throws FlooringMasteryPersistenceException{
        fileName = STATE_FILE;
        loadOrderList(fileName);        
    }
    /**
     * a function to load products from file
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private void loadProduct() throws FlooringMasteryPersistenceException{
        fileName = PRODUCTS_FILE;
        loadOrderList(fileName);
    }
    /**
     * a function to load using loadState and return the states for used purpose in service
     * @return list of states
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public List<List<String>> getStates() throws FlooringMasteryPersistenceException
    {   if(!states.isEmpty()){states.clear();}
        loadState();
        return states;
    }
    /**
     * a function to load using loadProduct and return the products for used purpose in service
     * @return list of products
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public List<List<String>> getProducts() throws FlooringMasteryPersistenceException
    {
        if(!products.isEmpty()){products.clear();}
        loadProduct();
        return products;
    }
    /**
     * a function to validating the state based on the loaded file
     * @param state
     * @return true or false based on validity of state
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public boolean isStateValid(String state) throws FlooringMasteryPersistenceException
    {
        getStates();
        boolean isStateValid = false;
        for(List<String> state1: this.states)
        {
            if(state1.get(0).equalsIgnoreCase(state))
            {
                isStateValid = true;
                break;
            }
        }
        return isStateValid;
    }
    /**
     * a function to get the order and get the file name using date in the order object
     * and check whether the file already exist or not by trying to open the file and throwing exception
     * then get the answer and pass to writing function
     * @param order
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public void addToListOfValidated(Order order) throws FlooringMasteryPersistenceException{
        // date, customername, state, product type, area
        String[] temp = order.getDate().split("-");
        String date = temp[1]+temp[2]+temp[0];
        ORDER_FILE = "Orders/Orders_" + date + ".txt";
        fileName = ORDER_FILE;
        boolean fileAlreadyExist = true;
        try
        {
            loadOrderList(fileName);
        }catch(FlooringMasteryPersistenceException e)
        {
            fileAlreadyExist = false;
        }finally
        {
            writeOrder(fileName, fileAlreadyExist, order, false, false, false, false);
            orders.clear();
        }
                        
    } 
    /**
     * a function to unmarshall the order from file and create a order object
     * @param OrderAsText
     * @return created order
     */
    private Order unmarshallOrder(String OrderAsText){

        String[] orderTokens = OrderAsText.split(DELIMITER);

        int orderNumber = Integer.parseInt(orderTokens[0]);

        Order orderFromFile = new Order(orderNumber);

        orderFromFile.setCustomerName(orderTokens[1]);

        orderFromFile.getState().setStateAbbreviation(orderTokens[2]);

        orderFromFile.getState().setTaxRate(new BigDecimal(orderTokens[3]));
                
        orderFromFile.getProduct().setProductType(orderTokens[4]);
        
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        
        orderFromFile.getProduct().setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        
        orderFromFile.getProduct().setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));

        return orderFromFile;
    }
    /**
     * a function to load lists using file name, check whether its order or state or product file
     * @param fileName
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private void loadOrderList(String fileName) throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- couldn't load the file!", e);
        }

        String currentLine;
        Order currentOrder;
        List<String> product;
        List<String> state;

        int n = 0;
        while (scanner.hasNextLine()) {
            

            currentLine = scanner.nextLine();
            n++;
            if(n == 1){continue;}
            if(fileName == ORDER_FILE)
            {
                currentOrder = unmarshallOrder(currentLine);
                String date = fileName.replace("Orders/Orders_", "");
                date = date.replace(".txt", "");
                String year = date.substring(4 , 8);
                String month = date.substring(2 , 4);
                String day = date.substring(0 , 2);
                date = year + "-" + month + "-" + day;

                currentOrder.setDate(date);

                orders.put(currentOrder.getOrderNumber(), currentOrder);    
            }else if(fileName == PRODUCTS_FILE)
            {
                product = unmarshallProducts(currentLine);
                products.add(product);
                
            }else if(fileName == STATE_FILE)
            {
                state = unmarshallState(currentLine); 
                states.add(state);
            }

            
        }
        scanner.close();
    }
    /**
     * a function to write to file using order, check whether its for exporting purpose or not and
     * a implement a little difference in writing based on isExportingForAll answer
     * @param aOrder
     * @param isExportForAll
     * @return string to write to file
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private String marshallOrder(Order aOrder, boolean isExportForAll) throws FlooringMasteryPersistenceException{

        String orderAsText = aOrder.getOrderNumber() + DELIMITER;

        orderAsText += aOrder.getCustomerName() + DELIMITER;

        orderAsText += aOrder.getState().getStateAbbreviation()+ DELIMITER;

        orderAsText += aOrder.getState().getTaxRate() + DELIMITER;
        
        orderAsText += aOrder.getProduct().getProductType() + DELIMITER;
        
        orderAsText += aOrder.getArea() + DELIMITER;
        
        orderAsText += aOrder.getProduct().getCostPerSquareFoot() + DELIMITER;
        
        orderAsText += aOrder.getProduct().getLaborCostPerSquareFoot() + DELIMITER;

        orderAsText += aOrder.getMaterialCost() + DELIMITER;
        
        orderAsText += aOrder.getLaborCost() + DELIMITER;
        
        orderAsText += aOrder.getTax() + DELIMITER;
        if(isExportForAll == false)
        {
            orderAsText += aOrder.getTotal();    
        }else
        {
            orderAsText += aOrder.getTotal() + DELIMITER;     
        }
        return orderAsText;
        
    }
    /**
     * very big function to decide what to do based on order and file name entered and 
     * based on parameter decide which path should it choose to write to file
     * @param fileName
     * @param fileAlreadyExist
     * @param order
     * @param isForEdit
     * @param isForRemoving
     * @param isForExtract
     * @param FirstTimeExtract
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private void writeOrder(String fileName, boolean fileAlreadyExist, Order order, boolean isForEdit, boolean isForRemoving, boolean isForExtract, boolean FirstTimeExtract) throws FlooringMasteryPersistenceException {
        /*
        used info of fileAlreadyExist for checking if file exist or not
        */
        PrintWriter out;
        try {
                out = new PrintWriter(new FileWriter(fileName, fileAlreadyExist));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save orders data.", e);
        }            
        /*
        creating header file to see if its first time for opening file it will write it
        */
        List<String> header = new ArrayList<>();
        header.add("OrderNumber");
        header.add("CustomerName");
        header.add("State");
        header.add("TaxRate");
        header.add("ProductType");
        header.add("Area");
        header.add("CostPerSquareFoot");
        header.add("LaborCostPerSquareFoot");
        header.add("MaterialCost");
        header.add("LaborCost");
        header.add("Tax");
        header.add("Total");
        String headerInFile = "";
        for(int i = 0; i < header.size()-1; i++)
        {
            headerInFile += header.get(i) + ",";
        }
        headerInFile += header.get(header.size()-1);
        /*
        choosing the correct path based on entry
        */
        if(isForExtract == false){
            if(isForEdit == false){
                if(fileAlreadyExist == false)
                {
                    out.println(headerInFile);
                    order.setOrderNumber(1);
                }else
                {

                    List<Order> availableOrder = listOfOrders(order.getDate());
                    order.setOrderNumber(availableOrder.get(availableOrder.size()-1).getOrderNumber()+1);
                    orders.clear();
                }
                String orderAsText = marshallOrder(order, false);

                out.println(orderAsText);

            }else
            {
                if(isForRemoving == false)
                {
                    List<Order> newOrders = creatEditedList(order);
                    out.println(headerInFile);
                    for(Order o : newOrders)
                    {
                        String orderAsText = marshallOrder(o, false);
                        out.println(orderAsText);
                    }                
                }else
                {

                    List<Order> newOrders = removeFromListOfValidated(order);
                    out.println(headerInFile);                
                    for(Order o : newOrders)
                    {
                        String orderAsText = marshallOrder(o, false);
                        out.println(orderAsText);
                    }                 
                }          
            }
        }
        else
        {
            if(FirstTimeExtract == true)
            {
                headerInFile += ",OrderDate";
                out.println(headerInFile);
            }
            for(Order o : orders.values())
            {
                String orderAsText = marshallOrder(o, isForExtract);
                String[] dateTemp = o.getDate().split("-");
                String dateForExtract = dateTemp[1]+"-"+dateTemp[2]+"-"+dateTemp[0];
                orderAsText += dateForExtract;
                out.println(orderAsText);            
            }                                    
        }
        out.flush();        

        out.close();       
    }
    /**
     * a function to unmarshall products from file
     * @param productAsText
     * @return list of products
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private List<String> unmarshallProducts(String productAsText) throws FlooringMasteryPersistenceException {
        String[] productTokens = productAsText.split(DELIMITER);
        List<String> products = new ArrayList<>();
        products.add(productTokens[0]);
        products.add(productTokens[1]);
        products.add(productTokens[2]);
        return products;
    }
    /**
     * a function to unmarshall states from file
     * @param stateAsText
     * @return list of states
     * @throws FlooringMasteryPersistenceException for file handling
     */    
    private List<String> unmarshallState(String stateAsText) throws FlooringMasteryPersistenceException {
        String[] stateTokens = stateAsText.split(DELIMITER);
        List<String> states = new ArrayList<>();
        states.add(stateTokens[0]);
        states.add(stateTokens[1]);
        states.add(stateTokens[2]);
        return states;     
    }
    /**
     * get the stream of products names
     * @return
     * @throws FlooringMasteryPersistenceException 
     */
    @Override
    public List<String> getProductname() throws FlooringMasteryPersistenceException
    {
        getProducts();
        return products.stream().map((p)->p.get(0)).collect(Collectors.toList());
    }
    /**
     * get the edited order from user and change the order
     * @param order
     * @return list of edited orders to write to file
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private List<Order> creatEditedList(Order order) throws FlooringMasteryPersistenceException{
        List<Order> EditedOrders = listOfOrders(order.getDate());
            for(Order o: EditedOrders)
            {
                if(o.getOrderNumber() == order.getOrderNumber())
                {
                    o.setCustomerName(order.getCustomerName());
                    o.setArea(order.getArea());
                    o.setLaborCost(order.getLaborCost());
                    o.setMaterialCost(order.getMaterialCost());
                    o.setTax(order.getTax());
                    o.setTotal(order.getTotal());
                    o.getProduct().setCostPerSquareFoot(order.getProduct().getCostPerSquareFoot());
                    o.getProduct().setLaborCostPerSquareFoot(order.getProduct().getLaborCostPerSquareFoot());
                    o.getProduct().setProductType(order.getProduct().getProductType());
                    o.getState().setStateAbbreviation(order.getState().getStateAbbreviation());
                    o.getState().setStateName(order.getState().getStateName());
                    o.getState().setTaxRate(order.getState().getTaxRate());        
                }
            }
            orders.clear();
        return EditedOrders;
    }
    /**
     * similar to editing, but removing the user choice and return the new list
     * @param order
     * @return new list with removed object
     * @throws FlooringMasteryPersistenceException for file handling
     */
    private List<Order> removeFromListOfValidated(Order order) throws FlooringMasteryPersistenceException {
        List<Order> EditedOrders = listOfOrders(order.getDate());
        orders.clear();
            for(int i = 0; i < EditedOrders.size(); i++)
            {
                if(EditedOrders.get(i).getOrderNumber() == order.getOrderNumber())
                {
                    EditedOrders.remove(i);
                }
            }
        return EditedOrders;        
    }
    /**
     * a function to get edited list and react based on isForRemoving and pass value to writeOrder function
     * @param order
     * @param isForRemoving
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public void EditedList(Order order, boolean isForRemoving) throws FlooringMasteryPersistenceException
    {
        String[] temp = order.getDate().split("-");
        String date = temp[1]+temp[2]+temp[0];
        ORDER_FILE = "Orders/Orders_" + date + ".txt";
        fileName = ORDER_FILE;
        writeOrder(fileName, false, order, true, isForRemoving, false, false);                
    }
    /**
     * a function to list all files in a directory and iterating throw all of them and writing the
     * information of them to another file to backup
     * @throws FlooringMasteryPersistenceException for file handling
     */
    @Override
    public void addAllReportsToBackupFile() throws FlooringMasteryPersistenceException {
        File file = new File("Orders/");
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++)
        {
            ORDER_FILE = "Orders/" + files[i].getName();
            fileName = ORDER_FILE;
            loadOrderList(fileName);
            fileName = "Backup/DataExport.txt";
            if(i == 0)
            {
                writeOrder(fileName, false, null, false, false, true, true);
            }else
            {
                writeOrder(fileName, true, null, false, false, true, false);
            }
            orders.clear();
        }
                        
    }







}
