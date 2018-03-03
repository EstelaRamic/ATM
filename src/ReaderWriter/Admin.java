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

	//@Override 
	// preoptereæenje metode prilikom ispisa objekta iz datoteke (buffered) kojeg ispisuje kao string
	/*public String toString() {
		return "Account name:" + username + "\nPIN: " + pin + "\nBalance: " + balance;
	}*/
	
	int getPin(){
		return this.pin;
	}
	
	int generatePIN() {
		return ((int)(Math.random()*1000)+1000);
	}
	
	
	//pokazuje ime
	String username(){
		return this.username;
	}
	// dodavanje novog korisnika
	void addPerson(String name, int balance){
		int pin=generatePIN();
		System.out.println("PIN je: " + pin);
		Person person1 = new Person(name, pin, balance);
		System.out.println("Unijeli ste novog korisnika " + name.toUpperCase() + " sa PIN lozinkom " + pin + " ciji je pocetni balans na racunu " + balance + " KM.");
		// upis u datotetku  
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String data = person1.toString();
			File file = new File("fajl.txt");

			// provjerava da li fajl postoji, inaèe ga kreira
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file - dozvoljeno dodavanje na kraj fajla
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
	
	//brisanje korisnika
	boolean deletePerson (String name, int pin){
		String thisLine = null;
		//boolean found=false;
		// upis u datotetku  
		BufferedReader br = null;
		FileReader fr = null;
		
		boolean deleted = false;

		File file = new File("fajl.txt");
		File tempFile = new File("fajl.tmp");
		
		
	     try {
        	fr = new FileReader(file); 
    		br = new BufferedReader(fr);  // open input stream test.txt for reading purpose.

			// provjerava da li fajl.tmp postoji, inaèe ga kreira
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}

			// true = append file - dozvoljeno dodavanje na kraj fajla
			FileWriter fw = new FileWriter(tempFile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
	      
	         while (((thisLine = br.readLine()) != null)) {
	            //System.out.println(thisLine);
	        	 String[] words = thisLine.split(" ");
	        	 //Person person= new Person(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2]));
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
	     
	     
	     
	   //Delete the original file
         if (!file.delete()) {
             System.out.println("Datoteka se ne moze izbrisati");
             return deleted;
         }
         //Rename the new file to the filename the original file had.
         if (!tempFile.renameTo(file)){
             System.out.println("Datoteka se ne moze preimenovati");
         }
         
         return deleted;
         
	}
	
	// korisnièki interfejs, opcije koje su dozvoljene administratoru i njihovo pozivanje na izvršenje 
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
					
					bankomat.showMenu();
					
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