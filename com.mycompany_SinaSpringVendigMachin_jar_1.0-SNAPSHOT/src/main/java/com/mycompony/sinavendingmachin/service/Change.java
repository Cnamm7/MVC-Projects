/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompony.sinavendingmachin.service;


/**
 * a class for changing money with number of each coins as parameters
 * @author Cna
 */
public class Change {
    private int toQuarter;
    private int toDime;
    private int toNickel;
    private int toPenny;
    /**
     * converting the remaining money to number different coins
     * @param remaining
     * @param coin
     * @return 
     */
    public int Convert(int remaining, Coins coin)
    {
        int temp;
        switch(coin)
        {
            case QUARTERS:
                temp = remaining%25;
                toQuarter = (remaining - temp)/25;
                return temp;
            case DIMES:
                temp = remaining%10;
                toDime = (remaining - temp)/10;
                return temp;
            case NICKELS:
                temp = remaining%5;
                toNickel = (remaining - temp)/5;
                return temp;
            case PENNIES:
                toPenny = remaining;
                return 0;
            default:
                return 0;                
        }
    }
    /**
     * a function with while loop to continue changing money untill it becomes zero
     * @param duoToUser get money which machine owe to user
     */
    public void ChangeMoney(int duoToUser)
    {
        int temp = duoToUser;
        while(temp != 0)
        {
            if(temp >= 25)
            {
                temp = Convert(temp, Coins.QUARTERS);
            }else if(temp >= 10)
            {
                temp = Convert(temp, Coins.DIMES);
            }else if(temp >= 5)
            {
                temp = Convert(temp, Coins.NICKELS);
            }else
            {                
                temp = Convert(temp, Coins.PENNIES);
            }
        }
    }

//    public void ChangeMoney(int duoToUser)
//    {
//        int proccessing;
//        int remaining;
//        if(duoToUser >= 25)
//        {
//            remaining = duoToUser%25;
//            toQuarter = (duoToUser - remaining)/25;
//            proccessing = remaining;
//            if(proccessing >= 10)
//            {
//                remaining = proccessing%10;
//                toDime = (proccessing - remaining)/10;
//                proccessing = remaining;
//                if(proccessing >= 5)
//                {
//                    remaining = proccessing%5;
//                    toNickel = (proccessing - remaining)/5;
//                    toPenny = remaining;
//                }else{
//                    toPenny = remaining;
//                }
//            }else if(proccessing >= 5)
//            {
//                remaining = proccessing%5;
//                toNickel = (proccessing - remaining)/5;
//                toPenny = remaining; 
//            }else
//            {
//                toPenny = remaining; 
//            }
//        }
//        else if(duoToUser >= 10)
//        {
//            remaining = duoToUser - (duoToUser%10);
//            toDime = (duoToUser - remaining)/10;
//            proccessing = remaining;
//            remaining = 0;
//            if(proccessing >= 5)
//            {
//                remaining = proccessing%5;
//                toNickel = (proccessing - remaining)/5;
//                toPenny = remaining;
//            }
//            else
//            {
//                toPenny = remaining;
//            }
//        }
//        else if(duoToUser >= 5)
//        {
//            remaining = duoToUser%5;
//            toNickel = (duoToUser - remaining)/5;
//            toPenny = remaining;
//        }
//        else
//        {
//            toPenny = duoToUser;
//        }
//    }

    public int getToQuarter() {
        return toQuarter;
    }

    public int getToDime() {
        return toDime;
    }

    public int getToNickel() {
        return toNickel;
    }

    public int getToPenny() {
        return toPenny;
    }
}
