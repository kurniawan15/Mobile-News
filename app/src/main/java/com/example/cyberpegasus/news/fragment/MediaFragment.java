package com.example.cyberpegasus.news.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cyberpegasus.news.R;

/**
 * Created by USER on 8/14/2018.
 */

public class MediaFragment extends Fragment{
    public static final String IMAGE_MESSAGE = "IMAGE_MESSAGE";
    public static final String IS_IMAGE = "IS_IMAGE";
    public static final String VIDEO_MESSAGE = "VIDEO_MESSAGE";


    public static final MediaFragment newInstance(int media, boolean isImage)
    {
        MediaFragment fragment = new MediaFragment();
        Bundle bdl = new Bundle();
        if (isImage) {
            bdl.putInt(IMAGE_MESSAGE,media);
        }else {
            bdl.putInt(VIDEO_MESSAGE, media);
        }
        bdl.putBoolean(IS_IMAGE, isImage);
        fragment.setArguments(bdl);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.swipe_layout, container, false);

        ImageView imageView = (ImageView)v.findViewById(R.id.imageViewSwipe);

        VideoView videoView = (VideoView)v.findViewById(R.id.videoViewSwipe);

        if(getArguments().getBoolean(IS_IMAGE)) {
            imageView.invalidate();

            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);

            try {
                int img = getArguments().getInt(IMAGE_MESSAGE);

                imageView.setImageResource(img);

            } catch(Exception e) {
                System.out.println("Error Of image File"+e);
            }


        } else
            try {
                videoView.invalidate();
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                int vid = getArguments().getInt(VIDEO_MESSAGE);

                //Uri dari aplikasi bukan uri dari class ini
                Uri uri = Uri.parse("android.resource://com.example.cyberpegasus.news/"+vid);
                videoView.setVideoURI(uri);
                videoView.setMediaController(new MediaController(getActivity()));
                videoView.setFocusable(true);
                videoView.start();
            } catch(Exception e) {
                e.printStackTrace();
            }

        return v;
    }
}
