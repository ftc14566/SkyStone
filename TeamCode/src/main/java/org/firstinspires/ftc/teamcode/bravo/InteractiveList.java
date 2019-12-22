package org.firstinspires.ftc.teamcode.bravo;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class InteractiveList {

	InteractiveList _mode;

	public InteractiveList(){
		_mode = this;
	}

	public void DisplayStatus(Telemetry telemetry){
		telemetry.addData("status","override me!");
		telemetry.update();
	}

	public void doOtherWork(Gamepad gamepad){}

	public void DpadUp_Pressed(){
		if(curIndex==0) return;
		int oldIndex = curIndex--;
		if(topOfPageIndex > curIndex) topOfPageIndex = curIndex;
		indexChanged(oldIndex);
	}
	public void DpadDown_Pressed(){
		int last=getLastIndex();
		if(curIndex == last) return;
		int oldIndex = curIndex++;
		if(topOfPageIndex < curIndex +1-LinesPerPage) topOfPageIndex = curIndex +1-LinesPerPage;
		indexChanged(oldIndex);
	}

	public void DpadLeft_Pressed(){}
	public void DpadRight_Pressed(){}
	public void DpadUp_Released(){}
	public void DpadDown_Released(){}

	public void DpadLeft_Released(){}
	public void DpadRight_Released(){}
	public void A_Pressed(){}
	public void B_Pressed(){}
	public void X_Pressed(){}
	public void Y_Pressed(){}
	public void Back_Pressed(){}
	public void Start_Pressed(){}
	public void Guide_Pressed(){}
	public void LeftBumper_Pressed(){}
	public void RightBumper_Pressed(){}

	abstract protected int getLastIndex();
	protected void indexChanged(int oldIndex){};

	int topOfPageIndex = 0;
	int curIndex = 0;
	static final int LinesPerPage = 4;


}
