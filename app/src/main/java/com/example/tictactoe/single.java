package com.example.tictactoe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

public class single extends Fragment {
    Gson gson=new Gson();
    SharedPreferences storage;
    TextView hiscore,won,lost,draw;
    stat player;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        storage=PreferenceManager.getDefaultSharedPreferences(getContext());
        String def=gson.toJson(new stat());
            player=gson.fromJson(storage.getString("single",def),stat.class);
        View v=inflater.inflate(R.layout.single_layout,container,false);
        hiscore=(TextView)v.findViewById(R.id.hiscore);
        won=(TextView)v.findViewById(R.id.won_score);
        lost=(TextView)v.findViewById(R.id.lost_score);
        draw=(TextView)v.findViewById(R.id.draw_score);
        ImageView del=(ImageView)v.findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player=new stat();
                SharedPreferences.Editor edit=storage.edit();
                edit.putString("single",gson.toJson(player));edit.apply();
                setViews();
            }
        });
        setViews();
        return v;
    }
    public void setViews(){
        hiscore.setText(Integer.toString(player.getHiscore()));
        won.setText(Integer.toString(player.getWon()));
        lost.setText(Integer.toString(player.getLost()));
        draw.setText(Integer.toString(player.getDraw()));return;
    }
}
