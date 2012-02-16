import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class MakeHappy extends Utilities{

	private ArrayList<Tent> tents = new ArrayList<Tent>();
	private ArrayList<Camper> campers = new ArrayList<Camper>();
	
	// defined in handleArgs()
	private Random rand;
	private boolean verbose = false;

	public static void main(String[] args) throws IOException {
		
		MakeHappy mh = new MakeHappy();
		mh.handleArgs(args);
		mh.search(0,0,0,0);
	}
	
	/*
	 * Assumptions
	 * 1. total tent capacity = total campers
	 */
	private int maxTents = tents.size();
	
	public int search(int curHappiness, int maxHappiness, int tIndex, int cIndex) {
		Tent t = tents.get(tIndex);
		Camper c = campers.get(cIndex);
		t.addCamper(c);
		curHappiness =+ t.happiness();
		
		// at a leaf?
		if(t.atCapacity() && tIndex == maxTents) {
			// have we beat the best solution so far?
			if(curHappiness > maxHappiness) {
				return curHappiness;
			}
			// our current solution sucks, backtrack for something better
			else {
				t.removeCamper(c);
				//return search();
				//...
			}
		}
		else {
			return search(curHappiness, maxHappiness, tIndex, ++cIndex);
		}
		
		/*
		 * Complete search algorithm (no heuristics)
		 * 
		 * Fill tent[tIndex] with camper[cIndex]
		 * 		if tent[tIndex].isfull()
		 * 			if tIndex == maxTents
		 * 				happiness = getHappiness()
		 * 				if happiness > maxHappiness
		 * 					saveSolution()
		 * 					maxHappiness = happiness
		 * 			else
		 * 				++tIndex
		 * 		else
		 * 			return Fill tent[tIndex] with camper[++cIndex]
		 */
		return 0;
	}
	
	private void setupTents(String tentListFile, boolean sorted, boolean big2small, boolean random) throws IOException {
		ArrayList<Integer> caps = new ArrayList<Integer>();	
		BufferedReader in = open(tentListFile);		
		String str;
		int capacity = 0;
		while((str = in.readLine()) != null) {
			try {
				capacity = Integer.parseInt(str);
			}catch(NumberFormatException e) {
				out("ERROR: could not parse tent list file. See README for file specifications.");
				System.exit(1);
			}
			
			caps.add(capacity);
		}
		
		int size = caps.size();
		int [] capacities = new int[size];
		for(int i = 0; i < size; ++i) 
			capacities[i] = caps.get(i);
		
		if(sorted) {
			if(big2small) {
				Arrays.sort(capacities);
				capacities = reverse(capacities);
			}
			else {
				Arrays.sort(capacities);
			}
		}
		if(random) {
			shuffle(capacities, rand);
		}
		
		for(int i = 0; i < size; ++i) {
			vout("new tent of size "+capacities[i]);
			tents.add(new Tent(capacities[i]));
		}
	}
	
	
	
	private void setupCampers(String prefTableFile, Comparator<Camper> camperComparator, boolean random) throws IOException  {
		BufferedReader in = open(prefTableFile);
		String str;
		String [] fields = new String[3];
		String current = "";
		
		// First, create all the campers
		while((str = in.readLine()) != null) {
			// fields: "camper", "mate", "camper's rating of mate"
			fields = str.split("\\s+", 3);
			addCamper(fields[0]);
		}
		
		// Second, setup each camper's preferences.
		in = open(prefTableFile);
		while((str = in.readLine()) != null) {
			fields = str.split("\\s+", 3);
			setPref(fields[0], fields[1], Integer.parseInt(fields[2]));
		}
		
		// Third, do any kind of sorting necessary
		if(camperComparator != null) {
			Collections.sort(campers, camperComparator);
		}
		if(random) {
			shuffle(campers, rand);
		}
		
		if(verbose)
			for(int i = 0; i < campers.size(); ++i) 
				campers.get(i).printv();
		
	}
	
	// puke...very ineffecient. fortunately, only done once while reading prefTableFile.
	private boolean setPref(String camper, String mate, int rating) {
		int size = campers.size();
		for(int i = 0; i < size; ++i) 
			if(campers.get(i).isName(camper)) 
				for(int j = 0; j < size; ++j) 
					if(campers.get(j).isName(mate)) 
						return campers.get(i).rateMate(campers.get(j), rating);
		return false;
	}
	
	private boolean addCamper(String name) {
		for(Camper c : campers) 
			if(c.isName(name))
				return false;
		return campers.add(new Camper(name));
	}
	
	private class CamperComparePickiestFirst implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {	
			return c2.getNumberPrefs() - c1.getNumberPrefs();
		}	
	}
	private class CamperComparePickiestLast implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {
			return c1.getNumberPrefs() - c2.getNumberPrefs();
		}	
	}
	private class CamperCompareHighSumsFirst implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {
			return c2.getSumPrefs() - c1.getSumPrefs();
		}	
	}
	private class CamperCompareHighSumsLast implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {
			return c1.getSumPrefs() - c2.getSumPrefs();
		}	
	}
	
	public void handleArgs(String args[]) throws IOException {
		
		long time = System.currentTimeMillis();
		long lesstime = (time / 100000) * 100000; // fills last 5 digits with zeros
		
		
		// Defaults:
		int seed = (int) (time - lesstime); // using a seed with only up to 5 digits makes it easier to remember
		String prefTableFile = "prefTable.txt";
		String tentListFile = "tentList.txt";
		Comparator<Camper> camperComparator = null;
		boolean sortTents = false;
		boolean sortTentsBig2Small = false;
		boolean random = false;
		
		for(int i = 0; i < args.length; ++i) {
			String arg = args[i];
			int len = arg.length() - 1;
			
			// filter options
			if(arg.charAt(0) == '-') {
				for(int j = 1; j <= len; ++j) {
					switch(arg.charAt(j)) {
					case 'c':
						if(j + 1 <= len && arg.charAt(j + 1) == '0') {
							camperComparator = new CamperComparePickiestLast();
							++j;
						}
						else {
							camperComparator = new CamperCompareHighSumsLast();
						}
						break;
					case 'C':
						if(j + 1 <= len && arg.charAt(j + 1) == '0') {
							camperComparator = new CamperComparePickiestFirst();
							++j;
						}
						else {
							camperComparator = new CamperCompareHighSumsFirst();
						}
						break;
					case 't':
						sortTents = true;
						sortTentsBig2Small = false;
						break;
					case 'T':
						sortTents = true;
						sortTentsBig2Small = true;
						break;
					case 'r':
							random = true;
							if(i + 1 < args.length && args[i + 1].charAt(0) != '-' && args[i + 1].matches("[0-9]+")) {
								try {
									seed = Integer.parseInt(args[++i]);
								}catch(NumberFormatException e) {
									usage("What kind of seed is that???");
								}
							}
						break;
					case 'v':
					case 'V':
							setVerbose(true);
							verbose = true;
						break;
					case 'h':
					case 'H':
							usage();
						break;
					default:
							usage("Weird argument. Just too weird.");
					}
				}
			}	
			else {
				// Filter files
				BufferedReader in = open(arg);
				String str;
				while((str = in.readLine()) != null) {
					if(str.matches("[a-zA-Z]+\\s*[a-zA-Z]+\\s*[-0-9]+")){
						prefTableFile = arg;
						break;
					}	
					if(str.matches("\\s*\\d+\\s*")) {
						tentListFile = arg;
						break;
					}
				}
			}
		}

		out(String.format("%-10s %s","Seed: ",seed));
		out(String.format("%-10s %s","TentList: ", tentListFile));
		out(String.format("%-10s %s","PreTable: ", prefTableFile));
		rand = new Random(seed);
		setupTents(tentListFile, sortTents, sortTentsBig2Small, random);
		setupCampers(prefTableFile, camperComparator, random);
		
	}
	
	
	private void usage() {
		usage(null);
	}
	
	private void usage(String errorMsg) {
		if(errorMsg != null) {
			out();
			out("ERROR: "+errorMsg);
			out();
		}

		out("MakeHappy [prefTableFile] [tentListTable]");
		out();
		out("This program attempts to solve the problem defined here:");
		out("	http://drdobbs.com/184410645.");
		out("Default preference table: \"prefTable.txt\".");
		out("Default tent list: \"tentList.txt\".");
		out("Default ordering of tents and campers is the order in the files.");
		out();
		out("Options:");
		out("  -c		order campers by preference sums lowest to highest");
		out("  -C		order campers by preference sums highest to lowest");
		out("  -c0		order campers by least picky to most picky");
		out("  -C0		order campers by most picky to least picky");
		out("  -t		order tents by smallest capacity to biggest");
		out("  -T		order tents by biggest capacity to smallest");
		out("  -r [seed]	shuffle the tents and campers");
		out();
		out("  -v		verbose");
		out("  -h		help");
		out();
		out("NOTE: ");
		out("  It doesn't matter which order you specify the instance data files.");
		out("  We'll figure it out!");
		out();
		System.exit((errorMsg == null ? 1 : 0));
	}

}
