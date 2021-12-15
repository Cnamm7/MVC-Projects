/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.service;

/**
 *  a class for handling insufficient fund exception extending exception class
 * @author Cna
 */
public class VendingMachinInsufficientFundsException extends Exception{
    VendingMachinInsufficientFundsException(String msg)
    {
        super(msg);
    }
    VendingMachinInsufficientFundsException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
    
}
