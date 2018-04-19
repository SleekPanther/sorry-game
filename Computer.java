import java.util.ArrayList;
import java.util.LinkedList;
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
			if(!pawn.getCurrentParentSquare().getClass().getName().equals("HomeSquare")){
				try{
					Square landingSquare = pawn.calculateLandingSquare(numSpaces);

					boolean leavesStart = false;
					if(pawn.getCurrentParentSquare().getClass().getName().equals("StartSquare")){
						if(numSpaces==1 || numSpaces==2){
							numSpaces=1;	//Set numSpaces to 1 in case they drew a 2 since we only want them moving 1 space forward out of Start
							leavesStart =true;
						}
						else{
							throw new AttemptedToLeaveStartWithNot1Or2Exception("Attempted To Leave Start With Not 1 Or 2");
						}
					}

					int bumpCount = 0;
					if(landingSquare.isOccupied() && landingSquare.getColor()!=color){
						bumpCount++;
					}

					boolean slide = false;
					if(landingSquare.getClass().getName().equals("SlideStartSquare") && landingSquare.getColor()!=color){
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
						if(slideDestinationSquare.isOccupied() 
								&& slideDestinationSquare.getPawn().getColor() == color){
							throw new LandedOnSquareOccupiedByPlayersOwnPawnException("Can't slide and land on yourself");
						}
					}

					moves.add(new Move(pawn, landingSquare, leavesStart, slide, bumpCount));
				}
				catch(OvershotHomeException e){
				}
				catch(LandedOnSquareOccupiedByPlayersOwnPawnException e){
				}
				catch(AttemptedToLeaveStartWithNot1Or2Exception e){
				}

				
			}
		}

		System.out.println("\nMoves");
		for(Move move : moves){
			System.out.println(move);
		}

		if(moves.isEmpty()){
			Popup popup = new Popup("No moves for "+name);
			popup.show();
		}
	}

}