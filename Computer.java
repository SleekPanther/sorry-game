import exceptions.*;
import Functions.ColorFunctions;
import enums.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Computer extends Player{
	private boolean smartness;
	private boolean meanness;
	
	public Computer(String name, Color color, ArrayList<Pawn> pawns, ArrayList<Pawn> allPawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset, boolean smartness, boolean meanness){
		super(name, color, pawns, allPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset);
		this.smartness = smartness;
		this.meanness = meanness;
	}

	@Override
	public void executeAutomaticTurn(int numSpaces){
		LinkedList<Move> moves = new LinkedList<Move>();
		int numSpacesAdjusted = numSpaces;	//in case leaving start changes 2 to 1
		if(numSpacesAdjusted==4){	//4 moves backwards
			numSpacesAdjusted = -4;
		}

		for(Pawn pawn : pawns){
			if(!pawn.getCurrentParentSquare().getClass().getSimpleName().equals("HomeSquare")){
				try{
					boolean leavesStart = false;
					if(pawn.getCurrentParentSquare().getClass().getSimpleName().equals("StartSquare")){
						if(numSpaces==1 || numSpaces==2){
							numSpacesAdjusted=1;	//Set numSpacesAdjusted to 1 in case they drew a 2 since we only want them moving 1 space forward out of Start
							leavesStart = true;
						}
						else{
							throw new AttemptedToLeaveStartWithNot1Or2Exception("Attempted To Leave Start With Not 1 Or 2");
						}
					}

					Square landingSquare = pawn.calculateLandingSquare(numSpacesAdjusted);

					//Check simple bumping (move lands on another pawn)
					int bumpCount = 0;
					if(landingSquare.isOccupied() && landingSquare.getColor()!=color){
						bumpCount++;
					}

					boolean slide = false;
					if(numSpacesAdjusted>0 && landingSquare.getClass().getSimpleName().equals("SlideStartSquare") && landingSquare.getColor()!=color){
						slide = true;
						bumpCount = 0;		//reset for slides since may already bumped by directly landing on
						Square slideDestinationSquare = ((SlideStartSquare)landingSquare).getDestinationSquare();
						//follow the slide forward checking if any pawns exist to bump
						for(Square square = landingSquare; square.getSquareId()!=slideDestinationSquare.getSquareId(); square=square.getImmediateNextSquare()){
							if(square.isOccupied() && square.getPawn().getColor()!=color){
								bumpCount++;
							}
						}
						landingSquare = slideDestinationSquare;
						if(slideDestinationSquare.isOccupied() && slideDestinationSquare.getPawn().getColor() == color){
							throw new LandedOnSquareOccupiedByPlayersOwnPawnException("Can't slide and land on yourself");
						}
					}

					int movesToHome = pawn.calculateMovesToHome();

					moves.add(new Move(pawn, landingSquare, leavesStart, slide, bumpCount, movesToHome));
				}
				catch(OvershotHomeException e){
				}
				catch(LandedOnSquareOccupiedByPlayersOwnPawnException e){
				}
				catch(AttemptedToLeaveStartWithNot1Or2Exception e){
				}
			}
		}

		// System.out.println("\n"+color+" Moves Before removing");
		// for(Move move : moves){
		// 	System.out.println(move);
		// }

		//Remove "duplicate" moves that leave start so only the last pawn in the ArrayList for the MultipleSquare is allowed to move from start
		for(int i=moves.size()-1; i>=0; i--){	//loop backwards since remmoving a move shifts the list
			if(moves.get(i).leavesStart && moves.get(i).pawnToMove.getPawnId()!=startSquares.get(ColorFunctions.colorToPlayerIndex(color)).getPawn().getPawnId()){
				moves.remove(i);
			}
		}

		if(moves.isEmpty()){
			completedMove = false;
		}
		else{
			completedMove = true;

			if(smartness){
				Collections.sort(moves);	//sort by moves closes to home first (smart)
			}
			else{	//Always picks move at the head of the list, so randomize order for Dumb
				Collections.shuffle(moves);
			}

			System.out.println("\n"+color+" Moves\t\tsmart="+smartness+"  mean="+meanness);
			for(Move move : moves){
				System.out.println(move);
			}

			chosenMove = moves.removeFirst();

			if(smartness){	//prioritize getting out of start
				StartSquare startSquare = startSquares.get(ColorFunctions.colorToPlayerIndex(color));
				Square squareAfterStart = startSquare.getImmediateNextSquare();
				//Attempt to find a move to get the your pawn off the start destination
				if(squareAfterStart.isOccupied() && squareAfterStart.getPawn().getColor()==color){
					for(int i=0; i<moves.size(); i++){
						if(moves.get(i).pawnToMove.getPawnId() == squareAfterStart.getPawn().getPawnId()){
							chosenMove = moves.remove(i);
							break;
						}
					}
				}
				//Else square after start is clear, attemp to leave start
				else if(!chosenMove.leavesStart){
					for(int i=0; i<moves.size(); i++){
						if(moves.get(i).leavesStart){
							chosenMove = moves.remove(i);
							break;
						}
					}
				}
			}

			if(!moves.isEmpty()){		//Must have @ least 1 other move to compare to
				if(meanness){
					if(moves.get(0).numPawnsBumpted > chosenMove.numPawnsBumpted){
						chosenMove = moves.remove(0);
					}
				}
				else{
					if(moves.get(0).numPawnsBumpted < chosenMove.numPawnsBumpted){
						chosenMove = moves.remove(0);
					}
				}
			}
			System.out.println("Chosen move="+chosenMove);

			actuallyMove(numSpacesAdjusted);
		}
	}

}