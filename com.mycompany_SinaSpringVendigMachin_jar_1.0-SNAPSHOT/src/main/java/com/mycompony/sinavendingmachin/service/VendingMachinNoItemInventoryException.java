/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.service;

/**
 * a class for handling exception related to item remaining exceptions
 * @author Cna
 */
public class VendingMachinNoItemInventoryException extends Exception{
    VendingMachinNoItemInventoryException(String msg)
    {
        super(msg);
    }
    VendingMachinNoItemInventoryException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
