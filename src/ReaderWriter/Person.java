package ReaderWriter;
/*
 * klasa koja èuva podatke o klijentima
 * 
 * */
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.Serializable;
import java.util.Scanner;

public class Person { 	// implements Serializable {

	private String username;
	private int pin;
	private int balance;

	Person() {
	};

	Person(String name, int pass, int amount) {
		this.username = name;
		this.pin = pass;
		this.balance = amount;
	}

	//@Override 
	// Konverzija objekta u String
	public String toString() {
		//return "Account name:" + username + "\nPIN: " + pin + "\nBalance: " + balance;
		return "" + username + " " + pin + " " + balance;
	}
	
	String getUsername(){
		return username;
	}
	
	int getPin(){
		return pin;
	}
	
	// dodavanje iznosa novca na trenutno stanje - ovlašten Administator
	void deposite(int amount){
		if (amount != 0){
			balance += amount;
		}
	}
	
	// provjera da li je moguæe izvršiti traženu isplatu novca, tj, da li ima dovoljno sredstava na raèunu 
	boolean isPosibleWithdraw(int amount){
		if ((balance >= amount)){
			return true;
		} else{
			return false;
		}
	}
	
	// isplaæuje novac korisniku, tj. umanjuje sredstva korisnika za traženi iznos
	void withdraw(int amount){
		if (amount != 0){
			balance -= amount;
			changeDataInFile(this);
		}
	}
	
	void changeDataInFile (Person person){
		String thisLine = null;
		
		// upis u datotetku  
		BufferedReader br = null;
		FileReader fr = null;

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
	        	 Person person1= new Person(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2]));
	        	 if ((words[0].equals(person.username.toLowerCase()))&&(Integer.parseInt(words[1])==person.pin)){
	        		 
	        		bw.write(person.toString());
	     			bw.newLine();
	        	 } else {
	        		bw.write(person1.toString());
		     		bw.newLine();
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
             System.out.println("Datoteka se ne može izbrisati");
             return;
         }
         //Rename the new file to the filename the original file had.
         if (!tempFile.renameTo(file)){
             System.out.println("Datoteka se ne može preimenovati");
         }
         
	}
	
	// korisnièki interfejs, opcije koje su dozvoljene korisniku i njihovo pozivanje na izvršenje 
	void showMenu(ATM bankomat){
		int choicePerson=0;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Dobrodosli "+ username);
		System.out.println("\n");
		
		
		do{
			System.out.println("****************************************************************");
			System.out.println("Izaberite akciju: ");
			System.out.println("\n");
			System.out.println("1. Stanje na racunu");
			System.out.println("2. Isplata gotovine");
			System.out.println("3. Izlaz");
			System.out.println("****************************************************************");
			choicePerson = input.nextInt();
						
			switch (choicePerson){
			
			case 1:
				System.out.println("----------------------------------------------------------------");
				System.out.println("Stanje na raèunu: "+ this.balance);
				System.out.println("----------------------------------------------------------------");
				
				break;
				
			case 2:
				boolean correctInput= true;
				do{
				System.out.println("----------------------------------------------------------------");
				System.out.println("Koji iznos želite podiæi: ");
				System.out.println("----------------------------------------------------------------");
				int amountWD = input.nextInt();
				if ((amountWD % 10) != 0) {
					correctInput= false;
					System.out.println("Pogresan unos. \nNe mozete podici iznose koji nisu zaokruzeni na desetice.");
				} else if (amountWD<0) {
					correctInput= false;
					System.out.println("Pogresan unos.\nNe mozete podici negativan iznoos.");
				}else {
				
					correctInput = true;
				}
				if (correctInput){
					//if (amountWD <= bankomat.balance()){
					if (amountWD <= balance){
						
						if (isPosibleWithdraw(amountWD)){
							
							if (bankomat.isPosiblePersonWithdraw(amountWD)){
								
								withdraw(amountWD);  // umanjenje iznosa na raèunu korisnika
								bankomat.payment(amountWD);  // umanjenje iznosa sa raèuna bankomata
								System.out.println("\nTransakcija je uspjesno obavljena.");
								System.out.println("Novo stanje na raèunu: "+ this.balance);
							} else{
								System.out.println("Bankomat nema dovoljno sredstava za ovu transakciju.");
								System.out.println("Molimo Vas da odete do sljedece najbliže poslovnice.");
							}
						}else {
							System.out.println("Nemate dovoljno sredstava na racunu za ovu transakciju.");
						}
					}// if amountWD<= bankomat....
					else{
						System.out.println("Nemate dovoljno sredstava na racunu za ovu transakciju.");
						//System.out.println("Molimo Vas da odete do sljedece najbliže poslovnice.");
					}
				}// if ((amountWD % 10) != 0)
				} while (!correctInput);
				break;
				
			case 3:
				System.out.println("****************************************************************");
				break;
			
			default:
				System.out.println("Pogresan unos. Pokusajte ponovo.");
				break;
				
			}
			
		} while (choicePerson != 3);
		
		System.out.println("****************************************************************\n\n");
		System.out.println("              Hvala sto koristite nase usluge.\n\n");
		System.out.println("****************************************************************");
		//input.close();
		
		
	}
}
