import java.util.ArrayList;


public class Tent {

	private int capacity = 0;
	private int camperCount = 0;

	private ArrayList<Camper> campers = new ArrayList<Camper>();
	
	Tent(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean addCamper(Camper c) {
		if(camperCount == capacity)
			return false;
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
		for(int i = 0; i < size; ++i) 
			for(int j = 0; j < size; ++j) 
				if(i != j) 
					happy += campers.get(i).getMateRating(campers.get(j));
		return happy;		
	}
}
