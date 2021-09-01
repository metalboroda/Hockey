package com.myhockey.hockey;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Fragment {

    boolean flagHit = true;

    // MediaPlayer
    com.myhockey.hockey.MediaPlayer mediaPlayer = new com.myhockey.hockey.MediaPlayer();
    private MediaPlayer mySound = null;
    private MediaPlayer mySoundSFX = null;

    // Timer value
    int timerValue = 25;
    Timer pauseTimer;

    View view;
    Random random = new Random();

    // Elements
    private TextView scoreLabel;
    private ImageView startLabel, box, pink, orange, gold, black, dynamite, gameTeamFlag;
    private int teamFlag;

    // Size
    private int screenHeight, screenWidth;
    private int frameHeight;
    private int boxSize;

    // Position
    private float boxY;
    private float orangeX, orangeY;
    private float pinkX, pinkY;
    private float goldX, goldY;
    private float blackX, blackY;
    private float dynamiteX, dynamiteY;
    float orangeCenterX, orangeCenterY, pinkCenterX, pinkCenterY, goldCenterX, goldCenterY, dynamiteCenterX, dynamiteCenterY, blackCenterX, blackCenterY;

    // Score
    private int scoreOrange, scorePink, scoreGold;
    private int score = (scoreOrange + scorePink + scoreGold);

    // Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    // Status
    private boolean action_flg = false;
    private boolean start_flg = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);

        // Initialize values
        scoreLabel = view.findViewById(R.id.scoreLabelText);
        startLabel = view.findViewById(R.id.toVictoryButton);
        box = view.findViewById(R.id.box);
        orange = view.findViewById(R.id.orange);
        pink = view.findViewById(R.id.pink);
        gold = view.findViewById(R.id.gold);
        black = view.findViewById(R.id.black);
        dynamite = view.findViewById(R.id.dynamite);
        gameTeamFlag = view.findViewById(R.id.gameTeamFlag);

        // Methods
        screenSizeMethod();
        initializePositionMethod();

        // Initialize animation values
        animationLeftRight(view.findViewById(R.id.scoreLabelText));
        animationLeftRight(view.findViewById(R.id.scoreLabelTextBG));
        animationY(view.findViewById(R.id.toVictoryButton));
        animationRightLeft(view.findViewById(R.id.gameTeamFlag));

        // OnTouch method и счетчик
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (!start_flg) {
                    start_flg = true;

                    // Frame height
                    FrameLayout frameLayout = view.findViewById(R.id.frame);
                    frameHeight = frameLayout.getHeight();

                    // Box
                    boxY = box.getY();
                    boxSize = box.getHeight();

                    startLabel.setVisibility(View.GONE);
                    view.setBackground(getResources().getDrawable(R.drawable.bg));

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    changePos();
                                }
                            });
                        }
                    }, 0, timerValue);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        action_flg = true;
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        action_flg = false;
                    }
                }
                return true;
            }
        });

        // Установка счета
        score = (scoreOrange + scorePink + scoreGold);
        scoreLabel.setText("SCORE: " + score);

        setTeamFlag();
        mediaPlayer.play(getActivity(), R.raw.game_screen);
        mediaPlayer.playSFX(getActivity(), R.raw.hockey_sfx);

        return view;
    }

    public void screenSizeMethod() {

        // Screen size
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;
    }

    public void initializePositionMethod() {

        // Initial positions with random start
        // Box
        box.setX(25.0f);
        box.setY(-10000.0f);

        // Default позиция предметов
        // Orange
        orange.setX(-1000.0f);
        orangeX = -1000;
        orange.setY(-1000.0f);
        orangeY = random.nextInt(1000) + 50;

        // Pink
        pink.setX(-2000.0f);
        pinkX = 2500;
        pink.setY(-1000.0f);
        pinkY = random.nextInt(1000) + 50;

        // Gold
        gold.setX(-10000.0f);
        goldX = 25000;
        gold.setY(-1000.0f);
        goldY = random.nextInt(1000) + 50;

        // Black
        black.setX(-1000.0f);
        blackX = random.nextInt(1500) + 1200;
        black.setY(-1000.0f);
        blackY = random.nextInt(1000) + 50;

        // Dynamite
        dynamite.setX(-1000.0f);
        dynamiteX = random.nextInt(3000) + 3500;
        dynamite.setY(-1000.0f);
        dynamiteY = random.nextInt(1000) + 50;

    }

    public void changePos() {

        hitCheck();

        // Orange
        orangeX -= 16;
        if (orangeX < -300) {
            orangeX = screenWidth + 200;
            orangeY = (float) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Pink
        // Скорость
        pinkX -= 10;
        if (pinkX < -1000) {
            pinkX = screenWidth + 400;
            pinkY = (float) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        // Gold
        goldX -= 35;
        if (goldX < -15000) {
            goldX = screenWidth + 200;
            goldY = (float) Math.floor(Math.random() * (frameHeight - gold.getHeight()));
        }
        gold.setX(goldX);
        gold.setY(goldY);

        // Black
        blackX -= 22;
        if (blackX < -900) {
            blackX = screenWidth + 600;
            blackY = (float) Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        // Dynamite
        dynamiteX -= 16;
        if (dynamiteX < -1800) {
            dynamiteX = screenWidth + 800;
            dynamiteY = (float) Math.floor(Math.random() * (frameHeight - dynamite.getHeight()));
        }
        dynamite.setX(dynamiteX);
        dynamite.setY(dynamiteY);

        // Box speed
        if (action_flg) {
            // Touching
            boxY -= 32;
        } else {
            // Releasing
            boxY += 32;
        }

        // Decline box outOfBounds
        if (boxY < 0) boxY = +25;
        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        score = (scoreOrange + scorePink + scoreGold);
        scoreLabel.setText("SCORE: " + score);
    }

    public void hitCheck() {

        if (flagHit) {
            hitNormalMethod();
        } else if (flagHit = false) {
            hitNullMethod();
        }
    }

    private void hitNormalMethod() {
        // Hit
        // Orange
        // Размер хитбокса предмета
        orangeCenterX = orangeX + orange.getWidth() / 1.5f;
        orangeCenterY = orangeY + orange.getHeight() / 1.5f;
        // Условие
        if (0 <= orangeCenterX && orangeCenterX <= boxSize &&
                boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            orangeX = -200.0f;
            // Кол-во очков
            scoreOrange += random.nextInt(10 - 5) + 5;
        }

        // Pink
        pinkCenterX = pinkX + pink.getWidth() / 1.5f;
        pinkCenterY = pinkY + pink.getHeight() / 1.5f;
        // Условие
        if (0 <= pinkCenterX && pinkCenterX <= boxSize &&
                boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            pinkX = -600.0f;
            // Кол-во очков
            scorePink += 10;
        }

        // Gold
        goldCenterX = goldX + gold.getWidth() / 1.5f;
        goldCenterY = goldY + gold.getHeight() / 1.5f;
        // Условие
        if (0 <= goldCenterX && goldCenterX <= boxSize &&
                boxY <= goldCenterY && goldCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            goldX = -800.0f;
            // Кол-во очков
            scoreGold += 25;
        }

        // Dynamite
        dynamiteCenterX = dynamiteX + dynamite.getWidth() / 2.0f;
        dynamiteCenterY = dynamiteY + dynamite.getHeight() / 2.0f;
        // Условие
        if (0 <= dynamiteCenterX && dynamiteCenterX <= boxSize &&
                boxY <= dynamiteCenterY && dynamiteCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            dynamiteX = -1200.0f;
            // Кол-во очков
            score = 0;
            scoreGold = 0;
            scorePink = 0;
            scoreOrange = 0;
            scoreLabel.setText("SCORE: " + score);
        }

        // Black
        // Размер хитбокса предмета
        blackCenterX = blackX + black.getWidth() / 1.5f;
        blackCenterY = blackY + black.getHeight() / 1.5f;
        // Условие
        if (0 <= blackCenterX && blackCenterX <= boxSize &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            blackX = -160000.0f;
            // При Game Over переходим на следующий фрагмент
            endGameMethod();
        }

        // При достижжении счета в "Score" endGame
        if (score >= 1000) {
            endGameMethod();
        }
    }

    private void hitNullMethod() {
        // Hit
        // Orange
        // Размер хитбокса предмета
        orangeCenterX = orangeX + orange.getWidth() / 100f;
        orangeCenterY = orangeY + orange.getHeight() / 100f;
        // Условие
        if (0 <= orangeCenterX && orangeCenterX <= boxSize &&
                boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            orangeX = -20000.0f;
            // Кол-во очков
            scoreOrange += 0;
        }

        // Pink
        pinkCenterX = pinkX + pink.getWidth() / 100f;
        pinkCenterY = pinkY + pink.getHeight() / 100f;
        // Условие
        if (0 <= pinkCenterX && pinkCenterX <= boxSize &&
                boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            pinkX = -60000.0f;
            // Кол-во очков
            scorePink += 0;
        }

        // Gold
        goldCenterX = goldX + gold.getWidth() / 100f;
        goldCenterY = goldY + gold.getHeight() / 100f;
        // Условие
        if (0 <= goldCenterX && goldCenterX <= boxSize &&
                boxY <= goldCenterY && goldCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            goldX = -80000.0f;
            // Кол-во очков
            scoreGold += 0;
        }

        // Dynamite
        dynamiteCenterX = dynamiteX + dynamite.getWidth() / 100f;
        dynamiteCenterY = dynamiteY + dynamite.getHeight() / 100f;
        // Условие
        if (0 <= dynamiteCenterX && dynamiteCenterX <= boxSize &&
                boxY <= dynamiteCenterY && dynamiteCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            dynamiteX = -120000.0f;
            // Кол-во очков
        }

        // Black
        // Размер хитбокса предмета
        blackCenterX = blackX + black.getWidth() / 100f;
        blackCenterY = blackY + black.getHeight() / 100f;
        // Условие
        if (0 <= blackCenterX && blackCenterX <= boxSize &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {
            // Параметр "Исчезновения" предмета
            blackX = -160000.0f;
            // При Game Over переходим на следующий фрагмент
        }
    }

    private void endGameMethod() {

        // Game Over
        if (timer != null) {
            timer.cancel();
            timer = null;

            // Переход на следующий фрагмент
            Navigation.findNavController(view).navigate(R.id.action_game_to_end_game);

            // Set score fragment data
            EndGame loseFragment = new EndGame();
            Bundle bundle = new Bundle();
            bundle.putString("SCORE", String.valueOf(score));
            bundle.putInt("SCORE_INT", score);
            bundle.putInt("TEAM_LOSE_FLAG", teamFlag);
            loseFragment.setArguments(bundle);

            FragmentManager manager = getParentFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.main_container, loseFragment)
                    .commit();
        }
    }

    // AnimationLeftRight
    private void animationLeftRight(final View view) {
        Animation scoreBG = AnimationUtils.loadAnimation(getActivity(), R.anim.left_right_scoreboard_animation);
        Animation scoreText = AnimationUtils.loadAnimation(getActivity(), R.anim.left_right_scoreboard_animation);
        //use this to make it longer:  animation.setDuration(1000);
        scoreBG.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(scoreBG);
        view.startAnimation(scoreText);
    }

    // AnimationY
    private void animationY(final View view) {
        Animation toVictoryButton = AnimationUtils.loadAnimation(getActivity(), R.anim.down_up_misc_animation);
        //use this to make it longer:  animation.setDuration(1000);
        toVictoryButton.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(toVictoryButton);
    }

    // Animation RightLeft
    private void animationRightLeft(final View view) {
        Animation flagLabel = AnimationUtils.loadAnimation(getActivity(), R.anim.right_left_referee_animation);
        //use this to make it longer:  animation.setDuration(1000);
        flagLabel.setAnimationListener(new Animation.AnimationListener() {
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

        view.startAnimation(flagLabel);
    }

    private void setTeamFlag() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            teamFlag = bundle.getInt("TEAM_DATA");

            switch (teamFlag) {
                case 0:
                    gameTeamFlag.setImageResource(R.drawable.team1);
                    break;
                case 1:
                    gameTeamFlag.setImageResource(R.drawable.team2);
                    break;
                case 2:
                    gameTeamFlag.setImageResource(R.drawable.team3);
                    break;
                case 3:
                    gameTeamFlag.setImageResource(R.drawable.team4);
                    break;
                case 4:
                    gameTeamFlag.setImageResource(R.drawable.team5);
                    break;
                case 5:
                    gameTeamFlag.setImageResource(R.drawable.team6);
                    break;
                case 6:
                    gameTeamFlag.setImageResource(R.drawable.team7);
                    break;
                case 7:
                    gameTeamFlag.setImageResource(R.drawable.team8);
                    break;
                case 8:
                    gameTeamFlag.setImageResource(R.drawable.team9);
                    break;
                case 9:
                    gameTeamFlag.setImageResource(R.drawable.team10);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        flagHit = true;

        mediaPlayer.mySound.start();
        mediaPlayer.mySoundSFX.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        flagHit = false;

        mediaPlayer.mySound.pause();
        mediaPlayer.mySoundSFX.stop();
    }

    @Override
    public void onStop() {
        super.onStop();

        flagHit = false;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mediaPlayer.mySound.release();
        mediaPlayer.mySoundSFX.release();
    }
}