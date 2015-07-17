package de.madcyph3r.example.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentInstruction extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        String instruction;
        String title;
        if(bundle != null) {
            instruction = bundle.getString("instruction");
        } else {
            instruction = "Open the drawer with the menu icon." +
                    " Then choose the category and the example, which you want to see.";
        }

        TextView text = new TextView(this.getActivity());
        text.setText(instruction);
        text.setGravity(Gravity.CENTER);
        return text;

    }
}
