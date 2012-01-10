package com.seriesmanager.connectivity;

import java.util.ArrayList;

/**
 * 
 * Class defined to solve reported punctual errors
 * Named as the hammer of THOR to solve all of my problems.
 * @author POGGER
 * 
 * */

public class Mjolnir {

	public static ArrayList<String> findMatch(String title){
		ArrayList<String> matches = new ArrayList<String>();
		if(title.equalsIgnoreCase("House")){
			matches.add("House M.D.(2004)");
		}else if(title.equalsIgnoreCase("V")){
			matches.add("V(2009)");
			matches.add("V(1984)");
		}else if(title.equalsIgnoreCase("Alphas")){
			matches.add("Alphas(2011)");
		}
		return matches;
	}
	
}
