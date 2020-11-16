package HoneypotAWS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVInterface extends SortingUtil {
	static ArrayList<HoneypotAttack> attacks = new ArrayList<HoneypotAttack>();
	static String filePath = "";
	
	
	public static void readCSV(int dataset) {
		//Picking which dataset is being used, the reason I need to do this 
		//as the datetime for different datasets is in different formats
		switch (dataset) {
		case 0:
			filePath = "data/Honeypots.csv";
			break;
		case 1:
			filePath = "data/Honeypots_L.csv";
			break;
		}
		String line = "";
		long startTime = System.nanoTime();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			//reading the CSV line and passing it into the constructor
			br.readLine();
			int total = 0;
			while ((line = br.readLine()) != null) {
				attacks.add(new HoneypotAttack(line));
				total++;
				//preforming a test so the user can easily see what stage the import is at
				if (total % 100 == 0) {
					System.err.printf("%06d rows imported!%n", total);
				}
			}
			long endTime = System.nanoTime();
			long duration = ((endTime - startTime) / 1000000);
			System.err.printf("Data Imported!%nTook %dms%n", ((endTime - startTime) / 1000000));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listCountries() {
		ArrayList<String> countries = new ArrayList<String>();
		for (HoneypotAttack attack : attacks) {
			//Checking if the arraylist is empty as the statement after would not work
			if (countries.isEmpty()) {
				countries.add(attack.getCountry() + ",1");
			} else {
				//compiling a regex pattern to search for the country
				Pattern pattern = Pattern.compile(attack.getCountry(), Pattern.LITERAL);
				for (int i = 0; i < countries.size(); i++) {
					String[] values = countries.get(i).split(",");
					Matcher matcher = pattern.matcher(values[0]);
					boolean matchFound = matcher.find();
					if (matchFound) {
						countries.set(i, attack.getCountry() + "," + (Integer.parseInt(values[1]) + 1));
						break;
					}
					//If the loop is at the end and hasn't found the index its looking for it adds a new index
					if (i == countries.size() - 1) {
						countries.add(attack.getCountry() + ",1");
					}
				}
			}
		}
		System.err.printf("%-33s : %s%n", "Country", "Number of attacks");
		int total = 0;
		//Sorting the arry so it displays properly
		SortingUtil su = new SortingUtil();
		countries = su.sort(countries, 0, countries.size() - 1);
		int emptyIndex = 0;
		//Loop is going backwards as to the sort sorting it the wrong way.
		for (int i = countries.size() - 1; i >= 0; i--) {
			String[] values = countries.get(i).split(",");
			if (!values[0].equals("N/A")) {
				System.out.printf("%-33s : %s%n", values[0], values[1]);
				total += Integer.parseInt(values[1]);
			} else {
				emptyIndex = i;
			}
		}
		int size = countries.size();
		if (emptyIndex != 0) {
			String[] values = countries.get(emptyIndex).split(",");
			System.out.printf("%n%s Attacks missing country data!%n", values[1]);
			//To get rid of "N/A" from the size count
			size -= 1;
		} 
		System.out.printf("%nTotal Attacks : %d%nTotal Countries : %d%n%n", total, size);
	}

	public static void attackSearchCountry(String choice) {
		ArrayList<String> hosts = new ArrayList<String>();
		//Data is stored in the system like
		//hosts = country,amount
		int total = 0;
		for (HoneypotAttack attack : attacks) {
			//Checking if the country matches the chosen country
			if (attack.getCountry().equals(choice)) {
				if (hosts.isEmpty()) {
					hosts.add(attack.getHost() + ",1");
				} else {
					Pattern pattern = Pattern.compile(attack.getHost(), Pattern.LITERAL);
					for (int i = 0; i < hosts.size(); i++) {
						String[] values = hosts.get(i).split(",");
						Matcher matcher = pattern.matcher(values[0]);
						boolean matchFound = matcher.find();
						if (matchFound) {
							hosts.set(i, attack.getHost() + "," + (Integer.parseInt(values[1]) + 1));
							break;
						} else if (i == hosts.size() - 1) {
							hosts.add(attack.getHost() + ",1");
						}
					}
				}
			}
		}
		//Sorting the array
		SortingUtil su = new SortingUtil();
		hosts = su.sort(hosts, 0, hosts.size() - 1);
		int emptyIndex = 0;
		//Loop is going backwards as to the sort sorting it the wrong way.
		for (int i = hosts.size() - 1; i >= 0; i--) {
			String[] values = hosts.get(i).split(",");
			if (!values[0].equals("N/A")) {
				System.out.printf("%-33s : %s%n", values[0], values[1]);
				total += Integer.parseInt(values[1]);
			} else {
				emptyIndex = i;
			}
		}
		if (emptyIndex != 0) {
			String[] values = hosts.get(emptyIndex).split(",");
			System.out.printf("%n%s Attacks missing country data!", values[1]);
		}
		System.out.printf("%nTotal Attacks : %d%n%n", total);
	}

	public static void attackSearchLocation(String choice) {
		ArrayList<String> hosts = new ArrayList<String>();
		for (HoneypotAttack attack : attacks) {
			if (attack.getCountry().equals(choice)) {
				hosts.add(attack.getLocale()+","+attack.getHost()+","+attack.getSrcstr());
			} 
		}
		//Using a built in sort to sort the data by name so it can be clustered
		Collections.sort(hosts, Collections.reverseOrder());
		for (int i = hosts.size() - 1; i >= 0; i--) {
			String[] values = hosts.get(i).split(",");
			System.out.printf("%-15s : %-15s : %-15s%n",values[2],values[0],values[1]);
		}
	}

	public static void completeData(String choice) {
		for (HoneypotAttack attack : attacks) {
			//checking if both the missing data flag is true and it equals the chosen index
			//The reason we have the missing data flag is due to some data not being as important
			//so it can be skipped
			if (attack.getMissingData() && attack.getSrcstr().equals(choice)) {
				System.out.println(attack.getAll());
				String[] values = attack.getAll().split(",");
				for(int i = 0; i < values.length ;i++) {
					if(values[i].equals("N/A")||values[i].equals("0")) {
						System.out.printf("Enter data for place %d : %n",i+1);
						values[i] = Main.scan.nextLine();
					}
				}
				//Producing a csv line and resetting the data
				String csv = String.join(",", values);
				attack.resetData(csv);
			}
		}
	}

	public static void attackSearchIP(String choice) {
		for (HoneypotAttack attack : attacks) {
			if (attack.getSrcstr().equals(choice)) {
				System.out.printf("%-20s : %s%n", attack.getHost(), attack.getDatetime());
			} 
		}
	}

	public static void attackSearchHost(String choice) {
		for (HoneypotAttack attack : attacks) {
			if (attack.getHost().equals(choice)) {
				System.out.printf("%-20s : %-20s : %s%n",attack.getSrcstr(),attack.getCountry(),attack.getLocale());
			} 
		}
	}
	
	public static void mostAttacks() {
		ArrayList<String> attackSource = new ArrayList<String>();
		int total = 0;
		for (HoneypotAttack attack : attacks) {
			total++;
			if (total % 1000 == 0) {
				System.err.printf("%06d rows searched!%n", total);
			}
			if (attackSource.isEmpty()) {
				attackSource.add(attack.getSrcstr() + ",1");
			} else {
				for(int i = 0; i < attackSource.size(); i++) {
					String[] data = attackSource.get(i).split(",");
					if(attack.getSrcstr().equals(data[0])) {
						attackSource.set(i, attack.getSrcstr() + "," + (Integer.parseInt(data[1]) + 1));
						break;
					}else if(i == attackSource.size() - 1) {
						//If at the end of the list then add it to the end because it is not contained 
						attackSource.add(attack.getSrcstr() + ",1");
					}
				}
			}
		}
		SortingUtil soU = new SortingUtil();
		attackSource = soU.sort(attackSource, 0, attackSource.size() - 1);
		String[] finalData = attackSource.get(attackSource.size()-1).split(",");
		System.out.printf("%s's attacks%n", finalData[0]);
		for (HoneypotAttack attack : attacks) {
			if (attack.getSrcstr().equals(finalData[0])) {
				System.out.printf("%-33s : %s%n", attack.getHost(), attack.getDatetime());
			} 
		}
	}

	public static void attackSearchLatLong(String choice) {
		double latitude = 0;
		double longitude = 0;
		//Finding the latitude and longitude of the chosen IP address
		for (HoneypotAttack attack : attacks) {
			if (attack.getSrcstr().equals(choice)) {
				latitude = attack.getLatitude();
				longitude = attack.getLongitude();
				break;
			} 		
		}
		//Printing the lat and long to 5 decimal places
		System.err.printf("%n%s's Location : %.5f : %.5f%n", choice, latitude, longitude);
		//Going through the array to see if the lat and long are between 1 degree away
		for (HoneypotAttack attack : attacks) {
			double tmpLat = attack.getLatitude();
			double tmpLong = attack.getLongitude();
			if(tmpLat >= latitude - 1  && tmpLat <= latitude + 1 && tmpLong >= longitude - 1  && tmpLong <= longitude + 1  ) {
				System.out.printf("%-20s : %.5f : %.5f : %-20s : %-20s%n", attack.getSrcstr(), tmpLat, tmpLong, attack.getHost(), attack.getDatetime());
			}
		}
		
	}
	
	public static void quit() {
		//This is called to save all the changes made
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			//First writing the column names
			bw.write("datetime,host,src,proto,type,spt,dpt,srcstr,cc,country,locale,localeabbr,postalcode,latitude,longitude");
			//Writing each line from the array
			for(HoneypotAttack attack : attacks) {
				bw.write(attack.getAll());
				bw.newLine();
			}
			System.out.println("File written Successfully");
		} catch (IOException e1) {
			System.out.println("File write unsuccessful");
		}
	}
}
