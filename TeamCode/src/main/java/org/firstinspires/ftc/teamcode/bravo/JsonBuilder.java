package org.firstinspires.ftc.teamcode.bravo;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class JsonBuilder {

    public JsonBuilder(){
        buf = new StringBuilder();
    }

    public JsonBuilder(StringBuilder builder){
        this.buf = builder;
    }

    public void append( MethodSignature[] steps ){
        buf.append('[');
        for(int i=0;i<steps.length;++i){
            if(i!=0) buf.append("\r\n\t,");
            append(steps[i]);
        }
    }

    public void append( MethodSignature sig ){
        if(sig==null){ appendNull(); return; }

        buf.append('{');
        appendProperyPrefix("method");
        appendString(sig.getName());
        comma();
        append(sig.params);
        buf.append('}');
    }

    public void append( Param[] param ) {
        buf.append('[');
        for(int i=0;i<param.length;++i){
            if(i!=0) comma();
            append(param[i]);
        }
        buf.append(']');
    }

    public void append( Param param ){
        buf.append('{');
        appendProperty("value", param.value); comma();
        appendProperty("isTrue", param.isTrue); comma();
//        appendProperty("label",param.label);
//        appendProperty("units", param.units); comma();
//        appendProperty("min", param.min); comma();
//      appendProperty("max", param.max); comma();
//        appendProperty("step", param.step); comma();
//        appendProperty("displayScale", param.displayScale); comma();
//        appendProperty("trueString", param.trueString); comma();
//        appendProperty("falseString",param.falseString);
        buf.append('}');
    }

    // region appendProperty

    public void appendProperyPrefix(String name){
        buf.append(name);
        buf.append(':');
    }

    public void appendProperty(String name, double value ){
        appendProperyPrefix(name);
        buf.append(value);
    }

    public void appendProperty(String name, boolean value ){
        appendProperyPrefix(name);
        buf.append(value);
    }

    public void appendProperty(String name, int value ){
        appendProperyPrefix(name);
        buf.append(value);
    }

    public void appendProperty(String name, String value ){
        appendProperyPrefix(name);
        appendString(value);
    }

    // endregion

    public void comma(){ buf.append(',');}
    public void appendNull(){ buf.append("null");}

    public void appendString(String s){
        buf.append('\"');
        buf.append(s); // !!! Escape!
        buf.append('\"');
    }


    public void saveTo(String filename){
        Context ctx = null;
        try {
            FileOutputStream fileout = ctx.openFileOutput(filename, MODE_PRIVATE );
            OutputStreamWriter outputWriter = new OutputStreamWriter( fileout );
            outputWriter.write("hello, world" );
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    StringBuilder buf;

}
