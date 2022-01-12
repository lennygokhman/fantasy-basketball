package base;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;

public class Team extends TeamAbstract {
	public PlayerNBA posPG;
	public PlayerNBA posSG;
	public PlayerNBA posG;
	public PlayerNBA posSF;
	public PlayerNBA posPF;
	public PlayerNBA posF;
	public PlayerNBA posC1;
	public PlayerNBA posC2;
	public PlayerNBA posUtil1;
	public PlayerNBA posUtil2;
	public PlayerNBA posBench1;
	public PlayerNBA posBench2;
	public PlayerNBA posBench3;
	public PlayerNBA posBench4;
	public PlayerNBA posIL1;
	public PlayerNBA posIL2;
	public PlayerNBA posIL3;

	public Team(Map<String, PlayerNBA> inputMap) {
		this.mapPlayerClass(inputMap);
	}

	@Override
	public void movePlayer(String startPos, String endPos) {
		// TODO Auto-generated method stub

	}

	private void mapPlayerClass(Map<String, PlayerNBA> inputMap) {
		PlayerNBA mappedPlayer;
		Set<String> playerSet;

		playerSet = inputMap.keySet();

		for (String player : playerSet) {
			mappedPlayer = inputMap.get(player);
			switch (mappedPlayer.currentposition) {
			case "PG":
				this.posPG = inputMap.get(player);
				break;
			case "SG":
				this.posSG = inputMap.get(player);
				break;
			case "G":
				this.posG = inputMap.get(player);
				break;
			case "SF":
				this.posSF = inputMap.get(player);
				break;
			case "PF":
				this.posPF = inputMap.get(player);
				break;
			case "F":
				this.posF = inputMap.get(player);
				break;
			case "C":
				if (this.posC1 == null)
					this.posC1 = inputMap.get(player);
				else
					this.posC2 = inputMap.get(player);
				break;
			case "Util":
				if (this.posUtil1 == null)
					this.posUtil1 = inputMap.get(player);
				else
					this.posUtil2 = inputMap.get(player);
				break;
			case "BN":
				if (this.posBench1 == null)
					this.posBench1 = inputMap.get(player);
				else if (this.posBench2 == null)
					this.posBench2 = inputMap.get(player);
				else if (this.posBench3 == null)
					this.posBench3 = inputMap.get(player);
				else
					this.posBench4 = inputMap.get(player);
				break;
			case "IL":
				if (this.posIL1 == null)
					this.posIL1 = inputMap.get(player);
				else if (this.posIL2 == null)
					this.posIL2 = inputMap.get(player);
				else
					this.posIL3 = inputMap.get(player);

			}
		}
	}
}
