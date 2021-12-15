/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.dao;

/**
 *
 * @author Cna
 */
public interface VendingMachinAuditDao {
    /**
     * a method to get entry date
     * @param entry
     * @throws VendingMachinPersistenceException 
     */
    public void writeAuditEntry(String entry) throws VendingMachinPersistenceException;
}
