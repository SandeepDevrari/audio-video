package com.example.angry.audiovideo;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AVHomeAct extends AppCompatActivity {

    RadioGroup rgb1;
    RadioButton rb1,rb2,rb3,rb4,rb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avhome);
        rgb1=(RadioGroup)findViewById(R.id.radioGroup1);
        rb1=(RadioButton)findViewById(R.id.radioButton1);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);
        rb5=(RadioButton)findViewById(R.id.radioButton5);
        rgb1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                i = rgb1.getCheckedRadioButtonId();
                if (rb1.getId() == i) {
                    Intent intt = new Intent(AVHomeAct.this,UploadSongAct.class);
                    startActivity(intt);
                } else if (rb2.getId() == i) {
                    Intent intt = new Intent(AVHomeAct.this,Upload_Movies.class);
                    startActivity(intt);
                } else if (rb3.getId() == i) {
                    Intent intt = new Intent(AVHomeAct.this,SongsListAct.class);
                    startActivity(intt);
                }
                else if (rb4.getId() == i) {
                    Intent intt = new Intent(AVHomeAct.this,MovieListAct.class);
                    startActivity(intt);
                }
                else
                {
                    Intent intt = new Intent(AVHomeAct.this,DeleteAV.class);
                    startActivity(intt);
                }
            }
        });
    }
}
