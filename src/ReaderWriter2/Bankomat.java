package ReaderWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Bankomat {

	
	// samo jednom otvoren i zatvoren skener
	public static void main(String[] args) {

		// otvoren na pocetku rada programa
		Scanner inputMain = new Scanner(System.in);

		// pokrenut program
		newMain(inputMain);

		
		// zatvoren kada program zavrsi sa radom a ne nakon svake metode ili klase
		inputMain.close();
	}

	// proslijedjen scanner
	public static void newMain(Scanner inputMain) {

		Admin a1 = new Admin();
		ATM bankomat = new ATM(60, 30, 20, 10);

		String userAccount = "";
		int attempt = 0;
		int pass = 0;

		while (true) {

			System.out.println("\n");
			System.out.println("\n");
			System.out.println("\n");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("       Dobrodosli u nas bankovni sistem.");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("\n");
			System.out.println("Unesite svoje podatke: ");
			System.out.println("**********************************************");
			System.out.print("        Korisnicko ime: ");

			userAccount = inputMain.next();

			if (userAccount.equals(a1.username())) {
				while (attempt < 3) {
					System.out.print("                PIN : ");
					pass = inputMain.nextInt();
					System.out.println("**********************************************");
					if (pass == a1.getPin()) {
						a1.showMenu(bankomat);
						userAccount = "";
						pass = 0;
						break;
					} else {
						attempt++;
						System.out.println("Pogresan PIN. Pokusajte ponovo.");
					}
				}
				if (attempt > 2) {
					System.out.println(
							"Pokusali ste 3 puta. Vas racun je blokiran. Otidjite do najblize poslovnice za dalje upute.");
				}

			} else {
				if (isPersonExist(userAccount)) {
					while (attempt < 3) {
						System.out.print("                PIN : ");
						pass = inputMain.nextInt();
						System.out.println("**********************************************");

						String thisLine = null;
						BufferedReader br = null;
						FileReader fr = null;
						Person person = null;

						File file = new File("fajl.txt");

						try {
							fr = new FileReader(file);
							br = new BufferedReader(fr);
							while (((thisLine = br.readLine()) != null)) {
								String[] words = thisLine.split(" ");

								if ((words[0].equals(userAccount)) && (Integer.parseInt(words[1]) == pass)) {

									person = new Person(words[0], Integer.parseInt(words[1]),
											Integer.parseInt(words[2]));
									break;
								}
							}
							br.close();
							fr.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

						if ((person != null) && (attempt < 3)) {
							
							// proslijedjen skener metodi
							person.showMenu(bankomat, inputMain);
							userAccount = " ";
							pass = 0;
							break;
						} else {
							attempt++;
							System.out.println("Pogresan PIN. Pokusajte ponovo.");
						}
					}
					if (attempt > 2) {
						System.out.println(
								"Pokusali ste 3 puta. Vas racun je blokiran ili ne postoji. Otidjite do najblize poslovnice za dalje upute.");
					}
				} else {
					System.out.println("-------------------------------------------------------------------\n");
					System.out.println("Korisnik pod imenom \"" + userAccount + "\" ne postoji.\n");
					System.out.println("-------------------------------------------------------------------");
				}
			}
		}
	}

	public static boolean isPersonExist(String name) {
		String thisLine = null;
		boolean found = false;
		BufferedReader br = null;
		FileReader fr = null;
		File file = new File("fajl.txt");
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while (((thisLine = br.readLine()) != null)) {
				String[] words = thisLine.split(" ");
				if ((words[0].equals(name))) {
					found = true;
					break;
				}
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return found;
	}
}