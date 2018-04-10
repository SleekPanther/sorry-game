import java.util.ArrayList;

public class Player{
	protected String name;
	protected Color color;
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	//homespace field so it can easily be bumped back without searching entire board
	protected boolean isTheirTurn = true;
	private int numPawnsInHom=0;
	private boolean hasWon=false;

	public Player(String name, Color color, ArrayList<Pawn> pawns){
		this.name = name;
		this.color = color;
		this.pawns = pawns;
	}
	
	public ArrayList<Pawn> getPawns(){
		return pawns;
	}

	public void setPawns(ArrayList<Pawn> pawns){
		this.pawns=pawns;
	}

}