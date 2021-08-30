package com.example.tictactoe;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CustomFragmentAdapter extends FragmentStateAdapter{
    public CustomFragmentAdapter(FragmentActivity fm){
        super(fm);
    }
    @Override
    public Fragment createFragment(int position){
        if(position==0){
            return new single();
        }
        else{
            return new two();
        }
    }
    @Override
    public int getItemCount(){
        return 2;
    }
}
