import java.util.ArrayList;
import exceptions.*;
import javafx.scene.control.Alert.AlertType;

public class Human extends Player{
	private Square selectedSquare;
	private Square landingSquare;
	
	public Human(String name, Color color, ArrayList<Pawn> pawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		super(name, color, pawns, startSquares, homeSquares, slideSquareDestinationForwardOffset);
	}

	public void handleSquareClick(Square clickedSquare, int numSpaces){
		if(selectedSquare==null){
			if(clickedSquare.getClass().getName().equals("HomeSquare")){	//Don't allow to move out of Home
				return;
			}
			else if(!clickedSquare.isOccupied()){	//Square Must have a pawn
				return;
			}
			else if(clickedSquare.getPawn().getColor() != color){	//Players can only move their pieces
				return;
			}
			
			if(clickedSquare.getClass().getName().equals("StartSquare")){
				if(numSpaces!=1 && numSpaces!= 2){
					selectedSquare = null;
					Popup popup = new Popup("Can only move from start with 1 or 2");
					popup.show();
					return;
				}
				else{	//Set numSpaces to 1 in case they drew a 2 since we only want them moving 1 space forward out of Start
					numSpaces=1;
				}
			}

			selectedSquare=clickedSquare;

			try{
				landingSquare = selectedSquare.getPawn().calculateLandingSquare(numSpaces);
				if(landingSquare.getClass().getName().equals("SlideStartSquare") && landingSquare.getColor()!=color){
					landingSquare = ((SlideStartSquare)landingSquare).getDestinationSquare();
					bumpOthersOnSlide();
				}
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
		//A second square was clicked to finalize a move or cancel
		else{
			if(clickedSquare.getSquareId() == landingSquare.getSquareId()){		//Make sure clicked square is the correct destination
				if(landingSquare.isOccupied() && landingSquare.getPawn().getColor() != color){
					bump(landingSquare.getPawn());
				}
				selectedSquare.getPawn().move(landingSquare);
				selectedSquare.unHighlight();
				landingSquare.unHighlight();
				selectedSquare=null;
			}
			else{
				selectedSquare.unHighlight();
				landingSquare.unHighlight();
				selectedSquare = null;
				handleSquareClick(clickedSquare, numSpaces);	//reset and handle as new click
			}
		}
	}
	
}