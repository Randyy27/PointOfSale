package pos.money;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Denomination {
    C001(0.01), C002(0.02), C005(0.05), C010(0.10), C020(0.20), C050(0.50),
    E1(1.0), E2(2.0), E5(5.0), E10(10.0), E20(20.0);

    private final double value;
    Denomination(double value) { this.value = value; }
    public double getValue() { return value; }

    public static List<Denomination> descending() {
        List<Denomination> all = Arrays.asList(values());
        all.sort((a,b) -> Double.compare(b.value, a.value));
        return Collections.unmodifiableList(all);
    }
}
