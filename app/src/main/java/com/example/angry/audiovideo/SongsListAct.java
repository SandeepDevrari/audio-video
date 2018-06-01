package com.example.angry.audiovideo;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongsListAct extends AppCompatActivity {

    private static final String NAME = "NAME";

    private ExpandableListAdapter mAdapter;

    private String group[] = {"My Favourite Songs"};

    ImageView iv1;
    private String[][] child = { { " ", " " }, { " ", " " } };
    MediaPlayer mediaPlayer;
    ExpandableListView elv1;
    ImageButton ib1,ib2,ib3;
    ArrayList<HashMap<String, String>> songslist;// = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        songslist = new ArrayList<HashMap<String, String>>();
        elv1=(ExpandableListView)findViewById(R.id.expandable1);
        SM sm=new SM();
        songslist=sm.getPlayList();
        System.out.println(songslist.get(0));
        for(int i=0,j=0;i<songslist.size();i++,j++)
        {
            String temp[]=String.valueOf(songslist.get(i)).split(",");
            child[i][j]=temp[0].substring(11);
            System.out.println("song: "+child[i][j]);
        }
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < group.length; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, group[i]);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < child[i].length; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, child[i][j]);
            }
            childData.add(children);
            System.out.println("@@@@@@@@@@@@@@@@@@22"+childData.get(0));
            mAdapter = new SimpleExpandableListAdapter(this, groupData,
                    android.R.layout.simple_expandable_list_item_1,
                    new String[] { NAME }, new int[] { android.R.id.text1 },
                    childData, android.R.layout.simple_expandable_list_item_2,
                    new String[] { NAME }, new int[] { android.R.id.text1 });
            //setListAdapter(mAdapter);
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444"+mAdapter.isEmpty()+"#####3333333333333333333333"+mAdapter.getChildrenCount(0));
            elv1.setAdapter(mAdapter);
            elv1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Object data=expandableListView.getExpandableListAdapter().getChild(i,i1);
                    String data_v=String.valueOf(data);
                    //String str=elv1.getChildid().toString();
                    //Toast.makeText(SongsListAct.class,data_value,Toast.LENGTH_LONG).show();
                    String data_value=data_v.substring(6,data_v.length()-1);
                    //System.out.println("SELECTED: "+);
                    try {
                        FileDescriptor fd = null;

                       /* if (isInInternalMemory(data_value)) {
                            int audioResourceId = mContext.getResources().getIdentifier(data_value, "raw", "com.ampirik.audio");
                            AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(audioResourceId);
                            fd = afd.getFileDescriptor();
                        } else if (isInSdCard(data_value)) {*/
                            File baseDir = Environment.getExternalStorageDirectory();
                            String audioPath = baseDir.getAbsolutePath()+"/"+data_value+".mp3";
                            System.out.println("SONG: "+audioPath);
                            FileInputStream fis = new FileInputStream(audioPath);
                            fd = fis.getFD();
                       // }
                        if (fd != null) {
                            //MediaPlayer mediaPlayer = new MediaPlayer();

                            mediaPlayer = MediaPlayer.create(SongsListAct.this, Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/"+ data_value+".mp3"));
                            //mediaPlayer.setDataSource(fd);
                            System.out.println("MediaPlayer Started@@@@222");
                            //mediaPlayer.prepare();
                            //mediaPlayer.start();
                            ib1=(ImageButton)findViewById(R.id.imageButton1);
                            ib1.setVisibility(View.VISIBLE);
                            ib1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mediaPlayer.start();
                                }
                            });
                            ib2=(ImageButton)findViewById(R.id.imageButton2);
                            ib2.setVisibility(View.VISIBLE);
                            ib2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mediaPlayer.pause();
                                }
                            });
                            ib3=(ImageButton)findViewById(R.id.imageButton3);
                            ib3.setVisibility(View.VISIBLE);
                            ib3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mediaPlayer.stop();
                                    ib1.setVisibility(View.INVISIBLE);
                                    iv1.setVisibility(View.INVISIBLE);
                                    ib2.setVisibility(View.INVISIBLE);
                                    ib3.setVisibility(View.INVISIBLE);
                                }
                            });
                            iv1=(ImageView)findViewById(R.id.imageView1);
                            iv1.setVisibility(View.VISIBLE);
                            //ib1.setVisibility(View.VISIBLE);
                            //iv1.setVisibility(View.VISIBLE);
                            //ib2.setVisibility(View.VISIBLE);
                            //ib3.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                    return true;
                }
            });

            //ExpandableListAdapter eadap=new Expan
        //elv1.setAdapter(eadap);
        //ExpandableListAdapter eadap = new ExpandableListAdapter(this, songslist);
        //elv1.setAdapter(eadap);
    }
}

class SM {
    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private String mp3Pattern = ".mp3";

    // Constructor
    public SM() {
    }

    public ArrayList<HashMap<String, String>> getPlayList() {
        System.out.println("Starting path "+MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println("listing "+file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }

        return songsList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(mp3Pattern)) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            songMap.put("songTitle", song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());
            songsList.add(songMap);
        }
    }
}}