package pl.edu.amu.dji.jms.lab2.wholesaler.service;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

public class OfferService {

    private JmsTemplate jmsTemplate;

    private Destination offerTopic;

    private Destination orderQueue;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setOfferTopic(Destination offerTopic) {
        this.offerTopic = offerTopic;
    }

    public void setOrderQueue(Destination orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void sendOffer(final Double price) {
        jmsTemplate.send(offerTopic, new MessageCreator() {
            public Message createMessage(Session sn) throws JMSException {
                MapMessage mapMessage = sn.createMapMessage();
                mapMessage.setDouble("price", price);
                mapMessage.setJMSReplyTo(offerTopic);
                return mapMessage;
            }
        });
    }
}
