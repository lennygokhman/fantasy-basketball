package base;

import java.util.Map;

public class PlayerNBA extends PlayerAbstract{
	public String name;
	public String position;
	public String currentposition;
	public String team;
	public String fieldGoalPer;
	public String fieldGoalMA;
	public String freeThrowPer;
	public String freeThrowMA;
	public String threesMade;
	public String points;
	public String rebounds;
	public String assists;
	public String steals;
	public String blocks;
	public String turnOvers;
	
	public PlayerNBA(Map<String, String> playerMap) {
		super(playerMap);
		this.name = playerMap.get("Name");
		this.position = playerMap.get("EligiblePositions");
		this.currentposition = playerMap.get("CurrentPosition");
		this.fieldGoalPer = playerMap.get("FG%");
		this.fieldGoalMA = playerMap.get("FG M/A");
		this.freeThrowPer = playerMap.get("FT%");
		this.freeThrowMA = playerMap.get("FT M/A");
		this.points = playerMap.get("PTS");
		this.threesMade = playerMap.get("3PM");
		this.assists = playerMap.get("AST");
		this.rebounds = playerMap.get("REB");
		this.blocks = playerMap.get("BLK");
		this.steals = playerMap.get("STL");
		this.turnOvers = playerMap.get("TO");
	}

}
