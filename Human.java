import java.util.ArrayList;
import exceptions.*;
import javafx.scene.control.Alert.AlertType;

public class Human extends Player{
	private Square selectedSquare;
	private Square destinationSquare;
	
	public Human(String name, Color color, ArrayList<Pawn> pawns){
		super(name, color, pawns);
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
				destinationSquare = selectedSquare.getPawn().calculateLandingSquare(numSpaces);
				selectedSquare.highlight();
				destinationSquare.highlight();
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
		else{
			if(clickedSquare.getSquareId() == destinationSquare.getSquareId()){		//Make sure clicked square is the correct destination
				selectedSquare.getPawn().move(destinationSquare);
				selectedSquare.unHighlight();
				destinationSquare.unHighlight();
				selectedSquare=null;
			}
		}
	}
	
}