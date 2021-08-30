package com.example.tictactoe;

import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StatsActivity extends AppCompatActivity {
    String[] list=new String[2];
     @Override
     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.stats_activity);
         list[0]="Single Player";
         list[1]="Two Player";
         TabLayout tab=(TabLayout)findViewById(R.id.tab);
         ViewPager2 view=(ViewPager2)findViewById(R.id.viewpager);
         view.setAdapter(new CustomFragmentAdapter(this));
         new TabLayoutMediator(tab,view,
                 (tab1,position) -> tab1.setText(list[position])).attach();
     }
}
