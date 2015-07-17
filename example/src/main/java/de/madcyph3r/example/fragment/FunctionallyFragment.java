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
import de.madcyph3r.example.example.functionally.ActionBarButtonsActivity;
import de.madcyph3r.example.example.functionally.AddRemoveHeadItemRuntimeActivity;
import de.madcyph3r.example.example.functionally.ClosePrevDrawerActivity_Activity;
import de.madcyph3r.example.example.functionally.MasterChildNavActivity;
import de.madcyph3r.example.example.functionally.NoClosePrevDrawerActivity_Activity;
import de.madcyph3r.example.example.functionally.SetCustomFragmentActivity;
import de.madcyph3r.example.example.headItemTypes.HeadItemTwoNoFragmentLoadOnChangeActivity;
import de.madcyph3r.example.example.headItemTypes.HeadItemTwoOnlyOneHasMenuActivity;
import de.madcyph3r.example.example.menu.AddRemoveMenuItemsActivity;

public class FunctionallyFragment extends Fragment {

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
        content.add(new ExampleData("Change/Hide Actionbar Button", ActionBarButtonsActivity.class));
        content.add(new ExampleData("Add Remove HeadItems At Runtime", AddRemoveHeadItemRuntimeActivity.class));
        content.add(new ExampleData("Add And Remove Menu Items At Runtime", AddRemoveMenuItemsActivity.class));
        content.add(new ExampleData("No Close Previous Drawer Activity", NoClosePrevDrawerActivity_Activity.class));
        content.add(new ExampleData("Close Previous Drawer Activity", ClosePrevDrawerActivity_Activity.class));
        content.add(new ExampleData("Set Custom Fragment (Not From Section)", SetCustomFragmentActivity.class));
        content.add(new ExampleData("Head Item Style (Two Items) Only First Item Has a Menu", HeadItemTwoOnlyOneHasMenuActivity.class));
        content.add(new ExampleData("Head Item Style (Two Items) Fragment Doesn't Change On HeadItem Change", HeadItemTwoNoFragmentLoadOnChangeActivity.class));
        content.add(new ExampleData("Master Child Navigation", MasterChildNavActivity.class));


        final ParallaxRecyclerAdapter<ExampleData> adapter = new ParallaxRecyclerAdapter<>(content);

        View header = getActivity().getLayoutInflater().inflate(R.layout.recycleview_header, recyclerView, false);
        ImageView headerImage = (ImageView) header.findViewById(R.id.imageViewHeader);
        headerImage.setImageResource(R.drawable.l_3);

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
