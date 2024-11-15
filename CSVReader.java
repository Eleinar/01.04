import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<Sale> loadSalesData(String filePath) {
        List<Sale> sales = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                int saleId = Integer.parseInt(parts[0]);
                LocalDateTime dateTime = LocalDateTime.parse(parts[1], DATE_FORMATTER);
                int customerId = Integer.parseInt(parts[2]);
                int productId = Integer.parseInt(parts[3]);
                double amount = Double.parseDouble(parts[4]);
                sales.add(new Sale(saleId, dateTime, customerId, productId, amount));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла продаж: " + e.getMessage());
        }
        return sales;
    }

    public static List<Product> loadProductData(String filePath) {
        List<Product> products = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                int productId = Integer.parseInt(parts[0]);
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                products.add(new Product(productId, productName, price));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла товаров: " + e.getMessage());
        }
        return products;
    }

    public static List<Customer> loadCustomerData(String filePath) {
        List<Customer> customers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                int customerId = Integer.parseInt(parts[0]);
                String customerName = parts[1];
                customers.add(new Customer(customerId, customerName));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла покупателей: " + e.getMessage());
        }
        return customers;
    }
}
