public class Card {
	private int type;
	private int width;
	private int height;
	
	public Card(int type){
		this.type=type;
	}

	public int getType(){
		return type;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString(){
		return type+"";
	}

}