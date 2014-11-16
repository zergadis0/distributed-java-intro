/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charityfleamarket;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author Zergadis0
 */
public class CharityFleaMarket {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        List<Donor> donors = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Donor donor = new Donor("Donor " + (i+1));
            for (int j = 0; j < 10; j++)
                donor.addProduct(new Product("Product " + (i+1) + " " + (j+1)));
            donors.add(donor);
        }
        
        MarketManager marketManager = new MarketManager();
        
        for (Donor d : donors)
            marketManager.addDonor(d);
        for (int i = 0; i < 15; i++)
            marketManager.addRecipient(new Recipient("Recipient " + (i+1)));
        
        Chairman.getInstance().setMarketManager(marketManager);
        marketManager.startThreads();
        
        //Object[] aaa = products.stream().filter(o -> o.getName().equals("test1")).toArray();
        //Product t = (Product) aaa[0];
        //System.out.print(t.getName());
    }
    
}
