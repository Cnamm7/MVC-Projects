/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.dao;

/**
 * a class to extends exception for handling Dao exceptions
 * @author Cna
 */
public class VendingMachinPersistenceException extends Exception{
    VendingMachinPersistenceException(String msg)
    {
        super(msg);
    }
    VendingMachinPersistenceException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
    
}
