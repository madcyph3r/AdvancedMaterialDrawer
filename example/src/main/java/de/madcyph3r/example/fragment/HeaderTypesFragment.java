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
import de.madcyph3r.example.example.headerTypes.CustomHeaderActivity;
import de.madcyph3r.example.example.headerTypes.CustomHeaderBelowToolbarActivity;
import de.madcyph3r.example.example.headerTypes.HeadItemFiveActivity;
import de.madcyph3r.example.example.headerTypes.HeadItemOneActivity;
import de.madcyph3r.example.example.headerTypes.HeadItemThreeActivity;
import de.madcyph3r.example.example.headerTypes.ImageHeaderActivity;
import de.madcyph3r.example.example.headerTypes.ImageHeaderBelowToolbarActivity;
import de.madcyph3r.example.example.headerTypes.NoHeaderActivity;
import de.madcyph3r.example.example.headerTypes.NoHeaderBelowToolbarActivity;

public class HeaderTypesFragment extends Fragment {

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
        content.add(new ExampleData("No Header", NoHeaderActivity.class));
        content.add(new ExampleData("No Header Below Toolbar", NoHeaderBelowToolbarActivity.class));
        content.add(new ExampleData("Image Header", ImageHeaderActivity.class));
        content.add(new ExampleData("Image Header Below Toolbar", ImageHeaderBelowToolbarActivity.class));
        //content.add(new ExampleData("Image Header With Custom Height", ImageHeaderCustomHeightActivity.class));
        content.add(new ExampleData("Custom Header", CustomHeaderActivity.class));
        content.add(new ExampleData("Custom Header Below Toolbar", CustomHeaderBelowToolbarActivity.class));
        //content.add(new ExampleData("Custom Header With Custom Height", CustomHeaderCustomHeightActivity.class));
        content.add(new ExampleData("Head Item Style (One Item)", HeadItemOneActivity.class));
        content.add(new ExampleData("Head Item Style (Three Item)", HeadItemThreeActivity.class));
        content.add(new ExampleData("Head Item Style (Five Item)", HeadItemFiveActivity.class));


        final ParallaxRecyclerAdapter<ExampleData> adapter = new ParallaxRecyclerAdapter<>(content);

        View header = getActivity().getLayoutInflater().inflate(R.layout.recycleview_header, recyclerView, false);
        ImageView headerImage = (ImageView) header.findViewById(R.id.imageViewHeader);
        headerImage.setImageResource(R.drawable.l_4);

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
