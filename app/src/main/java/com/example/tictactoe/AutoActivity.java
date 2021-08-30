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

import static java.lang.Math.max;

public class AutoActivity extends AppCompatActivity{
        Gson gson=new Gson();
        int player1Img;
        int player2Img;
        SharedPreferences storage;
        stat player;
        int turn=0; int lastWinner=-1;int startTurn=0;
        Dialog popup,popup1;
         int state=0;
        Integer[] score= new Integer[2];
         Integer[] players= new Integer[2];
         Integer[] grid= new Integer[9];
         Integer[] id=new Integer[9];
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            storage=PreferenceManager.getDefaultSharedPreferences(this);
            Bundle info=getIntent().getExtras();
            player1Img=info.getInt("player1");
            player2Img=info.getInt("player2");
            String def=gson.toJson(new stat());
                player=gson.fromJson(storage.getString("single",def),stat.class);
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
            edit.putString("single",gson.toJson(player));edit.apply();
        }
        public void clearBoard(){
            for(int i=0;i<9;i++){
                ImageView img=(ImageView)findViewById(id[i]);
                img.setImageResource(android.R.color.transparent);img.setTag(i);
                grid[i]=-1;
            }return;
        }
        public void newMatch(int change_turn){
            if(change_turn==1){
                turn=(int)(Math.random()*(2))+0;
            }
            startTurn=turn;
            state=0;lastWinner=-1;
            ImageView turnimg=(ImageView)findViewById(R.id.turn);
            turnimg.setImageResource(players[turn]);
            for(int i=0;i<9;i++){
                ImageView img=(ImageView)findViewById(id[i]);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView img=(ImageView)v;
                        int i=Integer.parseInt(img.getTag().toString());
                        if(grid[i]==-1&&turn==0){
                            grid[i]=turn;
                            img.setImageResource(players[turn]);
                            boolean result=getResult(1);if(result){
                                player.setWon(player.getWon()+1);
                                end();winner();}else{
                                if(allfilled()){player.setDraw(player.getDraw()+1);
                                    end();tie();}
                                else{turn^=1;
                                updateTurn();sysTurn();}}
                        }
                    }
                });
            }return;
        }
        public void initGame(){
            clearBoard();newMatch(1);
            score[0]=0;score[1]=0;
            TextView sc1=(TextView)findViewById(R.id.score1);
            TextView sc2=(TextView)findViewById(R.id.score2);
            sc1.setText(Integer.toString(score[0]));sc2.setText(Integer.toString(score[1]));
            if(turn==1){sysTurn();}
            return;
        }

        public void nextMatch(){
            newMatch(1);
            clearBoard();if(turn==1){sysTurn();}
            return;
        }
        public void updateScores(){
            player.setHiscore(max(player.getHiscore(),score[0]-score[1]));
            TextView sc1=(TextView)findViewById(R.id.score1);
            TextView sc2=(TextView)findViewById(R.id.score2);
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
        public boolean getResult(int u){
            if(grid[0]!=-1){boolean won=true;
                for(int i=0;i<3;i++){
                    if(grid[i]!=grid[0]){won=false;break;}
                }if(won){
                    if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
                }won=true;
                for(int i=0;i<7;i+=3){
                    if(grid[i]!=grid[0]){won=false;break;}
                }if(won){
                    if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
                }won=true;
                for(int i=0;i<9;i+=4){
                    if(grid[i]!=grid[0]){won=false;break;}
                }if(won){
                    if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
                }won=true;
            }
            if(grid[2]!=-1&&grid[4]==grid[2]&&grid[6]==grid[4]){
                if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
            }
            if(grid[2]!=-1&&grid[5]==grid[2]&&grid[8]==grid[5]){
                if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
            }
            if(grid[1]!=-1&&grid[4]==grid[1]&&grid[7]==grid[4]){
                if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
            }if(grid[3]!=-1&&grid[4]==grid[3]&&grid[5]==grid[4]){
                if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
            }
            if(grid[6]!=-1&&grid[7]==grid[6]&&grid[8]==grid[7]){
                if(u==1){state=1;lastWinner=turn;score[turn]++;updateScores();}return true;
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
            messageimg.setImageResource(players[turn]);if(turn==0){
            message.setText("CONGRATULATIONS");}else{
                message.setText("COMPUTER WON !");
            }
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
                public void onClick(View v) {popup.dismiss();
                    nextMatch();
                }
            });
            game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                    initGame();
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
        int minimax(int chance){
            boolean res=getResult(0);if(res){
                return -10;
            }if(allfilled()){return 0;}
            int max_possible=(-10);
            for(int i=0;i<9;i++){
                if(grid[i]==-1){grid[i]=chance;
                    int temp_score=(-1)*minimax(chance^1);
                    if(temp_score>max_possible){
                        max_possible=temp_score;
                    }
                    grid[i]=-1;
                }
            }
            return max_possible;
        }
        void sysTurn(){
            int max_possible=(-100000),best_id=(-1),v=-1;
            for(int i=0;i<9;i++){
                if(grid[i]==-1){grid[i]=turn;
                    int temp_score=(-1)*minimax(turn^1);
                    if(temp_score>max_possible){
                        max_possible=temp_score;best_id=id[i];v=i;
                    }
                    grid[i]=-1;
                }
            }
            ImageView img1=(ImageView)findViewById(best_id);
            img1.setImageResource(players[turn]);grid[v]=turn;
            boolean result=getResult(1);if(result){
                player.setLost(player.getLost()+1);
                end();winner();
            return;}else{
                if(allfilled()){player.setDraw(player.getDraw()+1);tie();return;}else{
            turn^=1;updateTurn();}
        }return;
    }}
