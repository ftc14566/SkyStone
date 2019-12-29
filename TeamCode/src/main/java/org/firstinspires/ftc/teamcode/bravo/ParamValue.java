package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class ParamValue extends Param {

	// region factory / singleton stuff

	static private ArrayList<NameValuePair> registeredNamedValues = new ArrayList<ParamValue.NameValuePair>();

	static public void registerValue(String name, Object value){
		registeredNamedValues.add(new NameValuePair(name,value));
	}

	static public void registerFields(Object o){
		Class c = o.getClass();
		Field[] fields = c.getFields();
		for(int i=0;i<fields.length;++i){
			Field field = fields[i];
			try{
				Object fieldValue = field.get(o);
				if(fieldValue!=null)
					registerValue(field.getName(),fieldValue);
			}
			catch(IllegalAccessException ex){}
		}
	}

	static public ParamValue getValuesFor(Class parameterType){
		ArrayList<NameValuePair> matches = new ArrayList<NameValuePair>();
		for(int i = 0; i< registeredNamedValues.size(); ++i){
			NameValuePair pair = registeredNamedValues.get(i);
			Class c = pair.value.getClass();
			if(parameterType.isAssignableFrom(c))
				matches.add(pair);
		}
		if(matches.size()==0) return null;

		Collections.sort(matches);
		// remove duplicates
		for(int i=1;i<matches.size();++i){
			if(matches.get(i).name == matches.get(i-1).name)
				matches.remove(i--);
		}

		NameValuePair[] options = matches.toArray(new NameValuePair[0]);
		return new ParamValue(parameterType, options );
	}

	static private class NameValuePair implements Comparable<NameValuePair>{
		public NameValuePair(String name,Object value){this.name=name;this.value=value;}
		public String name;
		public Object value;

		@Override
		public int compareTo(NameValuePair o) {
			return name.compareTo(o.name);
		}
	}

	// endregion

	public ParamValue(Class paramType, NameValuePair[] pairs){
		super(null, paramType);
		this.options = pairs;
		max = this.options.length-1;

		//
		StringBuilder b = new StringBuilder();
		for(int i = 0; i< options.length; ++i){
			if(i!=0)b.append(',');
			b.append(options[i].name);
		}
		rangeString = b.toString();

	}

	@Override
	String getScaledValueString(Object value) {
		return getRawValueString(value);
	}

	@Override
	public Object adjust(Object value, int steps) {
		int newIndex = findNewIndex(value,steps);
		return options[newIndex].value;
	}

	int findNewIndex(Object value, int steps){
		int index = findIndex(value) + steps;
			if(index<0) return 0;
			if(index>max) return max;
			return index;
	}

	int findIndex(Object value){
		for(int index = 0; index< options.length; ++index)
			if(options[index].value==value)
				return index;
		return 0; // could throw exception...
	}

	@Override
	Object getInitialValue() {
		return options[0].value;
	}

	@Override
	protected String getRangeString() {
		return rangeString;
	}

	@Override
	String getRawValueString(Object value) {
		int index = findIndex(value);
		return options[index].name;
	}

	@Override
	Object parseRawValueString(String s) {
		for(int index = 0; index< options.length; ++index)
			if(options[index].name==s)
				return options[index].value;
		return null;//???
	}

	NameValuePair[] options;
	int max;
	String rangeString;

}
