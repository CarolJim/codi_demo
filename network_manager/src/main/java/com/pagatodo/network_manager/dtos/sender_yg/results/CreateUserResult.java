package com.pagatodo.network_manager.dtos.sender_yg.results;

public class CreateUserResult extends SenderGenericResult {

    private CreateUserDataResult Data;

    public CreateUserResult() {
        Data = new CreateUserDataResult();
    }

    public CreateUserDataResult getData() {
        return Data;
    }

    public void setData(CreateUserDataResult data) {
        Data = data;
    }
}
