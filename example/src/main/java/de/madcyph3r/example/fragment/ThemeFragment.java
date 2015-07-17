package de.madcyph3r.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poliveira.parallaxrecycleradapter.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.madcyph3r.example.DividerItemDecoration;
import de.madcyph3r.example.MainActivity;
import de.madcyph3r.example.R;
import de.madcyph3r.example.data.ExampleData;
import de.madcyph3r.example.example.theme.ActionBarOverlayActivity;
import de.madcyph3r.example.example.theme.ActionBarOwnFontActivity;
import de.madcyph3r.example.example.theme.DarkThemeActivity;
import de.madcyph3r.example.example.theme.KitKatTranslucentStatusBarActivity;
import de.madcyph3r.example.example.theme.LightThemeActivity;
import de.madcyph3r.example.example.theme.MyThemeActivity;
import de.madcyph3r.example.example.theme.OnlyIconsActivity;
import de.madcyph3r.example.example.theme.OwnDrawerWidthActivity;
import de.madcyph3r.example.example.theme.UniqueToolbarColorActivity;

public class ThemeFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycleview_layout, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        createAdapter(mRecyclerView);

        return v;
    }

    private void createAdapter(RecyclerView recyclerView) {
        final List<ExampleData> content = new ArrayList<>();
        content.add(new ExampleData("Dark Theme", DarkThemeActivity.class));
        content.add(new ExampleData("Light Theme", LightThemeActivity.class));
        content.add(new ExampleData("My Theme", MyThemeActivity.class));
        content.add(new ExampleData("Unique Toolbar Color", UniqueToolbarColorActivity.class));
        content.add(new ExampleData("Actionbar Overlay", ActionBarOverlayActivity.class));
        content.add(new ExampleData("KitKat Translucent Statusbar", KitKatTranslucentStatusBarActivity.class));
        content.add(new ExampleData("Actionbar Own Font", ActionBarOwnFontActivity.class));
        content.add(new ExampleData("Own Drawer Width", OwnDrawerWidthActivity.class));
        content.add(new ExampleData("Only Icons Menu", OnlyIconsActivity.class));

        final ParallaxRecyclerAdapter<ExampleData> adapter = new ParallaxRecyclerAdapter<>(content);

        View header = getActivity().getLayoutInflater().inflate(R.layout.recycleview_header, recyclerView, false);
        ImageView headerImage = (ImageView) header.findViewById(R.id.imageViewHeader);
        headerImage.setImageResource(R.drawable.l_8);

        adapter.setParallaxHeader(header, recyclerView);
        adapter.setData(content);
        adapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ((ViewHolder) viewHolder).textView.setText(adapter.getData().get(i).getTitle());
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
                final ViewHolder holder = new ViewHolder(getActivity().getLayoutInflater().inflate(R.layout.recycleview_row, viewGroup, false));

                return holder;
            }

            @Override
            public int getItemCount() {
                return content.size();
            }
        });

        adapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View view) {
                ((MainActivity) getActivity()).setActionBarAlpha(percentage);
            }
        });

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View view, int i) {
                if(i >= 0) {
                    Intent intent = new Intent(getActivity(), adapter.getData().get(i).getClazz());
                    startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
