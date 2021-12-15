/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * a class for inventory items, containing itemNam and cost and quality attributes
 * @author Cna
 */
public class Inventory {
    private String itemName;
    private String cost;
    private String quantity;
    /**
     * constructor of class using item name
     * @param itemName 
     */
    public Inventory(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    /**
     * hashCode for testing purposes
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.itemName);
        hash = 97 * hash + Objects.hashCode(this.cost);
        hash = 97 * hash + Objects.hashCode(this.quantity);
        return hash;
    }
    /**
     * equals along with hashCode for testing purposes
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventory other = (Inventory) obj;
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        return true;
    }
    /**
     * mostly used for testing purposes
     * @return 
     */
    @Override
    public String toString() {
        return "Inventory{" + "itemName=" + itemName + ", cost=" + cost + ", quantity=" + quantity + '}';
    }
    
    
}
