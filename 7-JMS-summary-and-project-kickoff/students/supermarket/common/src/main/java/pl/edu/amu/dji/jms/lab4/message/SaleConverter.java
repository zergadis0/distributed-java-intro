/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.message;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Uczelnia
 */
@Component("saleConverter")
public class SaleConverter implements MessageConverter {

    @Override
    public Message toMessage(Object o, Session sn) throws JMSException, MessageConversionException {
        Sale sale = (Sale) o;
        ObjectMessage message = sn.createObjectMessage(sale);
        
        return message;
    }

    @Override
    public Object fromMessage(Message msg) throws JMSException, MessageConversionException {
        ObjectMessage objectMessage = (ObjectMessage) msg;
        return objectMessage.getObject();
    }
    
}
