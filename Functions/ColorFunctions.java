package Functions;
import enums.Color;

public class ColorFunctions {
	public static int colorToPlayerIndex(Color color){
		return colorToPlayerIndex(color.name());
	}
	
	public static int colorToPlayerIndex(String color){
		color = color.toUpperCase();
		if(color=="RED"){
			return 0;
		}
		else if(color=="BLUE"){
			return 1;
		}
		else if(color=="YELLOW"){
			return 2;
		}
		else if(color=="GREEN"){
			return 3;
		}
		return -1;
	}

}