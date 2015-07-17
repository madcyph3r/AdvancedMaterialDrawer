package de.madcyph3r.example.example.functionally.actionBarButtonsActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;


public class FragmentActionBarButtons extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.example_action_bar_buttons, container, false);

        setHasOptionsMenu(true);

        LinearLayout ll = (LinearLayout) v.findViewById(R.id.linearLayout);

        Button button = new Button(this.getActivity());
        button.setText("show menu icon and unlock the drawer");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setGravity(Gravity.CENTER);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialNavigationDrawer drawer = (MaterialNavigationDrawer) getActivity();
                // show the menu button and unlock the drawer
                drawer.showActionBarMenuIcon(MaterialNavigationDrawer.ActionBarMenuItem.MENU);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        Button button2 = new Button(this.getActivity());
        button2.setText("hide menu icon and lock the drawer");
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button2.setLayoutParams(params2);
        button2.setGravity(Gravity.CENTER);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide the drawer button and lock the drawer to close
                MaterialNavigationDrawer drawer = (MaterialNavigationDrawer) getActivity();
                drawer.showActionBarMenuIcon(MaterialNavigationDrawer.ActionBarMenuItem.NONE);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

        Button button3 = new Button(this.getActivity());
        button3.setText("convert menu icon to back icon and lock the drawer");
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button3.setLayoutParams(params3);
        button3.setGravity(Gravity.CENTER);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide the drawer button and show the back button and lock the drawer to close
                MaterialNavigationDrawer drawer = (MaterialNavigationDrawer) getActivity();
                drawer.showActionBarMenuIcon(MaterialNavigationDrawer.ActionBarMenuItem.BACK);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

        ll.addView(button);
        ll.addView(button2);
        ll.addView(button3);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                //you can here use the back pattern. call super.onBackPressed();
                Toast.makeText(getActivity(), "your back action. you can use here the back pattern, if you want", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
