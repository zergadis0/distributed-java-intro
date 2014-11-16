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
 * @author Zergadis0
 */
public class Donor implements Runnable {
    private String name;
    private volatile List<Product> products;

    public Donor(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }
    
    @Override
    public void run() {
        while(products.size() > 0 && Chairman.getInstance().isRun()) {
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(5000));
                boolean added = false;
                while (!added) {
                    added = Chairman.getInstance().addProduct(products.get(products.size()-1));
                    Thread.sleep(5000);
                }
                products.remove(products.get(products.size()-1));
            } catch (InterruptedException ex) {
                Logger.getLogger(Donor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(getName() + " says good bye");
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
    
    public void addProduct(Product product) {
        products.add(product);
    }
    
    public Product getLastProduct() {
        return products.get(products.size());
    }
    
    public void removeLastProduct() {
        products.remove(products.size()-1);
    }
}
