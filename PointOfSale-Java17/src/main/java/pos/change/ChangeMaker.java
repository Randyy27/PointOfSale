package pos.change;

import pos.money.CashBox;
import pos.money.MoneyBag;

public interface ChangeMaker {
    /** Returns the specific change bag or null if not possible. */
    MoneyBag makeChange(double amount, CashBox cashBox);
    String name();
}
