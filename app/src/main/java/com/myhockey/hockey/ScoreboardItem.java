package com.myhockey.hockey;

public class ScoreboardItem {
    private final String nameText;
    private final int scoreValue;

    public ScoreboardItem(String nameText, int scoreValue) {
        this.nameText = nameText;
        this.scoreValue = scoreValue;
    }



    public String getNameText() {
        return nameText;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
