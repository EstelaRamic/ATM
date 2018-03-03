package ReaderWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Admin {
	private String username;
	private int pin;
	
	Admin() {
		this.username = "administrator";
		this.pin = 9898;
	};

	Admin(int pass) {
		this.username = "administrator";
		this.pin = pass;
	}

	int getPin(){
		return this.pin;
	}
	
	int generatePIN() {
		return ((int)(Math.random()*1000)+1000);
	}
	
	
	String username(){
		return this.username;
	}
	void addPerson(String name, int balance){
		int pin=generatePIN();
		System.out.println("PIN je: " + pin);
		Person person1 = new Person(name, pin, balance);
		System.out.println("Unijeli ste novog korisnika " + name.toUpperCase() + " sa PIN lozinkom " + pin + " ciji je pocetni balans na racunu " + balance + " KM.");
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String data = person1.toString();
			File file = new File("fajl.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(data);
			bw.newLine();

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}
	
	boolean deletePerson (String name, int pin){
		String thisLine = null;
		BufferedReader br = null;
		FileReader fr = null;
		
		boolean deleted = false;

		File file = new File("fajl.txt");
		File tempFile = new File("fajl.tmp");
		
		
	     try {
        	fr = new FileReader(file); 
    		br = new BufferedReader(fr);  // open input stream test.txt for reading purpose.

			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}

			FileWriter fw = new FileWriter(tempFile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
	      
	         while (((thisLine = br.readLine()) != null)) {
	        	 String[] words = thisLine.split(" ");
	        	 if (!((words[0].equals(name))&&(Integer.parseInt(words[1])==pin))){
	        		 
	        		String str=words[0]+ " "+words[1]+" "+words[2];
	        		
	        		bw.write(str);
	     			bw.newLine();
	        	 } else {
	        		 deleted = true;
	        	 }
	         }
	        bw.close();
	        fw.close();
	        
	        br.close();
	        fr.close();
	               
	        } catch(Exception e) {
	         e.printStackTrace();
	      } 
	     
	     
	     
         if (!file.delete()) {
             System.out.println("Datoteka se ne moze izbrisati");
             return deleted;
         }
         if (!tempFile.renameTo(file)){
             System.out.println("Datoteka se ne moze preimenovati");
         }
         
         return deleted;
         
	}
	
		void showMenu(ATM bankomat){
			int choiceAdmin=0;
			Scanner inputAdmin = new Scanner(System.in);
			
			do{
				choiceAdmin=0;
				
				
				System.out.println("Dobrodosli "+ username.toUpperCase());
				System.out.println("\n");
				
				
				System.out.println("****************************************************************");
				System.out.println("Izaberite akciju: ");
				System.out.println("\n");
				System.out.println("1. Dodavanje novog korisnika");
				System.out.println("2. Brisanje postojeceg korisnika");
				System.out.println("3. Promjena stanja novcanica");
				System.out.println("4. Izlaz");
				System.out.println("****************************************************************");
				choiceAdmin = inputAdmin.nextInt();
							
				switch (choiceAdmin){
				
				case 1:
					System.out.println("----------------------------------------------------------------");
					System.out.print("Upisite ime novog korisnika : ");
					String newUserName=inputAdmin.next();
					System.out.println("----------------------------------------------------------------");
					System.out.print("Koliki je pocetni balans korisnika " + newUserName + " : ");
					int balance=inputAdmin.nextInt();
					System.out.println("----------------------------------------------------------------");
					addPerson(newUserName,balance);
					break;
					
				case 2:
					System.out.println("----------------------------------------------------------------");
					System.out.print("Upisite ime korisnika : ");
					String userNameToDel=inputAdmin.next();
					System.out.print("Upisite PIN korisnika : ");
					int pinToDel=inputAdmin.nextInt();
					System.out.println("----------------------------------------------------------------");
					if (deletePerson(userNameToDel, pinToDel)){
						System.out.println("Korisnik "+userNameToDel+" je izbrisan.");
					}	else {
						System.out.println("Korisnik "+userNameToDel+" ne postoji.");
					}
					break;
				
				case 3:
					System.out.println("****************************************************************");
					System.out.println("PROMJENA STANJA  NOVCANICA:");
					System.out.println("****************************************************************");
					
					// proslijedjen skener metodi
					bankomat.showMenu(inputAdmin);
					
					choiceAdmin=0;
					break;	
				
				case 4:
					System.out.println("****************************************************************");
					
					break;
				
				default:
					System.out.println("Pogresan unos. Pokusajte ponovo.");
					break;
					
				}
				
			} while (choiceAdmin != 4);
			
			
			System.out.println("Hvala sto koristite nase usluge.");
			//inputAdmin.close();
		}
}