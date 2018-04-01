public class Player{
	protected String name;
	protected Color color;

	//homespace field so it can easily be bumped back without searching entire board
	//pawns?	maybe not needed here
	private int numPawnsInHom=0;
	private boolean hasWon=false;

	public Player(String name, Color color){
		this.name=name;
		this.color=color;
	}
	
}