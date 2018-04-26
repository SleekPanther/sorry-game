import enums.Color;
import javafx.scene.layout.StackPane;

//Squares extend StackPane so anything added is centered. Each square has a reference to the pawn currently on the square
public class Square extends StackPane {
	private int id;
	private static int currentAvailableId=0;	//global id count so each square created is unique
	protected Color color;
	protected Square immediateNextSquare;
	protected Square previousSquare;
	protected Pawn pawn;
	protected boolean highlighted=false;
	protected boolean isOccupied = false;

	public Square(double sideLength){
		this(sideLength, Color.ANY);
	}

	public Square(double sideLength, Color color){
		id=currentAvailableId++;

		this.color=color;

		setPrefWidth(sideLength);
		setPrefHeight(sideLength);

		setStyle("-fx-background-color: rgba(0, 0, 0, .2);");	//Set black overlay semi-transparent, becomes completely transparent on hover

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
		
		//Diaplay Square ID's for testing on hover
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\n"+this);
				if(pawn!=null){
					System.out.print("\t"+pawn);
				}
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

	public Pawn getPawn(){
		return pawn;
	}


	public void setImmediateNextSquare(Square square){
		immediateNextSquare=square;
	}

	public Square getImmediateNextSquare(){
		return immediateNextSquare;
	}

	public void setPreviousSquare(Square square){
		previousSquare=square;
	}

	public Square getPreviousSquare(){
		return previousSquare;
	}

	public void highlight(){
		setStyle("-fx-background-color: rgba(0, 0, 0, 0);");	//reduce opacity to 0 to let background color through
		highlighted=true;
	}

	public void unHighlight(){
		setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
		highlighted=false;
	}

	public boolean isOccupied(){
		return isOccupied;
	}

	public void vacate(){
		isOccupied = false;
		this.pawn = null;
		getChildren().clear();		//update UI
	}

	public void add(Pawn pawn){
		isOccupied = true;
		this.pawn=pawn;
		pawn.setCurrentParentSquare(this);
		getChildren().add(pawn);	//add to pane in UI
	}
	
	@Override
	public String toString(){
		String pawnText = "";
		if(pawn!=null){
			pawnText= " pawn="+pawn.getPawnId()+" ";
		}
		String next = "Empty";
		if(immediateNextSquare!=null){
			next=immediateNextSquare.getSquareId()+"";
		}
		return "Square="+id+" \t"+color+pawnText+"\tnext="+next;
	}

}