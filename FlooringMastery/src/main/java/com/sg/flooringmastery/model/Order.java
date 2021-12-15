/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * order class as data transfer object containing product and state as two objects and other attributes
 * @author Cna
 */
public class Order {
    private int orderNumber;
    private String customerName;
    private BigDecimal area;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    private String date;
    
    private Products product = new Products();
    private State state = new State();
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public Products getProduct() {
        return product;
    }

    public State getState() {
        return state;
    }
    
    public Order(int orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }


    // it should be calculated and be equal to MaterialCost = (Area * CostPerSquareFoot)
    public BigDecimal getMaterialCost() {
        materialCost = area.multiply(product.getCostPerSquareFoot());
        return materialCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }
    // it should be calculated and be equal to LaborCost = (Area * LaborCostPerSquareFoot)
    public BigDecimal getLaborCost() {
        laborCost = area.multiply(product.getLaborCostPerSquareFoot());
        return laborCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }
    // it should be calculated and be equal to Tax = (MaterialCost + LaborCost) * (TaxRate/100)
    // Tax rates are stored as whole numbers
    public BigDecimal getTax() {
        tax = (materialCost.add(laborCost)).multiply(state.getTaxRate().divide(new BigDecimal("100.00")));
        return tax.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    // it should be calculated and be equal to Total = (MaterialCost + LaborCost + Tax)
    public BigDecimal getTotal() {
        total = (materialCost.add(laborCost)).add(tax);
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    /**
     * toString for representation of order
     * @return 
     */
    @Override
    public String toString() {
        return orderNumber + "\t\t" + customerName + "\t" + state.getStateAbbreviation() + "\t" + state.getTaxRate() + "\t" + product.getProductType() + "\t\t" + area + "\t\t" + product.getCostPerSquareFoot()+ "\t\t\t" + product.getLaborCostPerSquareFoot() + "\t\t\t" + materialCost + "\t\t" + laborCost + "\t\t" + tax + "\t" + total;
    }
    /**
     * for testing purposes
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.orderNumber;
        hash = 97 * hash + Objects.hashCode(this.customerName);
        hash = 97 * hash + Objects.hashCode(this.area);
        hash = 97 * hash + Objects.hashCode(this.materialCost);
        hash = 97 * hash + Objects.hashCode(this.laborCost);
        hash = 97 * hash + Objects.hashCode(this.tax);
        hash = 97 * hash + Objects.hashCode(this.total);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Objects.hashCode(this.product);
        hash = 97 * hash + Objects.hashCode(this.state);
        return hash;
    }
    /*
    used along hashmap for testing purposes
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
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }
    
    
}
