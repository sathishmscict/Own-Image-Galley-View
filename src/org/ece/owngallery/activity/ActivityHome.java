package org.ece.owngallery.activity;

import org.ece.owngallery.R;
import org.ece.owngallery.adapter.SlideMenuAdapter;
import org.ece.owngallery.adapter.SlideMenuAdapter.SlideMenuAdapterInterface;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ActivityHome extends ActionBarActivity implements SlideMenuAdapterInterface{

	private Context mContext;
	private Toolbar toolbar;
	private DrawerLayout Drawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
	private Fragment currentFragment=null;
	
	private String[] title={"Gallery","Video","Resent"};
	private ListView slidingList;
	private SlideMenuAdapter mSlideMenuAdapter;
	private int currentPosition=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		 mContext=ActivityHome.this;
		 initializeActionBar();
		 initialCalling();
	}

	@Override
	public void onBackPressed() {
		if(Drawer.isDrawerOpen(Gravity.LEFT)){
			Drawer.closeDrawer(Gravity.LEFT);
	    }else{
	        super.onBackPressed();
	    }
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void slideRowClickEvent(int postion) {
		if (currentPosition== postion) {
			closeDrware();
			return;
		}
		currentPosition= postion;
		getFragment(postion);
		attachedFragment();
	}
	
	private void initializeActionBar() {
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		toolbar.setTitle("");
		setSupportActionBar(toolbar);
		
		slidingList=(ListView)findViewById(R.id.sliding_listView);
		mSlideMenuAdapter=new SlideMenuAdapter(mContext, title);
		mSlideMenuAdapter.setSlidemenuadapterinterface(this);
		slidingList.setAdapter(mSlideMenuAdapter);

		Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
		mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar,
				R.string.openDrawer, R.string.closeDrawer) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);

			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

		};
		Drawer.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
		

	}
	
	private void closeDrware(){
		if(Drawer.isDrawerOpen(Gravity.LEFT)){
			Drawer.closeDrawer(Gravity.LEFT);
	    }
	}
	
	private void initialCalling(){
		fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

		getFragment(0);
		attachedFragment();
	}
	
	
	
	private void attachedFragment(){
		try {
			if (currentFragment != null) {
				if (fragmentTransaction.isEmpty()) {
					fragmentTransaction.add(R.id.fragment_container, currentFragment,"" + currentFragment.toString());
					fragmentTransaction.commit();
					toolbar.setTitle("Gallery");
				}else {
					fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.fragment_container, currentFragment,"" + currentFragment.toString());
					fragmentTransaction.commit();
					toolbar.setTitle(title[1]);
				}
				
			}
			closeDrware();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void getFragment(int postion){
		switch (postion) { 
		case 0:
			currentFragment = new GalleryFragment();
			break;
		case 1:
			currentFragment = new VideoFragment();
			break;

		default:
			break;
		}
	}
}
