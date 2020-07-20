package com.gbridge.etners.ui.main.list;

import com.gbridge.etners.ui.main.list.interfaces.ListFragmentView;
import com.gbridge.etners.ui.main.list.interfaces.ListRetrofitInterface;
import com.gbridge.etners.ui.main.list.models.ListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gbridge.etners.ApplicationClass.getRetrofit;

public class ListService {
    private final ListFragmentView mListFragmentView;

    public ListService(final ListFragmentView listFragmentView) {
        this.mListFragmentView = listFragmentView;
    }

    public void getList(){
        final ListRetrofitInterface listRetrofitInterface = getRetrofit().create(ListRetrofitInterface.class);
        listRetrofitInterface.getList().enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                final ListResponse listResponse = response.body();
                if (listResponse == null) {
                    mListFragmentView.validateFailure(null);
                    return;
                }

                mListFragmentView.getListSuccess(listResponse.getMessage(),listResponse.getListHistory());
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                mListFragmentView.validateFailure(null);
            }
        });
    }
}
