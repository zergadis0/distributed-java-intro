/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.service;

import java.util.ArrayList;
import javax.jms.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.amu.dji.jms.lab4.message.Product;
import pl.edu.amu.dji.jms.lab4.message.Sale;

/**
 *
 * @author Uczelnia
 */
@Service("saleService")
public class SaleService {
    
    @Autowired
    @Qualifier("saleJmsTemplate")
    private JmsTemplate saleJmsTemplate;
    
    @Transactional
    public void sendSalesInformation(String productName, int quantity, double price){
        Sale sale = new Sale(productName, quantity, price);
        saleJmsTemplate.convertAndSend(sale);
    }
}
