/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charityfleamarket;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Uczelnia
 */
public class MarketManager {
    private List<Donor> donors;
    private List<Recipient> recipients;
    private ExecutorService donorsExecutorService;
    private ExecutorService recipientsExecutorService;
    private ExecutorService chairmanExecutorService;
    
    public MarketManager() {
        donors = new ArrayList<Donor>();
        recipients = new ArrayList<Recipient>();
    }

    public List<Donor> getDonors() {
        return donors;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }
    
    public void addDonor(Donor donor) {
        this.donors.add(donor);
    }
    
    public void addRecipient(Recipient recipient) {
        this.recipients.add(recipient);
    }
    
    public void startThreads() throws InterruptedException {
        chairmanExecutorService = Executors.newFixedThreadPool(1);
        donorsExecutorService = Executors.newFixedThreadPool(donors.size());
        recipientsExecutorService = Executors.newFixedThreadPool(recipients.size());
        
        chairmanExecutorService.execute(Chairman.getInstance());
        for (Donor d : donors)
            donorsExecutorService.execute(d);
        
        for (Recipient r : recipients)
            recipientsExecutorService.execute(r);
    }
    
    public void EndThreads() throws InterruptedException {
        Chairman.getInstance().End();
        donorsExecutorService.shutdown();
        recipientsExecutorService.shutdown();
        donorsExecutorService.awaitTermination(10, TimeUnit.SECONDS);
        recipientsExecutorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
