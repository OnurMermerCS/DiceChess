//This all GUI part of the game
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame
{
  GridPanel board;
  DicePanel dicePanel;
  PlayerPanel playerPanel;
  TimerPanel timerPanel;
  
  static Board ground = new Board();
  static Player player1 = new Player(1);
  static Player player2 = new Player(2);
  static Dice dice1 = new Dice();
  static Dice dice2 = new Dice();
  static int playerCounter;
  static Timer timer;
  static int timePlayer1;
  static int timePlayer2;
  
  private static final int FRAME_WIDTH = 1200;
  private static final int FRAME_HEIGHT = 650;
  
  //displays the chess board
  public GameFrame()
  {
    createComponents();
    setSize(FRAME_WIDTH,FRAME_HEIGHT);
    setLocation(360, 215);
    setTitle("ChessGame");
  }
  
  //
  public GameFrame(int value)
  {
    createComponents();
    setSize(FRAME_WIDTH,FRAME_HEIGHT);
    setLocation(360, 215);
    setTitle("ChessGame");
    
    timer = new Timer();
    timePlayer1 = value * 60;
    timePlayer2 = value * 60;
    
    timerPanel = new TimerPanel();
    timerPanel.setBounds(800, 120, 200, 200);
    add(timerPanel);
    
    timer.schedule(new TimerTask() {
      public void run()
      {
        if (playerCounter % 2 == 0)
        {
          timePlayer1--;
        }
        else
        {
          timePlayer2--;
        }
        if (timePlayer1 == 0)
        {
          timer.cancel();
          new CheckMateFrame(player2, player1).setVisible(true);
          System.out.println("CHECK-MATE!");
          dispose();
          return;
        }
        if (timePlayer2 == 0)
        {
          timer.cancel();
          new CheckMateFrame(player1, player2).setVisible(true);
          System.out.println("CHECK-MATE!");
          dispose();
          return;
        }
        timerPanel.drawTimerPanel();
      }
    }, 1000, 1000);
  }
  
  //creates the board with the pieces
  private void createComponents()
  {
    playerCounter = 0;
    for (int i = 1; i < 9; i++)
    {
      player1.addPiece(new Pawn(player1, 2, i), i - 1);
      player2.addPiece(new Pawn(player2, 7, i), i - 1);
    }
    
    player1.addPiece(new Knight(player1, 1, 2), 8);
    player1.addPiece(new Knight(player1, 1, 7), 9);
    player2.addPiece(new Knight(player2, 8, 2), 8);
    player2.addPiece(new Knight(player2, 8, 7), 9);
    
    player1.addPiece(new Bishop(player1, 1, 3), 10);
    player1.addPiece(new Bishop(player1, 1, 6), 11);
    player2.addPiece(new Bishop(player2, 8, 3), 10);
    player2.addPiece(new Bishop(player2, 8, 6), 11);
    
    player1.addPiece(new Rook(player1, 1, 1), 12);
    player1.addPiece(new Rook(player1, 1, 8), 13);
    player2.addPiece(new Rook(player2, 8, 1), 12);
    player2.addPiece(new Rook(player2, 8, 8), 13);
    
    player1.addPiece(new Queen(player1, 1, 4), 14);
    player1.addPiece(new King(player1, 1, 5), 15);
    player2.addPiece(new Queen(player2, 8, 4), 14);
    player2.addPiece(new King(player2, 8, 5), 15);
    
    for (int i = 1; i < 17; i++)
    {
      ground.addPiece(player1.getAPiece(i - 1), player1.getAPiece(i - 1).getX(), player1.getAPiece(i - 1).getY());
      ground.addPiece(player2.getAPiece(i - 1), player2.getAPiece(i - 1).getX(), player2.getAPiece(i - 1).getY());
    }
    
    setLayout(null);
    board = new GridPanel();
    board.setBounds(0, 0, 600, 600);
    dicePanel = new DicePanel();
    dicePanel.setBounds(600, 0, 200, 100);
    playerPanel = new PlayerPanel();
    playerPanel.setBounds(600, 120, 200, 200);
    
    add(board);
    add(dicePanel);
    add(playerPanel);
  }
  
  //creates time panel for each player
  class TimerPanel extends JPanel
  {
    JLabel j1;
    JLabel j2;
    
    public TimerPanel()
    {
      j1 = new JLabel(Integer.toString(timePlayer1 / 60) + ":" + String.format("%02d", timePlayer1 % 60));
      j2 = new JLabel(Integer.toString(timePlayer2 / 60) + ":" + String.format("%02d", timePlayer2 % 60));
      
      setLayout(null);
      j1.setFont(new Font("Calibri", Font.BOLD, 30));
      j2.setFont(new Font("Calibri", Font.BOLD, 30));
      j1.setBounds(20, 0, 100, 60);
      j2.setBounds(20, 50, 100, 60);
      
      add(j1);
      add(j2);
      
      setSize(100, 50);
    }
    
    public void drawTimerPanel()
    {
      removeAll();
      
      j1 = new JLabel(Integer.toString(timePlayer1 / 60) + ":" + String.format("%02d", timePlayer1 % 60));
      j2 = new JLabel(Integer.toString(timePlayer2 / 60) + ":" + String.format("%02d", timePlayer2 % 60));
      
      j1.setFont(new Font("Calibri", Font.BOLD, 30));
      j2.setFont(new Font("Calibri", Font.BOLD, 30));
      j1.setBounds(20, 0, 100, 60);
      j2.setBounds(20, 50, 100, 60);
      
      add(j1);
      add(j2);
      
      validate();
      repaint();
    }
  }
  
  //creates Player panels with arrows pointing to the playing player
  class PlayerPanel extends JPanel
  {
    JLabel j1;
    JLabel j2;
    JLabel j3;
    ImageIcon imgArrowPointer = new ImageIcon(this.getClass().getResource("resources/arrowPointer.png"));
    
    public PlayerPanel()
    {
      j1 = new JLabel("Player1");
      j2 = new JLabel("Player2");
      j3 = new JLabel(imgArrowPointer);
      
      setLayout(null);
      j1.setFont(new Font("Calibri", Font.BOLD, 30));
      j2.setFont(new Font("Calibri", Font.BOLD, 30));
      j1.setBounds(20, 0, 100, 60);
      j2.setBounds(20, 50, 100, 60);
      j3.setBounds(100, 0, 100, 60);
      
      add(j1);
      add(j2);
      add(j3);
      
      setSize(100, 50);
    }
    
    public void drawPlayerPanel()
    {
      removeAll();
      
      j1 = new JLabel("Player1");
      j2 = new JLabel("Player2");
      j3 = new JLabel(imgArrowPointer);
      
      j1.setFont(new Font("Calibri", Font.BOLD, 30));
      j2.setFont(new Font("Calibri", Font.BOLD, 30));
      j1.setBounds(20, 0, 100, 60);
      j2.setBounds(20, 50, 100, 60);
      if (playerCounter % 2 == 0)
      {
        j3.setBounds(100, 0, 100, 60);
      }
      else
      {
        j3.setBounds(100, 50, 100, 60);
      }
      
      add(j1);
      add(j2);
      add(j3);
      
      validate();
      repaint();
    }
  }
  //creates dice panel 
  class DicePanel extends JPanel
  {
    int counter;
    JLabel j1;
    JLabel j2;
    JButton rollButton;
    ImageIcon img1 = new ImageIcon(this.getClass().getResource("resources/1.png"));
    ImageIcon img2 = new ImageIcon(this.getClass().getResource("resources/2.png"));
    ImageIcon img3 = new ImageIcon(this.getClass().getResource("resources/3.png"));
    ImageIcon img4 = new ImageIcon(this.getClass().getResource("resources/4.png"));
    ImageIcon img5 = new ImageIcon(this.getClass().getResource("resources/5.png"));
    ImageIcon img6 = new ImageIcon(this.getClass().getResource("resources/6.png"));
    
    public DicePanel(){
      counter = 0;
      j1 = new JLabel();
      j2 = new JLabel();
      rollButton = new JButton("Roll the Dice");
      
      if(dice1.getFace() == 1){
        
        j1.setIcon(img1);
      }
      else if(dice1.getFace() == 2){
        
        j1.setIcon(img2);
      }
      else if(dice1.getFace() == 3){
        
        j1.setIcon(img3);
      }
      else if(dice1.getFace() == 4){
        
        j1.setIcon(img4);
      }
      else if(dice1.getFace() == 5){
        
        j1.setIcon(img5);
      }
      else if(dice1.getFace() == 6){
        
        j1.setIcon(img6);
      }
      
      if(dice2.getFace() == 1){
        
        j2.setIcon(img1);
      }
      else if(dice2.getFace() == 2){
        
        j2.setIcon(img2);
      }
      else if(dice2.getFace() == 3){
        
        j2.setIcon(img3);
      }
      else if(dice2.getFace() == 4){
        
        j2.setIcon(img4);
      }
      else if(dice2.getFace() == 5){
        
        j2.setIcon(img5);
      }
      else if(dice2.getFace() == 6){
        
        j2.setIcon(img6);
      }
      
      ActionListener listener = new ClickListener();
      rollButton.addActionListener(listener);
      
      j1.setBounds(30, 30, 20, 20);
      j2.setBounds(60, 60, 20, 20);
      this.add(j1);
      this.add(j2);
      this.add(rollButton, BorderLayout.SOUTH);
      this.setSize(100, 200);
      
    }
    
    public void drawDicePanel()
    {
      removeAll();
      counter = 0;
      
      j1 = new JLabel();
      j2 = new JLabel();
      rollButton = new JButton("Roll the Dice");
      
      if(dice1.getFace() == 1){
        
        j1.setIcon(img1);
      }
      else if(dice1.getFace() == 2){
        
        j1.setIcon(img2);
      }
      else if(dice1.getFace() == 3){
        
        j1.setIcon(img3);
      }
      else if(dice1.getFace() == 4){
        
        j1.setIcon(img4);
      }
      else if(dice1.getFace() == 5){
        
        j1.setIcon(img5);
      }
      else if(dice1.getFace() == 6){
        
        j1.setIcon(img6);
      }
      
      if(dice2.getFace() == 1){
        
        j2.setIcon(img1);
      }
      else if(dice2.getFace() == 2){
        
        j2.setIcon(img2);
      }
      else if(dice2.getFace() == 3){
        
        j2.setIcon(img3);
      }
      else if(dice2.getFace() == 4){
        
        j2.setIcon(img4);
      }
      else if(dice2.getFace() == 5){
        
        j2.setIcon(img5);
      }
      else if(dice2.getFace() == 6){
        
        j2.setIcon(img6);
      }
      
      ActionListener listener = new ClickListener();
      rollButton.addActionListener(listener);
      
      j1.setBounds(30, 30, 20, 20);
      j2.setBounds(60, 60, 20, 20);
      
      add(j1);
      add(j2);
      add(rollButton, BorderLayout.SOUTH);
      validate();
      repaint();
    }
    //repaints the pice and player panel according to the dice roll result
    class ClickListener implements ActionListener
    {
      public void actionPerformed(ActionEvent event)
      {
        if (counter == 0)
        {
          dice1.rollDice();
          dice2.rollDice();
          
          if(dice1.getFace() == 1){
            
            j1.setIcon(img1);
          }
          else if(dice1.getFace() == 2){
            
            j1.setIcon(img2);
          }
          else if(dice1.getFace() == 3){
            
            j1.setIcon(img3);
          }
          else if(dice1.getFace() == 4){
            
            j1.setIcon(img4);
          }
          else if(dice1.getFace() == 5){
            
            j1.setIcon(img5);
          }
          else if(dice1.getFace() == 6){
            
            j1.setIcon(img6);
          }
          
          if(dice2.getFace() == 1){
            
            j2.setIcon(img1);
          }
          else if(dice2.getFace() == 2){
            
            j2.setIcon(img2);
          }
          else if(dice2.getFace() == 3){
            
            j2.setIcon(img3);
          }
          else if(dice2.getFace() == 4){
            
            j2.setIcon(img4);
          }
          else if(dice2.getFace() == 5){
            
            j2.setIcon(img5);
          }
          else if(dice2.getFace() == 6){
            
            j2.setIcon(img6);
          }
          if (playerCounter % 2 == 0)
          {
            if (Check.checkMoves(player1, ground, dice1, dice2))
            {
              rollButton.setText("Make a move");
              counter++;
            }
            else
            {
              dicePanel.counter += 2;
              playerCounter++;
              board.drawBoard();
              dicePanel.drawDicePanel();
              playerPanel.drawPlayerPanel();
            }
          }
          else
          {
            if (Check.checkMoves(player2, ground, dice1, dice2))
            {
              rollButton.setText("Make a move");
              counter++;
            }
            else
            {
              dicePanel.counter += 2;
              playerCounter++;
              board.drawBoard();
              dicePanel.drawDicePanel();
              playerPanel.drawPlayerPanel();
            }
          }
        }
      }
    }
  }
  //creates board panel
  class GridPanel extends JPanel
  {
    JPanel[][] cells = new JPanel[8][8];
    
    Piece piece;
    
    public GridPanel()
    {
      super(new GridLayout(8,8));
      setBackground(Color.white);
      this.setBounds(0,0,600,600);
      MouseListener listener = new ActionListener();
      this.addMouseListener(listener);
      
      for(int i = 8; i>0; i--)
      {
        for(int j = 1; j<9; j++)
        {
          cells[i - 1][j - 1] = new CellPanel(i, j, ground);
          if((i+j)%2 == 0)
          {
            cells[i - 1][j - 1].setBackground(Color.gray);
            add(cells[i - 1][j - 1]);
          }
          else
          {
            cells[i - 1][j - 1].setBackground(Color.white);
            add(cells[i - 1][j - 1]); 
          }
        }
      }
    }
    //cretes the cell panel which one the 64 cells on the board
    class CellPanel extends JPanel
    {
      ImageIcon iconWhitePawn = new ImageIcon(getClass().getResource("resources/whitePawn.png"));
      ImageIcon iconWhiteKnight = new ImageIcon(getClass().getResource("resources/whiteKnight.png"));
      ImageIcon iconWhiteBishop = new ImageIcon(getClass().getResource("resources/whiteBishop.png"));
      ImageIcon iconWhiteRook = new ImageIcon(getClass().getResource("resources/whiteRook.png"));
      ImageIcon iconWhiteQueen = new ImageIcon(getClass().getResource("resources/whiteQueen.png"));
      ImageIcon iconWhiteKing = new ImageIcon(getClass().getResource("resources/whiteKing.png"));
      ImageIcon iconBlackPawn = new ImageIcon(getClass().getResource("resources/blackPawn.png"));
      ImageIcon iconBlackKnight = new ImageIcon(getClass().getResource("resources/blackKnight.png"));
      ImageIcon iconBlackBishop = new ImageIcon(getClass().getResource("resources/blackBishop.png"));
      ImageIcon iconBlackRook = new ImageIcon(getClass().getResource("resources/blackRook.png"));
      ImageIcon iconBlackQueen = new ImageIcon(getClass().getResource("resources/blackQueen.png"));
      ImageIcon iconBlackKing = new ImageIcon(getClass().getResource("resources/blackKing.png"));
      
      public CellPanel(int x, int y, Board ground)
      {
        super();
        
        if (ground.isTherePiece(x, y))
        {
          if (ground.getPiece(x, y).getPlayer().getColor().equals("White"))
          {
            if (ground.getPiece(x, y) instanceof Pawn)
            {
              this.add(new JLabel(iconWhitePawn, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Knight)
            {
              this.add(new JLabel(iconWhiteKnight, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Bishop)
            {
              this.add(new JLabel(iconWhiteBishop, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Rook)
            {
              this.add(new JLabel(iconWhiteRook, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Queen)
            {
              this.add(new JLabel(iconWhiteQueen, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof King)
            {
              this.add(new JLabel(iconWhiteKing, JLabel.CENTER));
            }
          }
          else
          {
            if (ground.getPiece(x, y) instanceof Pawn)
            {
              this.add(new JLabel(iconBlackPawn, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Knight)
            {
              this.add(new JLabel(iconBlackKnight, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Bishop)
            {
              this.add(new JLabel(iconBlackBishop, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Rook)
            {
              this.add(new JLabel(iconBlackRook, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Queen)
            {
              this.add(new JLabel(iconBlackQueen, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof King)
            {
              this.add(new JLabel(iconBlackKing, JLabel.CENTER));
            }
          }
        }
      }
      
      public void drawCell(int x, int y, Board ground)
      {
        removeAll();
        
        if (ground.isTherePiece(x, y))
        {
          if (ground.getPiece(x, y).getPlayer().getColor().equals("White"))
          {
            if (ground.getPiece(x, y) instanceof Pawn)
            {
              this.add(new JLabel(iconWhitePawn, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Knight)
            {
              this.add(new JLabel(iconWhiteKnight, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Bishop)
            {
              this.add(new JLabel(iconWhiteBishop, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Rook)
            {
              this.add(new JLabel(iconWhiteRook, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Queen)
            {
              this.add(new JLabel(iconWhiteQueen, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof King)
            {
              this.add(new JLabel(iconWhiteKing, JLabel.CENTER));
            }
          }
          else
          {
            if (ground.getPiece(x, y) instanceof Pawn)
            {
              this.add(new JLabel(iconBlackPawn, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Knight)
            {
              this.add(new JLabel(iconBlackKnight, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Bishop)
            {
              this.add(new JLabel(iconBlackBishop, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Rook)
            {
              this.add(new JLabel(iconBlackRook, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof Queen)
            {
              this.add(new JLabel(iconBlackQueen, JLabel.CENTER));
            }
            else if (ground.getPiece(x, y) instanceof King)
            {
              this.add(new JLabel(iconBlackKing, JLabel.CENTER));
            }
          }
        }
        
        validate();
        repaint();
      }
    }
    
    public int getX(int x)
    {
      if(x <=75)
        return 8;
      else if(x <= 150)
        return 7;
      else if(x <= 225)
        return 6;
      else if(x <= 300)
        return 5; 
      else if(x <= 375)
        return 4;
      else if(x <= 450)
        return 3;
      else if(x <= 525)
        return 2;
      else
        return 1;
    }
    
    public int getY(int y)
    {
      if(y <=75)
        return 1;
      else if(y <= 150)
        return 2;
      else if(y <= 225)
        return 3;
      else if(y <= 300)
        return 4; 
      else if(y <= 375)
        return 5;
      else if(y <= 450)
        return 6;
      else if(y <= 525)
        return 7;
      else
        return 8;
    }
    
    public void drawBoard()
    {
      removeAll();
      
      for(int i = 8; i>0; i--)
      {
        for(int j = 1; j<9; j++)
        {
          ((CellPanel)cells[i - 1][j - 1]).drawCell(i, j, ground);
          if((i+j)%2 == 0)
          {
            add(cells[i - 1][j - 1]);
          }
          else
          {
            add(cells[i - 1][j - 1]); 
          }
        }
      }
      
      validate();
      repaint();
    }
    
    //repaints the board panel according to the player decisions 
    private class ActionListener implements MouseListener
    {
      public void mouseClicked(MouseEvent event)
      {
        if (dicePanel.counter == 1)
        {
          if (playerCounter % 2 == 0)
          {
            System.out.println(getX(event.getY()) + " - " + getY(event.getX()));
            if (piece instanceof Piece)
            {
              if (Check.checkChecks(player1, ground))
              {
                int k = piece.getX();
                int l = piece.getY();
                Piece temp = null;
                if (ground.isTherePiece(getX(event.getY()), getY(event.getX())))
                {
                  temp = ground.getPiece(getX(event.getY()), getY(event.getX()));
                }
                boolean foundPlace = false;
                if (piece.moveTo(getX(event.getY()), getY(event.getX()), ground))
                {
                  if (Check.checkChecks(player1, ground))
                  {
                    ground.movePiece(getX(event.getY()), getY(event.getX()), k, l);
                    piece.setPos(k, l);
                    if (temp != null)
                    {
                      for (int i = 0; i < 16 && !foundPlace; i++)
                      {
                        if (player2.getAPiece(i) == null)
                        {
                          player2.addPiece(temp, i);
                          ground.addPiece(temp, temp.getX(), temp.getY());
                          foundPlace = true;
                        }
                      }
                    }
                    System.out.println("Hareket baþarýsýz");
                  }
                  else
                  {
                    System.out.println("Hareket baþarýlý");
                    dicePanel.counter++;
                    playerCounter++;
                    if (player2.getAPiece(15) == null)
                    {
                      if (timer != null)
                      {
                        timer.cancel(); 
                      }
                      new CheckMateFrame(player1, player2).setVisible(true);
                      System.out.println("CHECK-MATE!");
                      dispose();
                      return;
                    }
                    if (Check.checkChecks(player1, ground))
                    {
                      new CheckFrame(player2, player1).setVisible(true);
                      System.out.println("CHECK!");
                    }
                    if (Check.checkChecks(player2, ground))
                    {
                      new CheckFrame(player1, player2).setVisible(true);
                      System.out.println("CHECK!");
                    }
                    for (int i = 0; i < 8; i++)
                    {
                      if (player1.getAPiece(i) instanceof Pawn)
                      {
                        if (((Pawn)player1.getAPiece(i)).getPromotion())
                        {
                          new PromotionFrame(player1.getAPiece(i)).setVisible(true);
                        }
                      }
                      
                      if (player2.getAPiece(i) instanceof Pawn)
                      {
                        ((Pawn)player2.getAPiece(i)).resetEnPassant();
                      }
                    }
                    board.drawBoard();
                    dicePanel.drawDicePanel();
                    playerPanel.drawPlayerPanel();
                  }
                }
                else
                {
                  System.out.println("Hareket baþarýsýz");
                }
                piece = null;
                System.out.println("Taþ býrakýldý.");
              }
              else
              {
                if (piece.moveTo(getX(event.getY()), getY(event.getX()), ground))
                {
                  System.out.println("Hareket baþarýlý");
                  dicePanel.counter++;
                  playerCounter++;
                  if (player2.getAPiece(15) == null)
                  {
                    if (timer != null)
                    {
                      timer.cancel(); 
                    }
                    new CheckMateFrame(player1, player2).setVisible(true);
                    System.out.println("CHECK-MATE!");
                    dispose();
                    return;
                  }
                  if (Check.checkChecks(player1, ground))
                  {
                    new CheckFrame(player2, player1).setVisible(true);
                    System.out.println("CHECK!");
                  }
                  if (Check.checkChecks(player2, ground))
                  {
                    new CheckFrame(player1, player2).setVisible(true);
                    System.out.println("CHECK!");
                  }
                  for (int i = 0; i < 8; i++)
                    {
                      if (player1.getAPiece(i) instanceof Pawn)
                      {
                        if (((Pawn)player1.getAPiece(i)).getPromotion())
                        {
                          new PromotionFrame(player1.getAPiece(i)).setVisible(true);
                        }
                      }
                      
                      if (player2.getAPiece(i) instanceof Pawn)
                      {
                        ((Pawn)player2.getAPiece(i)).resetEnPassant();
                      }
                    }
                  board.drawBoard();
                  dicePanel.drawDicePanel();
                  playerPanel.drawPlayerPanel();
                }
                else
                {
                  System.out.println("Hareket baþarýsýz");
                }
                piece = null;
                System.out.println("Taþ býrakýldý.");
              }
            }
            else if(ground.isTherePiece(getX(event.getY()), getY(event.getX())))
            {
              if (ground.getPiece(getX(event.getY()), getY(event.getX())).getPlayer().getColor().equals("White"))
              {
                if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Pawn && (dice1.getFace() == 1 || dice2.getFace() == 1 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Knight && (dice1.getFace() == 2 || dice2.getFace() == 2 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Bishop && (dice1.getFace() == 3 || dice2.getFace() == 3 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Rook && (dice1.getFace() == 4 || dice2.getFace() == 4 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Queen && (dice1.getFace() == 5 || dice2.getFace() == 5 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof King && (dice1.getFace() == 6 || dice2.getFace() == 6 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
              }
            }
          }
          else
          {
            System.out.println(getX(event.getY()) + " - " + getY(event.getX()));
            if (piece instanceof Piece)
            {
              if (Check.checkChecks(player2, ground))
              {
                int k = piece.getX();
                int l = piece.getY();
                Piece temp = null;
                if (ground.isTherePiece(getX(event.getY()), getY(event.getX())))
                {
                  temp = ground.getPiece(getX(event.getY()), getY(event.getX()));
                }
                boolean foundPlace = false;
                if (piece.moveTo(getX(event.getY()), getY(event.getX()), ground))
                {
                  if (Check.checkChecks(player2, ground))
                  {
                    ground.movePiece(getX(event.getY()), getY(event.getX()), k, l);
                    piece.setPos(k, l);
                    if (temp != null)
                    {
                      for (int i = 0; i < 16 && !foundPlace; i++)
                      {
                        if (player1.getAPiece(i) == null)
                        {
                          player1.addPiece(temp, i);
                          ground.addPiece(temp, temp.getX(), temp.getY());
                          foundPlace = true;
                        }
                      }
                    }
                    System.out.println("Hareket baþarýsýz");
                  }
                  else
                  {
                    System.out.println("Hareket baþarýlý");
                    dicePanel.counter++;
                    playerCounter++;
                    if (player1.getAPiece(15) == null)
                    {
                      if (timer != null)
                      {
                        timer.cancel(); 
                      }
                      new CheckMateFrame(player2, player1).setVisible(true);
                      System.out.println("CHECK-MATE!");
                      dispose();
                      return;
                    }
                    if (Check.checkChecks(player1, ground))
                    {
                      new CheckFrame(player2, player1).setVisible(true);
                      System.out.println("CHECK!");
                    }
                    if (Check.checkChecks(player2, ground))
                    {
                      new CheckFrame(player1, player2).setVisible(true);
                      System.out.println("CHECK!");
                    }
                    for (int i = 0; i < 8; i++)
                    {
                      if (player2.getAPiece(i) instanceof Pawn)
                      {
                        if (((Pawn)player2.getAPiece(i)).getPromotion())
                        {
                          new PromotionFrame(player2.getAPiece(i)).setVisible(true);
                        }
                      }
                      
                      if (player1.getAPiece(i) instanceof Pawn)
                      {
                        ((Pawn)player1.getAPiece(i)).resetEnPassant();
                      }
                    }
                    board.drawBoard();
                    dicePanel.drawDicePanel();
                    playerPanel.drawPlayerPanel();
                  }
                }
                else
                {
                  System.out.println("Hareket baþarýsýz");
                }
                piece = null;
                System.out.println("Taþ býrakýldý.");
              }
              else
              {
                if (piece.moveTo(getX(event.getY()), getY(event.getX()), ground))
                {
                  System.out.println("Hareket baþarýlý");
                  dicePanel.counter++;
                  playerCounter++;
                  if (player1.getAPiece(15) == null)
                  {
                    if (timer != null)
                    {
                      timer.cancel(); 
                    }
                    new CheckMateFrame(player2, player1).setVisible(true);
                    System.out.println("CHECK-MATE!");
                    dispose();
                    return;
                  }
                  if (Check.checkChecks(player1, ground))
                  {
                    new CheckFrame(player2, player1).setVisible(true);
                    System.out.println("CHECK!");
                  }
                  if (Check.checkChecks(player2, ground))
                  {
                    new CheckFrame(player1, player2).setVisible(true);
                    System.out.println("CHECK!");
                  }
                  for (int i = 0; i < 8; i++)
                  {
                    if (player2.getAPiece(i) instanceof Pawn)
                    {
                      if (((Pawn)player2.getAPiece(i)).getPromotion())
                      {
                        new PromotionFrame(player2.getAPiece(i)).setVisible(true);
                      }
                    }
                    
                    if (player1.getAPiece(i) instanceof Pawn)
                    {
                      ((Pawn)player1.getAPiece(i)).resetEnPassant();
                    }
                  }
                  board.drawBoard();
                  dicePanel.drawDicePanel();
                  playerPanel.drawPlayerPanel();
                }
                else
                {
                  System.out.println("Hareket baþarýsýz");
                }
                piece = null;
                System.out.println("Taþ býrakýldý.");
              }
            }
            else if(ground.isTherePiece(getX(event.getY()), getY(event.getX())))
            {
              if (ground.getPiece(getX(event.getY()), getY(event.getX())).getPlayer().getColor().equals("Black"))
              {
                if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Pawn && (dice1.getFace() == 1 || dice2.getFace() == 1 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Knight && (dice1.getFace() == 2 || dice2.getFace() == 2 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Bishop && (dice1.getFace() == 3 || dice2.getFace() == 3 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Rook && (dice1.getFace() == 4 || dice2.getFace() == 4 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof Queen && (dice1.getFace() == 5 || dice2.getFace() == 5 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
                else if (ground.getPiece(getX(event.getY()), getY(event.getX())) instanceof King && (dice1.getFace() == 6 || dice2.getFace() == 6 || dice1.getFace() == dice2.getFace()))
                {
                  piece = ground.getPiece(getX(event.getY()), getY(event.getX()));
                  System.out.println("Taþ seçildi.");
                }
              }
            }
          }
        }
        System.out.println("Loop dýþý.");
        System.out.println(ground);
      }
      
      public void mouseExited(MouseEvent event)
      {
        return;
      }
      
      public void mouseEntered(MouseEvent event)
      {
        return;
      }
      
      public void mousePressed(MouseEvent event)
      {
        return;
      }
      
      public void mouseReleased(MouseEvent event)
      {
        return;
      }
    }
  }
  //displayes when king is threatened by other player
  class CheckFrame extends JFrame
  {
    JLabel label;
    JButton button;
    
    public CheckFrame(Player player1, Player player2)
    {
      label = new JLabel(player1 + " has threatened " + player2 + "'s King!");
      button = new JButton("Ok");
      
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        {
          dispose();
        }
      });
      
      setLayout(null);
      label.setBounds(40, 0, 220, 100);
      button.setBounds(100, 100, 100, 50);
      
      add(label);
      add(button);
      setSize(300, 200);
      setLocation(810, 440);
      setTitle("Check!");
    }
  }
  //displays when game ends
  class CheckMateFrame extends JFrame
  {
    JLabel label;
    JButton button;
    
    public CheckMateFrame(Player player3, Player player4)
    {
      label = new JLabel(player3 + " has won against " + player4 + "!");
      button = new JButton("Ok");
      
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        {
          ground = new Board();
          player1 = new Player(1);
          player2 = new Player(2);
          
          for (int i = 1; i < 9; i++)
          {
            player1.addPiece(new Pawn(player1, 2, i), i - 1);
            player2.addPiece(new Pawn(player2, 7, i), i - 1);
          }
          
          player1.addPiece(new Knight(player1, 1, 2), 8);
          player1.addPiece(new Knight(player1, 1, 7), 9);
          player2.addPiece(new Knight(player2, 8, 2), 8);
          player2.addPiece(new Knight(player2, 8, 7), 9);
          
          player1.addPiece(new Bishop(player1, 1, 3), 10);
          player1.addPiece(new Bishop(player1, 1, 6), 11);
          player2.addPiece(new Bishop(player2, 8, 3), 10);
          player2.addPiece(new Bishop(player2, 8, 6), 11);
          
          player1.addPiece(new Rook(player1, 1, 1), 12);
          player1.addPiece(new Rook(player1, 1, 8), 13);
          player2.addPiece(new Rook(player2, 8, 1), 12);
          player2.addPiece(new Rook(player2, 8, 8), 13);
          
          player1.addPiece(new Queen(player1, 1, 4), 14);
          player1.addPiece(new King(player1, 1, 5), 15);
          player2.addPiece(new Queen(player2, 8, 4), 14);
          player2.addPiece(new King(player2, 8, 5), 15);
          
          for (int i = 1; i < 17; i++)
          {
            ground.addPiece(player1.getAPiece(i - 1), player1.getAPiece(i - 1).getX(), player1.getAPiece(i - 1).getY());
            ground.addPiece(player2.getAPiece(i - 1), player2.getAPiece(i - 1).getX(), player2.getAPiece(i - 1).getY());
          }
          
          dispose();
          new MainFrame().setVisible(true);
        }
      });
      
      setLayout(null);
      label.setBounds(40, 0, 220, 100);
      button.setBounds(100, 100, 100, 50);
      
      add(label);
      add(button);
      setSize(300, 200);
      setLocation(810, 440); 
      setTitle("CheckMate!");
    }
  }
  //shows when promotion property is available
  class PromotionFrame extends JFrame
  {
    Piece piece;
    JPanel panel;
    JLabel label;
    JButton rookButton;
    JButton queenButton;
    JButton bishopButton;
    JButton knightButton;
    
    
    //Contructor
    public PromotionFrame(Piece piece)
    {
      this.piece = piece;
      setSize(515,250);
      setLocation(710, 290);
      setLayout(null);
      setTitle("Promotion!");
      
      panel = new JPanel();
      label = new JLabel("Please select a Piece to promote from Pawn.");
      queenButton = new JButton("QUEEN");
      rookButton = new JButton("ROOK");
      bishopButton = new JButton("BISHOP");
      knightButton = new JButton("KNIGHT");
      ActionListener listener1 = new ClickListenerQueen();
      queenButton.addActionListener(listener1);
      ActionListener listener2 = new ClickListenerRook();
      rookButton.addActionListener(listener2);
      ActionListener listener3 = new ClickListenerBishop();
      bishopButton.addActionListener(listener3);
      ActionListener listener4 = new ClickListenerKnight();
      knightButton.addActionListener(listener4);
      
      panel.setBounds(0, 0, 500, 200);
      panel.setLayout(null);
      
      label.setBounds(125, 25, 275, 50);
      queenButton.setBounds(20, 100, 85, 50);
      rookButton.setBounds(145, 100, 85, 50);
      bishopButton.setBounds(270, 100, 85, 50);
      knightButton.setBounds(395, 100, 85, 50);
      
      panel.add(label);
      panel.add(queenButton);
      panel.add(rookButton);
      panel.add(bishopButton);
      panel.add(knightButton);
      
      add(panel);
      
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
    }
    
    class ClickListenerQueen implements ActionListener
    {
      public void actionPerformed(ActionEvent event)
      {
        int x = piece.getX();
        int y = piece.getY();
        ground.removePiece(x,y);
        piece.getPlayer().removePiece(piece);
        
        for(int i = 0; i <= 7; i++)
        {
          if(piece.getPlayer().getAPiece(i) == null)
          {
            piece.getPlayer().addPiece(new Queen(piece.getPlayer(), x, y), i);
            ground.addPiece(piece.getPlayer().getAPiece(i),x,y);
            board.drawBoard();
            dispose();
            return;
          }
        }
      }
    }
    
    class ClickListenerRook implements ActionListener
    {
      public void actionPerformed(ActionEvent event)
      {
        int x = piece.getX();
        int y = piece.getY();
        ground.removePiece(x,y);
        piece.getPlayer().removePiece(piece);
        
        for(int i = 0; i <= 7; i++)
        {
          if(piece.getPlayer().getAPiece(i) == null)
          {
            piece.getPlayer().addPiece(new Rook(piece.getPlayer(), x, y), i);
            ground.addPiece(piece.getPlayer().getAPiece(i),x,y);
            board.drawBoard();
            dispose();
            return;
          }
        }
      }
    }
    
    class ClickListenerBishop implements ActionListener
    {
      public void actionPerformed(ActionEvent event)
      {
        int x = piece.getX();
        int y = piece.getY();
        ground.removePiece(x,y);
        piece.getPlayer().removePiece(piece);
        
        for(int i = 0; i <= 7; i++)
        {
          if(piece.getPlayer().getAPiece(i) == null)
          {
            piece.getPlayer().addPiece(new Bishop(piece.getPlayer(), x, y), i);
            ground.addPiece(piece.getPlayer().getAPiece(i),x,y);
            board.drawBoard();
            dispose();
            return;
          }
        }
      }
    }
    
    class ClickListenerKnight implements ActionListener
    {
      public void actionPerformed(ActionEvent event)
      {
        int x = piece.getX();
        int y = piece.getY();
        ground.removePiece(x,y);
        piece.getPlayer().removePiece(piece);
        
        for(int i = 0; i <= 7; i++)
        {
          if(piece.getPlayer().getAPiece(i) == null)
          {
            piece.getPlayer().addPiece(new Knight(piece.getPlayer(), x, y), i);
            ground.addPiece(piece.getPlayer().getAPiece(i),x,y);
            board.drawBoard();
            dispose();
            return;
          }
        }
      }
    } 
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new GameFrame(1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}