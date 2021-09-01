package com.myhockey.hockey;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainMenu extends Fragment implements View.OnClickListener {

    // MediaPlayer
    com.myhockey.hockey.MediaPlayer mediaPlayer = new com.myhockey.hockey.MediaPlayer();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // Initialize animation values
        animationUpDownMisc(view.findViewById(R.id.mainLogoImage));
        animationDownUpMisc(view.findViewById(R.id.startGameButton));
        animationDownUpMisc(view.findViewById(R.id.infoButton));
        animationLeftRight(view.findViewById(R.id.hockeyPlayerImage));

        // OnClick switch fragment to game
        /*view.findViewById(R.id.startGameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_startGame_to_chooseTeam);
            }
        });*/
        // OnClick switch fragment to info
        /*view.findViewById(R.id.infoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_rules);
            }
        });*/

        view.findViewById(R.id.startGameButton).setOnClickListener(this);
        view.findViewById(R.id.infoButton).setOnClickListener(this);

        mediaPlayer.play(getActivity(), R.raw.main_menu);
        mediaPlayer.playSFX(getActivity(), R.raw.hockey_sfx);


        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startGameButton:
                Navigation.findNavController(view).navigate(R.id.action_startGame_to_chooseTeam);
                break;
            case R.id.infoButton:
                Navigation.findNavController(view).navigate(R.id.action_mainMenu_to_rules);
                break;
        }
    }

    // Animation misc
    private void animationUpDownMisc(final View view) {
        Animation mainLogoImage = AnimationUtils.loadAnimation(getActivity(), R.anim.up_down_misc_animation);
        //use this to make it longer:  animation.setDuration(1000);
        mainLogoImage.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });

        view.startAnimation(mainLogoImage);
    }

    // Animation DownUp
    private void animationDownUpMisc(final View view) {
        Animation startGameButton = AnimationUtils.loadAnimation(getActivity(), R.anim.down_up_misc_animation);
        Animation infoButton = AnimationUtils.loadAnimation(getActivity(), R.anim.down_up_misc_animation);
        //use this to make it longer:  animation.setDuration(1000);
        startGameButton.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });

        view.startAnimation(startGameButton);
        view.startAnimation(infoButton);
    }

    // Animation LeftRight
    private void animationLeftRight(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.right_left_referee_animation);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });

        view.startAnimation(animation);
    }

    @Override
    public void onResume() {
        super.onResume();

        mediaPlayer.mySound.start();
        mediaPlayer.mySoundSFX.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        mediaPlayer.mySound.pause();
        mediaPlayer.mySoundSFX.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mediaPlayer.mySound.release();
        mediaPlayer.mySoundSFX.release();
    }
}