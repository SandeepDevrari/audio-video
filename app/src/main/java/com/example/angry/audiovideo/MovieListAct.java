package com.example.angry.audiovideo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SimpleExpandableListAdapter;
import android.widget.VideoView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieListAct extends AppCompatActivity {

    private static final String NAME = "NAME";

    private ExpandableListAdapter mAdapter;

    private String group[] = {"My Favourite Movie"};


    private String[][] child = { { " ", " " }, { " ", " " } };

    ExpandableListView elv1;
    VideoView vv1;
    ImageButton vib1,vib2,vib3;
    MediaController mediaController;
    ArrayList<HashMap<String, String>> movielist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        movielist = new ArrayList<HashMap<String, String>>();
        elv1=(ExpandableListView)findViewById(R.id.expandable2);
        vv1=(VideoView)findViewById(R.id.videoView1);
        mediaController= new MediaController(this);
        mediaController.setAnchorView(vv1);
        MovieListAct.MM mm=new MovieListAct.MM();
        movielist=mm.getPlayList();
        System.out.println(movielist.get(0));
        for(int i=0,j=0;i<movielist.size();i++,j++)
        {
            String temp[]=String.valueOf(movielist.get(i)).split(",");
            child[i][j]=temp[1].substring(12,temp[1].length()-1);
            System.out.println("movie: "+child[i][j]);
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
                    new String[]{NAME}, new int[]{android.R.id.text1},
                    childData, android.R.layout.simple_expandable_list_item_2,
                    new String[]{NAME}, new int[]{android.R.id.text1});
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444"+mAdapter.isEmpty()+"#####3333333333333333333333"+mAdapter.getChildrenCount(0));
            elv1.setAdapter(mAdapter);
            elv1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                    Object data = expandableListView.getExpandableListAdapter().getChild(i, i1);
                    String data_v = String.valueOf(data);
                    //Toast.makeText(SongsListAct.class,data_value,Toast.LENGTH_LONG).show();
                    String data_value = data_v.substring(6, data_v.length() - 1);
                    try {
                        FileDescriptor fd = null;
                        File baseDir = Environment.getExternalStorageDirectory();
                        String moviePath = baseDir.getAbsolutePath() + "/" + data_value + ".mp4";
                        System.out.println("movie: " + moviePath);
                        FileInputStream fis = new FileInputStream(moviePath);
                        fd = fis.getFD();
                        if (fd != null) {
                            //MediaPlayer mediaPlayer = MediaPlayer.create(MovieListAct.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + data_value + ".mp4"));
                            //System.out.println("MediaPlayer Started@@@@222");
                            //mediaPlayer.start();

                            //Creating MediaController
                           // MediaController mediaController= new MediaController(this);
                            //mediaController.setAnchorView(vv1);

                            //specify the location of media file
                            Uri uri=Uri.parse(moviePath);//Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");
                            vv1=(VideoView)findViewById(R.id.videoView1);
                            vv1.setVisibility(View.VISIBLE);
                            //Setting MediaController and URI, then starting the videoView
                            //vv1.setMediaController(mediaController);
                            vv1.setVideoPath(uri.toString());
                            //vv1.requestFocus();
                            vib1=(ImageButton)findViewById(R.id.VimageButton1);
                            vib1.setVisibility(View.VISIBLE);
                            vib1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    vv1.start();
                                }
                            });
                            vib2=(ImageButton)findViewById(R.id.VimageButton2);
                            vib2.setVisibility(View.VISIBLE);
                            vib2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    vv1.pause();
                                }
                            });
                            vib3=(ImageButton)findViewById(R.id.VimageButton3);
                            vib3.setVisibility(View.VISIBLE);
                            vib3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    vv1.stopPlayback();
                                    vib1.setVisibility(View.INVISIBLE);
                                    vv1.setVisibility(View.INVISIBLE);
                                    vib2.setVisibility(View.INVISIBLE);
                                    vib3.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                    return true;
                }
            });
        }
    }


class MM {
    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    private ArrayList<HashMap<String, String>> movieList = new ArrayList<HashMap<String, String>>();
    private String mp4Pattern = ".mp4";

    // Constructor
    public MM() {
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
                        addMovieToList(file);
                    }
                }
            }
        }

        return movieList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addMovieToList(file);
                    }

                }
            }
        }
    }

    private void addMovieToList(File movie) {
        if (movie.getName().endsWith(mp4Pattern)) {
            HashMap<String, String> movieMap = new HashMap<String, String>();
            movieMap.put("movieTitle", movie.getName().substring(0, (movie.getName().length() - 4)));
            movieMap.put("moviePath", movie.getPath());
            movieList.add(movieMap);
        }
    }
}}