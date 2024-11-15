import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class SalesDataProcessor {
    private List<Sale> sales;
    private List<Product> products;
    private List<Customer> customers;

    public SalesDataProcessor(List<Sale> sales, List<Product> products, List<Customer> customers) {
        this.sales = sales;
        this.products = products;
        this.customers = customers;
    }

    public double getTotalSalesAmount() {
        return sales.stream().mapToDouble(Sale::getAmount).sum();
    }

    public Map<String, Long> getTopPopularProducts(int topN) {
        return sales.stream()
                .collect(Collectors.groupingBy(sale -> String.valueOf(sale.getProductId()), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Long> getTopUnpopularProducts(int topN) {
        return sales.stream()
                .collect(Collectors.groupingBy(sale -> String.valueOf(sale.getProductId()), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(topN)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public List<Customer> getCustomersWithTotalAbove(double threshold) {
        Map<Integer, Double> customerTotals = sales.stream()
                .collect(Collectors.groupingBy(Sale::getCustomerId, Collectors.summingDouble(Sale::getAmount)));
        return customers.stream()
                .filter(customer -> customerTotals.getOrDefault(customer.getCustomerId(), 0.0) > threshold)
                .collect(Collectors.toList());
    }

    public Map<String, Long> getSalesTrends() {
        return sales.stream()
                .collect(Collectors.groupingBy(sale -> sale.getSaleDateTime().toLocalDate().withDayOfMonth(1).toString(),
                        Collectors.counting()));
    }

    public void saveReport(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("Отчет по продажам");
        lines.add("Общая сумма продаж: " + getTotalSalesAmount());
        lines.add("Пять самых популярных товаров:");
        getTopPopularProducts(5).forEach((product, count) -> lines.add("Товар ID: " + product + ", Количество: " + count));
        lines.add("Пять самых непопулярных товаров:");
        getTopUnpopularProducts(5).forEach((product, count) -> lines.add("Товар ID: " + product + ", Количество: " + count));
        lines.add("Тенденции продаж по месяцам:");
        getSalesTrends().forEach((month, count) -> lines.add("Месяц: " + month + ", Продаж: " + count));
        Files.write(Paths.get(filename), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
