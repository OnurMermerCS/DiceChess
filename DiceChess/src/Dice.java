//This class creates die objects
public class Dice
{
  private int face;
  
  public Dice()
  {
    face = 1;
  }
  //rolls the die
  public void rollDice()
  {
    face = ((int)((Math.random() * 6) + 1));
  }
  
  public int getFace()
  {
    return face;
  }
}