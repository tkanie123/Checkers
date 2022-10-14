/** Created by Tiffanie Ku and +1 other
  * This project simulates a game of checkers 
  */

import java.util.*;

class Main {
	public static void main(String[] args) {
		
		/** Gets player1 name
		  *
			* @param namePicked name of player1
			*/

		String namePicked;
		Scanner nameScanner = new Scanner(System.in);
		do 
		{
			System.out.println("Enter Player 1:");
			namePicked = nameScanner.nextLine();
		} 
		while (!(namePicked.length() >= 2));
		
		/** Gets player2 name
		  *
			* @param namePickedTwo name of player2
			*/

		String namePickedTwo;
		Scanner nameScannerTwo = new Scanner(System.in);
		do 
		{
			System.out.println("Enter Player 2:");
			namePickedTwo = nameScannerTwo.nextLine();
		} 
		while (!(namePickedTwo.length() >= 2));
		
		/** Sets wager for gambling
			*
			* @param max line 42 determines upper bound for amount of money players start with
			* @param max line 51 determines upper bound for random wager amount set by God
			*/
    
		ArrayList<Player> list = new ArrayList<Player>();

		double max = 100;
    Player PlayerOne = new Player(namePicked, (Math.round(Math.random() * max * 100) / 100.0), 0);
		Player PlayerTwo = new Player(namePickedTwo, (Math.round(Math.random() * max * 100) / 100.0), 0);

    list.add(PlayerOne);
    list.add(PlayerTwo);

		/** Introduction
		  *
		  * @param turns players have to "eat" as many of the opponent's pieces as they can in the given amount of turns
		  */
		int turns = (10 + ((int)(10 * Math.random() )));
		System.out.println("Welcome to Checkers, " + PlayerOne.getName() + " and " + PlayerTwo.getName() + ". \n"
			+ PlayerOne.getName() + ", you have $" + PlayerOne.getMoney() + " and " + PlayerTwo.getName() + ", you have $"
			+ PlayerTwo.getMoney()+ "\nThere will also be " + turns+ " turns, please try to take as much pieces as you can.");
    
		max = 0;
		if (PlayerOne.getMoney() < PlayerTwo.getMoney()) 
		{
			max = PlayerOne.getMoney();
		} 
		else 
		{
			max = PlayerTwo.getMoney();
		}
		double wager = Math.round(Math.random() * max * 100) / 100.0;

		System.out.println("God has decided the wager to be: $" + wager + ".");

		PlayerOne.setGamble(wager);
		PlayerTwo.setGamble(wager);

		/** Initialize the arrays and board */

		String[][] board = new String[9][9];
		String[] alpha = { "A", "B", "C", "D", "E", "F", "G", "H" };
		String[] numbers = { "  1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 " };

		int holdIndex = 0;
		int holdRow = 0;
		for (int row = 0; row < board.length; row++) 
		{
			for (int column = 0; column < board[row].length; column++) 
			{
				if (column == 0 && row == 0) {
					board[row][column] = "";
				} 
				else if (column == 0 && row > 0) 
				{
					board[row][column] = alpha[holdIndex];
					holdIndex++;
				} 
				else if (row == 0 && column > 0) 
				{
					board[row][column] = numbers[holdRow];
					holdRow++;
				} 
				else if (row % 2 == 0) 
				{
					if (column % 2 == 0 && column > 0 || row > 3 && row < 6 && column > 0) 
					{
						board[row][column] = "[ ]";
					} 
					else if (row > 4 && column > 0) 
					{
						board[row][column] = "[e]";
					} 
					else 
					{
						board[row][column] = "[o]";
					}
				} 
				else if (row % 2 == 1) 
				{
					if (column % 2 == 1 || row > 3 && row < 6) 
					{
						board[row][column] = "[ ]";
					} 
					else if (row > 4) 
					{
						board[row][column] = "[e]";
					} 
					else 
					{
						board[row][column] = "[o]";
					}
				}
				
				System.out.print(board[row][column]);
			}
			System.out.println();
		}
		
    for(int i = 0;i<turns;i++)
    {
		System.out.println("Turn: " + (i +1) + " of "+ turns);	
		movePart1(board, alpha, numbers, PlayerOne, PlayerTwo);
		movePartO(board, alpha, numbers, PlayerOne, PlayerTwo);
    }
		
    if(PlayerOne.getScore()> PlayerTwo.getScore() )
    {
      System.out.println(PlayerOne.getName() + " wins!");
      PlayerOne.gainMoney(wager);
			PlayerTwo.loseMoney(wager);
			
			System.out.println(PlayerOne.getName() + "'s Money: $" + PlayerOne.getMoney() + "\n" + PlayerTwo.getName() +"'s Money: $" + PlayerTwo.getMoney());
    }
    else if(PlayerOne.getScore()< PlayerTwo.getScore() )
    {
      System.out.println(PlayerTwo.getName() + " wins!");
      PlayerTwo.gainMoney(wager);
      PlayerOne.loseMoney(wager);
      System.out.println(PlayerTwo.getName()+  "'s Money: $" + PlayerTwo.getMoney() + "\n" + PlayerOne.getName() +"'s Money: $" + PlayerOne.getMoney());
    }
    else
    {
      System.out.println("TIE GAME");
    }

		/** Column major */
    System.out.println("\nBoard using Column Major");
    for (int column = 0; column < board[0].length; column++) 
		{
			for (int row = 0; row < board.length; row++) 
			{
        System.out.print(board[column][row]);
      }
      System.out.println();
    }
	}

