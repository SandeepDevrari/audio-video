package com.example.angry.audiovideo;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Upload_Movies extends AppCompatActivity {

    Button bt1;
    Spinner sp1;
    String list[],filename;
    AssetManager fileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__movies);
        bt1=(Button)findViewById(R.id.uploadmovies);
        sp1=(Spinner)findViewById(R.id.listmovie);
        fileList=getAssets();
        try {
            list = fileList.list("movies");
        }
        catch (IOException io)
        {
            io.getStackTrace();
            System.out.println(io.getMessage());
        }
        ArrayAdapter<String> adapt=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapt);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filename=sp1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing to do here
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedInputStream bis=null;
                ByteArrayOutputStream baf=new ByteArrayOutputStream();
                try
                {
                    InputStream path = fileList.open("movies/"+filename);
                    bis = new BufferedInputStream(path,1024);
                    int current = 0;
                    while ((current = bis.read()) != -1)
                    {
                        baf.write((byte) current);
                    }
                }
                catch(Exception e)
                {
                    e.getStackTrace();
                    System.out.println(e.getMessage());
                }
                byte[] data1=baf.toByteArray();
                File file = new File(Environment.getExternalStorageDirectory(),filename);
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(data1);
                    fos.flush();
                    fos.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
