import java.util.ArrayList;

import enums.Color;

public class Computer extends Player{
	//difficulty/meanness
	
	public Computer(String name, Color color, ArrayList<Pawn> pawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		super(name, color, pawns, startSquares, homeSquares, slideSquareDestinationForwardOffset);
	}

	@Override
	public void executeAutomaticTurn(int cardValue){
		pawns.get(pawns.size()-1).move(cardValue);
	}

}