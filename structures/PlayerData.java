package structures;

import enums.Color;
import Functions.ColorFunctions;

//Holds data to pass from the menu screen to the game screen to instantiate players
public class PlayerData implements Comparable<PlayerData>{
	public String name;
	public Color color;

	//For computers only but global for container type
	public boolean smartness;
	public boolean meanness;

	//Sort by color so creation of players can be red, blue, yellow, green clockwise around the board
	@Override
	public int compareTo(PlayerData playerData2) {
		int player1ColorIndex = ColorFunctions.colorToPlayerIndex(this.color);
		int player2ColorIndex = ColorFunctions.colorToPlayerIndex(playerData2.color);
		return player1ColorIndex - player2ColorIndex;
	}

}