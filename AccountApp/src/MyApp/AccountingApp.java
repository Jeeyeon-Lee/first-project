package MyApp;

public class AccountingApp {

	public static void main(String[] args) {

		System.out.println("Value of supp;ly : " + 12345.0);
		System.out.println("Vat : " + (12345.0*0.1));
		System.out.println("Toatal : " + (12345.0+12345.0*0.1));
		System.out.println("Expense : " + (12345.0*0.3));
		System.out.println("Income : " + (12345.0 - 12345.0*0.3));
		System.out.println("Dividend : " + (12345.0 - 12345.0*0.3) * 0.5);
		System.out.println("Dividend : " + (12345.0 - 12345.0*0.3) * 0.3);
		System.out.println("Dividend : " + (12345.0 - 12345.0*0.3) * 0.2);
	}

}
