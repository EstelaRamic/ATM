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
import java.util.Scanner;

public class Person { 	

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

	@Override 
	public String toString() {
		return "" + username + " " + pin + " " + balance;
	}
	
	String getUsername(){
		return username;
	}
	
	int getPin(){
		return pin;
	}
	
	void deposite(int amount){
		if (amount != 0){
			balance += amount;
		}
	}
	
	boolean isPosibleWithdraw(int amount){
		if ((balance >= amount)){
			return true;
		} else{
			return false;
		}
	}
	
	void withdraw(int amount){
		if (amount != 0){
			balance -= amount;
			changeDataInFile(this);
		}
	}
	
	void changeDataInFile (Person person){
		String thisLine = null;
		
		BufferedReader br = null;
		FileReader fr = null;

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
	     
         if (!file.delete()) {
             System.out.println("Datoteka se ne može izbrisati");
             return;
         }
         if (!tempFile.renameTo(file)){
             System.out.println("Datoteka se ne može preimenovati");
         }
         
	}
	
	// Dodan input kao parametar metode
	void showMenu(ATM bankomat, Scanner input){
		int choicePerson=0;
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
					if (amountWD <= balance){
						
						if (isPosibleWithdraw(amountWD)){
							
							if (bankomat.isPosiblePersonWithdraw(amountWD)){
								
								withdraw(amountWD);  
								bankomat.payment(amountWD); 
								System.out.println("\nTransakcija je uspjesno obavljena.");
								System.out.println("Novo stanje na raèunu: "+ this.balance);
							} else{
								System.out.println("Bankomat nema dovoljno sredstava za ovu transakciju.");
								System.out.println("Molimo Vas da odete do sljedece najbliže poslovnice.");
							}
						}else {
							System.out.println("Nemate dovoljno sredstava na racunu za ovu transakciju.");
						}
					}
					else{
						System.out.println("Nemate dovoljno sredstava na racunu za ovu transakciju.");
					}
				}
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
		
	}
}
