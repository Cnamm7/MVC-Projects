/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.dao;

/**
 * the persistence exception for file handling and errors
 * @author Cna
 */
public class FlooringMasteryPersistenceException extends Exception{
    FlooringMasteryPersistenceException(String msg)
    {
        super(msg);
    }
    FlooringMasteryPersistenceException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
