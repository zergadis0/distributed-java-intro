/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charityfleamarket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uczelnia
 */
public class Chairman implements Runnable {
    private Queue<Product> productsQueue;
    private List<Recipient> registeredRecipients;
    private MarketManager marketManager;
    private boolean run;
    
    private static Chairman instance;
    
    private Chairman() {
        productsQueue = new LinkedList<>();
        registeredRecipients = new ArrayList<>();
        run = true;
    }
    
    public static Chairman getInstance() {
        if (instance == null)
            instance = new Chairman();
        return instance;
    }

    public Queue<Product> getProductsQueue() {
        return productsQueue;
    }

    public List<Recipient> getRegisteredRecipients() {
        return registeredRecipients;
    }

    public boolean isRun() {
        return run;
    }

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }
    
    public void start() throws InterruptedException {
        
    }
    
    public boolean addProduct(Product product) {
        if (productsQueue.size() < 10) {
            productsQueue.add(product);
            return true;
        }
        
        return false;
    }
    
    public boolean addRecipient(Recipient recipient) {
        if (registeredRecipients.size() < 10 && !productsQueue.isEmpty()) {
            registeredRecipients.add(recipient);
            System.out.println("Registering " + recipient.getName());
            return true;
        }
        
        return false;
    }
    
    public void End() {
        System.out.println("Chairman says good bye");
        run = false;
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Chairman.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (registeredRecipients.size() > 0 && productsQueue.size() > 0) {
                Random rand = new Random();
                Recipient winner = registeredRecipients.get(rand.nextInt(registeredRecipients.size()));
                
                System.out.println("Winner for auction " + productsQueue.element().getName() + " is " + winner.getName());
                
                for (Recipient r : registeredRecipients) {
                    if (r == winner)
                        r.addProduct(productsQueue.element());
                    else r.notifyAboutFailure();
                }
                
                productsQueue.remove();
                registeredRecipients.clear();
            }
            else if (registeredRecipients.isEmpty() && !productsQueue.isEmpty()) {
                System.out.println("There is no winner for " + productsQueue.element().getName());
                productsQueue.remove();
            }
            else if (productsQueue.size() == 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Chairman.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (productsQueue.size() == 0) {
                    System.out.println("No auctions within 5 seconds. Closing the market.");
                    try {
                        marketManager.EndThreads();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Chairman.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
