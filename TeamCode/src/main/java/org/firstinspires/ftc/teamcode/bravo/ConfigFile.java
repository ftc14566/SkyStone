package org.firstinspires.ftc.teamcode.bravo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConfigFile {
    public ConfigFile(String filename, Context context){
        this.filename = filename;
        this.context = context;
    }
    public String read(){
        MethodSerializer repo = new MethodSerializer();
        try {
            InputStream ii = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ii));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) out.append(line);
            reader.close();
            return out.toString();
        }catch(Exception ex){ return null; }
    }
    public void write(String content){
        try {
            OutputStream o = context.openFileOutput(filename, Context.MODE_PRIVATE);
            o.write(content.getBytes());
            o.close();

            File file = new File(filename);
            file.setReadable(true,false);
        }catch(Exception ex){}
    }
    private String filename;
    private Context context;


}
