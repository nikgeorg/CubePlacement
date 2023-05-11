/**
 * 
 */
package cardsAi;
import java.util.Scanner;


/**
 * 
 *
 */
public class Cards {
	
	/**
	 * one reader as scanner
	 */
	private Scanner reader;
	
	
	/**
	 * M number of cards, K number of teams(stacks)
	 */
	private int m;


	private int k;
	
	/**
	 * A a table with the number of cards in each stack/team(k)
	 */
	private int [] a;
	
	/**
	 * B a table with the max number of cards that can be picked each time from each stack/team(k)
	 */
	private int [] b;
	
	/**
	 * sum of cards in each stack/team (must be<m)
	 */
	private int sumOfA;
	
	/**
	 * whose turn it is
	 */
	private int playerCount;
	
	/**
	 * Contractor  int reader;
	 * 
	 */
	public Cards(){
		reader=new Scanner(System.in);
		playerCount=0;
	}
	
	public Scanner getReader() {
		return reader;
	}
	
	/**
	 * a print to get the number of players if 1 minimax with the ai as max playing first if  2 no ai the players enter actions alternating
	 */
	public int printSelectNumPlayers() {
		int typeOfGame=0;
		System.out.println("Chose (1 or 2). Enter 1 to play solo vs ai or 2 for 2 players");
		
		while(typeOfGame<1 || typeOfGame>2) {
			reader=new Scanner(System.in);
			
			try {
				typeOfGame=reader.nextInt();
			}
			catch(Exception e) {
				System.out.println(e);
				typeOfGame=0;
				//reader.close
				
			}
			if(typeOfGame<1 || typeOfGame>2)
				System.out.println("Not a valid entry!!! Please select between 1 and 2 intiger numbers.");
		}
		return typeOfGame;
	}
	
	
	/**
	 * a print to get the initial state of the game 
	 */
	public int printEnterInitOfGame() {
		m=0; 
		k=0;
		//m/k>=2
		System.out.println("Enter the total number of cards (m>=2) and the number of teams(k>=1).");
		while(m<2 || k<1) {
			reader=new Scanner(System.in);
			try {
				m=reader.nextInt();
				k=reader.nextInt();
			}
			catch(Exception e) {
				System.out.println(e);
				m=0; 
				k=0;
				//reader.close
				
			}
			if(m<2 || k<1)
				System.out.println("Not a valid entry!!! Please select an integer number bigger than 2 for the number of cards and an integer number bigger than 1 for the teams.");
			//check for each team(stack) to have min 2 cards
			else if(m/k<2) {
				System.out.println("Not a valid entry!!! Each team(k) must have at least 2 cards. Please select an integer number bigger than 2 for the number of cards and an integer number bigger than 1 for the teams."); 
				m=0; 
				k=0;
			}
		}
		return 1;
	}
	
	/**
	 * a print to user to separate the cards in each team/stack  
	 */
	public int printInitOfStacksA() {
		 sumOfA=0;
		a= new int[k];
		
		System.out.println("Enter the number of cards in each team(k) a (ai>=2).");
		for (int i=0;i<k;i++) {
			a[i]=0;
			System.out.println("Enter the number of cards in team("+(i+1)+"):");
			while(a[i]<1) {
				reader=new Scanner(System.in);
				try {
					a[i]=reader.nextInt();
					
				}
				catch(Exception e) {
					System.out.println(e);
					a[i]=0;
					//reader.close
					
				}
				//check for each team(stack) to have min 2 cards
				if(a[i]<2) {
					System.out.println("Not a valid entry!!! Please enter an ineger value >= 2.");
					a[i]=0;
				}
				
				}
			//increase the sum
			sumOfA+=a[i];
			}
		
		return 1;
	}
	
	
	/**
	 * a method to enter the "B" (the max #cards a player can pick in each stack/team)  
	 */
	public int calculateBmatrix() {
		 
		b= new int[k];
		
		for (int i=0;i<k;i++) {
			
			int bi=0;
			while(bi<1||bi>=a[i] ) {
				System.out.println("Enter max # that can be picked for team "+(i+1)+" (B). Must be smaller than the total number of cards on team ("+a[i]+"):");
				reader=new Scanner(System.in);
				try {
					bi=reader.nextInt();
					
				}
				catch(Exception e) {
					System.out.println(e);
					bi=0;
				}
				b[i]=bi;
						
				}
		}
		
		return 0;
	}
	
	
	/**
	 * a print of the current state of the game and the teams/stacks 
	 */
	public int printGameState() {
	
		System.out.println("Current state of the game: \n");
		for (int i=0;i<k;i++) {
			
			System.out.println("[Team ("+(i+1)+"): #cardsleft: "+a[i]+", max # can pick(B): "+Math.min(a[i],b[i])+" ]\t");
			
				
				}
		System.out.println();
			
		
		return 1;
	}
	
