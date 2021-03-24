package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {

	//private Set<String> words = new HashSet<String>();
	private List<String> words = new LinkedList<String>();
	//private List<String> words = new ArrayList<String>();
	
	public void loadDictionary(String language) {
		
		String file;
		
		if(language.equals("English")) {
			file = "src/main/resources/English.txt";
		}
		else {
			file = "src/main/resources/Italian.txt";
		}
		
		try {
			FileReader fr= new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String word;
			while((word = br.readLine()) != null) {
				words.add(word);
			}
			
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Errore nella lettura del file");
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		
		List<RichWord> result = new LinkedList<RichWord>();
		
		for(String s : inputTextList) {
			if(!words.contains(s)) {
				result.add(new RichWord(s, false));
			}
			else {
				result.add(new RichWord(s, true));
			}
		}
		
		return result;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		
		List<RichWord> result = new LinkedList<RichWord>();
		boolean found = false;
		
		for(String s : inputTextList) {
			for(String w : words) {
				if(w.equals(s)) {
					result.add(new RichWord(s, true));
					found = true;
					break;
				}
			}
			
			if(!found) {
				result.add(new RichWord(s, false));
			}
			
		}
		
		return result;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
			
			List<RichWord> result = new LinkedList<RichWord>();
			
			for(String s : inputTextList) {
				
				int sup = words.size();
				int inf = 0;
				
				while(words.get((sup+inf)/2).compareTo(s)!=0 && (sup-inf)!=1) {
					if(words.get((sup+inf)/2).compareTo(s)>0) {
						sup = Math.round((sup+inf)/2);
					} else {
						inf = Math.round(sup+inf)/2;
					}
				}
				
				if((sup-inf)!=1) {
					result.add(new RichWord(s, true));
				} else {
					result.add(new RichWord(s, false));
				}
				
			}
			
			return result;
		}
	

	
}
