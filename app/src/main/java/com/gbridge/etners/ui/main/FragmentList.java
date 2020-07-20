package com.gbridge.etners.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gbridge.etners.R;
import com.gbridge.etners.ui.main.list.ListAdapter;
import com.gbridge.etners.ui.main.list.ListService;
import com.gbridge.etners.ui.main.list.interfaces.ListFragmentView;
import com.gbridge.etners.ui.main.list.models.ListResponse;

import java.util.ArrayList;

public class FragmentList extends Fragment implements ListFragmentView {

    ViewGroup viewGroup;
    private ArrayList<ListResponse.ListHistory> mListHistory;
    private ListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ListService mListService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListService = new ListService(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        mListHistory = new ArrayList<ListResponse.ListHistory>();
        mRecyclerView = viewGroup.findViewById(R.id.list_rv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mAdapter = new ListAdapter(mListHistory, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getListHistory();

        return viewGroup;
    }

    public void getListHistory(){
        mListService.getList();
    }
    @Override
    public void validateSuccess(String text) {

    }

    @Override
    public void validateFailure(String message) {

    }

    @Override
    public void getListSuccess(String message, ArrayList<ListResponse.ListHistory> listHistory) {
        if(message != null){
            mListHistory.clear();
            mListHistory.addAll(listHistory);
            for (int i = listHistory.size()-1; i >= 0; i--) {
                if(listHistory.get(i).getClockOutTime() == null){
                    mListHistory.remove(i);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
