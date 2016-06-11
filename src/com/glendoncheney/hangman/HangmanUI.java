package com.glendoncheney.hangman;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
/**
 * The HangmanUI class sets up the game panel 
 * and initializes the game with chosen word
 * @author Glendon Cheney
 *
 */
public class HangmanUI extends JFrame {
   private JPanel imagePanel;     
   private JLabel imageLabel;
   private JPanel guessPanel;
   private static JLabel lblguessedLetters;
   private JTextField txtGuess;
   private JLabel lblGuess;
   private JPanel buttonPanel;    
   private JButton btnGuess;        
   private JButton btnNewGame;
   public Hangman hangman;
   private String guess;
   private String secretWord;
   boolean buildGame = true;

   /**
    * Sets the chosen word and sets up the
    * panel components 
    */
   public HangmanUI() {
	   do {
		   try {
			   secretWord = JOptionPane.showInputDialog("Enter a secret word for your "
					   + "friend to guess (case sensitive): ");
			   System.out.println(secretWord); //for cheaters..
			   
			   if (secretWord == null) {
				   System.exit(0);
			   }
			   if (secretWord.length() < 2){
				   throw new Exception();
			   }
			   buildGame = true;
		   } 
		   catch(Exception ex) {
			   JOptionPane.showMessageDialog(null,
					  "Please enter a valid word. A word must be at least two characters long.",
					  "Invalid Word Error",
					  JOptionPane.WARNING_MESSAGE); 
			   buildGame = false;
		   }
	   } while (buildGame == false);
	   
	   if (buildGame) {
		   hangman = new Hangman(secretWord);
	  
		   //Set window dimensions and location
		   this.setTitle("Hangman: The Game!");
		   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		   int width = (int)screenSize.getWidth();
		   int height = (int)screenSize.getHeight();
		   this.setSize(width / 2, height / 2);
		   this.setLocation(width / 3, height / 3);
		   this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		   
		   //exit game
		   this.addWindowListener( new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
				   JFrame frame = (JFrame)e.getSource();

				   int dialogResult = JOptionPane.showConfirmDialog(
						   frame,
						   "Are you sure you want to exit the game?",
						   "Exit Game?",
						   JOptionPane.YES_NO_OPTION);

	              if (dialogResult == JOptionPane.YES_OPTION) {
	                  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	              }
			   }
		   });
      
		   //Set and Build layout
		   this.setLayout(new BorderLayout());
		   buildGuessBox();
		   buildImagePanelOne();
		   buildButtonPanel();
		   this.add(imagePanel, BorderLayout.CENTER);
		   this.add(guessPanel, BorderLayout.NORTH);
		   this.add(buttonPanel, BorderLayout.SOUTH);
		   this.pack();
		   this.setVisible(true);
	   }
   }
   

   private void buildGuessBox() {
	   guessPanel = new JPanel();
	   lblGuess = new JLabel("Guess a letter: ");
	   guessPanel.add(lblGuess);
	   txtGuess = new JTextField(10);
	   guessPanel.add(txtGuess);
	   lblguessedLetters = new JLabel(hangman.setHiddenWord());
	   lblguessedLetters.setFont(new Font("Serif", Font.PLAIN, 24));
	   guessPanel.add(lblguessedLetters);
   }

   private void buildImagePanelOne() {
      imagePanel = new JPanel();
      imageLabel = new JLabel();
      String startImgRes = "/images/hangman1.GIF";
      imageLabel.setIcon(new ImageIcon(getClass().getResource(startImgRes)));
      imagePanel.add(imageLabel);
   }
   

   private void buildButtonPanel() {
      buttonPanel = new JPanel();
      btnGuess = new JButton("Make your guess!");
      btnGuess.addActionListener(new GuessButtonListener());
      buttonPanel.add(btnGuess);
      btnNewGame = new JButton("Start a new game!");
      btnNewGame.addActionListener(new NewGameButtonListener());
      buttonPanel.add(btnNewGame);
   }

   private class GuessButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
    	  boolean guessedAlready = false;
    	  boolean keepGoing = true;
    	  try {
    		  guess = txtGuess.getText();
    		  if (guess.length() < 1) {
    			  throw new Exception();
    		  }
    	  } 
    	  catch(Exception ex) {
    		  JOptionPane.showMessageDialog(null,
					  "Please make a valid guess!",
					  "Guess Error",
					  JOptionPane.WARNING_MESSAGE); 
    		  keepGoing = false;
    		  txtGuess.requestFocusInWindow();
			  txtGuess.selectAll();
    	  }
    	  if(keepGoing) {
    		  char ch = guess.charAt(0);
    		  guessedAlready = hangman.guessed(ch);
    		  if (!guessedAlready) {
    			  int numGuesses = hangman.getNumGuesses(ch);
    			  lblguessedLetters.setText(hangman.updateHangman(ch));
    			  txtGuess.requestFocusInWindow();
    			  txtGuess.selectAll();
    			  boolean correct = hangman.isCorrect(ch);
    			  if (!correct) {
    				  if (numGuesses == 10) {
    					  JOptionPane.showMessageDialog(null,
    							  "Sorry, you lost this round!",
    							  "You lost!",
    							  JOptionPane.ERROR_MESSAGE);
    				  } 
    				  else {
    					  String imgRes = "/images/hangman" + numGuesses + ".GIF";
    					  imageLabel.setIcon(new ImageIcon(getClass().getResource(imgRes)));
    				  }
    			  }
    			  
    			  if (lblguessedLetters.getText().equalsIgnoreCase(hangman.getWord())) {
    				  JOptionPane.showMessageDialog(null,
    						  "Congratulations! You won!",
    						  "Winner!",
    						  JOptionPane.PLAIN_MESSAGE);
    			  }
    		  } 
    		  else {
    			  JOptionPane.showMessageDialog(null,
    					  "Oops! You already guessed that letter!",
    					  "Repeat!",
    					  JOptionPane.WARNING_MESSAGE);
    			  txtGuess.requestFocusInWindow();
    			  txtGuess.selectAll();
    		  }
    	  }
      }
  }
   
   private class NewGameButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
    	  int dialogResult = JOptionPane.showConfirmDialog(null,
                  "Are you sure you want to start a new game?",
                  "Start New Game",
                  JOptionPane.YES_NO_OPTION);

          if (dialogResult == JOptionPane.YES_OPTION) {
            	setVisible(false);
            	dispose();
            	main(null);
          } 
          else {
        	  txtGuess.requestFocusInWindow();
			  txtGuess.selectAll();
          }
      }
   }
   
   /**
    * Application entry point
    * @param args command line arguments
    */
   public static void main(String[] args) {
      new HangmanUI();
   }
}
