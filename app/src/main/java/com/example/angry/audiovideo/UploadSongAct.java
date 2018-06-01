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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadSongAct extends AppCompatActivity {

    Button bt1;
    Spinner sp1;
    String list[],filename;
    AssetManager fileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_song);
        bt1=(Button)findViewById(R.id.upload);
        sp1=(Spinner)findViewById(R.id.listmovie);
        fileList=getAssets();
        try {
            list = fileList.list("songs");
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
                //ArrayList<Byte> buf=new ArrayList<Byte>();
                ByteArrayOutputStream baf=new ByteArrayOutputStream();
                //byte[]buf=new byte[1024];
                try
                {
                    InputStream path = fileList.open("songs/"+filename);
                    System.out.println("File Successfully Opened");
                    bis = new BufferedInputStream(path,1024);
                    int current = 0;
                    while ((current = bis.read()) != -1)
                    {
                        baf.write((byte)current);
                    }
                }
                catch(Exception e)
                {
                    System.out.println("permission denied");
                    e.getStackTrace();
                    System.out.println(e.getMessage());
                }
                byte[] data1=baf.toByteArray();
                File file = new File(Environment.getExternalStorageDirectory(),filename);
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(data1);
                    //Toast.makeText(UploadSongAct.class,"Song Transfered",Toast.LENGTH_LONG).show();
                    System.out.println("File Transfered!!");
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
