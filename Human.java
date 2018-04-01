public class Human extends Player{
	private boolean isTheirTurn = true;
	private Square selectedSquare;
	private Square destinationSquare;
	
	public Human(String name, Color color){
		super(name, color);
	}

	public void handleSquareClick(Square clickedSquare, int numSpaces){
		if(selectedSquare==null){
			if(!clickedSquare.isOccupied()){	//Square Must have a pawn
				return;
			}
			else if(clickedSquare.getPawn().getColor() != color){	//Colors must match in order to move
				return;
			}

			selectedSquare=clickedSquare;
			selectedSquare.highlight();

			destinationSquare = selectedSquare.getPawn().calculateLandingSquare(numSpaces);
			destinationSquare.highlight();
		}
		else{
			// Popup alert = new Popup(AlertType.INFORMATION, "Square occupied, can't move");

			if(clickedSquare.getSquareId() == destinationSquare.getSquareId()){		//Make sure clicked square is the correct destination
				selectedSquare.getPawn().move(destinationSquare);
				selectedSquare.unHighlight();
				destinationSquare.unHighlight();
				selectedSquare=null;
			}
		}
	}
	
}