package com.pagatodo.network_manager.dtos.sender_yg.requests;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {

    private Data request;

    public CreateUserRequest(String usuario, String contrasena, String nombre, String primerApellido,
                             String segundoApellido, String genero, String fechaNacimiento, String RFC,
                             String CURP, String nacionalidad, String IdEstadoNacimiento, String correo,
                             String telefono, String telefonoCelular, String idColonia, String colonia,
                             String CP, String calle, String numeroExterior, String numeroInterior, int idPaisNacimiento) {
        request = new Data(usuario, contrasena, nombre, primerApellido, segundoApellido, genero, fechaNacimiento,
                RFC, CURP, idPaisNacimiento, nacionalidad, IdEstadoNacimiento,correo, telefono, telefonoCelular,
                idColonia, colonia, CP, calle, numeroExterior, numeroInterior);
    }

    class Data {
         String Usuario = "";
         String Contrasena = "";
         String Nombre = "";
         String PrimerApellido = "";
         String SegundoApellido = "";
         String Genero = "";
         String FechaNacimiento = "";
         String RFC = "";
         String CURP = "";
         int IdPaisNacimiento;
         String Nacionalidad;
         String IdEstadoNacimiento;
         String Correo = "";
         String Telefono = "";
         String TelefonoCelular = "";
         String IdColonia = "";
         String Colonia = "";
         String CP = "";
         String Calle = "";
         String NumeroExterior = "";
         String NumeroInterior = "";

        public Data(String usuario, String contrasena, String nombre, String primerApellido, String segundoApellido,
                    String genero, String fechaNacimiento, String RFC, String CURP, int idPaisNacimiento,
                    String nacionalidad, String idEstadoNacimiento, String correo, String telefono,
                    String telefonoCelular, String idColonia, String colonia, String CP, String calle,
                    String numeroExterior, String numeroInterior) {
            Usuario = usuario;
            Contrasena = contrasena;
            Nombre = nombre;
            PrimerApellido = primerApellido;
            SegundoApellido = segundoApellido;
            Genero = genero;
            FechaNacimiento = fechaNacimiento;
            this.RFC = RFC;
            this.CURP = CURP;
            IdPaisNacimiento = idPaisNacimiento;
            Nacionalidad = nacionalidad;
            IdEstadoNacimiento = idEstadoNacimiento;
            Correo = correo;
            Telefono = telefono;
            TelefonoCelular = telefonoCelular;
            IdColonia = idColonia;
            Colonia = colonia;
            this.CP = CP;
            Calle = calle;
            NumeroExterior = numeroExterior;
            NumeroInterior = numeroInterior;
        }
    }
}
