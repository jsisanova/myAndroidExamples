/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivity extends AppCompatActivity {

  PlayerView playerView;
  private SimpleExoPlayer player;

  private boolean playWhenReady = true;
  private int currentWindow = 0;
  private long playbackPosition = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);

    playerView = findViewById(R.id.video_view);

  }

  // Starting with API level 24 Android supports multiple windows. As our app can be visible but not active in split window mode,
  // we need to initialize the player in onStart.
  @Override
  public void onStart() {
    super.onStart();
    if (Util.SDK_INT >= 24) {
      initializePlayer();
    }
  }

  // Before API level 24 we wait as long as possible until we grab resources, so we wait until onResume before initializing the player.
  @Override
  public void onResume() {
    super.onResume();
    // hideSystemUi is a helper method called in onResume which allows us to have a full screen experience
    hideSystemUi();
    if ((Util.SDK_INT < 24 || player == null)) {
      initializePlayer();
    }
  }

  // Before API Level 24 there is no guarantee of onStop being called. So we have to release the player as early as possible in onPause.
  // Starting with API Level 24 (which brought multi and split window mode) onStop is guaranteed to be called.
  // In the paused state our activity is still visible so we wait to release the player until onStop.
  @Override
  public void onPause() {
    super.onPause();
    if (Util.SDK_INT < 24) {
      releasePlayer();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (Util.SDK_INT >= 24) {
      releasePlayer();
    }
  }

  private void initializePlayer() {
    // To play streaming media we need to create an ExoPlayer object
    player = ExoPlayerFactory.newSimpleInstance(this);
    // Bind the Player to corresponding view
    playerView.setPlayer(player);

    // Audio
//    Uri uri = Uri.parse(getString(R.string.media_url_mp3));
    // Video
    Uri uri = Uri.parse(getString(R.string.media_url_mp4));
    MediaSource mediaSource = buildMediaSource(uri);

    // Supply the state information we saved in releasePlayer to our player during initialization
    //
    // tells the player whether to start playing as soon as all resources for playback have been acquired.
    // Since playWhenReady is initially true, playback will start automatically the first time the app is run
    player.setPlayWhenReady(playWhenReady);
    // tells the player to seek to a certain position within a specific window.
    // Both currentWindow and playbackPosition were initialized to zero so that playback starts from the very start the first time the app is run.
    player.seekTo(currentWindow, playbackPosition);
    // tells the player to acquire all the resources for the given mediaSource,
    // and additionally tells it not to reset the position or state, since we already set these in the two previous lines.
    player.prepare(mediaSource, false, false);
  }

  // Add some content to play
  // This method takes a Uri as its parameter, containing the URI of a media file
  //
  // There are many different types of MediaSource, but we'll start by creating a ProgressiveMediaSource for an MP3 file on the internet
  // A progressive media source is one which is obtained through progressive download - is the transfer of digital media files from a server to a client,
  // typically using the HTTP protocol when initiated from a computer. The consumer may begin playback of the media before the download is complete.
  private MediaSource buildMediaSource(Uri uri) {
    //  First, we create a DefaultDataSourceFactory, specifying our context and the user-agent string which will be used when making the HTTP request for the media file.
    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, "exoplayer-codelab");
    // Next we pass our newly created dataSourceFactory to ProgressiveMediaSource.Factory. This is, as the name suggests, a factory for creating progressive media data sources.
    //
    // Multimedia data is usually stored using a container format, such as MP4 or Ogg. Before the video and/or audio data can be played it must be extracted from the container.
    // ExoPlayer is capable of extracting data from many different container formats using a variety of Extractor classes.
    // By default the ProgressiveMediaSource.Factory uses a DefaultExtractorsFactory.
    return new ProgressiveMediaSource.Factory(dataSourceFactory)
            // Finally, we call createMediaSource, supplying our uri, to obtain a MediaSource object
            .createMediaSource(uri);
  }

  // Release resources with releasePlayer - frees up the player's resources and destroys it
  private void releasePlayer() {
    if (player != null) {

      // Before we release and destroy the player we store the following information:
      playWhenReady = player.getPlayWhenReady();
      playbackPosition = player.getCurrentPosition();
      currentWindow = player.getCurrentWindowIndex();
      // This will allow us to resume playback from where the user left off. All we need to do is supply this state information when we initialize our player.

      player.release();
      player = null;
    }
  }

  // hideSystemUi is a helper method called in onResume which allows us to have a full screen experience
  @SuppressLint("InlinedApi")
  private void hideSystemUi() {
    playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }
}
