package de.madcyph3r.example.example.theme.actionBarOverlayActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.madcyph3r.example.R;

/**
 * Created by marc on 12.03.2015.
 */
public class FragmentActionBarOverlay extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.mat3);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return imageView;
    }


}

