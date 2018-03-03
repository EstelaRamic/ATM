package ReaderWriter;

import java.util.Scanner;

public class ATM {

	private int[][] banknote = new int[2][4];

	ATM() {
	};

	ATM(int bill10, int bill20, int bill50, int bill100) {

		banknote[0][0] = 10;
		banknote[0][1] = 20;
		banknote[0][2] = 50;
		banknote[0][3] = 100;

		banknote[1][0] = bill10;
		banknote[1][1] = bill20;
		banknote[1][2] = bill50;
		banknote[1][3] = bill100;
	}

	int balance() {
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			sum = sum + banknote[0][i] * banknote[1][i];
		}
		return sum;
	}

	boolean contains(int[] arr, int item) {
		for (int n : arr) {
			if (item == n) {
				return true;
			}
		}
		return false;
	}

	boolean deposite(int bill, int amount) {
		if (amount > (100 - banknote[1][bill])) {
			System.out.println("U odjeljku za novcanice od " + banknote[0][bill] + " KM može stati još "
					+ (100 - banknote[1][bill]) + " novcanica.");
			System.out.println("Ne možete dodati " + amount + " novcanica.");
			return false;
		} else {
			banknote[1][bill] += amount;
			System.out.println("Sada u odjeljku za novcanice od " + banknote[0][bill] + " KM imate " + banknote[1][bill]
					+ " novcanica.");
			return true;
		}
	}

	boolean isPosiblePersonWithdraw(int amount) {
		return (balance() >= amount);
	}

	boolean withdraw(int bill, int amount) {
		if (amount > banknote[1][bill]) {
			System.out.println("U odjeljku za novcanice od " + banknote[0][bill] + " KM imate " + banknote[1][bill]
					+ " novcanica.");
			System.out.println("Ne možete izvaditi " + amount + " novcanica.");
			return false;
		} else {
			banknote[1][bill] -= amount;
			System.out.println("Sada u odjeljku za novcanice od " + banknote[0][bill] + " KM imate " + banknote[1][bill]
					+ " novcanica.");
			return true;
		}
	}

	void payment(int amount) {
		int numBills = 0, j = 3; // j=3 jer kreæe od davanja najviše moguæih 100-ki, pa onda 50-ki, itd.
		while ((amount > 0) && (j >= 0)) {

			numBills = (int) amount / banknote[0][j];
			if (numBills <= banknote[1][j]) { // ako ima dovoljno novèanica date vrijednosti
				banknote[1][j] -= numBills;
				amount = amount % banknote[0][j];
			} else {
				amount -= (banknote[1][j] * banknote[0][j]);
				banknote[1][j] = 0;
			}

			j--;
		}
	}

	void status() {
		for (int i = 0; i < 4; i++) {
			System.out.println(
					"U odjeljku za novèanice od " + banknote[0][i] + " KM imate " + banknote[1][i] + " novèanica.");
		}
		System.out.println("\n");
		System.out.println("Ukupan iznos novca u bankomatu je " + this.balance() + " KM.");
	}

	// proslijedjen skener metodi
	void showMenu(Scanner inputATM) {
		int izbor = 0, bill = 0, amount = 0, oldBalance = 0;
		this.status(); 

		do {
			System.out.println("****************************************************************");
			System.out.println("Izaberite akciju: ");
			System.out.println("\n");
			System.out.println("1. Dodavanje novcanica");
			System.out.println("2. Uzimanje novcanica");
			System.out.println("3. Izlaz");
			System.out.println("****************************************************************");
			izbor = inputATM.nextInt();

			oldBalance = this.balance();

			switch (izbor) {

			case 1:
				System.out.println("----------------------------------------------------------------");
				do {
					System.out.println("Koju vrijednost novcanica zelite dodati (10, 20, 50 ili 100) :");

					bill = inputATM.nextInt();
					if (!(contains(banknote[0], bill))) {
						System.out.println("Pogresan unos vrijednosti novcanice.");
					}
				} while (!(contains(banknote[0], bill)));

				bill = bill / 20;
				if (bill == 5) {
					bill = 3;
				}

				do {
					System.out.println("----------------------------------------------------------------");
					System.out.println("U odjeljku za novcanice od " + banknote[0][bill] + " KM imate "
							+ banknote[1][bill] + " novcanica.");
					System.out.println("U svaki odjeljak moze stati maksimalno 100 novcanica.");
					System.out.println("Mozete dodati maksimalno " + (100 - banknote[1][bill]) + " novcanica.");
					do {
						System.out.println("----------------------------------------------------------------");
						System.out.println("Koliko novcanica vrijednosti " + banknote[0][bill] + " KM dodajete :");
						amount = inputATM.nextInt();
						if (amount < 0) {
							System.out.println("Pogresan unos. Ne mozete dodati negativan broj novcanica.");
						}
					} while (amount < 0);

				} while (!deposite(bill, amount));

				if (oldBalance != this.balance()) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Novi status u bankomatu je:");
					System.out.println("----------------------------------------------------------------");
					this.status();
				}
				break;

			case 2:
				System.out.println("----------------------------------------------------------------");
				do {
					System.out.println("Koju vrijednost novcanica zelite izvaditi (10, 20, 50 ili 100) :");

					bill = inputATM.nextInt();
					if (!(contains(banknote[0], bill))) {
						System.out.println("Pogresan unos vrijednosti novcanice.");
					}
				} while (!(contains(banknote[0], bill)));

				bill = bill / 20;
				if (bill == 5) {
					bill = 3;
				}

				do {

					System.out.println("----------------------------------------------------------------");
					System.out.println("U odjeljku za novcanice od " + banknote[0][bill] + " KM imate "
							+ banknote[1][bill] + " novcanica.");
					System.out.println("Mozete podici maksimalno " + banknote[1][bill] + " novcanica.");
					do {
						System.out.println("----------------------------------------------------------------");
						System.out.println("Koliko novcanica vrijednosti " + banknote[0][bill] + " KM uzimate :");
						amount = inputATM.nextInt();
						if (amount < 0) {
							System.out.println("Pogresan unos. Ne mozete uzeti negativan broj novcanica.");
						}
					} while (amount < 0);

				} while (!withdraw(bill, amount));

				if (oldBalance != this.balance()) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Novi status u bankomatu je:");
					System.out.println("----------------------------------------------------------------");
					this.status();
				}
				break;

			case 3:
				System.out.println("****************************************************************");
				break;

			default:
				System.out.println("Pogresan unos. Pokusajte ponovo.");
				break;

			}

		} while (izbor != 3);

		System.out.println("Zavrsili ste sa promjenom statusa bankomata. Vraticemo Vas na glavni izbornik.");

	}

}