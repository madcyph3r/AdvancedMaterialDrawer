package de.madcyph3r.example.example.functionally.masterChildNavActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class ChildFragment extends Fragment {

    private MaterialNavigationDrawer drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        drawer = (MaterialNavigationDrawer) getActivity();

        // important, to handle the back click
        setHasOptionsMenu(true);

        TextView text = new TextView(getActivity());
        text.setText("your content");
        text.setGravity(Gravity.CENTER);

        // show back button
        drawer.showActionBarMenuIcon(MaterialNavigationDrawer.ActionBarMenuItem.BACK);
        // close and lock the drawer
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        return text;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                // your back action, here get the last section fragment called
                if ((drawer.getCurrentSection().getTarget() == MaterialSection.TARGET_FRAGMENT)) {

                    // show the menu button and unlock the drawer
                    drawer.showActionBarMenuIcon(MaterialNavigationDrawer.ActionBarMenuItem.MENU);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                    // set the fragment from the selected section
                    drawer.setCustomFragment(drawer.getCurrentSection().getTargetFragment(), drawer.getCurrentSection().getFragmentTitle());
                    // normally currentSection gets unselect on setCustomFragment call
                    // in the next relase, i will add a new method without unselect
                    drawer.getCurrentSection().select();

                    // call on current git head. drawer.getCurrentSection().select(); is not needed
                    // drawer.setCustomFragment(drawer.getCurrentSection().getTargetFragment(), drawer.getCurrentSection().getFragmentTitle(), true, false);

                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}