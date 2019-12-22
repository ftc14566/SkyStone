package org.firstinspires.ftc.teamcode.bravo;

public class ParamString extends Param {
	public ParamString(Config cfg){
		super(cfg,String.class);

		initialValue = cfg.stringValue();
		joinedOptions = cfg.stringOptions();
		options = joinedOptions.split(",");
		max = options.length-1;
	}

	// endregion

	@Override
	public Object adjust(Object src, int steps){
		if(steps==0) return src;
		int index = findNewIndex((String)src, steps);
		return options[index];
	}
	private int findNewIndex(String src, int steps){
		int index = findIndex(src) + steps;
		if(index<0) return 0;
		if(index>max) return max;
		return index;
	}
	private int findIndex(String s){
		for(int i=0;i<=max;++i)
			if(options[i]==s)
				return i;
		// could throw an exception here...
		return 0;
	}

	@Override
	String getParamTypeString(){ return "String"; } // instead of java.lang.String

	@Override
	String getScaledValueString(Object value){ return (String)value; }
	@Override
	String getRawValueString(Object value){ return (String)value; }
	@Override
	Object parseRawValueString(String s){ return s;	}

	@Override
	public Object getInitialValue() {
		return initialValue;
	}

	@Override
	protected String getRangeString() {
		return joinedOptions;
	}

	// region private fields

	private String initialValue;
	private String[] options;
	private String joinedOptions;
	private int max;
	// endregion

}
