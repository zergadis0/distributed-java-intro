/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.service;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import pl.edu.amu.dji.jms.lab4.message.Product;

/**
 *
 * @author Uczelnia
 */
public class PointOfSaleService implements MessageListener {
    
    ArrayList<Product> products;
    
    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;
    
    public List<Product> getProducts() {
        return products;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    public void onMessage(Message message) {
        
        if (message instanceof ObjectMessage) {
            try {
                products = (ArrayList<Product>)((ObjectMessage)message).getObject();
                System.out.println("Product list has been updated.");
            } catch (JMSException ex) {
                Logger.getLogger(PointOfSaleService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            
            try {
                String productName = mapMessage.getString("name");
                double newPrice = mapMessage.getDouble("price");

                Object[] tempProducts = products.stream().filter(o -> o.getName().equals(productName)).toArray();
                Product productToChange = (Product)tempProducts[0];
                productToChange.setPrice(newPrice);
                System.out.println("Price of " + productToChange.getName() + " has been changed to " + productToChange.getPrice());
            } catch (JMSException ex) {
                Logger.getLogger(PointOfSaleService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
