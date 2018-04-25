package structures;

import enums.Color;
import Functions.ColorFunctions;

public class PlayerData implements Comparable<PlayerData>{
	public String name;
	public Color color;

	//For computers only
	public boolean smartness;
	public boolean meanness;
	@Override
	
	public int compareTo(PlayerData playerData2) {
		int player1ColorIndex = ColorFunctions.colorToPlayerIndex(this.color);
		int player2ColorIndex = ColorFunctions.colorToPlayerIndex(playerData2.color);
		return player1ColorIndex - player2ColorIndex;
	}

}