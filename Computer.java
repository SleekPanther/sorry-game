import java.util.ArrayList;

public class Computer extends Player{
	private Square selectedSquare;
	private Square destinationSquare;
	
	public Computer(String name, Color color, ArrayList<Pawn> pawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		super(name, color, pawns, startSquares, homeSquares, slideSquareDestinationForwardOffset);
	}

}