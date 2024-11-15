import java.time.LocalDateTime;

public class Sale {
    private int saleId;
    private LocalDateTime saleDateTime;
    private int customerId;
    private int productId;
    private double amount;

    public Sale(int saleId, LocalDateTime saleDateTime, int customerId, int productId, double amount) {
        this.saleId = saleId;
        this.saleDateTime = saleDateTime;
        this.customerId = customerId;
        this.productId = productId;
        this.amount = amount;
    }

    public LocalDateTime getSaleDateTime() { return saleDateTime; }
    public int getProductId() { return productId; }
    public double getAmount() { return amount; }
    public int getCustomerId() { return customerId; }
}
