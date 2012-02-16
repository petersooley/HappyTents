

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Utilities {
	protected BufferedReader open(String file) {
		try {
			return new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			out("ERROR: couldn't find file: "+file);
			return null;
		}
	}
	
	protected int [] reverse(int [] array) {
		int [] newArray = new int[array.length];
		for(int i = 0, j = array.length - 1; i < array.length; ++i, --j) {
			newArray[i] = array[j];
		}
		return newArray;
	}
	
	protected void out(Object o) {
		System.out.println(o);
		System.out.flush();
	}
	protected void out() {
		System.out.println();
		System.out.flush();
	}
	
	protected void err(String msg) {
		System.err.println(msg);
		System.err.flush();
	}
	
	protected void err() {
		System.err.println();
		System.err.flush();
	}
	
	protected void vital(boolean expression) {
		if(!expression) {
			int line = Thread.currentThread().getStackTrace()[2].getLineNumber();
			String file = Thread.currentThread().getStackTrace()[2].getFileName();
			err("["+file+":"+line+"] assertion error");
			throw new AssertionError();
		}
	}
}