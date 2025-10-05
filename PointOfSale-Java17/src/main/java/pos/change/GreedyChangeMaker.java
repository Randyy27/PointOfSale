package pos.change;

import pos.money.*;

public class GreedyChangeMaker implements ChangeMaker {
    @Override
    public MoneyBag makeChange(double amount, CashBox cashBox) {
        amount = round2(amount);
        MoneyBag change = new MoneyBag();
        MoneyBag available = cashBox.snapshot();
        for (Denomination d : Denomination.descending()) {
            double v = d.getValue();
            int maxNeeded = (int)Math.floor((amount + 1e-9) / v);
            int take = Math.min(maxNeeded, available.get(d));
            if (take > 0) {
                change.add(d, take);
                amount = round2(amount - take * v);
            }
            if (amount == 0.0) break;
        }
        return (amount == 0.0) ? change : null;
    }

    @Override public String name() { return "greedy"; }

    private static double round2(double v) { return Math.rint(v * 100.0) / 100.0; }
}
