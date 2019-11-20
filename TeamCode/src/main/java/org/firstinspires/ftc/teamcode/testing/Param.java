package org.firstinspires.ftc.teamcode.testing;

public interface Param {
	void inc();

	void dec();

	void appendSummary(StringBuilder builder);

	void showDetails(org.firstinspires.ftc.robotcore.external.Telemetry telemetry);
}
