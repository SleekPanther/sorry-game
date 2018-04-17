package structures;

import enums.Color;

public class ComputerData extends PlayerData{
	public String difficulity;
	public String meanness;

	public ComputerData(String name, Color color, String difficulity, String meanness){
		this.name = name;
		this.color = color;
		this.difficulity = difficulity;
		this.meanness = meanness;
	}

}