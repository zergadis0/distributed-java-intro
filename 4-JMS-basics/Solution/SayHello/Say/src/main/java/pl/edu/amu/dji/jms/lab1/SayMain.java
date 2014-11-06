package pl.edu.amu.dji.jms.lab1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SayMain {

    public static final String EXIT = "exit";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue("SayHelloQueue");
        Destination topic = session.createTopic("SayHelloTopic");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setTimeToLive(1000 * 3);
        connection.start();

        connection.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            System.out.print("Say hello to:");
            in = bufferedReader.readLine();

            TextMessage message = session.createTextMessage(in);

            message.setBooleanProperty("dots", in.contains("."));

            producer.send(message);
        }

        session.close();
        connection.close();
    }
}
