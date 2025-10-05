package pos.money;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MoneyBag {
    private final EnumMap<Denomination, Integer> quantities = new EnumMap<>(Denomination.class);

    public MoneyBag() {
        for (Denomination d : Denomination.values()) quantities.put(d, 0);
    }

    public static MoneyBag single(Denomination d, int n) {
        MoneyBag bag = new MoneyBag();
        bag.add(d, n);
        return bag;
    }

    public void add(Denomination d, int n) {
        if (n < 0) throw new IllegalArgumentException("negative add");
        quantities.put(d, quantities.get(d) + n);
    }

    public void addAll(MoneyBag other) {
        for (Denomination d : Denomination.values()) add(d, other.get(d));
    }

    public int get(Denomination d) { return quantities.get(d); }

    public boolean canCover(MoneyBag other) {
        for (Denomination d : Denomination.values()) {
            if (this.get(d) < other.get(d)) return false;
        }
        return true;
    }

    public void subtractAll(MoneyBag other) {
        if (!canCover(other)) throw new IllegalArgumentException("insufficient contents");
        for (Denomination d : Denomination.values()) {
            quantities.put(d, quantities.get(d) - other.get(d));
        }
    }

    public boolean isEmpty() {
        for (Denomination d : Denomination.values()) if (get(d) > 0) return false;
        return true;
    }

    public double total() {
        double t = 0.0;
        for (Denomination d : Denomination.values()) t += d.getValue() * get(d);
        return round2(t);
    }

    public MoneyBag copy() {
        MoneyBag c = new MoneyBag();
        for (Denomination d : Denomination.values()) c.add(d, get(d));
        return c;
    }

    public java.util.List<String> toLines() {
        List<String> lines = new ArrayList<>();
        for (Denomination d : Denomination.values()) {
            int n = get(d);
            if (n > 0) lines.add(n + " of " + String.format("%.2f", d.getValue()));
        }
        return lines;
    }

    private static double round2(double v) { return Math.rint(v * 100.0) / 100.0; }
}
