package org.usfirst.frc.team1732.robot.controlutils.guiPathing;

import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.MyIterator;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointPair;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.VelocityPoint;

public abstract class Path {
	public final double[][] path = new double[0][0];
	public final double max = 0;
	// total time (arb)
	public final double time = 0;
	public final double inc = 0;

	public double[] getPointAt(double t) {
		return path[(int) (time * inc)];
	}

	public PointProfile getPath() {
		return new PointProfile(new MyIterator<PointPair<VelocityPoint>>(null, 0) {
			private int current = 0;

			@Override
			public boolean hasNext() {
				return current < path.length;
			}

			@Override
			public PointPair<VelocityPoint> next() {
				// Time(arb), Heading(deg), Left_vel(inches/Time), Right_vel(inches/Time)
				double[] cur = path[current];
				VelocityPoint left = new VelocityPoint(cur[1], cur[2]);
				VelocityPoint right = new VelocityPoint(cur[1], cur[3]);

				current++;
				return new PointPair<VelocityPoint>(left, right);
			}
		}, 0);
	}
}
