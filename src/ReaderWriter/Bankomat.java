package ReaderWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bankomat {

	public static void main(String[] args) {

		Admin a1 = new Admin();
		ATM bankomat = new ATM(60, 30, 20, 10);
		Scanner inputMain = new Scanner(System.in);

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

			// provjerava da li je korisnik adminstrator ili obican korisnik
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

					if (attempt > 2) {
						System.out.println(
								"Pokusali ste 3 puta. Vas racun je blokiran. Otidjite do najblize poslovnice za dalje upute.");
					}

				}
			} else {
				// sada treba provjeriti da li korisnièki raèun postoji i da li
				// je ispravan PIN
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
							br = new BufferedReader(fr); // open input stream
															// test.txt for
															// reading purpose.
							while (((thisLine = br.readLine()) != null)) {
								// System.out.println(thisLine);
								String[] words = thisLine.split(" ");

								if ((words[0].equals(userAccount)) && (Integer.parseInt(words[1]) == pass)) {

									person = new Person(words[0], Integer.parseInt(words[1]),
											Integer.parseInt(words[2]));
									break;
								}
							} // while
							br.close();
							fr.close();

						} catch (Exception e) {
							e.printStackTrace();
						}

						if ((person != null) && (attempt < 3)) {
							person.showMenu(bankomat);
							userAccount = " ";
							pass = 0;
							break;
						} else {
							attempt++;
							System.out.println("Pogresan PIN. Pokusajte ponovo.");
						}

					} // while
					if (attempt > 2) {
						System.out.println(
								"Pokusali ste 3 puta. Vas racun je blokiran ili ne postoji. Otidjite do najblize poslovnice za dalje upute.");
					}

				} else {
					System.out.println("-------------------------------------------------------------------\n");
					System.out.println("Korisnik pod imenom \"" + userAccount + "\" ne postoji.\n");
					System.out.println("-------------------------------------------------------------------");

				}

			} // else

			// inputMain.close();

		} // while (true)

	} // main

	// provjera da li korisnik postoji
	public static boolean isPersonExist(String name) {
		String thisLine = null;
		boolean found = false;
		// èitanje datotetke
		BufferedReader br = null;
		FileReader fr = null;

		File file = new File("fajl.txt");
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr); // open input stream test.txt for
											// reading purpose.

			while (((thisLine = br.readLine()) != null)) {
				// System.out.println(thisLine);
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