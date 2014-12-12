package pl.edu.amu.dji.jms.lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import pl.edu.amu.dji.jms.lab4.message.Product;
import pl.edu.amu.dji.jms.lab4.service.PointOfSaleService;
import pl.edu.amu.dji.jms.lab4.service.SaleService;

public class PointOfSalesApp {
    public static final String EXIT = "exit";
    
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        SaleService saleService = (SaleService) context.getBean("saleService");
        PointOfSaleService pointOfSaleService = (PointOfSaleService) context.getBean("pointOfSaleService");
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            in = bufferedReader.readLine();

            if (in.startsWith("sell: ")) {
                String sellInfo = in.substring(6);
                String productName = sellInfo.split(",")[0];
                List<Product> products = pointOfSaleService.getProducts();
                if (products.equals(null))
                    continue;
                if (products.stream().anyMatch(o -> o.getName().equals(productName))) {
                    int quantity =  Integer.parseInt(sellInfo.split(",")[1]);
                    Product p = (Product)(products.stream().filter(o -> o.getName().equals(productName)).toArray()[0]);
                    double price = quantity * p.getPrice();
                    saleService.sendSalesInformation(productName, quantity, price);
                }
                else
                    System.out.println("Product with this name doesn't exits.");
            }
        }
    }
}
