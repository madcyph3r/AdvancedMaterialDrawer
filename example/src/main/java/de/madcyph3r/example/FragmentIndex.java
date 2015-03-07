package de.madcyph3r.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by neokree on 24/11/14.
 */
public class FragmentIndex extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView text = new TextView(this.getActivity());
        text.setText("Section - Click me to add new section dinamically");
        text.setGravity(Gravity.CENTER);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = ((MaterialNavigationDrawer)getActivity()).getCurrentMenu().getSections().size();
                ((MaterialNavigationDrawer)getActivity()).newSection("new section " + i, getResources().getDrawable(R.drawable.ic_launcher), false, ((MaterialNavigationDrawer)getActivity()).getCurrentMenu(), true);
            }
        });

        return text;

    }
}
