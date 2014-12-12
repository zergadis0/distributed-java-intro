/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.service;

import java.util.List;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.amu.dji.jms.lab4.message.PriceChange;
import pl.edu.amu.dji.jms.lab4.message.Product;

/**
 *
 * @author Uczelnia
 */
@Service("warehouseService")
public class WarehouseService {
    @Autowired
    @Qualifier("productsJmsTemplate")
    private JmsTemplate productsJmsTemplate;
    
    @Autowired
    @Qualifier("priceChangeJmsTemplate")
    private JmsTemplate priceChangeJmsTemplate;
    
    @Transactional
    public void sendProductsList(List<Product> products){
        productsJmsTemplate.convertAndSend(products);
    }
    
    @Transactional
    public void sendPriceChange(String productName, double newPrice) throws JMSException{
        PriceChange priceChange = new PriceChange(productName, newPrice);
        priceChangeJmsTemplate.convertAndSend(priceChange);
    }
}
