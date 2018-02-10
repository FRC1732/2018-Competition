package org.usfirst.frc.team1732.robot.conf;

public class ConfigNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5462973943032620943L;

	public ConfigNotFoundException(String arg0) {
		super(arg0);
		Config.loaded = false;
	}

	public ConfigNotFoundException(Throwable arg0) {
		super(arg0);
		Config.loaded = false;
	}

	public ConfigNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		Config.loaded = false;
	}

	public ConfigNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		Config.loaded = false;
	}

}
