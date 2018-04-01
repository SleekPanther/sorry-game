import javafx.scene.layout.StackPane;

public class Square extends StackPane {
	protected Color color;
	private boolean isOccupied = false;
	private int globalSequencePosition;

	private int id;
	private static int currentAvailableId=0;
	
	public Square(double sideLength, String initialBackgroundColor){
		this(sideLength);
		setStyle("-fx-background-color: "+initialBackgroundColor);
	}

	public Square(double sideLength){
		this(sideLength, Color.ANY);
	}

	public Square(double sideLength, Color color){
		id=currentAvailableId++;

		this.color=color;

		setPrefWidth(sideLength);
		setPrefHeight(sideLength);

		//Set black overlay semi-transparent, becomes completely transparent on hover
		setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
			}else{
				setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
			}
		});
		
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\n"+this);
			}
		});
	}

	public int getSquareId(){
		return id;
	}

	public void setColor(Color color){
		this.color=color;
	}
	
	public Color getColor(){
		return color;
	}
	

	public boolean isOccupied(){
		return isOccupied;
	}

	public void vacate(){
		isOccupied = false;
		getChildren().clear();;
	}

	public void setGlobalSequencePosition(int position){
		globalSequencePosition = position;
	}

	public void add(Pawn pawn){
		isOccupied = true;
		getChildren().addAll(pawn);
	}
	
	@Override
	public String toString(){
		return "Id="+id+""+"\tcolor="+color;
	}

}