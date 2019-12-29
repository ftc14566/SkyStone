package org.firstinspires.ftc.teamcode.bravo;

public class ParamInt extends Param {

	// region constructors

	public ParamInt(Config cfg){
		super(cfg,int.class);

		if(cfg!=null){
			stepSize = (int)Math.round(cfg.step());
			min = (int)Math.round(cfg.min());
			max = (int)Math.round(cfg.max());
			displayScale = (int)Math.round(cfg.displayScale());
			initialValue = (int)Math.round(cfg.value() );
		}

		if(stepSize == 0 ) stepSize = 1; else if(stepSize <0) stepSize = -stepSize;

		if(max < min){ int temp = max; max = min; min = temp; }
		if(min == max) max = min + stepSize * 100;

		initialValue = clip( initialValue );

	}

	// endregion

	@Override
	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		return clip( (int)src + steps*this.stepSize);
	}

	int clip(int value){
		if(value<min) return min;
		if(value>max) return max;
		return value;
	}

	@Override
	String getScaledValueString(Object value){ return format((int)value ); }

	String format(int i){ return (i* displayScale)+""; }

	@Override
	String getRawValueString(Object value){ return ((Integer)value).toString(); }
	@Override
	Object parseRawValueString(String s){ return Integer.parseInt(s);	}

	@Override
	public Object getInitialValue() {
		return initialValue;
	}

	@Override
	protected String getRangeString() {
		return format(min) + " to " + format(max) + " ("+format(stepSize)+")";
	}

	// region private fields

	int initialValue;
	int min;
	int max;
	int stepSize;
	int displayScale;
	// endregion


}
