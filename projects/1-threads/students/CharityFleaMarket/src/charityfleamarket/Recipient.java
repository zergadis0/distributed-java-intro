/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charityfleamarket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uczelnia
 */
public class Recipient implements Runnable {
    private String name;
    private List<Product> wonProducts;
    private boolean waitingForAucutionFinish;
    private boolean wonLastAuction;

    public Recipient(String name) {
        this.name = name;
        wonProducts = new ArrayList<>();
        wonLastAuction = false;
        waitingForAucutionFinish = false;
    }
    
    @Override
    public void run() {
       while (Chairman.getInstance().isRun()) {
           try {
               int additionalWaitingTime = 0;
               if (wonLastAuction) {
                   Random rand = new Random();
                   additionalWaitingTime = rand.nextInt(10000) + 5000;
               }
               Thread.sleep(5000 + additionalWaitingTime);
               wonLastAuction = false;
               
               if (!Chairman.getInstance().isRun())
                   break;
               
               boolean registered = Chairman.getInstance().addRecipient(this);
               
               if (registered) {
                   waitingForAucutionFinish = true;
                   while (waitingForAucutionFinish)
                       Thread.sleep(1000);
               }
           } catch (InterruptedException ex) {
               Logger.getLogger(Recipient.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       String wonProductsStr = "";
       for (Product p : wonProducts)
           wonProductsStr += " " + p.getName() + ",";
       
       System.out.println(getName() + " says good bye leaving with items" + wonProductsStr);
    }

    public String getName() {
        return name;
    }

    public List<Product> getWonProducts() {
        return wonProducts;
    }
   
    public void addProduct(Product product) {
        System.out.println(getName() + " won " + product.getName());
        wonProducts.add(product);
        waitingForAucutionFinish = false;
    }
    
    public void notifyAboutFailure() {
        waitingForAucutionFinish = false;
    }
}
