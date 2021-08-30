package com.example.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<object> {
    public CustomAdapter(Context context,ArrayList<object> objects) {
        super(context,0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v=convertView;
        if(v==null){
              v= LayoutInflater.from(getContext()).inflate(R.layout.list_items,parent,false);
        }
        object obj=getItem(position);
        ImageView player1=(ImageView)v.findViewById(R.id.play1);
        player1.setImageResource(obj.getPlayer1());
        ImageView player2=(ImageView)v.findViewById(R.id.play2);
        player2.setImageResource(obj.getPlayer2());
        return v;
    }
}
