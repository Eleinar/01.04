import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Sale> sales = CSVReader.loadSalesData("sales.csv");
        List<Product> products = CSVReader.loadProductData("products.csv");
        List<Customer> customers = CSVReader.loadCustomerData("customers.csv");

        SalesDataProcessor processor = new SalesDataProcessor(sales, products, customers);

        List<String> reportLines = new ArrayList<>();
        reportLines.add("Отчет по продажам");
        reportLines.add("Общая сумма продаж: " + processor.getTotalSalesAmount());

        reportLines.add("Пять самых популярных товаров:");
        processor.getTopPopularProducts(5).forEach((product, count) -> 
            reportLines.add("Товар ID: " + product + ", Количество: " + count)
        );

        reportLines.add("Пять самых непопулярных товаров:");
        processor.getTopUnpopularProducts(5).forEach((product, count) -> 
            reportLines.add("Товар ID: " + product + ", Количество: " + count)
        );

        reportLines.add("Тенденции продаж по месяцам:");
        processor.getSalesTrends().forEach((month, count) -> 
            reportLines.add("Месяц: " + month + ", Продаж: " + count)
        );

        try {
            ReportGenerator.saveReport(reportLines, "report.txt");
            System.out.println("Отчет успешно сохранен в файл report.txt");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении отчета: " + e.getMessage());
        }
    }
}
