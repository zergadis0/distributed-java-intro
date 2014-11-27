package pl.edu.amu.dji.jms.lab2.wholesaler.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class OrderService implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;
            System.out.println(mapMessage.getDouble("quantity"));
            System.out.println(mapMessage.getString("retailerID"));
        } catch (JMSException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
