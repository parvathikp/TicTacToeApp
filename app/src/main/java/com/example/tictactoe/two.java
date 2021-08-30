package com.example.tictactoe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

public class two extends Fragment {
    Gson gson=new Gson();
    SharedPreferences storage;

    TextView hiscore1,won1,lost1,draw1,hiscore2,won2,lost2,draw2;
    stat player1,player2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        storage=PreferenceManager.getDefaultSharedPreferences(getContext());
        String def=gson.toJson(new stat());
            player1=gson.fromJson(storage.getString("player1",def),stat.class);
            player2=gson.fromJson(storage.getString("player2",def),stat.class);

        View v=inflater.inflate(R.layout.two_layout,container,false);
        hiscore1=(TextView)v.findViewById(R.id.hiscore);
        won1=(TextView)v.findViewById(R.id.won_score);
        lost1=(TextView)v.findViewById(R.id.lost_score);
        draw1=(TextView)v.findViewById(R.id.draw_score);
        hiscore2=(TextView)v.findViewById(R.id.hiscore2);
        won2=(TextView)v.findViewById(R.id.won_score2);
        lost2=(TextView)v.findViewById(R.id.lost_score2);
        draw2=(TextView)v.findViewById(R.id.draw_score2);
        ImageView del=(ImageView)v.findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1=new stat();player2=new stat();
                SharedPreferences.Editor edit=storage.edit();
                edit.putString("player1",gson.toJson(player1));edit.apply();
                edit.putString("player2",gson.toJson(player2));edit.apply();
                setViews();
            }
        });
        setViews();
        return v;
    }
    public void setViews(){
        hiscore1.setText(Integer.toString(player1.getHiscore()));
        won1.setText(Integer.toString(player1.getWon()));
        lost1.setText(Integer.toString(player1.getLost()));
        draw1.setText(Integer.toString(player1.getDraw()));
        hiscore2.setText(Integer.toString(player2.getHiscore()));
        won2.setText(Integer.toString(player2.getWon()));
        lost2.setText(Integer.toString(player2.getLost()));
        draw2.setText(Integer.toString(player2.getDraw()));return;
    }
}