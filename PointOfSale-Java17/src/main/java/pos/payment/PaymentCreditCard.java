package pos.payment;

public class PaymentCreditCard extends Payment {
    private final String maskedNumber;
    public PaymentCreditCard(String number) {
        this.maskedNumber = mask(number);
    }
    private String mask(String s) {
        if (s == null) return "****";
        if (s.length() < 4) return "****";
        return "**** **** **** " + s.substring(s.length()-4);
    }
    @Override
    public void print(double total) {
        System.out.printf("Paid by credit card %s, total: %.2f%n", maskedNumber, total);
    }
}
