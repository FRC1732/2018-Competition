package org.usfirst.frc.team1732.robot.config;

public class CTREParam {

	public final int id;
	public final boolean inverted;
	public final int masterID;

	public CTREParam(int ownID, boolean isInverted) {
		this(ownID, isInverted, ownID);
	}

	public CTREParam(int ownID, boolean isInverted, int masterID) {
		this.id = ownID;
		this.inverted = isInverted;
		this.masterID = masterID;
	}

	public boolean isFollower() {
		return id != masterID;
	}

}