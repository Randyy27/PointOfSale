package pos.payment;

import pos.change.ChangeMaker;
import pos.money.CashBox;
import pos.money.MoneyBag;

public class PaymentInCash extends Payment {
    private final MoneyBag handed;
    private MoneyBag change;
    private boolean accepted = false;

    public PaymentInCash(MoneyBag handed) { this.handed = handed; }

    public void process(double total, ChangeMaker changeMaker, CashBox cashBox) {
        double handedTotal = handed.total();
        if (handedTotal + 1e-9 < total) {
            accepted = false;
            System.out.printf("Not enough cash handed: %.2f, total to pay %.2f%n", handedTotal, total);
            return;
        }
        double toReturn = round2(handedTotal - total);
        System.out.println("Make change with " + changeMaker.name() + " change maker\n");
        System.out.println("money handed");
        for (String s : handed.toLines()) System.out.println(s);
        System.out.println();

        // First, add handed money to cash box
        cashBox.add(handed);
        System.out.println("added payment to cash box");
        for (String s : handed.toLines()) System.out.println(s);
        System.out.println();

        if (toReturn == 0.0) {
            change = new MoneyBag();
            accepted = true;
            return;
        }

        change = changeMaker.makeChange(toReturn, cashBox);
        if (change == null) {
            accepted = false;
            System.out.printf("Cannot make change of %.2f with current cash box.%n", toReturn);
            return;
        }
        // Give change: remove from cash box
        if (!cashBox.canGive(change)) {
            accepted = false;
            System.out.println("Unexpected: computed change not available in cash box.");
            return;
        }
        cashBox.remove(change);
        accepted = true;

        System.out.printf("total to pay %.2f, change to give %.2f%n", total, toReturn);
        System.out.println("the change is");
        for (String s : change.toLines()) System.out.println(s);
        System.out.println();

        System.out.println("after payment and giving change the cash box has");
        for (String s : cashBox.snapshot().toLines()) System.out.println(s);
        System.out.println();
    }

    @Override
    public void print(double total) {
        if (!accepted) return; // message already printed in process
    }

    private static double round2(double v) { return Math.rint(v * 100.0) / 100.0; }
}
