package pos;

import pos.payment.*;
import pos.money.*;
import pos.change.ChangeMaker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private final int id;
    private final List<SaleLineItem> saleLineItems = new ArrayList<>();
    private Payment payment;
    private boolean isPaid = false;

    public Sale(int id) { this.id = id; }
    public int getId() { return id; }

    public void addLineItem(ProductSpecification productSpecification, int quantity) {
        for (SaleLineItem item : saleLineItems) {
            if (item.getProductSpecification() == productSpecification) {
                item.incrementQuantity(quantity);
                return;
            }
        }
        saleLineItems.add(new SaleLineItem(productSpecification, quantity));
    }

    private double total() {
        double t = 0.0;
        for (SaleLineItem sli : saleLineItems) t += sli.subtotal();
        return Math.rint(t*100.0)/100.0;
    }

    public void printReceipt() {
        System.out.printf("Sale %d%n", id);
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        for (SaleLineItem sli : saleLineItems) sli.print();
        System.out.printf("Total %.2f%n%n", total());
    }

    public void payInCash(MoneyBag money, ChangeMaker changeMaker, CashBox cashBox) {
        if (isPaid) throw new IllegalStateException("sale " + id + " has already been paid");
        PaymentInCash pic = new PaymentInCash(money);
        pic.process(total(), changeMaker, cashBox);
        this.payment = pic;
        isPaid = true;
    }

    public void payByCreditCard(String number) {
        if (isPaid) throw new IllegalStateException("sale " + id + " has already been paid");
        this.payment = new PaymentCreditCard(number);
        isPaid = true;
    }

    public void printPayment() {
        if (payment == null) {
            System.out.println("No payment for sale " + id);
            return;
        }
        payment.print(total());
    }
}
