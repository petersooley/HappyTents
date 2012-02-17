import java.util.ArrayList;


public class Tent extends Utilities{

	private int capacity = 0;
	private int camperCount = 0;
	private int ID = 0;

	private ArrayList<Camper> campers = new ArrayList<Camper>();
	
	Tent(int ID,int capacity) {
		this.capacity = capacity;
		this.ID = ID;
	}
	
	public boolean addCamper(Camper c) {
		if(camperCount >= capacity)
			return false;
		++camperCount;
		return campers.add(c);
	}
	
	public boolean removeCamper(Camper c) {
		if(camperCount <= 0)
			return false;
		boolean rt = campers.remove(c);
		if(rt == true) 
			--camperCount;
		return rt;
			
	}
	
	public boolean atCapacity() {
		return camperCount >= capacity;
	}
	
	public int happiness() {
		int happy = 0;
		int size = campers.size();
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				if(i != j) {
					happy += campers.get(i).getMateRating(campers.get(j));
				}
			}
		}
		return happy;		
	}
	
	public Object clone() {
		Tent t = new Tent(ID, capacity);
		t.camperCount = camperCount;
		int size = campers.size();
		for(int i = 0; i < size; ++i){
			t.campers.add((Camper) campers.get(i).clone());
		}
		return t;
	}
	
	public void print() {
		out("Tent "+ID+": ("+happiness()+")");
		for(Camper c : campers) {
			out("  "+c);
		}
	}
	public void printv() {
		vout(String.format("tent %d, cap. %d, campers %d", ID, capacity, camperCount));
	}
}
