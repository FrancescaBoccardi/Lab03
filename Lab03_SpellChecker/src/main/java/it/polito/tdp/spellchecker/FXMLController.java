package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	Dictionary dictionary;
	boolean start = false;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboLanguage;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label labelErrors;

    @FXML
    private Button btnClear;

    @FXML
    private Label labelTime;

    @FXML
    void doClearText(ActionEvent event) {
    	this.txtInput.clear();
    	this.txtOutput.clear();
    	this.labelErrors.setText("");
    	this.labelTime.setText("");
    	comboLanguage.getItems().clear();
    	comboLanguage.getItems().addAll("English", "Italian");
    	start = false;
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	this.txtOutput.clear();
    	
    	if(start) {
    		dictionary.loadDictionary(this.comboLanguage.getValue());
    	}else {
    		this.txtOutput.setText("Choose a language!");
    		return;
    	}
    	
    	String input = txtInput.getText();
    	String result = input.replaceAll("[.?,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	String words[] = result.toLowerCase().split(" ");
    	List<String> wordsList = new LinkedList<String>();
    	
    	for(int i=0; i<words.length;i++) {
    		wordsList.add(words[i]);
    	}
    	
    	long start = System.nanoTime();
    	List<RichWord> output = dictionary.spellCheckText(wordsList);
    	long end = System.nanoTime();
    	
    	int count = 0;
    	
    	for(RichWord w : output) {
    		if(!w.isCorrect()) {
    			this.txtOutput.appendText(w.getWord()+"\n");
    			count++;
    		}
    	}
    	
    	this.labelErrors.setText("The text contains "+count+" errors");
    	this.labelTime.setText("Spell check completed in: "+((end-start)/1e9)+" secondi");
    	
    	
    }
    
    @FXML
    void selectLanguage(ActionEvent event) {
    	start = true;
    }

    @FXML
    void setModel(Dictionary d) {
    	this.dictionary = d;
    	this.comboLanguage.setPromptText("Language");
    	comboLanguage.getItems().addAll("English", "Italian");
    }

    @FXML
    void initialize() {
        assert comboLanguage != null : "fx:id=\"comboLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert labelErrors != null : "fx:id=\"labelErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert labelTime != null : "fx:id=\"labelTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}

