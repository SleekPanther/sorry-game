import javafx.scene.layout.StackPane;

public class Square extends StackPane {
	protected Color color;
	protected Square immediateNextSquare;
	protected Pawn pawn;
	protected boolean highlighted=false;
	protected boolean isOccupied = false;

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
			if(!highlighted){	//don't change hover if a square is highlighted
				if(hover){
					setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
				}
				else{
					setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
				}
			}
		});
		
		//Square ID's for testing display on hover
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\n"+this);
			}
		});
	}

	public void highlight(){
		setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		highlighted=true;
	}

	public void unHighlight(){
		setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
		highlighted=false;
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

	public Pawn getPawn(){
		return pawn;
	}

	
	public void setImmediateNextSquare(Square square){
		immediateNextSquare=square;
	}

	public Square getImmediateNextSquare(){
		return immediateNextSquare;
	}

	public boolean isOccupied(){
		return isOccupied;
	}

	public void vacate(){
		isOccupied = false;
		getChildren().clear();
	}

	public void add(Pawn pawn){
		isOccupied = true;
		this.pawn=pawn;
		pawn.setCurrentParentSquare(this);
		getChildren().add(pawn);
	}
	
	@Override
	public String toString(){
		String next = "empty";
		if(immediateNextSquare!=null){
			next=immediateNextSquare.getSquareId()+"";
		}

		return "Id="+id+""+"\tcolor="+color+"\tnext="+next;
	}

}