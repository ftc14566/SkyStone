package org.firstinspires.ftc.teamcode.testing;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SettingsSelector {

	private int settingsIndex = 0;
	private Settings[] settings = new Settings[]{
//			new TravelSettings(),
	};

	public Settings s;

	public SettingsSelector(){
		changeSettingsIndex(0); // initialize s;
	}

	public void onRightBumperPressed() { changeSettingsIndex(1); }

	public void onLeftBumperPressed() { changeSettingsIndex(-1);	}

	public void onPadRightPressed() { s.nextParam(); }

	public void onPadLeftPressed() { s.prevParam(); }

	public void onPadDownPressed(int count) { for(int i=0;i<count;++i) s.decParam(); }

	public void onPadUpPressed(int count) { for(int i=0;i<count;++i) s.incParam(); }

	public void onAButtonPressed() {
		s.execute();
	}

	public void onBButtonPressed(){}
	public void onXButtonPressed(){}
	public void onYButtonPressed(){}
	public void onLeftTriggerPressed(){}
	public void onRightTriggerPressed(){}
	public void onBackPressed(){}
	public void onStartPressed(){}
	public void onGuidePressed(){}

	public void showStatus( Telemetry telemetry ){
		s.show(telemetry);
	}

	void changeSettingsIndex(int delta){
		settingsIndex = (settingsIndex+delta+settings.length) % settings.length;
		s = settings[settingsIndex];
		s.resetIndex();
	}


}
