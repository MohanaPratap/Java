import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MatchCards {
    class Card{
        String cardName;
        ImageIcon CardImageIcon;
        Card(String cardName, ImageIcon cardImageIcon){
            this.cardName = cardName;
            this.CardImageIcon = cardImageIcon;
        }
        public String toString(){
            return cardName;
        }
    }

    String[] cardList = {
        "darkness",
        "double",
        "fairy",
        "fighting",
        "fire",
        "grass",
        "lightning",
        "metal",
        "psychic",
        "water"
    };

    int rows = 4;
    int columns = 5;
    int cardWidth = 90;
    int cardHieght = 128;

    ArrayList<Card> cardSet;
    ImageIcon cardBackImageIcon;
    
    int boardWidth = columns * cardWidth;
    int boardHeight = rows * cardHieght;

    JFrame frame = new JFrame("Pokemon Match cards");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartGamePanel = new JPanel();
    JPanel restartButton = new JPanel();



    int erroCount =7;
    ArrayList<JButton> board;
    Timer hideCardTimer;
    boolean gameReady = false;
    JButton cardOneSelected;
    JButton cardTwoSelected;

    MatchCards(){
        setUpCards();
        shuffleCards();

        // frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        textLabel.setHorizontalAlignment((JLabel.CENTER));
        textLabel.setText("Errors: "+ Integer.toString(erroCount));
        
        textPanel.setPreferredSize(new Dimension(boardWidth, 30));
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        board = new ArrayList<JButton>();
        boardPanel.setLayout(new GridLayout(rows, columns));
        for(int i = 0; i < cardSet.size(); i++ ){
            JButton title = new JButton();
            title.setPreferredSize(new Dimension(cardWidth, cardHieght));
            title.setOpaque(true);
            title.setIcon(cardSet.get(i).CardImageIcon);
            title.setFocusable(false);
            title.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(!gameReady){
                        return;
                    }
                    JButton title = (JButton) e.getSource();
                    if(title.getIcon() == cardBackImageIcon){
                        if(cardOneSelected == null){
                            cardOneSelected = title;
                            int index = board.indexOf(cardOneSelected);
                            cardOneSelected.setIcon(cardSet.get(index).CardImageIcon);
                        }
                        else if(cardTwoSelected == null){
                            cardTwoSelected = title;
                            int index = board.indexOf(cardTwoSelected);
                            cardTwoSelected.setIcon(cardSet.get(index).CardImageIcon);
                            
                            if(cardOneSelected.getIcon() != cardTwoSelected.getIcon()){
                                erroCount += 1;
                                hideCardTimer.start();
                            }
                            else{
                                cardOneSelected = null;
                                cardTwoSelected = null;
                            }
                        }
                    }                
                }
            });
            board.add(title);
            boardPanel.add(title);
            
        }

        frame.add(boardPanel);

        restartButton.setFont(new Font("Arial", Font.PLAIN, 16 ));
        restartButton.setPreferredSize(new Dimension(boardWidth, 30));
        restartButton.setFocusable(false);
        restartGamePanel.add(restartButton);
        frame.add(restartGamePanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        hideCardTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                hideCards();
            }
        });
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();
    }    

    void setUpCards(){
        cardSet = new ArrayList<Card>();
        for(String cardName : cardList){
            Image cardImg = new ImageIcon(getClass().getResource("./img/"+ cardName+ ".jpg")).getImage();
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardWidth, cardHieght, java.awt.Image.SCALE_SMOOTH));
       
            Card card = new Card(cardName, cardImageIcon);
            cardSet.add(card);
        }
        cardSet.addAll(cardSet);

    Image cardBackImg = new ImageIcon(getClass().getResource("./img/back.jpg")).getImage();
    cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHieght, java.awt.Image.SCALE_SMOOTH));
    }

    void shuffleCards(){
        System.out.println(cardSet);
        for(int i =0; i < cardSet.size(); i++){
            int j = (int)(Math.random() * cardSet.size());
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
         }
         System.out.println(cardSet);
    }

    void hideCards(){
        if(gameReady && cardOneSelected != null && cardTwoSelected != null){
            cardOneSelected.setIcon(cardBackImageIcon);
            cardOneSelected = null;
            cardTwoSelected.setIcon(cardBackImageIcon);
            cardTwoSelected = null;
        }
        else{
            for(int i = 0; i < board.size(); i++){
                board.get(i).setIcon(cardBackImageIcon);
            }
            gameReady = true;
        }
     
    }
} 
