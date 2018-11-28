package com.pagatodo.network_manager.dtos.sender_yg.requests;

import java.io.Serializable;

public class LogInRequest implements Serializable {

    private Request request;

    public LogInRequest(String usuarioCorreo, String contrasena) {
        request = new Request(usuarioCorreo, contrasena);
    }

    private class Request {

        private String UsuarioCorreo = "";
        private String Contrasena = "";

        public Request(String usuarioCorreo, String contrasena) {
            UsuarioCorreo = usuarioCorreo;
            Contrasena = contrasena;
        }
    }
}
