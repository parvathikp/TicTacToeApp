package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{
    int player1Img=R.drawable.cross;
    Dialog selector;
    int player2Img=R.drawable.zero;
    ArrayList<object> objlist;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Button play=(Button)findViewById(R.id.play);
        Button choose=(Button)findViewById(R.id.choose);
        Button single=(Button)findViewById(R.id.single);
        Button stats=(Button)findViewById(R.id.stats);
        objlist=new ArrayList<>();
        objlist.add(new object(R.drawable.fire,R.drawable.waterdrop));
        objlist.add(new object(R.drawable.cross,R.drawable.zero));
        objlist.add(new object(R.drawable.ariel,R.drawable.ursula));
        objlist.add(new object(R.drawable.snow,R.drawable.vill));
        objlist.add(new object(R.drawable.sun,R.drawable.moon));
        objlist.add(new object(R.drawable.cat,R.drawable.dog));
        objlist.add(new object(R.drawable.ironman,R.drawable.thanos));
        objlist.add(new object(R.drawable.batman,R.drawable.joker));
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=new Dialog(HomeActivity.this);
                selector.setContentView(R.layout.chooser);
                TextView close=(TextView)selector.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selector.dismiss();
                    }
                });
                ListView list=(ListView)selector.findViewById(R.id.listview);
                CustomAdapter adapter=new CustomAdapter(getApplicationContext(),objlist);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        object selected=objlist.get(position);
                        player1Img=selected.getPlayer1();
                        player2Img=selected.getPlayer2();
                        selector.dismiss();
                    }
                });
                selector.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                selector.show();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call=new Intent(HomeActivity.this,MainActivity.class);
                call.putExtra("player1",player1Img);
                call.putExtra("player2",player2Img);
                startActivity(call);
            }
        });
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call=new Intent(HomeActivity.this,AutoActivity.class);
                call.putExtra("player1",player1Img);
                call.putExtra("player2",player2Img);
                startActivity(call);
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call=new Intent(HomeActivity.this,StatsActivity.class);
                startActivity(call);
            }
        });}

}
