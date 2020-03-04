import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void readRooms(Scanner in, House house, int nrRooms) {
		
		while(nrRooms > 0) {
			String line = in.nextLine();
			String[] words = line.split(" ", 0);
			String name = words[0];
			String id = words[1];
			int surface = Integer.parseInt(words[2]);
			Room r = new Room(name, id, surface);
			house.getList().add(r);
			nrRooms--;
		}
	}
	public static void readCommands(Scanner in, long timestampRef, House house, double houseTemp, double houseHum, BufferedWriter out) throws IOException {
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] words = line.split(" ", 0);
			if(words[0].equals("OBSERVE")) {
				String id = words[1];
				long timestamp = Long.parseLong(words[2]);
				double temp = Double.parseDouble(words[3]);
				Room room = house.getRoom(id);

				room.addTemp(timestamp, timestampRef, temp);
			}
			if(words[0].equals("OBSERVEH")) {
				String id = words[1];
				long timestamp = Long.parseLong(words[2]);
				double hum = Double.parseDouble(words[3]);
				Room room = house.getRoom(id);

				room.addHum(timestamp, timestampRef, hum);
			}
			if(words[0].equals("LIST")) {
				String name = words[1];
				long tInitial = Long.parseLong(words[2]);
				long tFinal = Long.parseLong(words[3]);
				house.display(name, tFinal, tInitial, timestampRef, out);
			}
			if(words[0].equals("TRIGGER")) {
				double avgTemp = house.averageTemp(timestampRef);
				double avgHum = house.averageHum(timestampRef);
				
				
				if(avgHum > houseHum && avgHum != 0) {
					out.write("NO\n");
					continue;
				}
				else
				if(avgTemp < houseTemp) {
					out.write("YES\n");
				}
					
				else {
					out.write("NO\n");
				}
			}
			if(words[0].equals("TEMPERATURE")) {
				double t = Double.parseDouble(words[1]);
				houseTemp = t;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		File inputFile = new File("therm.in");
		Scanner in = new Scanner(inputFile);
		String firstLine = in.nextLine();
		String[] firstLineArgs = firstLine.split(" ", 0);
		int nrRooms = 0;
		double houseTemp = 0, houseHum = 0;
		long timestampRef = 0;
		//System.out.println(firstLineArgs.length);
		if(firstLineArgs.length == 4) {
			nrRooms = Integer.parseInt(firstLineArgs[0]);
			houseTemp = Double.parseDouble(firstLineArgs[1]);
			houseHum = Double.parseDouble(firstLineArgs[2]);
			timestampRef = Long.parseLong(firstLineArgs[3]);
		} else {
			nrRooms = Integer.parseInt(firstLineArgs[0]);
			houseTemp = Double.parseDouble(firstLineArgs[1]);
			timestampRef = Long.parseLong(firstLineArgs[2]);
		}
		//System.out.println(nrRooms + " " + houseTemp + " " + houseHum + " " + timestampRef);
		House house = new House();
		
		readRooms(in, house, nrRooms);
		
		String outFile = "therm.out";
		BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		readCommands(in, timestampRef, house, houseTemp, houseHum, out);
		in.close();
		out.close();
	}
}
