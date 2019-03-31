package com.offerup.newtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubeVideos extends YouTubeBaseActivity {

  // YouTubePlayerView mYoutubePlayerView;
 //  Button btnPlay;
   YouTubePlayer.OnInitializedListener mOnInitListen;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_absfragment);






           //btnPlay = (Button) findViewById(R.id.btnPlay);
          // mYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.nYoutubeView);


           mOnInitListen = new YouTubePlayer.OnInitializedListener() {
               @Override
               public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                       youTubePlayer.loadVideo("UWnwXUfa3bI");

               }

               @Override
               public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

               }
           };


        //   btnPlay.setOnClickListener(new View.OnClickListener() {
          //     @Override
         //      public void onClick(View view) {
        //           mYoutubePlayerView.initialize(YoutubePlayerConfig.getApiKey(), mOnInitListen);

          //     }
         //  });



       }





   }

