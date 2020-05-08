// This class constructs Knight objects and provides properties accordingly
public class Knight extends Piece{
  
  public Knight(Player player, int x, int y) {
    super(player, x, y);
  }
  //Returns true if knight object can move to (x,y) on the board, returns false otherwise
  public boolean moveTo(int x, int y, Board board)
  {
    if((x == posX + 1 && y == posY +2) || (x == posX-1 && y == posY +2))
    {
      if(!board.isTherePiece(x,y))
      {
        board.movePiece(posX,posY,x,y);
        posX = x;
        posY = y;
        return true; 
      }
      else
      {
        Piece piece = board.getPiece(x,y);
        if(player.getColor().equals(piece.getPlayer().getColor()))
        {
          return false;
        }
        else
        {
          board.getPiece(x, y).getPlayer().removePiece(board.getPiece(x, y));
          board.movePiece(posX,posY,x,y);
          posX = x;
          posY = y;
          return true;
        }
      } 
      
    }
    else if((x == posX + 1 && y == posY -2) || (x == posX-1 && y == posY -2))
    {
      if(!board.isTherePiece(x,y))
      {
        board.movePiece(posX,posY,x,y);
        posX = x;
        posY = y;
        return true; 
      }
      else
      {
        Piece piece = board.getPiece(x,y);
        if(player.getColor().equals(piece.getPlayer().getColor()))
        {
          return false;
        }
        else
        {
          board.getPiece(x, y).getPlayer().removePiece(board.getPiece(x, y));
          board.movePiece(posX,posY,x,y);
          posX = x;
          posY = y;
          return true;
        }
      } 
    }
    else if((x == posX + 2 && y == posY + 1) || (x == posX + 2 && y == posY - 1))
    {
      if(!board.isTherePiece(x,y))
      {
        board.movePiece(posX,posY,x,y);
        posX = x;
        posY = y;
        return true; 
      }
      else
      {
        Piece piece = board.getPiece(x,y);
        if(player.getColor().equals(piece.getPlayer().getColor()))
        {
          return false;
        }
        else
        {
          board.getPiece(x, y).getPlayer().removePiece(board.getPiece(x, y));
          board.movePiece(posX,posY,x,y);
          posX = x;
          posY = y;
          return true;
        }
      } 
    }
    else if((x == posX - 2 && y == posY + 1) || (x == posX - 2 && y == posY - 1))
    {
      if(!board.isTherePiece(x,y))
      {
        board.movePiece(posX,posY,x,y);
        posX = x;
        posY = y;
        return true; 
      }
      else
      {
        Piece piece = board.getPiece(x,y);
        if(player.getColor().equals(piece.getPlayer().getColor()))
        {
          return false;
        }
        else
        {
          board.getPiece(x, y).getPlayer().removePiece(board.getPiece(x, y));
          board.movePiece(posX,posY,x,y);
          posX = x;
          posY = y;
          return true;
        }
      } 
    }
    return false;
    
  }
  
  public String toString()
  {
    if (player.getColor().equals("White"))
    {
      return "W/Knight";
    }
    else
    {
      return "B/Knight";
    }
  }
}