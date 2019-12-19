package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MethodManager {

	public MethodManager(Class c){
		final Method[] methods = c.getDeclaredMethods();
		for(int i=0;i<methods.length;++i){
			Method method = methods[i];
			if(Modifier.isPublic(method.getModifiers()))
				items.add(new MethodSignature(method));
		}
	}

	public MethodSignature find(String stringRepresentative){
		for(int i=0;i<items.size();++i){
			MethodSignature sig = items.get(i);
			if(sig.methodString ==stringRepresentative)
				return sig;
		}
		throw new IllegalStateException("method for "+stringRepresentative+" not found.");
	}

	public MethodSignature find(Method method){
		for(int i=0;i<items.size();++i){
			MethodSignature sig = items.get(i);
			if(sig.method==method)
				return sig;
		}
		throw new IllegalStateException("unable to find string repo for method "+method.getName());
	}

	public String[] getAllMethodStringReps(){
		String[] names = new String[items.size()];
		for(int i=0;i<items.size();++i)
			names[i] = items.get(i).methodString;
		return names;
	}

	private ArrayList<MethodSignature> items = new ArrayList<MethodSignature>();


}
