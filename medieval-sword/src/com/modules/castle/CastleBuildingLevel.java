package com.modules.castle;

/**
 * Information about a level for a building of castle
 */
public abstract class CastleBuildingLevel {

	private String buildTexture;

	int level;

	private int gold_price;
	private int wood_price;
	private int stone_price;

	private String description;
	private String name;

	public abstract void passWeek( TopCastle castle );

	public abstract void passDay( TopCastle castle );

	public abstract void up( TopCastle castle );

	public int getGoldPrice() {
		return gold_price;
	}

	public void setGoldPrice(int gold_price) {
		this.gold_price = gold_price;
	}

	public int getWoodPrice() {
		return wood_price;
	}

	public void setWoodPrice(int wood_price) {
		this.wood_price = wood_price;
	}

	public int getStonePrice() {
		return stone_price;
	}

	public void setStonePrice(int stone_price) {
		this.stone_price = stone_price;
	}

	public String getBuildTexture() {
		return buildTexture;
	}

	public void setBuildTexture(String buildTexture) {
		this.buildTexture = buildTexture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
