import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import exceptions.*;

import enums.Color;

public class Computer extends Player{
	//difficulty/meanness
	
	public Computer(String name, Color color, ArrayList<Pawn> pawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		super(name, color, pawns, startSquares, homeSquares, slideSquareDestinationForwardOffset);
	}

	@Override
	public void executeAutomaticTurn(int numSpaces){
		LinkedList<Move> moves = new LinkedList<Move>();

		for(Pawn pawn : pawns){
			if(!pawn.getCurrentParentSquare().getClass().getSimpleName().equals("HomeSquare")){
				try{
					Square landingSquare = pawn.calculateLandingSquare(numSpaces);

					boolean leavesStart = false;
					if(pawn.getCurrentParentSquare().getClass().getSimpleName().equals("StartSquare")){
						if(numSpaces==1 || numSpaces==2){
							numSpaces=1;	//Set numSpaces to 1 in case they drew a 2 since we only want them moving 1 space forward out of Start
							leavesStart =true;
						}
						else{
							throw new AttemptedToLeaveStartWithNot1Or2Exception("Attempted To Leave Start With Not 1 Or 2");
						}
					}

					//Check simple bumping (move lands on another pawn)
					int bumpCount = 0;
					if(landingSquare.isOccupied() && landingSquare.getColor()!=color){
						bumpCount++;
					}

					boolean slide = false;
					if(landingSquare.getClass().getSimpleName().equals("SlideStartSquare") && landingSquare.getColor()!=color){
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

		//Actually get these from the computer class later
		boolean smartness = true;
		boolean meanness = false;

		if(moves.isEmpty()){
			//or skip turn
			Popup popup = new Popup("No moves for "+name);
			popup.show();
		}
		else{
			if(smartness){
				Collections.sort(moves);	//sort by moves closes to home first (smart)
			}
			else{	//Always picks move at the head of the list, so randomize order for Dumb
				Collections.shuffle(moves);
			}

			System.out.println("\nMoves");
			for(Move move : moves){
				System.out.println(move);
			}

			Move chosenMove = moves.removeFirst();

			if(!moves.isEmpty()){	//Must have @ least 1 other move to compare to
				if(meanness){
					if(moves.get(0).numPawnsBumpted > chosenMove.numPawnsBumpted){
						chosenMove = moves.remove(0);
						System.out.println("mean");
					}
				}
				else{
					if(moves.get(0).numPawnsBumpted < chosenMove.numPawnsBumpted){
						chosenMove = moves.remove(0);
						System.out.println("nice");
					}
				}
			}
			System.out.println("Chosen move="+chosenMove);

			// try {
			// 	Square nextHighlighSquare = chosenMove.pawnToMove.getCurrentParentSquare().getImmediateNextSquare();	//normal square
			// 	//Check if currently sitting on safety entry square
			// 	if(chosenMove.pawnToMove.getCurrentParentSquare().getClass().getSimpleName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)chosenMove.pawnToMove.getCurrentParentSquare()).getNextSafetySquare().getColor()){
			// 		nextHighlighSquare = ((SafetyEntrySquare)chosenMove.pawnToMove.getCurrentParentSquare()).getNextSafetySquare();
			// 	}

			// 	while(nextHighlighSquare.getSquareId() != chosenMove.landingSquare.getSquareId()){
			// 		nextHighlighSquare.highlight();
			// 		System.out.println("Highlighting");
			// 		TimeUnit.MILLISECONDS.sleep(600);
			// 		// Thread.sleep(600);
			// 		nextHighlighSquare.unHighlight();
			// 		if(nextHighlighSquare.getClass().getSimpleName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)nextHighlighSquare).getNextSafetySquare().getColor()){
			// 			nextHighlighSquare = ((SafetyEntrySquare)nextHighlighSquare).getNextSafetySquare();
			// 		}
			// 		else{
			// 			nextHighlighSquare = nextHighlighSquare.getImmediateNextSquare();
			// 		}
			// 	}
			// } catch (InterruptedException e) {
			// 	e.printStackTrace();
			// }

			//Bump first, or else the pawn to be bumped is the one we are moving
			if(chosenMove.slide){
				bumpOthersOnSlide(chosenMove.pawnToMove.calculateLandingSquare(numSpaces));
			}
			else if(chosenMove.numPawnsBumpted > 0){	//simple bumping shouldn't happen as well as sliding bumping
				bump(chosenMove.landingSquare.getPawn());
			}

			chosenMove.pawnToMove.move(chosenMove.landingSquare);	//actually move (any exceptions and illegal moves will be taken care of)

			if(chosenMove.landingSquare.getClass().getSimpleName().equals("HomeSquare")){
				numPawnsInHome++;
			}
		}
	}

}