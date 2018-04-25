package structures;

import enums.Color;

public class ComputerData extends PlayerData{

	public ComputerData(String name, Color color, boolean smartness, boolean meanness){
		this.name = name;
		this.color = color;
		this.smartness = smartness;
		this.meanness = meanness;
	}

	public String difficulty(){
		if(smartness){
			return "smart";
		}
		return "dumb";
	}

	public String meanness(){
		if(meanness){
			return "mean";
		}
		return "nice";
	}

}