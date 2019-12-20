package org.firstinspires.ftc.teamcode.bravo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MethodManager {

	static final MethodManager Singleton = new MethodManager();

	public void initForClass(Class<?> c){
		currentClass = c;
		final Method[] methods = c.getDeclaredMethods();
		ArrayList<MethodSignature> newItems = new ArrayList<MethodSignature>();
		for(int i=0;i<methods.length;++i){
			Method method = methods[i];
			if(Modifier.isPublic(method.getModifiers()))
				newItems.add(new MethodSignature(method));
		}
		items = newItems.toArray(new MethodSignature[0]);
	}

	public MethodSignature find(String stringRepresentative, Class<?> c){
		if(currentClass != c) initForClass(c);

		for(int i=0;i<items.length;++i){
			MethodSignature sig = items[i];
			if(sig.methodString ==stringRepresentative)
				return sig;
		}
		throw new IllegalStateException("method for "+stringRepresentative+" not found.");
	}

	public MethodSignature find(Method method){
		Class<?> methodClass = method.getDeclaringClass();
		if(methodClass != currentClass) initForClass(methodClass);

		for(int i=0;i<items.length;++i){
			MethodSignature sig = items[i];
			if(sig.method==method)
				return sig;
		}
		throw new IllegalStateException("unable to find string repo for method "+method.getName());
	}

	public MethodSignature[] getAllSignatures(Class<?> c){
		if(currentClass != c) initForClass(c);
		return items;
	}

	private MethodSignature[] items;
	private Class<?> currentClass;

}
