package com.pagatodo.network_manager.dtos.sender_yg.results;

public class CreateUserDataResult {

    private ClientResult Cliente;
    private ControlResult Control;
    private UyuSenderResult Emisor;
    private UserResult Usuario;

    public ClientResult getCliente() {
        return Cliente;
    }

    public void setCliente(ClientResult cliente) {
        Cliente = cliente;
    }

    public ControlResult getControl() {
        return Control;
    }

    public void setControl(ControlResult control) {
        Control = control;
    }

    public UyuSenderResult getEmisor() {
        return Emisor;
    }

    public void setEmisor(UyuSenderResult emisor) {
        Emisor = emisor;
    }

    public UserResult getUsuario() {
        return Usuario;
    }

    public void setUsuario(UserResult usuario) {
        Usuario = usuario;
    }
}
