package com.alexflex.testyoursoftskills.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alexflex.testyoursoftskills.fragments.LoginPasswordFragment;
import com.alexflex.testyoursoftskills.fragments.ProfileFragment;
import com.alexflex.testyoursoftskills.fragments.YourPhotoFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public int getCount(){
        return 3;
    }

    @Override
    public @NonNull Fragment getItem(int position){
        switch (position){
            case 1:
                return new YourPhotoFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new LoginPasswordFragment();
        }
    }
}
