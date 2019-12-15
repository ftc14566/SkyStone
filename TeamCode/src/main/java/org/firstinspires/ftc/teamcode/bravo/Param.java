package org.firstinspires.ftc.teamcode.bravo;

public interface Param {

	void inc();
	void dec();

	String getLabel();
	Object getValue();
	String getValueString();
	String getRangeString();

	Param Clone();
}