	/** Method to move the "e" pieces */

	public static void movePart1(String[][] board, String[] alpha, String[] numbers, Player PlayerOne, Player PlayerTwo) 
	{
		boolean playerOneTurn = true;
		String checkerOne = "  ";
		Scanner checkerScanner = new Scanner(System.in);
		do 
		{
			System.out.println(PlayerOne.getName() + ", Please choose a (e) piece to move:");
			checkerOne = checkerScanner.nextLine();

			/** Checks if input for (e) piece has a char length of 2 and that the first char is between A-H and the second between 0-8 */
			if (checkerOne.length() == 2 && checkerOne.charAt(0) >= 'A' && checkerOne.charAt(0) <= 'H' && checkerOne.charAt(1) > '0' && checkerOne.charAt(1) <= '8') 
			{
				String l = "";
				int n = 0;
				l = String.valueOf(checkerOne.charAt(0));
				n = Character.getNumericValue(checkerOne.charAt(1));

				int alphaIndex = 0;
				for (int i = 0; i < alpha.length; i++) 
				{
					if (l.equals(alpha[i])) 
					{
						alphaIndex = i;
					}
				}
				
				if (board[alphaIndex + 1][n].equals("[ ]") 
					|| board[alphaIndex + 1][n].equals("[o]")
					|| (n == 1 && board[alphaIndex][n + 1].equals("[e]")) 
					|| (n == 8 && board[alphaIndex][n - 1].equals("[e]"))
					|| (board[alphaIndex][n - 1].equals("[e]") && board[alphaIndex][n + 1].equals("[e]")
          // TWO Os LEFT ONE E RIGHT
					|| (n>2 && n <8 && board[alphaIndex][n - 1].equals("[o]") && board[alphaIndex-1][n-2].equals("[o]") && board[alphaIndex][n + 1].equals("[e]"))
          // TWO OS RIGHT ONE E LEFT
					|| (n>1 && n <7 && board[alphaIndex][n-1].equals("[e]") && board[alphaIndex][n + 1].equals("[o]") && board[alphaIndex - 1][n+2].equals("[o]"))
          || (n == 2 && board[alphaIndex][n-1].equals("[o]") && board[alphaIndex][n+1].equals("[e]")
          || (n == 7 && board[alphaIndex][n-1].equals("[e]") && board[alphaIndex][n+1].equals("[e]")
          ))))
				{
					movePart1(board, alpha, numbers, PlayerOne, PlayerTwo);
				} 
				else 
				{
					movePiece1(checkerOne, alpha, numbers, board, playerOneTurn, PlayerOne, PlayerTwo);
				}
			}
		} 
		while ( (checkerOne.length()!=2) || checkerOne.charAt(0) < 'A' || checkerOne.charAt(0) > 'H' || checkerOne.charAt(1) < '1' || checkerOne.charAt(1) > '8');
	}

