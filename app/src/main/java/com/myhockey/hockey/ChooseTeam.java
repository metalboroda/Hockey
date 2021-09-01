package com.myhockey.hockey;

import android.annotation.SuppressLint;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseTeam extends Fragment implements View.OnClickListener {

    View view;

    // MediaPlayer
    com.myhockey.hockey.MediaPlayer mediaPlayer = new com.myhockey.hockey.MediaPlayer();

    /*ImageButton team1;
    ImageButton team2;
    ImageButton team3;
    ImageButton team4;
    ImageButton team5;
    ImageButton team6;
    ImageButton team7;
    ImageButton team8;
    ImageButton team9;
    ImageButton team10;*/

    ImageButton chooseTeamBackButton;

    ArrayList<ImageButton> buttonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_team, container, false);

        // Initialize
        chooseTeamBackButton = view.findViewById(R.id.chooseTeamBackButton);

        // Animations
        downUpAnimation(view.findViewById(R.id.teamChooseLayout));
        downUpAnimation(view.findViewById(R.id.chooseTeamBackButton));
        upDownAnimation(view.findViewById(R.id.chooseYourTeamText));
        upDownAnimation(view.findViewById(R.id.chooseYourTeamTextBG));

        /*team1 = view.findViewById(R.id.team1);
        team2 = view.findViewById(R.id.team2);
        team3 = view.findViewById(R.id.team3);
        team4 = view.findViewById(R.id.team4);
        team5 = view.findViewById(R.id.team5);
        team6 = view.findViewById(R.id.team6);
        team7 = view.findViewById(R.id.team7);
        team8 = view.findViewById(R.id.team8);
        team9 = view.findViewById(R.id.team9);
        team10 = view.findViewById(R.id.team10);*/

        // Add buttons to list
        buttonList.add((ImageButton) view.findViewById(R.id.team1));
        buttonList.add((ImageButton) view.findViewById(R.id.team2));
        buttonList.add((ImageButton) view.findViewById(R.id.team3));
        buttonList.add((ImageButton) view.findViewById(R.id.team4));
        buttonList.add((ImageButton) view.findViewById(R.id.team5));
        buttonList.add((ImageButton) view.findViewById(R.id.team6));
        buttonList.add((ImageButton) view.findViewById(R.id.team7));
        buttonList.add((ImageButton) view.findViewById(R.id.team8));
        buttonList.add((ImageButton) view.findViewById(R.id.team9));
        buttonList.add((ImageButton) view.findViewById(R.id.team10));

        /*view.findViewById(R.id.chooseTeamBackButton).setOnClickListener(this);*/

        // Считаем кнопки по индексу
        for (int i = 0; i < buttonList.size(); i++) {
            {
                int finalI = i;

                /*buttonList.get(i).setOnClickListener(this);*/

                buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(v).navigate(R.id.action_chooseTeam_to_game);
                        // Set fragment data
                        Game gameFragment = new Game();
                        Bundle bundle = new Bundle();
                        bundle.putInt("TEAM_DATA", finalI);
                        gameFragment.setArguments(bundle);

                        FragmentManager manager = getParentFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.main_container, gameFragment)
                                .commit();
                    }
                });
            }
        }

        // Back button
        view.findViewById(R.id.chooseTeamBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_chooseTeam_to_mainMenu);
            }
        });

        mediaPlayer.play(getActivity(), R.raw.choose_screen);
        mediaPlayer.playSFX(getActivity(), R.raw.hockey_sfx);

        return view;
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.chooseTeamBackButton:
                Navigation.findNavController(view).navigate(R.id.action_chooseTeam_to_mainMenu);
                break;
            case R.id.team1:
                toGameScreenMethod(0);
                break;
            case R.id.team2:
                toGameScreenMethod(1);
                break;
            case R.id.team3:
                toGameScreenMethod(2);
                break;
            case R.id.team4:
                toGameScreenMethod(3);
                break;
            case R.id.team5:
                toGameScreenMethod(4);
                break;
            case R.id.team6:
                toGameScreenMethod(5);
                break;
            case R.id.team7:
                toGameScreenMethod(6);
                break;
            case R.id.team8:
                toGameScreenMethod(7);
                break;
            case R.id.team9:
                toGameScreenMethod(8);
                break;
            case R.id.team10:
                toGameScreenMethod(9);
                break;
        }*/
    }

    /*private void toGameScreenMethod(int teamFlag) {
        chooseTeamBackButton.setClickable(false);
        try {
            Navigation.findNavController(view).navigate(R.id.action_chooseTeam_to_game);
        } catch (IllegalArgumentException error) {
            Toast.makeText(getContext(), "Леха, хватит клацать!", Toast.LENGTH_LONG).show();
        }

        // Set fragment data
        Game gameFragment = new Game();
        Bundle bundle = new Bundle();
        bundle.putInt("TEAM_DATA", teamFlag);
        gameFragment.setArguments(bundle);

        FragmentManager manager = getParentFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_container, gameFragment)
                .commit();
    }*/

    // Animation DownUp
    private void downUpAnimation(final View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.team_choose_animation);
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

    // Animation UpDown
    private void upDownAnimation(final View view) {
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