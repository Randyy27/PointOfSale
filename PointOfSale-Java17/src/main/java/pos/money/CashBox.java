package pos.money;

public class CashBox {
    private final MoneyBag contents;

    public CashBox(MoneyBag initial) {
        this.contents = (initial == null) ? new MoneyBag() : initial.copy();
    }

    public synchronized void add(MoneyBag m) { contents.addAll(m); }
    public synchronized boolean canGive(MoneyBag m) { return contents.canCover(m); }
    public synchronized void remove(MoneyBag m) { contents.subtractAll(m); }
    public synchronized double total() { return contents.total(); }
    public synchronized MoneyBag snapshot() { return contents.copy(); }

    public synchronized void print() {
        System.out.println("cash box initially loaded with");
        for (String s : contents.toLines()) System.out.println(s);
        System.out.println();
    }
}
