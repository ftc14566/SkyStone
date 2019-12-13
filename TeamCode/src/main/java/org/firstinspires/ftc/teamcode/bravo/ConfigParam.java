package org.firstinspires.ftc.teamcode.bravo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface ConfigParam {
	void inc();
	void dec();

	Object getValue();
	Class<?> getValueClass();

	void show(Telemetry telemetry);
	ConfigParam Clone();
}
