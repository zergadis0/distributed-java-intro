/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.analysis.service;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import pl.edu.amu.dji.jms.lab4.message.Product;
import pl.edu.amu.dji.jms.lab4.message.Sale;

/**
 *
 * @author Uczelnia
 */
public class ReportService implements MessageListener {
    
    private ArrayList<Sale> sales;
    
    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            if (sales == null)
                sales = new ArrayList<>();
            try {
                Sale sale = (Sale)((ObjectMessage)message).getObject();
                sales.add(sale);
                System.out.println("selled " + sale.getQuantity() + " " + sale.getProductName() + " for " + sale.getPrice());
            } catch (JMSException ex) {
                Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
