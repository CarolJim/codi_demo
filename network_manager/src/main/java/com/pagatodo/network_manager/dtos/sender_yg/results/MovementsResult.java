package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.util.ArrayList;
import java.util.List;

public class MovementsResult extends SenderGenericResult {

    private List<MovementsItemResult> Data;

    public MovementsResult() {
        Data = new ArrayList<MovementsItemResult>();
    }

    public List<MovementsItemResult> getData() {
        return Data;
    }

    public void setData(List<MovementsItemResult> data) {
        Data = data;
    }
}
