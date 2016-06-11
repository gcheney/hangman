package hangman;

import javax.swing.*;

public class Hangman {
	private String word = "";
	private String hiddenWord = "";
	private char[] arr;
	private String guessed = "";
	private int numberOfGuesses = 1;
	
	public Hangman(String s) {
		word = s;
		int size = word.length();
		arr = new char[size];
	}

	public String getWord(){
		return word;
	}
	
	public String setHiddenWord() {
		arr[0] = word.charAt(0);
		
		for (int i = 1; i < word.length(); i++) {
		   arr[i] = '-';
		}
		hiddenWord = new String(arr);
		
		return hiddenWord;
	}
	
	public boolean guessed(char ch) {
		boolean duplicate = false;
		for (char c : guessed.toCharArray()) {
		    if (c == ch) {
		    	duplicate = true;
		    }
		}
		guessed += ch;
		
		return duplicate;
	}
	
	public int numGuess(char c) {
		int correct = 0;
		for (int i = 0; i < word.length(); i++) {
			  if(c == word.charAt(i))
				  correct += 1;
		}
		if (correct == 0) {
			numberOfGuesses += 1;
		}
		
		return numberOfGuesses;
	}
	
	public String updateHangman(char c) {			
		int correctLetter = 0;
		for (int i = 0; i < word.length(); i++) {
			if (c == word.charAt(i)){
				arr[i] = c;
				correctLetter += 1;
			}
		}
		if (correctLetter == 0){
			JOptionPane.showMessageDialog(null,
					"Sorry, that guess was incorect!",
					"Wrong!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"Good guess! That letter appears " + correctLetter + " times!",
					"Correct!",
					JOptionPane.PLAIN_MESSAGE);
		}
		
		hiddenWord = new String(arr);
		return hiddenWord;
	}
	
	public Boolean isCorrect(char c) {
		boolean isCorrect = false;
		for (int i = 0; i < word.length(); i++) {
			  if(c == word.charAt(i)) {
				  isCorrect = true;
			  }
		}
		return isCorrect;
	}
}
