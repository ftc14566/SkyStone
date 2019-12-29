package org.firstinspires.ftc.teamcode.bravo;

import java.util.Collection;

// String / Collection helper class for C# programmers not familiar with Java.
public class Linq {

	static public String getShortName(Class c){
		String s = c.getName();
		int i = s.lastIndexOf('.');
		return (i==-1) ? s : s.substring(i+1);
	}

	static public String join(String glue, String[] items){
		StringBuilder b = new StringBuilder();
		for(int i=0;i<items.length;++i){
			if(i!=0) b.append(glue);
			b.append(items[i]);
		}
		return items.toString();
	}

	static public String join(String glue, Collection<String> items){
		return join(glue,items.toArray(new String[0]));
	}


}
