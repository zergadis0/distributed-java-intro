package pl.edu.amu.dji.jms.lab4;

import pl.edu.amu.dji.jms.lab4.service.WarehouseService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import pl.edu.amu.dji.jms.lab4.message.Product;

public class WarehouseApp {
    public static final String EXIT = "exit";
    private static List<Product> products;
    
    public static void main(String[] args) throws IOException, JMSException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        WarehouseService warehouseService = (WarehouseService) context.getBean("warehouseService");
        
        Product product1 = new Product("Test1", 12);
        Product product2 = new Product("Test2", 114);
        Product product3 = new Product("Test3", 43);
        Product product4 = new Product("Test4", 57);
        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            in = bufferedReader.readLine();

            if (in.equals("send list")) 
                warehouseService.sendProductsList(products);
            if (in.startsWith("change price: ")) {
                String namePrice = in.substring(14);
                String name = namePrice.split(",")[0];
                if (products.stream().anyMatch(o -> o.getName().equals(name))) {
                    double price = Double.parseDouble(namePrice.split(",")[1]);
                    Product p = (Product)(products.stream().filter(o -> o.getName().equals(name)).toArray()[0]);
                    p.setPrice(price);
                    
                    warehouseService.sendPriceChange(name, price);
                }
                else
                    System.out.println("Product with this name doesn't exits.");
            }
        }
    }
}
