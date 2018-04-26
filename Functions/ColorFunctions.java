package Functions;
import enums.Color;

//Convert between integers representing players and their colors
public class ColorFunctions {
	public static int colorToPlayerIndex(Color color){
		return colorToPlayerIndex(color.name());
	}
	
	public static int colorToPlayerIndex(String color){
		color = color.toUpperCase().trim();
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

	public static Color stringToColor(String stringColor){
		stringColor = stringColor.toUpperCase().trim();
		if(stringColor.equals("RED")){
			return Color.RED;
		}
		else if(stringColor.equals("BLUE")){
			return Color.BLUE;
		}
		else if(stringColor.equals("YELLOW")){
			return Color.YELLOW;
		}
		else if(stringColor.equals("GREEN")){
			return Color.GREEN;
		}
		return Color.ANY;
	}

}