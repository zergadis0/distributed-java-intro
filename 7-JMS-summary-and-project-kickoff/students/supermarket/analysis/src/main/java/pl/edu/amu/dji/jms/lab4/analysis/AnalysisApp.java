package pl.edu.amu.dji.jms.lab4.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import pl.edu.amu.dji.jms.lab4.analysis.service.ReportService;
import pl.edu.amu.dji.jms.lab4.message.Sale;

public class AnalysisApp {
    public static final String EXIT = "exit";
    
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        ReportService reportService = (ReportService) context.getBean("reportService");
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            in = bufferedReader.readLine();
                if (in.equals("sales information")) {
                    List<Sale> sales = reportService.getSales();
                    for (Sale s : sales) {
                        System.out.println("Product: " + s.getProductName() + ", Quantity: " + s.getQuantity() + 
                                ", Price: " + s.getPrice());
                    }
                }
            }
    }
}
