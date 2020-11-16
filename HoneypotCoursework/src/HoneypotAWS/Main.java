package HoneypotAWS;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	public static Scanner scan = new Scanner(System.in);
	public static int dataset = 0;
	
	public static void main(String[] args) throws InterruptedException {
		System.err.printf("Select which dataset;%n0 - Sample%n1 - Large%n");
		dataset = Integer.parseInt(scan.nextLine());
		CSVInterface.readCSV(dataset);
		String choice = "";
		boolean quit = false;
		do{
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.printf("%nMenu%n");
			System.out.printf(
					"A-List Countries that attacks have originated from.%nB-Display all hosts attacked from a country/location.%n"
							+ "C-Complete data on specific attack.%nD-Query specific Host/IP address.%nE-Find address with the highest amount of attacks."
							+ "%nF-Find other attacks close to a specific IP address.%nQ-Quit%nEnter Choice (A-F,Q)%n");
			choice = scan.nextLine();
			System.out.printf("%n");
			switch (choice.toUpperCase()) {
			case "A":
				long startTime = System.nanoTime();
				CSVInterface.listCountries();
				long endTime = System.nanoTime();
				long duration = ((endTime - startTime) / 1000000);
				TimeUnit.MILLISECONDS.sleep(100);
				System.err.printf("Action%nTook %dms!%n", (duration));
				break;
			case "B":
				System.out.printf(
						"A-Display as a list.%nB-Display clusteded by location.%nQ-Back%nEnter Choice (A,B,Q)%n");
				choice = scan.nextLine();
				System.out.printf("%n");
				switch (choice.toUpperCase()) {
				case "A":
					System.out.print("Enter Country : ");
					choice = scan.nextLine();
					startTime = System.nanoTime();
					CSVInterface.attackSearchCountry(choice);
					endTime = System.nanoTime();
					duration = ((endTime - startTime) / 1000000);
					TimeUnit.MILLISECONDS.sleep(100);
					System.err.printf("Action%nTook %dms!%n", (duration));
					break;
				case "B":;
					System.out.print("Enter Location : ");
					choice = scan.nextLine();
					startTime = System.nanoTime();
					CSVInterface.attackSearchLocation(choice);
					endTime = System.nanoTime();
					duration = ((endTime - startTime) / 1000000);
					TimeUnit.MILLISECONDS.sleep(100);
					System.err.printf("Action%nTook %dms!%n", (duration));
					break;
				case "Q":

					break;
				default:
					System.err.printf("%n%nPlease enter a valid option!%n");
				}
				break;
			case "C":
				System.out.print("Enter IP : ");
				choice = scan.nextLine();
				startTime = System.nanoTime();
				CSVInterface.completeData(choice);
				endTime = System.nanoTime();
				duration = ((endTime - startTime) / 1000000);
				TimeUnit.MILLISECONDS.sleep(100);
				System.err.printf("Action%nTook %dms!%n", (duration));
				break;
			case "D":
				System.out.printf(
						"A-Display based on IP.%nB-Display based on host.%nQ-Back%nEnter Choice (A,B,Q)%n");
				choice = scan.nextLine();
				System.out.printf("%n");
				switch (choice.toUpperCase()) {
				case "A":
					System.out.print("Enter IP : ");
					choice = scan.nextLine();
					startTime = System.nanoTime();
					CSVInterface.attackSearchIP(choice);
					endTime = System.nanoTime();
					duration = ((endTime - startTime) / 1000000);
					TimeUnit.MILLISECONDS.sleep(100);
					System.err.printf("Action%nTook %dms!%n", (duration));
					break;
				case "B":;
					System.out.print("Enter host : ");
					choice = scan.nextLine();
					startTime = System.nanoTime();
					CSVInterface.attackSearchHost(choice);
					endTime = System.nanoTime();
					duration = ((endTime - startTime) / 1000000);
					TimeUnit.MILLISECONDS.sleep(100);
					System.err.printf("Action%nTook %dms!%n", (duration));
					break;
				case "Q":

					break;
				default:
					System.err.printf("%n%nPlease enter a valid option!%n");
				}
				break;
			case "E":
				startTime = System.nanoTime();
				CSVInterface.mostAttacks();
				endTime = System.nanoTime();
				duration = ((endTime - startTime) / 1000000);
				TimeUnit.MILLISECONDS.sleep(100);
				System.err.printf("Action%nTook %dms!%n", (duration));
				break;
			case "F":
				System.out.print("Enter IP : ");
				choice = scan.nextLine();
				startTime = System.nanoTime();
				CSVInterface.attackSearchLatLong(choice);
				endTime = System.nanoTime();
				duration = ((endTime - startTime) / 1000000);
				TimeUnit.MILLISECONDS.sleep(100);
				System.err.printf("Action%nTook %dms!%n", (duration));
				break;
			case "Q":
				quit = true;
				break;
			default:
				System.err.printf("%n%nPlease enter a valid option!%n");
			}
		}while (!quit);
		CSVInterface.quit();
		scan.close();
	}
}
