package com.starflask.maps;

/**
 * A blackprint is a configuraion file for a map.
 * It contains the map name, author, 
 * 
 *  
 * they are encoded in json and can be easily shared 
 */
public class Blackprint {
		
	
	String name;
	String author;
	
	String mapFileName;//this is a vox file.. the base map file 
	String mapFileHash;//to verify it in unchanged
	
	
	
}
