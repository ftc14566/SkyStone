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
