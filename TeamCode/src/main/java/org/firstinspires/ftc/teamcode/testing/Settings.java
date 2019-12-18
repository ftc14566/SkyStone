package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Settings {
	protected Settings(String label){
		_label = label;
		this.params = new Param[0];
	}

	public void execute(){
//		buildCommand().exec();
	}

//	abstract public Cmd buildCommand();

	public void nextParam(){
		++paramIndex;
		if(paramIndex==params.length) paramIndex = 0;
	}

	public void resetIndex(){ paramIndex=0; }

	public void prevParam(){
		if(paramIndex==0) paramIndex = params.length;
		--paramIndex;
	}

	public void incParam(){
		params[paramIndex].inc();
	}

	public void decParam(){
		params[paramIndex].dec();
	}

	public void show(Telemetry telemetry){
		showSummary( telemetry );
		telemetry.addData("-","-");
		showParamDetails(telemetry);
		telemetry.update();
	}

	public void showSummary(Telemetry telemetry){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<params.length;++i){
			builder.append(' ');
			if(i==paramIndex) builder.append("[");
			params[i].appendSummary(builder);
			if(i==paramIndex) builder.append("]");
		}
		telemetry.addData(_label,  builder.toString() );
	}

	void showParamDetails(Telemetry telemetry){
		params[paramIndex].showDetails(telemetry);
	}

	private String _label;
	protected Param[] params;
	private int paramIndex = 0;

}
