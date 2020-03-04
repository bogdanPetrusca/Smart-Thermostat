
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.util.SortedSet;
import java.util.TreeSet;

public class Room {
	private String name, id;
	int surface;
	private ArrayList<SortedSet<Temp>> list;
	private ArrayList<SortedSet<Humidity>> list2;
	public Room(String name, String id, int surface) {
		this.name = name;
		this.id = id;
		this.surface = surface;
		
		this.list = new ArrayList<SortedSet<Temp>>(24);
		for(int i = 0; i < 24; i ++) {
			Comparator<Temp> comp = Comparator.comparing(Temp::getTemp);
			SortedSet<Temp> set = new TreeSet<Temp>(comp);
			this.list.add(i, set);
		}
		this.list2 = new ArrayList<SortedSet<Humidity>>(24);
		for(int i = 0; i < 24; i ++) {
			Comparator<Humidity> comp2 = Comparator.comparing(Humidity::getHum);
			SortedSet<Humidity> set = new TreeSet<Humidity>(comp2);
			this.list2.add(i, set);
		}
	}
	public String getName() {
		return this.name;
	}
	public String getId() {
		return this.id;
	}
	public int getSurface() {
		return this.surface;
	}
	public ArrayList<SortedSet<Temp>> getList() {
		return this.list;
	}
	public ArrayList<SortedSet<Humidity>> getList2() {
		return this.list2;
	}
	
	public void addTemp(long timestamp, long timestampRef, double temp) {
		
		int index = (int) (timestampRef - timestamp) / 3600;
		Temp t = new Temp(timestamp, temp);
		if(timestamp > timestampRef)
			return;
		this.list.get(index).add(t);
		
	}
	public void addHum(long timestamp, long timestampRef, double hum) {
		
		int index = (int) (timestampRef - timestamp) / 3600;
		Humidity t = new Humidity(timestamp, hum);
		if(timestamp > timestampRef)
			return;
		this.list2.get(index).add(t);
		
	}
	public void display(long tInitial, long tFinal, long tRef, BufferedWriter out) throws IOException {
		int pozI = 0;
		if((tRef - tInitial) % 3600 == 0)
			pozI = (int) (tRef - tInitial) / 3600;
		else {
			pozI = (int) (tRef - tInitial) / 3600;
			pozI++;
		}
		int pozF = 0;
		if((tRef - tFinal) % 3600 == 0)
			pozF = (int) (tRef - tFinal) / 3600;
		else {
			pozF = (int) (tRef - tFinal) / 3600;
			pozF++;
		}
		pozF--;
		out.write(this.getName());
		for(int i = 0; i < 24; i++) {
			if(i >= pozI && i <= pozF) {
				SortedSet<Temp> set = this.list.get(i);
				for(Temp t: set) {
					out.write(' ');
					Formatter f=new Formatter();
					f.format("%.2f", t.getTemp());
					out.write(f.toString());
					f.close();
				}
					
			}
			
				
		}
		out.write('\n');
	}
	public double getMinTemp(long timestampRef) {
		double Min = 1000;
		for(int i = 0; i < 23; i++) {
			SortedSet<Temp> set = this.list.get(i);
			if(set.size() == 0)
				continue;
			else {
				
				for(Temp t: set) 	
					if(t.getTemp() <= Min)
						Min = t.getTemp();
				break;
			}
		}
		return Min;
		
	}
	public double getMaxHum(long timestampRef) {
		double Max = -1;
		for(int i = 0; i < 23; i++) {
			SortedSet<Humidity> set = this.list2.get(i);
			if(set.size() == 0)
				continue;
			else {
				for(Humidity t: set)
				{
					if(t.getHum() >= Max)
						Max = t.getHum();
				break;
				}
			}
		}
		return Max;
		
	}
	public void diplayHum() {
		System.out.println(this.getId());
		for(SortedSet<Humidity> set: list2) {
			
			for(Humidity h: set) {
				System.out.print(h.getHum() + " ");
				
			}
			
		}
		System.out.println();
	}
	
}
