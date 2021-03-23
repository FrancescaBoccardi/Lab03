package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {

	private Set<String> words = new HashSet<String>();
	
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
	
	
}
