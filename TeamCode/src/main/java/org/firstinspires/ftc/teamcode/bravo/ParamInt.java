package org.firstinspires.ftc.teamcode.bravo;

public class ParamInt extends Param {

	// region constructors

	public ParamInt(Config cfg){
		super(cfg,int.class);

		step = (int)Math.round(cfg.step());
		if(step == 0 ) step = 1; else if(step<0) step = -step;

		min = (int)Math.round(cfg.min());
		max = (int)Math.round(cfg.max());
		if(max < min){ int temp = max; max = min; min = temp; }
		if(min == max) max = min + step * 100;

		displayScale = (int)Math.round(cfg.displayScale());

		initialValue = clip( (int)Math.round(cfg.value() ));

	}

	// endregion

	@Override
	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		return clip( (int)src * steps*this.step );
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
		return format(min) + " to " + format(max);
	}

	// region private fields

	int initialValue;
	int min;
	int max;
	int step;
	int displayScale;
	// endregion


}
