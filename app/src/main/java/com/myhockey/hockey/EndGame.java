package com.myhockey.hockey;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class EndGame extends Fragment {

    // MediaPlayer
    com.myhockey.hockey.MediaPlayer mediaPlayer = new com.myhockey.hockey.MediaPlayer();

    TextView scoreResultValue;
    int flagID;
    ImageView flagImage;
    String score;
    int score_int;

    Random random = new Random();

    // RecyclerView values
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Random randomScore = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        scoreResultValue = view.findViewById(R.id.scoreResult);
        flagImage = view.findViewById(R.id.flagImage);

        // Получаем score
        Bundle bundle = getArguments();
        if (bundle != null) {
            score = bundle.getString("SCORE");
            score_int = bundle.getInt("SCORE_INT");
            int getFlagIDData = bundle.getInt("TEAM_LOSE_FLAG");
            scoreResultValue.setText(score);
            flagID = getFlagIDData;
            /*Toast.makeText(getContext(), "Score is: " + score, Toast.LENGTH_LONG).show();
            Log.d("SCORE", "Score is: " + scoreResultValue);*/

            int randomNumbers = randomScore.nextInt(15 - 1) + 1;

            /*int notRandomScore;
            if (score_int == score_int) {
                notRandomScore +=
            }*/

            // Names list
            ArrayList<ScoreboardItem> scoreboardList = new ArrayList<>();
            scoreboardList.add(new ScoreboardItem(getString(R.string.YouScore), score_int));
            scoreboardList.add(new ScoreboardItem("James M -", random.nextInt( 1000 - 525) + 525));
            scoreboardList.add(new ScoreboardItem("Robert P -", random.nextInt(950 - 500) + 500));
            scoreboardList.add(new ScoreboardItem("John J -", random.nextInt(900 - 475) + 475));
            scoreboardList.add(new ScoreboardItem("Steven K -", random.nextInt(850 - 450) + 450));
            scoreboardList.add(new ScoreboardItem("Edward D -", random.nextInt(800 - 425) + 425));
            scoreboardList.add(new ScoreboardItem("Paul E -", random.nextInt(750 - 400) + 400));
            scoreboardList.add(new ScoreboardItem("Jeffrey L -", random.nextInt(700 - 375) + 375));
            scoreboardList.add(new ScoreboardItem("Michael M -", random.nextInt(650 - 350) + 350));
            scoreboardList.add(new ScoreboardItem("William E -", random.nextInt(600 - 325) + 325));
            scoreboardList.add(new ScoreboardItem("David B -", random.nextInt(550 - 300) + 300));
            scoreboardList.add(new ScoreboardItem("Richard S -", random.nextInt(500 - 275) + 275));
            scoreboardList.add(new ScoreboardItem("Ronald R -", random.nextInt(450 - 250) + 250));
            scoreboardList.add(new ScoreboardItem("Andrew D -", random.nextInt(400 - 225) + 225));
            scoreboardList.add(new ScoreboardItem("Richard S -", random.nextInt(350 - 200) + 200));
            scoreboardList.add(new ScoreboardItem("Joseph J -", random.nextInt(300 - 175) + 175));
            scoreboardList.add(new ScoreboardItem("Thomas S -", random.nextInt(250 - 150) + 150));
            scoreboardList.add(new ScoreboardItem("Joseph J -", random.nextInt(200 - 135) + 135));
            scoreboardList.add(new ScoreboardItem("Timothy R -", random.nextInt( 150 - 125) + 125));
            scoreboardList.add(new ScoreboardItem("Joshua M -", random.nextInt(135 - 100) + 100));
            scoreboardList.add(new ScoreboardItem("Thomas S -", random.nextInt(125 - 75) + 75));
            scoreboardList.add(new ScoreboardItem("Charles K -", random.nextInt(100 - 50) + 50));
            scoreboardList.add(new ScoreboardItem("Kenneth D -", random.nextInt(75 - 25) + 25));
            scoreboardList.add(new ScoreboardItem("Daniel L -", random.nextInt(50 - 25) + 25));
            scoreboardList.add(new ScoreboardItem("Jason S -", random.nextInt(25 - 10) + 10));

            // Sorting
            Collections.shuffle(scoreboardList);
            scoreboardList.sort(Comparator.comparingDouble(ScoreboardItem::getScoreValue));
            Collections.reverse(scoreboardList);

            // RecyclerView
            mRecyclerView = view.findViewById(R.id.scoreboardRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this.getContext());
            mAdapter = new ScoreboardAdapter(scoreboardList);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        // Initialize animation values
        animationReferee(view.findViewById(R.id.imageReferee));
        animationUpDownMisc(view.findViewById(R.id.flagImage));
        animationUpDownMisc(view.findViewById(R.id.yourScoreText));
        animationDownUpMisc(view.findViewById(R.id.backButton));
        animationUpDownMisc(view.findViewById(R.id.scoreResult));
        animationLeftRight(view.findViewById(R.id.scoreboardImage));
        animationLeftRight(view.findViewById(R.id.scoreboardText));
        animationLeftRight(view.findViewById(R.id.scoreboardRecyclerView));


        // Onclick to Main Menu
        view.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_lose_to_mainMenu);
            }
        });

        // Methods
        setLoseFlag();
        mediaPlayer.play(getActivity(), R.raw.end_screen);
        mediaPlayer.playSFX(getActivity(), R.raw.hockey_sfx);
        mediaPlayer.playEndHorn(getActivity(), R.raw.end_horn);

        return view;
    }

    // Animation methods
    // Animation referee
    private void animationReferee(final View view) {
        Animation animationReferee = AnimationUtils.loadAnimation(getActivity(), R.anim.right_left_referee_animation);
        //use this to make it longer:  animation.setDuration(1000);
        animationReferee.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(animationReferee);
    }

    // Animation UpDown
    private void animationUpDownMisc(final View view) {
        Animation textLoose = AnimationUtils.loadAnimation(getActivity(), R.anim.up_down_misc_animation);
        //use this to make it longer:  animation.setDuration(1000);
        textLoose.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(textLoose);
    }

    // Animation DownUp
    private void animationDownUpMisc(final View view) {
        Animation backButton = AnimationUtils.loadAnimation(getActivity(), R.anim.down_up_misc_animation);
        //use this to make it longer:  animation.setDuration(1000);
        backButton.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(backButton);
    }

    // PlayerList animations
    private void animationLeftRight(final View view) {
        Animation playersList1 = AnimationUtils.loadAnimation(getActivity(), R.anim.player_list_1);
        playersList1.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(playersList1);
    }

    private void setLoseFlag() {
        switch (flagID) {
            case 0:
                flagImage.setImageResource(R.drawable.team1);
                break;
            case 1:
                flagImage.setImageResource(R.drawable.team2);
                break;
            case 2:
                flagImage.setImageResource(R.drawable.team3);
                break;
            case 3:
                flagImage.setImageResource(R.drawable.team4);
                break;
            case 4:
                flagImage.setImageResource(R.drawable.team5);
                break;
            case 5:
                flagImage.setImageResource(R.drawable.team6);
                break;
            case 6:
                flagImage.setImageResource(R.drawable.team7);
                break;
            case 7:
                flagImage.setImageResource(R.drawable.team8);
                break;
            case 8:
                flagImage.setImageResource(R.drawable.team9);
                break;
            case 9:
                flagImage.setImageResource(R.drawable.team10);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mediaPlayer.mySound.start();
        mediaPlayer.mySoundSFX.start();
        mediaPlayer.mySoundEndHorn.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        mediaPlayer.mySound.pause();
        mediaPlayer.mySoundSFX.stop();
        mediaPlayer.mySoundEndHorn.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mediaPlayer.mySound.release();
        mediaPlayer.mySoundSFX.release();
        mediaPlayer.mySoundEndHorn.release();
    }
}