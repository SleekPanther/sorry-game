public class Card {
	private int type;
	
	public Card(int type){
		this.type=type;
	}

	public int getType(){
		return type;
	}

	@Override
	public String toString(){
		return "Card="+type;
	}

}