package base;

import java.sql.Array;
import java.util.List;

public class Player {
	private String name;
	private String team;
	private String position;
	private List<String> playerAttributes;
	
	
	public List<String> main(Player player) {
		// TODO Auto-generated method stub
		
		return playerAttributes;
	}
	
	public void setName(String playerName) {
		this.name = playerName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTeam(String teamName) {
		this.team = teamName;
	}
	
	public String getTeam() {
		return team;
	}
	
	public void setPosition(String positionName) {
		this.position = positionName;
	}
	
	public String getPosition() {
		return position;
	}

}
