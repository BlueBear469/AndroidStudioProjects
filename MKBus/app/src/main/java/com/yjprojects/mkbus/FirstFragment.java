package com.yjprojects.mkbus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jyj on 2016-04-12.
 */
public class FirstFragment extends Fragment {

    GridLayoutManager gridLayout;
    Context context;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.recycler_layout, container, false);
        context = getActivity();

        List<ImageText> rowListItem = getAllItemList();
        gridLayout = new GridLayoutManager(context, 2);

        RecyclerView rView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        rView.setHasFixedSize(true);
        rView.setNestedScrollingEnabled(false);
        rView.setLayoutManager(gridLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(context, rowListItem);
        rView.setAdapter(rcAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(rcAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rView);
        return view;
    }

    private List<ImageText> getAllItemList(){
        //Sample
        List allitems = new ArrayList<>();
        allitems.add(new ImageText("북부수도사업소", "15190", "12", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("성북초교", "21841", "25", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("광운초교앞", "75132", "22", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("장삼주유소", "15090", "12", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("한성대입구역", "11299", "10", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("안암동주민센터안암동주민센터", "15100", "4", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("북부수도사업소", "15190", "12", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("성북초교", "21841", "25", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("광운초교앞", "75132", "22", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("장삼주유소", "15090", "12", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("한성대입구역", "11299", "10", getResources().getDrawable(R.drawable.ic_place_white_48dp)));
        allitems.add(new ImageText("안암동주민센터안암동주민센터", "15100", "4", getResources().getDrawable(R.drawable.ic_place_white_48dp)));

        return allitems;
    }
}
