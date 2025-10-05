package pos;

import pos.change.ChangeMaker;
import pos.money.CashBox;
import pos.money.MoneyBag;

import java.util.*;

public class PointOfSale {
    private final ProductCatalog productCatalog;
    private final List<Sale> sales = new ArrayList<>();
    private final CashBox cashBox;
    private ChangeMaker changeMaker;
    private int idLastSale = 0;

    public PointOfSale(ProductCatalog productCatalog, CashBox cashBox, ChangeMaker changeMaker) {
        this.productCatalog = productCatalog;
        this.cashBox = cashBox;
        this.changeMaker = changeMaker;
    }

    public int makeNewSale() {
        idLastSale++;
        Sale s = new Sale(idLastSale);
        sales.add(s);
        return idLastSale;
    }

    public void addLineItemToSale(int idSale, String productName, int quantity) {
        ProductSpecification ps = productCatalog.searchByName(productName);
        Sale s = searchSaleById(idSale);
        if (s == null) throw new IllegalArgumentException("sale id not found: " + idSale);
        s.addLineItem(ps, quantity);
        System.out.println("ordered " + quantity + " " + productName);
    }

    private Sale searchSaleById(int id) {
        for (Sale s : sales) if (s.getId() == id) return s; return null;
    }

    public void printReceiptOfSale(int idSale) { searchSaleById(idSale).printReceipt(); }

    public void payOneSaleCash(int idSale, MoneyBag moneyHanded) {
        searchSaleById(idSale).payInCash(moneyHanded, changeMaker, cashBox);
    }

    public void payOneSaleCreditCard(int idSale, String ccNumber) {
        searchSaleById(idSale).payByCreditCard(ccNumber);
    }

    public void printPayment(int idSale) { searchSaleById(idSale).printPayment(); }

    public void setChangeMaker(ChangeMaker cm) { this.changeMaker = cm; }
    public void printCashBox() { cashBox.print(); }
}
