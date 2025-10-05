package pos_creditcard;

import pos.*;
import pos.change.*;
import pos.money.*;

public class Main {
    public static void main(String[] args) {
        ProductCatalog catalog = new ProductCatalog();

        MoneyBag initial = new MoneyBag();
        initial.add(Denomination.C001, 5);
        initial.add(Denomination.C002, 5);
        initial.add(Denomination.C005, 5);
        initial.add(Denomination.C010, 5);
        initial.add(Denomination.C020, 5);
        initial.add(Denomination.C050, 5);
        initial.add(Denomination.E1, 5);
        initial.add(Denomination.E2, 5);
        initial.add(Denomination.E5, 2);
        initial.add(Denomination.E10, 2);
        initial.add(Denomination.E20, 2);
        CashBox cashBox = new CashBox(initial);

        ChangeMaker changeMaker = new GreedyChangeMaker();
        PointOfSale pos = new PointOfSale(catalog, cashBox, changeMaker);

        pos.printCashBox();

        int idSale = pos.makeNewSale();
        pos.addLineItemToSale(idSale, "Moritz", 4);
        pos.addLineItemToSale(idSale, "Coca-cola", 1);
        pos.addLineItemToSale(idSale, "Coca-cola", 2);
        pos.printReceiptOfSale(idSale);

        MoneyBag handed = new MoneyBag();
        handed.add(Denomination.E10, 2);
        pos.payOneSaleCash(idSale, handed);
        pos.printPayment(idSale);

        // Uncomment to try the random strategy
        // pos.setChangeMaker(new RandomChangeMaker());
        // int idSale2 = pos.makeNewSale();
        // pos.addLineItemToSale(idSale2, "Donut", 3);
        // pos.printReceiptOfSale(idSale2);
        // MoneyBag handed2 = new MoneyBag();
        // handed2.add(Denomination.E5, 1);
        // pos.payOneSaleCash(idSale2, handed2);
        // pos.printPayment(idSale2);
    }
}
