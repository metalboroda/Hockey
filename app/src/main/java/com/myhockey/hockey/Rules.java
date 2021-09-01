package com.myhockey.hockey;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Rules extends Fragment {

    // MediaPlayer
    com.myhockey.hockey.MediaPlayer mediaPlayer = new com.myhockey.hockey.MediaPlayer();

    private ImageButton rulesBackButton;

    private ImageView rulesBG, puck1, puck2, puck3, dynamiteRules, blackRules, rules_text_bg;

    private TextView score1000points, text_1_10points, text_10_points, text_25_points, text_resetPoints, text_gameOver, rulesText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rules, container, false);

        // Initialization
        rules_text_bg = view.findViewById(R.id.rules_text_bg);
        rulesText = view.findViewById(R.id.rulesText);
        rulesBackButton = view.findViewById(R.id.rulesBackButton);
        rulesBG = view.findViewById(R.id.rulesBG);
        score1000points = view.findViewById(R.id.score1000points);
        puck1 = view.findViewById(R.id.puck1);
        text_1_10points = view.findViewById(R.id.text_1_10points);
        puck2 = view.findViewById(R.id.puck2);
        text_10_points = view.findViewById(R.id.text_10_points);
        puck3 = view.findViewById(R.id.puck3);
        text_25_points = view.findViewById(R.id.text_25_points);
        dynamiteRules = view.findViewById(R.id.dynamiteRules);
        text_resetPoints = view.findViewById(R.id.text_resetPoints);
        blackRules = view.findViewById(R.id.blackRules);
        text_gameOver = view.findViewById(R.id.text_gameOver);

        // Animations
        animationLeftRight(rulesBG);
        animationLeftRight(score1000points);
        animationLeftRight(puck1);
        animationLeftRight(text_1_10points);
        animationLeftRight(puck2);
        animationLeftRight(text_10_points);
        animationLeftRight(puck3);
        animationLeftRight(text_25_points);
        animationLeftRight(dynamiteRules);
        animationLeftRight(text_resetPoints);
        animationLeftRight(blackRules);
        animationLeftRight(text_gameOver);

        animationDownUp(rulesBackButton);

        animationUpDown(rules_text_bg);
        animationUpDown(rulesText);

        // Переход на Main Menu фрагмент
        view.findViewById(R.id.rulesBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_rules_to_startGame);
            }
        });

        mediaPlayer.play(getActivity(), R.raw.rules_screen);
        mediaPlayer.playSFX(getActivity(), R.raw.hockey_sfx);

        return view;
    }

    // Animation LeftRight
    private void animationLeftRight(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.left_right_scoreboard_animation);
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

    // Animation DownUp
    private void animationDownUp(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.down_up_misc_animation);
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

    // Animation DownUp
    private void animationUpDown(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.up_down_misc_animation);
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