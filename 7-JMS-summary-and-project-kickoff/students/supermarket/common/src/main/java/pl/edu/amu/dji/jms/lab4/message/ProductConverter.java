/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.amu.dji.jms.lab4.message;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
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
@Component("productConverter")
public class ProductConverter implements MessageConverter {

    @Override
    public Message toMessage(Object o, Session sn) throws JMSException, MessageConversionException {
        ArrayList<Product> products = (ArrayList<Product>) o;
        ObjectMessage message = sn.createObjectMessage(products);
        return message;
    }

    @Override
    public Object fromMessage(Message msg) throws JMSException, MessageConversionException {
        ObjectMessage message = (ObjectMessage) msg;
        return message.getObject();
    }
    
}