	public static void movePartO(String[][] board, String[] alpha, String[] numbers, Player PlayerOne, Player PlayerTwo) 
	{
		String checkerOne = "  ";
		Scanner checkerScanner = new Scanner(System.in);
		do 
		{
			System.out.println(PlayerTwo.getName() + ", Please choose an (o) piece to move:");
			checkerOne = checkerScanner.nextLine();
			
			if (checkerOne.length() == 2 && checkerOne.charAt(0) >= 'A' && checkerOne.charAt(0) <= 'H' && checkerOne.charAt(1) >= '0' && checkerOne.charAt(1) <= '8') 
			{
				String l = "";
				int n = 0;
				l = String.valueOf(checkerOne.charAt(0));
				n = Character.getNumericValue(checkerOne.charAt(1));
				int alphaIndex = 0;
				for (int i = 0; i < alpha.length; i++) 
				{
					if (l.equals(alpha[i])) 
					{
						alphaIndex = i;
					}
				}
        if (alphaIndex ==7)
        {
					movePartO(board, alpha, numbers, PlayerOne, PlayerTwo);
				} 
      	else if (board[alphaIndex + 1][n].equals("[ ]") 
				|| board[alphaIndex + 1][n].equals("[e]")
						|| (n == 1 && board[alphaIndex + 2][n + 1].equals("[o]"))
						|| (n == 8 && board[alphaIndex + 2][n - 1].equals("[o]"))
						|| (board[alphaIndex + 2][n - 1].equals("[o]") && board[alphaIndex + 2][n + 1].equals("[o]") 
						|| (n>2 && n <8 && board[alphaIndex + 2][n - 1].equals("[e]") && board[alphaIndex + 3][n - 2].equals("[e]") && board[alphaIndex + 2][n + 1].equals("[o]")
            
            || (n>1 && n <7 && board[alphaIndex + 2][n - 1].equals("[o]") && board[alphaIndex + 2][n+1].equals("[e]") && board[alphaIndex + 3][n + 2].equals("[e]")
            || (n == 2 && board[alphaIndex + 2][n - 1].equals("[e]") && board[alphaIndex + 2][n + 1].equals("[o]"))
						|| (n == 7 && board[alphaIndex + 2][n - 1].equals("[o]") && board[alphaIndex + 2][n + 1].equals("[e]"))
						))))
				{
					movePartO(board, alpha, numbers, PlayerOne, PlayerTwo);
				} 
				else 
				{
					boolean playerOneTurn = false;
					movePiece1(checkerOne, alpha, numbers, board, playerOneTurn,PlayerOne,PlayerTwo);
				}
			}
		} 
		while ( (!(checkerOne.length()==2)) || checkerOne.charAt(0) < 'A' || checkerOne.charAt(0) > 'H' || checkerOne.charAt(1) < '1'	|| checkerOne.charAt(1) > '8');
	}

