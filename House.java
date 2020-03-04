import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;

public class House {
	ArrayList<Room> list;
	public House() {
		this.list = new ArrayList<Room>();
	}
	public ArrayList<Room> getList() {
		return this.list;
	}
	public Room getRoom(String id) {
		for(Room r: this.list)
			if(r.getId().equals(id))
				return r;
		return null;
	}
	
	public void display(String name, long tInitial, long tFinal, long tRef, BufferedWriter out) throws IOException {
		for(Room r: this.list) {
			if(r.getName().equals(name))
				r.display(tInitial, tFinal, tRef, out);
		}
	}
	
	public double averageTemp(long timestampRef) {
		double s = 0;
		int sum = 0;
		for(Room r: this.list) {
			if(r.getMinTemp(timestampRef) != 0) {
				s += r.getSurface() * r.getMinTemp(timestampRef);
				sum += r.getSurface();
			}
		}
		return s / sum;
			
	}
	public double averageHum(long timestampRef) {
		double s = 0;
		int sum = 0;
		for(Room r: this.list) {
			if(r.getMaxHum(timestampRef) != 0) {
				s += r.getSurface() * r.getMaxHum(timestampRef);
				sum += r.getSurface();
			}
		}
		return s / sum;	
	}
	public void displayLast() {
		for(Room r: this.list) {
			SortedSet<Temp> set = r.getList().get(0);
			for(Temp t: set)
				System.out.print(t.getTemp() + " ");
			System.out.println();
		}
	}
	public void displayHum() {
		for(Room r: list) {
			r.diplayHum();
		}
	}
}
