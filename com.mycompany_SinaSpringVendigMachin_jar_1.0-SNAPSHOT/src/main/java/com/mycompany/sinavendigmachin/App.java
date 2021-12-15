/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sinavendigmachin;

import com.mycompony.sinavendingmachin.controller.VendingMachinController;
import com.mycompony.sinavendingmachin.dao.VendingMachinAuditDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinAuditDaoImpl;
import com.mycompony.sinavendingmachin.dao.VendingMachinDao;
import com.mycompony.sinavendingmachin.dao.VendingMachinDaoFileImpl;
import com.mycompony.sinavendingmachin.service.Change;
import com.mycompony.sinavendingmachin.service.VendingMachinServiceLayer;
import com.mycompony.sinavendingmachin.service.VendingMachinServiceLayerImpl;
import com.mycompony.sinavendingmachin.ui.UserIO;
import com.mycompony.sinavendingmachin.ui.UserIOConsoleImpl;
import com.mycompony.sinavendingmachin.ui.VendingMachinView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Cna
 */
public class App {
        public static void main(String[] args) {
//        // Instantiate the UserIO implementation
//        UserIO myIo = new UserIOConsoleImpl();
//        // Instantiate the View and wire the UserIO implementation into it
//        VendingMachinView myView = new VendingMachinView(myIo);
//        // Instantiate the DAO
//        VendingMachinDao myDao = new VendingMachinDaoFileImpl();
//        // Instantiate the Audit DAO
//        VendingMachinAuditDao myAuditDao = new VendingMachinAuditDaoImpl();
//        // Instatiate the Change object
//        Change change = new Change();
//        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
//        VendingMachinServiceLayer myService = new VendingMachinServiceLayerImpl(change, myDao, myAuditDao);
//        // Instantiate the Controller and wire the Service Layer into it
//        VendingMachinController controller = new VendingMachinController(myService, myView);
//        // Kick off the Controller
//        controller.run();
            ApplicationContext ctx = 
               new ClassPathXmlApplicationContext("applicationContext.xml");
            VendingMachinController controller = 
               ctx.getBean("controller", VendingMachinController.class);
            controller.run();
        }    
}
