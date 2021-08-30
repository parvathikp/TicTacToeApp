package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import static java.lang.StrictMath.max;

public class MainActivity extends AppCompatActivity {
    Gson gson=new Gson();
    int player1Img;
    int player2Img;int draw=0;
    SharedPreferences storage;
    stat player1,player2;
    public int turn=0;public int lastWinner=-1;int startTurn=0;
    public Dialog popup,popup1;
    public int state=0;
    public Integer[] score= new Integer[2];
    public Integer[] players= new Integer[2];
    public Integer[] grid= new Integer[9];
    public Integer[] id=new Integer[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage=PreferenceManager.getDefaultSharedPreferences(this);
        String def=gson.toJson(new stat());
            player1=gson.fromJson(storage.getString("player1",def),stat.class);
            player2=gson.fromJson(storage.getString("player2",def),stat.class);
        Bundle info=getIntent().getExtras();
        player1Img=info.getInt("player1");
        player2Img=info.getInt("player2");
        id[0]=R.id.b0;id[1]=R.id.b1;id[2]=R.id.b2;id[3]=R.id.b3;
        id[4]=R.id.b4;id[5]=R.id.b5;id[6]=R.id.b6;id[7]=R.id.b7;
        id[8]=R.id.b8;players[0]=player1Img;players[1]=player2Img;score[0]=0;score[1]=0;
        ImageView img1=(ImageView)findViewById(R.id.player1);
        ImageView img2=(ImageView)findViewById(R.id.player2);
        img1.setImageResource(players[0]);img2.setImageResource(players[1]);
        ImageView menuop=(ImageView)findViewById(R.id.menu);
        menuop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpPop();
            }
        });
        initGame();

    }
    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor edit=storage.edit();
        edit.putString("player1",gson.toJson(player1));edit.apply();
        edit.putString("player2",gson.toJson(player2));edit.apply();
    }
    public void clearBoard(){
        for(int i=0;i<9;i++){
            ImageView img=(ImageView)findViewById(id[i]);
            img.setImageResource(android.R.color.transparent);img.setTag(i);
            grid[i]=-1;
        }return;
    }
    public void newMatch(){startTurn=turn;
        state=0;lastWinner=-1;draw=0;
        ImageView turnimg=(ImageView)findViewById(R.id.turn);
        turnimg.setImageResource(players[turn]);
        for(int i=0;i<9;i++){
            ImageView img=(ImageView)findViewById(id[i]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView img=(ImageView)v;
                    int i=Integer.parseInt(img.getTag().toString());
                    if(grid[i]==-1){
                        grid[i]=turn;
                        img.setImageResource(players[turn]);
                        boolean result=getResult();if(result){end();
                        if(turn==0){
                            player1.setWon(player1.getWon()+1);player2.setLost(player2.getLost()+1);
                        }else{
                            player1.setLost(player1.getLost()+1);player2.setWon(player2.getWon()+1);
                        }
                        winner();}else{
                            if(allfilled()){draw=1;player1.setDraw(player1.getDraw()+1);
                                player2.setDraw(player2.getDraw()+1);
                            tie();}
                            turn^=1;updateTurn();}
                    }
                }
            });
        }return;
    }
    public void initGame(){
        clearBoard();newMatch();
        score[0]=0;score[1]=0;
        TextView sc1=(TextView)findViewById(R.id.score1);
        TextView sc2=(TextView)findViewById(R.id.score2);
        sc1.setText(Integer.toString(score[0]));sc2.setText(Integer.toString(score[1]));
        return;
    }
    public void nextMatch(){
        newMatch();
        clearBoard();
        return;
    }
    public void updateScores(){
        TextView sc1=(TextView)findViewById(R.id.score1);
        TextView sc2=(TextView)findViewById(R.id.score2);
        player1.setHiscore(max(player1.hiscore,score[0]-score[1]));
        player2.setHiscore(max(player2.hiscore,score[1]-score[0]));
        sc1.setText(Integer.toString(score[0]));sc2.setText(Integer.toString(score[1]));return;
    }
    public void end(){
        for(int i=0;i<9;i++){
            ImageView img=(ImageView)findViewById(id[i]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   return;
                }
            });
        }
    }
    public boolean getResult(){
        if(grid[0]!=-1){boolean won=true;
            for(int i=0;i<3;i++){
                if(grid[i]!=grid[0]){won=false;break;}
            }if(won){
                state=1;lastWinner=turn;score[turn]++;updateScores();return true;
            }won=true;
            for(int i=0;i<7;i+=3){
                if(grid[i]!=grid[0]){won=false;break;}
            }if(won){
                state=1;lastWinner=turn;score[turn]++;updateScores();return true;
            }won=true;
            for(int i=0;i<9;i+=4){
                if(grid[i]!=grid[0]){won=false;break;}
            }if(won){
                state=1;lastWinner=turn;score[turn]++;updateScores();return true;
            }won=true;
        }
        if(grid[2]!=-1&&grid[4]==grid[2]&&grid[6]==grid[4]){
                state=1;lastWinner=turn;score[turn]++;updateScores();return true;
        }
        if(grid[2]!=-1&&grid[5]==grid[2]&&grid[8]==grid[5]){
            state=1;lastWinner=turn;score[turn]++;updateScores();return true;
        }
        if(grid[1]!=-1&&grid[4]==grid[1]&&grid[7]==grid[4]){
            state=1;lastWinner=turn;score[turn]++;updateScores();return true;
        }if(grid[3]!=-1&&grid[4]==grid[3]&&grid[5]==grid[4]){
            state=1;lastWinner=turn;score[turn]++;updateScores();return true;
        }
        if(grid[6]!=-1&&grid[7]==grid[6]&&grid[8]==grid[7]){
            state=1;lastWinner=turn;score[turn]++;updateScores();return true;
        }
        return false;
    }
    public boolean allfilled(){
        for(int i=0;i<9;i++){
            if(grid[i]==-1){
                return false;
            }
        }
        return true;
    }
    public void tie(){
        popup=new Dialog(this);
        popup.setContentView(R.layout.winning_popup);
        TextView message=(TextView)popup.findViewById(R.id.message);
        ImageView messageimg=(ImageView)popup.findViewById(R.id.messageimg);
        messageimg.setVisibility(View.INVISIBLE);
        message.setText("WELL FOUGHT TIE!");
        TextView overalltext=(TextView)popup.findViewById(R.id.overalltext);
        ImageView overallimg=(ImageView)popup.findViewById(R.id.overallimg);
        if(score[0]==score[1]){
            overalltext.setText("DRAW !");overallimg.setVisibility(View.INVISIBLE);
        }else if(score[0]>score[1]){
            overalltext.setText("LEADING");overallimg.setImageResource(players[0]);
        }else{
            overalltext.setText("LEADING");overallimg.setImageResource(players[1]);
        }
        TextView close=(TextView)popup.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
        setOptions(popup);
    return;}
    public void winner(){
        popup=new Dialog(this);
        popup.setContentView(R.layout.winning_popup);
        TextView message=(TextView)popup.findViewById(R.id.message);
        ImageView messageimg=(ImageView)popup.findViewById(R.id.messageimg);
        messageimg.setImageResource(players[turn]);
        message.setText("CONGRATULATIONS");
        TextView overalltext=(TextView)popup.findViewById(R.id.overalltext);
        ImageView overallimg=(ImageView)popup.findViewById(R.id.overallimg);
        if(score[0]==score[1]){
            overalltext.setText("DRAW !");overallimg.setVisibility(View.INVISIBLE);
        }else if(score[0]>score[1]){
            overalltext.setText("LEADING");overallimg.setImageResource(players[0]);
        }else{
            overalltext.setText("LEADING");overallimg.setImageResource(players[1]);
        }
        TextView close=(TextView)popup.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
        setOptions(popup);return;
    }
    public void updateTurn(){
        ImageView turnimg=(ImageView)findViewById(R.id.turn);
        turnimg.setImageResource(players[turn]);return;
    }
    public void setUpPop(){
        popup1=new Dialog(this);
        popup1.setContentView(R.layout.menubar);
        TextView close=(TextView)popup1.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup1.dismiss();
            }
        });
        popup1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup1.show();
        setOptions(popup1);
        return;
    }
    public void setOptions(Dialog popup){
        Button ahead=(Button)popup.findViewById(R.id.ahead);
        Button game=(Button)popup.findViewById(R.id.game);
        Button exit1=(Button)popup.findViewById(R.id.exit);
        ahead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMatch();
                popup.dismiss();
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initGame();popup.dismiss();
            }
        });
        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
                popup.dismiss();
            }
        });
        return;
    }
}