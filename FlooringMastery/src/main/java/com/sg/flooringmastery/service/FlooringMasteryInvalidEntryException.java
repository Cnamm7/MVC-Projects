/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.service;

/**
 * the exception class for handling user entry
 * @author Cna
 */
public class FlooringMasteryInvalidEntryException extends Exception{
    FlooringMasteryInvalidEntryException(String msg)
    {
        super(msg);
    }
    FlooringMasteryInvalidEntryException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
