package org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing;

import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.math.Curve;
import org.usfirst.frc.team1732.robot.util.Util;

public class PathSegment {

	public final Waypoint start;
	public final Waypoint end;
	public final Curve curve;
	public final boolean noAccel;

	public PathSegment(Waypoint start, Waypoint end, Curve curve, boolean noAccel) {
		this.start = start;
		this.end = end;
		this.curve = curve;
		this.noAccel = noAccel && Util.epsilonEquals(start.vel, end.vel);
	}

}