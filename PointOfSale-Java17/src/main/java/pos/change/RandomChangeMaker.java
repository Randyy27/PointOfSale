package pos.change;

import pos.money.*;
import java.util.*;

public class RandomChangeMaker implements ChangeMaker {
    private final Random rnd = new Random();

    @Override
    public MoneyBag makeChange(double amount, CashBox cashBox) {
        amount = round2(amount);
        MoneyBag available = cashBox.snapshot();
        List<Denomination> all = Arrays.asList(Denomination.values());
        for (int attempt = 0; attempt < 200; attempt++) {
            Collections.shuffle(all, rnd);
            MoneyBag change = new MoneyBag();
            double remaining = amount;
            MoneyBag tempAvail = available.copy();
            while (remaining > 0.0) {
                List<Denomination> candidates = new ArrayList<>();
                for (Denomination d : all) {
                    if (tempAvail.get(d) > 0 && d.getValue() <= remaining + 1e-9)
                        candidates.add(d);
                }
                if (candidates.isEmpty()) break;
                Denomination pick = candidates.get(rnd.nextInt(candidates.size()));
                change.add(pick, 1);
                tempAvail.add(pick, -1);
                remaining = round2(remaining - pick.getValue());
            }
            if (remaining == 0.0) return change;
        }
        return null;
    }

    @Override public String name() { return "random"; }

    private static double round2(double v) { return Math.rint(v * 100.0) / 100.0; }
}
