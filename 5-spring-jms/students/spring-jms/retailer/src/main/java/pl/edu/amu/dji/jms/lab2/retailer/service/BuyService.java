package pl.edu.amu.dji.jms.lab2.retailer.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

public class BuyService implements MessageListener {

    private JmsTemplate jmsTemplate;

    private Double maxPrice;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public void onMessage(final Message message) {
        MapMessage mapMessage = (MapMessage) message;
        double price = 0;
        try {
            price = mapMessage.getDouble("price");
        } catch (JMSException ex) {
            Logger.getLogger(BuyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (maxPrice.compareTo(price) == 1) {
            try {
                jmsTemplate.send(message.getJMSReplyTo(), new MessageCreator() {
                    public Message createMessage(Session sn) throws JMSException {
                        MapMessage returnMessage = sn.createMapMessage();
                        returnMessage.setDouble("quantity", 3);
                        returnMessage.setString("retailerID", getClass().getName());
                        return returnMessage;
                    }
                });
            } catch (JMSException ex) {
                Logger.getLogger(BuyService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
