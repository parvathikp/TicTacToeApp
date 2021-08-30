package com.example.tictactoe;

public class stat {
    int won;
    int lost;
    int draw;
    int hiscore;
    stat(){
        reset();
    }
    void reset(){
        won=0;lost=0;draw=0;hiscore=0;return;
    }
    int getWon(){
        return won;
    }
    int getHiscore(){
        return hiscore;
    }
    int getLost(){
        return lost;
    }
    int getDraw(){
        return draw;
    }
    void setWon(int a){
        won=a;
    }
    void setHiscore(int a){
        hiscore=a;
    }
    void setLost(int a){
        lost=a;
    }
    void setDraw(int a){
        draw=a;
    }
}
