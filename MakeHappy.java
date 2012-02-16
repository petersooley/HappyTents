import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


public class MakeHappy extends Utilities{

	private ArrayList<Tent> tents = new ArrayList<Tent>();
	private ArrayList<Camper> campers = new ArrayList<Camper>();
	
	// defined in handleArgs()
	private Random rand;

	public static void main(String[] args) throws IOException {
		
		MakeHappy mh = new MakeHappy();
		mh.handleArgs(args);
		mh.search();
	}
	
	public boolean search() {
		
		return false;
	}
	
	private void setupTents(String tentListFile, boolean sorted, boolean big2small) throws IOException {
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
		
		for(int i = 0; i < size; ++i) {
			out("new tent of size "+capacities[i]);
			tents.add(new Tent(capacities[i]));
		}
	}
	
	
	
	private void setupCampers(String prefTableFile) throws IOException  {
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

			
	}
	
	// puke...very ineffecient. fortunately, only done once per line in prefTableFile.
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
			return c1.getNumberPrefs() - c2.getNumberPrefs();
		}	
	}
	private class CamperComparePickiestLast implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {
			return c2.getNumberPrefs() - c1.getNumberPrefs();
		}	
	}
	private class CamperCompareHighSumsFirst implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {
			return c1.getSumPrefs() - c2.getSumPrefs();
		}	
	}
	private class CamperCompareHighSumsLast implements Comparator<Camper> {
		@Override
		public int compare(Camper c1, Camper c2) {
			return c2.getSumPrefs() - c1.getSumPrefs();
		}	
	}
	
	public void handleArgs(String args[]) throws IOException {
		
		// Defaults:
		long seed = System.currentTimeMillis();
		String prefTableFile = "prefTable.txt";
		String tentListFile = "tentList.txt";
		Comparator<Camper> camperComparator = null;
		boolean sortTents = false;
		boolean sortTentsBig2Small = false;
		
		for(int i = 0; i < args.length; ++i) {
			String arg = args[i];
			int len = arg.length() - 1;
			out("arg: "+arg);
			
			// filter options
			if(arg.charAt(0) == '-') {
				for(int j = 1; j <= len; ++j) {
					switch(arg.charAt(j)) {
					case 'c':
						if(arg.charAt(j + 1) == '0') {
							out("Sorting campers by zeros low to high...");
							camperComparator = new CamperComparePickiestLast();
							++j;
						}
						else {
							out("Sorting campers by prefs low to high...");
							camperComparator = new CamperCompareHighSumsLast();
						}
						break;
					case 'C':
						if(arg.charAt(j + 1) == '0') {
							out("Sorting campers by zeros high to low...");
							camperComparator = new CamperComparePickiestFirst();
							++j;
						}
						else {
							out("Sorting campers by prefs high to low...");
							camperComparator = new CamperCompareHighSumsFirst();
						}
						break;
					case 't':
						sortTents = true;
						sortTentsBig2Small = false;
						out("Sorting tents smallest to biggest...");
						break;
					case 'T':
						sortTents = true;
						sortTentsBig2Small = true;
						out("Sorting tents biggest to smallest...");
						break;
					case 'r':
							out("Shuffling tents and campers...");
							if(i + 1 < args.length && args[i + 1].charAt(0) != '-' && args[i + 1].matches("[0-9]+")) {
								try {
									seed = Long.parseLong(args[++i]);
								}catch(NumberFormatException e) {
									usage("What kind of seed is that???");
								}
							}
						break;
					case 'h':
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

		out("Seed: "+seed);
		rand = new Random(seed);
		out("Tent list file: "+tentListFile);
		setupTents(tentListFile, sortTents, sortTentsBig2Small);
		out("Preference table file: "+prefTableFile);
		setupCampers(prefTableFile);
		
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
		out("This program attempts to solve the problem defined here: http://drdobbs.com/184410645.");
		out("Default preference table: \"prefTable.txt\".");
		out("Default tent list: \"tentList.txt\".");
		out("Default ordering of tents and campers is the order in the files.");
		out();
		out("Optimizations:");
		out("  -c		order campers by preference sums lowest to highest");
		out("  -C		order campers by preference sums highest to lowest");
		out("  -c0		order campers by least picky to most picky");
		out("  -C0		order campers by most picky to least picky");
		out("  -t		order tents by smallest capacity to biggest");
		out("  -T		order tents by biggest capacity to smallest");
		out("  -r [seed]	shuffle the tents and campers");
		out();
		out("NOTE: ");
		out("  It doesn't matter which order you specify the instance data files. We'll figure it out!");
		out();
		System.exit((errorMsg == null ? 1 : 0));
	}

}