	/**
	 * a print to ask the user to play again or exit 
	 */
	public int printContinue() {
		String playAgain=" ";
		int exitGame=0;
		
		System.out.println("Thanks for playing!!! Would you like to play again? Enter \"y\" for yes or \"n\" to exit. ");
		while(!playAgain.equals("y")&&!playAgain.equals("n") ) {
			reader=new Scanner(System.in);
			try {
				playAgain=reader.next();
				
			}
			catch(Exception e) {
				System.out.println(e);
				playAgain="n";
			}
			if(!playAgain.equals("y")&&!playAgain.equals("n"))
				System.out.println("Not a valid entry!!! Please enter <<y>> or <<n>>.");
		}
		if(playAgain.equals("n"))
			exitGame=1;
		return exitGame;
	}
	
	
	/**
	 * The Action each player can take  
	 */
	public int turnAction(boolean aiTurn,int [] action) {
		
		if(aiTurn) {
			System.out.println("Ai's Max turn.");
			int team=action[0];
			int cards=action[1];
			//calculate the new state of the game
			a[team]-=cards;
			System.out.println("Ai Max removed from team: "+(team+1)+" cards: "+cards+".");
		}
		else {
		String player="Min";
		int team=0;
		int cards=0;
		if(playerCount%2==0) {
			player="Max";
		}
		
		System.out.println("Players "+player+" turn.");
		System.out.println("Enter the number of team you want to pick cards from (1-"+k+"):");
		
			while(team<1) {
				reader=new Scanner(System.in);
				try {
					team=reader.nextInt();
					
				}
				catch(Exception e) {
					System.out.println(e);
					team=0;
					//reader.close					
				}
				//check stack int to exist
				if(team>k||team<1) {
					System.out.println("Not a valid entry!!! Please enter an ineger value > 1 and <"+k+".");
					team=0;
				}
				else if(a[team-1]==0) {
					System.out.println("Not a valid entry!!! Please select a team that has cards");
					team=0;
				}
			}
				System.out.println("Enter the number of cards you want to pick/remove from "+team+" team [#cardsleft: "+a[team-1]+", max # can pick(B): "+Math.min(a[team-1],b[team-1])+" ] ");
				
				while(cards<1) {
					reader=new Scanner(System.in);
					try {
						cards=reader.nextInt();
						
					}
					catch(Exception e) {
						System.out.println(e);
						cards=0;
						//reader.close					
					}
					//check if stack has enough B for the cards
					if(cards>b[team-1]) {
						System.out.println("Not a valid entry!!! Please enter an ineger value > 0 and less or equal B: "+b[team-1]+".");
						cards=0;
					}
					else if(cards>a[team-1]) {
						cards=a[team-1];
						
					}
				
				}
		
			//calculate the new state of the game
				a[team-1]-=cards;
			
		}
			//increase the playerCount
			playerCount++;
			
		
		return playerCount;
	}
	
	
	/**
	 * Returns True if all teams are empty  
	 */
	public boolean isGameFinished() {
		boolean finish=true;
		for(int i=0;i<k;i++) {
			if(a[i]!=0) {
				finish=false;
			}
		}
		return finish;
	}
	
	/**
	 * Remove "numCards" cards from "team" team  
	 */
	public void removeCards(int team, int numCards) {
		
		a[team] -= numCards;
	}
	
	/**
	 * Add "numCards" cards from "team" team  
	 */
	public void addCards(int team, int numCards) {
			
			a[team] += numCards;
		}
	
	/**
	 * MiniMax Algorithm to calculate Max's best next move  
	 */
	public int[] minimax(boolean parent_max_turn) {
		//System.out.println("...");
		int []bestMove=new int[] {0,0,0};//null
		int bestScore=10000;
		if(parent_max_turn)
			bestScore=-10000;
		
	
		
		if(isGameFinished()) {
			if(parent_max_turn)
				bestScore= -1;
			else
				bestScore=1;
			return new int[] {0,0,bestScore};
		}
		//get scores for states removing from every team "a" all cards checking one by one until the max num allowed to remove from each team "b"
		else {
			//if(max_turn) {
				
				//get scores for states removing from every team "a" all cards checking one by one until the max num allowed to remove from each team "b"
				for (int i=0;i<a.length;i++) {
					for(int j=1;j<=Math.min(b[i],a[i]);j++) {
						//extra check if there are enough cards
						if(a[i]>=j) {
							removeCards(i,j);
							//get score of child state
							int score=minimax(!parent_max_turn)[2];
							if(parent_max_turn&&score>bestScore) {
								bestScore=score;
								bestMove[0]=i;
								bestMove[1]=j;
								bestMove[2]=bestScore;
							}
							if(!parent_max_turn&&score<bestScore) {
								bestScore=score;
								bestMove[0]=i;
								bestMove[1]=j;
								bestMove[2]=bestScore;
							}
							addCards(i,j);
						}
					}
				}
			//}
			
			return bestMove;
		}//else
		
	}
		
	
		
	
	
	
	
	
	public static void main(String[] args) {
		
		int exitGame=0;
		
		
		while (exitGame==0) {
			
			Cards game=new Cards();
			//empty list
			//initiate the game
			//get m and k from user
			game.printEnterInitOfGame();
			
			//split the cards from user
			game.printInitOfStacksA();
			
			//check if sum of Ai>m
			while(game.sumOfA!=game.m) {
				System.out.println("Not a valid A!!! The sum of number of cards in each stack must be equal to m:"+game.m+".");
				game.printInitOfStacksA();
			}
			game.calculateBmatrix();
			game.printGameState();
			
			
			//select number of players
			int typeOfgame=game.printSelectNumPlayers();
			
			//check if the game has finished(all teams are empty)
			while(!game.isGameFinished()) {
				
				//two players game for type==2
				if (typeOfgame==2){
					
						
						
						//each player enters his action no ai input
						game.turnAction(false,null);
						game.printGameState();
												
						}					
						
				
				else {
					//minimax game
					//ai max turn
					System.out.println("Calculating Ai's move...");
					
					int [] aIaction=game.minimax(false);
					game.turnAction(true,aIaction);
					
					game.printGameState();
					//player's turn
					if(!game.isGameFinished())
					game.turnAction(false,null);
				}
			}
			//Game finished
			String player="Max";
			if(game.playerCount%2==0) {
				player="Min";
			}
		
			System.out.println("Game Finished!!! The winner is:"+player+".");
			//Ask the user if he wants to play again.
			exitGame=game.printContinue();
			
		}
		System.exit(0);

	}

}