	public static void movePiece1(String location, String[] alpha, String[] numbers, String[][] board,boolean turn, Player PlayerOne, Player PlayerTwo) 
	{
		boolean jumpLeft = false;
		String firstMove = "";
		String secondMove = "";
		String checkerTwo = "  ";
		String l = "";
		int n = 0;
		l = String.valueOf(location.charAt(0));
		n = Character.getNumericValue(location.charAt(1));
		int alphaIndex = 0;
		for (int i = 0; i < alpha.length; i++) 
		{
			if (l.equals(alpha[i])) 
			{
				alphaIndex = i;
			}
		}
		if (alphaIndex > 0 && board[alphaIndex + 1][n].equals("[e]") && turn == true) 
		{
			if (n == 1 && board[alphaIndex][n + 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex - 1] + numbers[n];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove);
			} 

		/** (e) single right jump from 1*/
      else if (n == 1 && board[alphaIndex][n + 1].equals("[o]") && board[alphaIndex-1][n+2].equals("[ ]"))  
			{
				firstMove = "" + alpha[alphaIndex - 2] + numbers[n+1];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove);
			} 

			else if (n == 8 && board[alphaIndex][n - 1].equals("[ ]"))
			{
				firstMove = "" + alpha[alphaIndex - 1] + numbers[n - 2];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove);
			} 

		/** (e) single left jump from 8*/
      else if (n == 8 && board[alphaIndex][n - 1].equals("[o]") && board[alphaIndex-1][n- 2].equals("[ ]"))
			{
				firstMove = "" + alpha[alphaIndex - 2] + numbers[n - 3];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove);
			} 

			else if (board[alphaIndex][n - 1].equals("[ ]") && board[alphaIndex][n + 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex - 1] + numbers[n - 2];
				secondMove = "" + alpha[alphaIndex - 1] + numbers[n];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove + ", " + secondMove);
			}
      else if (  ((alphaIndex > 1)  &&  (!(n > 6 || n < 3 ))  )
      && (  board[alphaIndex -1][n + 2].equals("[ ]")
			&& board[alphaIndex-1][n - 2].equals("[ ]") 
			&& board[alphaIndex ][n + 1].equals("[o]") 
			&& board[alphaIndex ][n - 1].equals("[o]")))  
			{ 
				firstMove = "" + alpha[alphaIndex-2] + numbers[n - 3];
				secondMove = "" + alpha[alphaIndex -2] + numbers[n+1];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove + ", " + secondMove);
			} 

		/** Single & Left Jump for E**/
			else if ((( ( (alphaIndex>1)  && (!(n>7) && (!(n<3))))
			&& board[alphaIndex][n-1].equals("[o]"))
			&& board[alphaIndex-1][n-2].equals("[ ]") 
			&& board[alphaIndex][n+1].equals("[ ]")))
			{
				firstMove = "" + alpha[alphaIndex-2] + numbers[n-3];
				secondMove = "" + alpha[alphaIndex-1] + numbers[n];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove + ", or move to: " + secondMove);
			} 

		/** Single & Right Jump for E**/
      else if (( (alphaIndex>1  )&&(!(n<2) && !(n>6)) 
      && board[alphaIndex][n+1].equals("[o]"))
			&& board[alphaIndex-1][n+2].equals("[ ]") 
			&& board[alphaIndex][n-1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex-2] + numbers[n+1];
				secondMove = "" + alpha[alphaIndex-1] + numbers[n-2];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove + ", or jump to: " + secondMove);
			} 

      else if ( !(n > 6)
			&& (board[alphaIndex-1][n + 2].equals("[ ]") 
			&& board[alphaIndex][n+1].equals("[o]"))  )   
			{
				firstMove = "" + alpha[alphaIndex-2] + numbers[n+1];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove);
			} 
			else if ((!(n<2) )
			&& (board[alphaIndex-1][n-2].equals("[ ]")
			&& board[alphaIndex][n-1].equals("[o]")))
			{
				firstMove = "" + alpha[alphaIndex-2] + numbers[n-3];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove);
			} 
      else if (board[alphaIndex][n - 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex -1] + numbers[n-2];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove);
			} 
			else if (board[alphaIndex][n + 1].equals("[ ]")) 
			{
				secondMove = "" + alpha[alphaIndex-1] + numbers[n];
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + secondMove);
			}
	}
    else if (alphaIndex == 0 && board[alphaIndex + 1][n].equals("[e]") && turn == true) 
		{
			movePart1(board, alpha, numbers, PlayerOne, PlayerTwo);
		}
    else if (alphaIndex == 7 && board[alphaIndex + 1][n].equals("[o]") && turn == false) 
		{
			movePartO(board, alpha, numbers, PlayerOne, PlayerTwo);
		}
		else if (board[alphaIndex + 1][n].equals("[o]") && turn == false) {

			if (n == 1 && board[alphaIndex + 2][n + 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex + 1] + numbers[n];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove);
			} 
			else if (n == 8 && board[alphaIndex + 2][n - 1].equals("[ ]"))
			{
				firstMove = "" + alpha[alphaIndex + 1] + numbers[n - 2];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove);
			} 
			else if (n>1 && n<8 && board[alphaIndex + 2][n + 1].equals("[ ]") && board[alphaIndex + 2][n - 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex + 1] + numbers[n - 2];
				secondMove = "" + alpha[alphaIndex + 1] + numbers[n];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove + ", " + secondMove);
			} 

			else if ( (alphaIndex < 7  && !(n > 6 || n < 3 ))
      &&(  (board[alphaIndex + 3][n + 2].equals("[ ]"))
			&& (board[alphaIndex + 3][n - 2].equals("[ ]")) 
			&& (board[alphaIndex + 2][n + 1].equals("[e]")) 
			&& (board[alphaIndex + 2][n - 1].equals("[e]")))   )
			{
				firstMove = "" + alpha[alphaIndex + 2] + numbers[n - 3];
				secondMove = "" + alpha[alphaIndex + 2] + numbers[n+1];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove + ", " + secondMove);
			} 

		/** Single & Left Jump for O**/
			else if (( (alphaIndex<6)&&(!(n<3) && !(n>7)) && board[alphaIndex + 2][n - 1].equals("[e]"))
			&& board[alphaIndex + 3][n - 2].equals("[ ]") 
			&& board[alphaIndex + 2][n + 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex + 2] + numbers[n - 3];
				secondMove = "" + alpha[alphaIndex + 1] + numbers[n];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove + ", " + secondMove);
			} 

		/** Single & Right Jump for O**/
			else if ((((alphaIndex<6)&& (!(n>6) && (!(n<2)))
			&& board[alphaIndex + 2][n + 1].equals("[e]"))
			&& board[alphaIndex + 3][n + 2].equals("[ ]") 
			&& board[alphaIndex + 2][n - 1].equals("[ ]")))
			{
				firstMove = "" + alpha[alphaIndex +2] + numbers[n+1];
				secondMove = "" + alpha[alphaIndex +1] + numbers[n-2];
				firstMove = (firstMove.replaceAll(" ", ""));
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove + ", " + secondMove);
			} 

			else if ( (!(n>6) && (alphaIndex < 6))
			&& ((board[alphaIndex + 3][n + 2].equals("[ ]"))
			&& (board[alphaIndex + 2][n + 1].equals("[e]")))  )
			{
				firstMove = "" + alpha[alphaIndex + 2] + numbers[n + 1];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + firstMove);
			} 
      else if ( (!(n<3) && (alphaIndex < 6) )
      && (board[alphaIndex + 3][n-2].equals("[ ]")
			&& board[alphaIndex + 2][n-1].equals("[e]") ))
			{
				secondMove = "" + alpha[alphaIndex + 2] + numbers[n-3];
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can jump to: " + secondMove);
			}
			 
			else if (board[alphaIndex + 2][n - 1].equals("[ ]")) 
			{
				firstMove = "" + alpha[alphaIndex + 1] + numbers[n - 2];
				firstMove = (firstMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + firstMove);
			} 
			
			else if (board[alphaIndex + 2][n + 1].equals("[ ]")) 
			{
				secondMove = "" + alpha[alphaIndex + 1] + numbers[n];
				secondMove = (secondMove.replaceAll(" ", ""));
				System.out.println("You can move to: " + secondMove);
			}
		}
		if (firstMove.length() > 0 && secondMove.length() > 0) 
		{
			Scanner checkerScannerTwo = new Scanner(System.in);
			do 
			{
				System.out.println("Where would you like to move?");
				checkerTwo = checkerScannerTwo.nextLine();
			} 
			while (!(checkerTwo.equals(firstMove) || checkerTwo.equals(secondMove)));

			printEBoard(board, alpha, alphaIndex + 1, n, checkerTwo, turn, PlayerOne, PlayerTwo);
		} 

		else if (firstMove.length() > 0) {
			Scanner checkerScannerTwo = new Scanner(System.in);
			do 
			{
				System.out.println("Where would you like to move?");
				checkerTwo = checkerScannerTwo.nextLine();
			} 
			while (!(checkerTwo.equals(firstMove)));
			printEBoard(board, alpha, alphaIndex + 1, n, checkerTwo, turn, PlayerOne, PlayerTwo);
		}

    else if (secondMove.length() > 0) {
			Scanner checkerScannerTwo = new Scanner(System.in);
			do 
			{
				System.out.println("Where would you like to move?");
				checkerTwo = checkerScannerTwo.nextLine();
			} 
			while (!(checkerTwo.equals(secondMove)));
			
			printEBoard(board, alpha, alphaIndex + 1, n, checkerTwo, turn, PlayerOne, PlayerTwo);
		}
	}

	public static void printEBoard(String[][] board, String[] alpha, int oldAlpha, int oldNum, String newLocation, boolean turn, Player PlayerOne, Player PlayerTwo) 
	{
		board[oldAlpha][oldNum] = "[ ]";

		String l = "";
		int n = 0;
		l = String.valueOf(newLocation.charAt(0));
		n = Character.getNumericValue(newLocation.charAt(1));
		int alphaIndex = 0;
		for (int i = 0; i < alpha.length; i++) 
		{
			if (l.equals(alpha[i])) 
			{
				alphaIndex = i;
			}
		}

		/** Determines what to print at the chosen locations
		 *
		 * @param turn = true PlayerOne's turn
		 * @param turn = false PlayerTwo's turn
	   */

		if (turn == true) 
		{
			board[alphaIndex + 1][n] = "[e]";
			/* (e) will always move to lower alpha numbers*/

			/* All left jumps are n < oldNum*/
			if(oldNum < n && board[alphaIndex+2][n-1].equals("[o]"))
			{
				board[alphaIndex+2][n-1] = "[ ]";
				PlayerOne.increaseScore();
			}
			else if(oldNum > n && board[alphaIndex+2][n+1].equals("[o]"))
			{
				board[alphaIndex+2][n+1] = "[ ]";
				PlayerOne.increaseScore();
			}
			System.out.println("Score 1: "+ PlayerOne.getScore());
		} 
		else if (turn == false) 
		{
			board[alphaIndex + 1][n] = "[o]";

			if(oldNum < n && board[alphaIndex][n-1].equals("[e]"))
			{
				board[alphaIndex][n-1] = "[ ]";
				PlayerTwo.increaseScore();
			}
			else if (oldNum > n && board[alphaIndex][n+1].equals("[e]"))
			{
				board[alphaIndex][n+1] = "[ ]";
				PlayerTwo.increaseScore();
			}
			System.out.println("Score 2: "+ PlayerTwo.getScore());
		}
	
		for (int row = 0; row < board.length; row++) 
		{
			for (int column = 0; column < board[row].length; column++) 
			{
				System.out.print(board[row][column]);
			}
			System.out.println();
		}
	}

}