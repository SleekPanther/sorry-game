import java.util.*;
import enums.Color;
import exceptions.*;

public class Human extends Player{
	private Square selectedSquare;
	private LinkedList<Move> moves;
	
	public Human(String name, Color color, ArrayList<Pawn> pawns, ArrayList<Pawn> allPawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		super(name, color, pawns, allPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset);
	}

	@Override
	public String handleSquareClick(Square clickedSquare, int numSpaces){
		if(selectedSquare==null){	//they haven't clicked anything yet
			if(clickedSquare.getClass().getSimpleName().equals("HomeSquare")){	//Don't allow to move out of Home
				return "error";
			}
			else if(!clickedSquare.isOccupied()){	//Square Must have a pawn
				return "error";
			}
			else if(clickedSquare.getPawn().getColor() != color){	//Players can only move their pieces
				return "error";
			}

			if(clickedSquare.getClass().getSimpleName().equals("StartSquare")){
				if(numSpaces!=0 && numSpaces!=1 && numSpaces!=2){
					selectedSquare = null;
					Popup popup = new Popup("Can only move from start with 1, 2 or Sorry! Card");
					popup.show();
					return "error";
				}
				else if(numSpaces==2){	//Set numSpaces to 1 in case they drew a 2 since we only want them moving 1 space forward out of Start
					numSpaces=1;
				}
			}

			selectedSquare=clickedSquare;

			moves = new LinkedList<Move>();

			//Sorry card
			if(numSpaces==0){
				if(selectedSquare.getClass().getSimpleName().equals("StartSquare")){
					System.out.println();
					for(Pawn otherPawn : allPawns){
						//Find other pawns on the perimeter
						if(otherPawn.getColor()!=color && !otherPawn.getCurrentParentSquare().getClass().getSimpleName().equals("StartSquare") && !otherPawn.getCurrentParentSquare().getClass().getSimpleName().equals("SafetySquare") && !otherPawn.getCurrentParentSquare().getClass().getSimpleName().equals("HomeSquare")){
							int movesToHome = otherPawn.calculateMovesToHome(color);
							moves.add(new Move(selectedSquare.getPawn(), otherPawn.getCurrentParentSquare(), true, false, 1, movesToHome));
							System.out.println(" move="+moves.get(moves.size()-1));
						}
					}
					selectedSquare.highlight();
					for(Move move : moves){
						move.landingSquare.highlight();
					}
					if(moves.isEmpty()){
						Popup popup = new Popup("No other pawns available to bump for Sorry Card");
						popup.show();
					}
				}
			}
			else{
				try{
					Square landingSquare = selectedSquare.getPawn().calculateLandingSquare(numSpaces);
					int bumpCount = 0;
					if(landingSquare.isOccupied() && landingSquare.getPawn().getColor()!=color){
						bumpCount++;
					}
					boolean slide = false;
					if(landingSquare.getClass().getSimpleName().equals("SlideStartSquare") && landingSquare.getColor()!=color){	//only slide on other player's slides
						bumpCount = 0;
						slide = true;
						Square slideDestinationSquare = ((SlideStartSquare)landingSquare).getDestinationSquare();
						if(slideDestinationSquare.isOccupied() && slideDestinationSquare.getPawn().getColor()==color){	//Don't slide and land on yourself
							throw new LandedOnSquareOccupiedByPlayersOwnPawnException("Can't slide and land on top of yourself");
						}
						else{
							for(Square square = landingSquare; square.getSquareId()!=slideDestinationSquare.getSquareId(); square=square.getImmediateNextSquare()){
								if(square.isOccupied() && square.getPawn().getColor()!=color){	//don't bump yourself
									bumpCount++;
								}
							}
							landingSquare = slideDestinationSquare;
						}
					}
					boolean leftStart = false;
					if(selectedSquare.getClass().getSimpleName().equals("StartSquare")){
						leftStart = true;
					}
					int movesToHome = selectedSquare.getPawn().calculateMovesToHome();
					moves.add(new Move(selectedSquare.getPawn(), landingSquare, leftStart, slide, bumpCount, movesToHome));

					selectedSquare.highlight();
					landingSquare.highlight();
				}
				catch(OvershotHomeException e){
					selectedSquare = null;		//clear selected square if error
					Popup popup = new Popup(e.getMessage());
					popup.show();
				}
				catch(LandedOnSquareOccupiedByPlayersOwnPawnException e){
					selectedSquare = null;
					Popup popup = new Popup(e.getMessage());
					popup.show();
				}
			}
		}
		//A second square was clicked to finalize a move or cancel
		else{
			if(!moves.isEmpty()){
				chosenMove = moves.getFirst();
				boolean clickedValidLandingSquare = false;
				for(int i=0; i<moves.size(); i++){
					if(moves.get(i).landingSquare.getSquareId() == clickedSquare.getSquareId()){
						clickedValidLandingSquare = true;
						chosenMove = moves.get(i);
						break;
					}
				}
				if(clickedValidLandingSquare){		//Make sure clicked square is the correct destination
					actuallyMove(numSpaces);

					selectedSquare.unHighlight();
					for(Move move : moves){
						move.landingSquare.unHighlight();
					}
					selectedSquare=null;

					if(chosenMove.landingSquare.getClass().getSimpleName().equals("HomeSquare")){
						numPawnsInHome++;
					}

					return "done";
				}
				else{	//They didn't click the correct destination square
					selectedSquare.unHighlight();
					for(Move move : moves){
						move.landingSquare.unHighlight();
					}
					selectedSquare = null;
					handleSquareClick(clickedSquare, numSpaces);	//reset and handle as new click
				}
			}
		}
		return "default";
	}
	
}