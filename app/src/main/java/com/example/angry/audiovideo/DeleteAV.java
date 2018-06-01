package com.example.angry.audiovideo;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteAV extends AppCompatActivity {

    String list[]=null;
    private static final String NAME = "NAME";
    private ExpandableListAdapter mAdapter;
    private String group[] = {"SONGS/VIDEOS"};
    private String[][] child = { { " ", " " }, { " ", " " } };
    ExpandableListView elv1;
    ArrayList<HashMap<String, String>> songslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_av);
        songslist = new ArrayList<HashMap<String, String>>();
        elv1=(ExpandableListView)findViewById(R.id.expandable1);
        DeleteAV.SM sm=new DeleteAV.SM();
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
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444"+mAdapter.isEmpty()+"#####3333333333333333333333"+mAdapter.getChildrenCount(0));
            elv1.setAdapter(mAdapter);
            elv1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Object data = expandableListView.getExpandableListAdapter().getChild(i, i1);
                    String data_v = String.valueOf(data);
                    //Toast.makeText(SongsListAct.class,data_value,Toast.LENGTH_LONG).show();
                    String data_value = data_v.substring(6, data_v.length() - 1);
                    return true;
                }
            });
        }
    }

    class SM {
        final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
        private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
        private String mp3Pattern = ".mp3";
        private String mp4Pattern = ".mp4";

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
            if (song.getName().endsWith(mp3Pattern) || song.getName().endsWith(mp4Pattern)) {
                HashMap<String, String> songMap = new HashMap<String, String>();
                songMap.put("songTitle", song.getName().substring(0, (song.getName().length() - 4)));
                songMap.put("songPath", song.getPath());
                songsList.add(songMap);
            }
        }
    }
}
