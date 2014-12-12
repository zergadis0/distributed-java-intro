/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.message;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Uczelnia
 */
@Component("priceChangeConverter")
public class PriceChangeConverter implements MessageConverter {

    @Override
    public Message toMessage(Object o, Session sn) throws JMSException, MessageConversionException {
        PriceChange priceChange = (PriceChange) o;
        MapMessage message = sn.createMapMessage();
        message.setString("name", priceChange.getName());
        message.setDouble("price", priceChange.getPrice());
        
        return message;
    }

    @Override
    public Object fromMessage(Message msg) throws JMSException, MessageConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
