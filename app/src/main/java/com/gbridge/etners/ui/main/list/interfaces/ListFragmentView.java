package com.gbridge.etners.ui.main.list.interfaces;

import com.gbridge.etners.ui.main.list.models.ListResponse;

import java.util.ArrayList;

public interface ListFragmentView {

    void validateSuccess(String text);

    void validateFailure(String message);

    void getListSuccess(String message, ArrayList<ListResponse.ListHistory> listHistory);
}
