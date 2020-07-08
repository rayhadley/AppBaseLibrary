package com.stardust.app.base.common;

//import androidx.core.app.Fragment;
//import androidx.core.app.FragmentManager;
//import androidx.core.app.FragmentPagerAdapter;
//
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BaseFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;


	public BaseFragmentAdapter(FragmentManager fm, int i) {
		super(fm, i);
	}

	public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}