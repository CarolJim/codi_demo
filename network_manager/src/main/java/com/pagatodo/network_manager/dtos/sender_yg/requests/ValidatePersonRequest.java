package com.pagatodo.network_manager.dtos.sender_yg.requests;

public class ValidatePersonRequest {

    private PersonData request;

    public ValidatePersonRequest(PersonData request) {
        this.request = request;
    }

    public void setNombre(String nombre) {
        request.Nombre = nombre;
    }

    public void setPrimerApellido(String primerApellido) {
        request.PrimerApellido = primerApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        request.SegundoApellido = segundoApellido;
    }

    public void setGenero(String genero) {
        request.Genero = genero;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        request.FechaNacimiento = fechaNacimiento;
    }

    public void setIdEstadoNacimiento(String idEstadoNacimiento) {
        request.IdEstadoNacimiento = idEstadoNacimiento;
    }

    public static class PersonData{
       public String Nombre, PrimerApellido, SegundoApellido, Genero, FechaNacimiento, IdEstadoNacimiento;

        public PersonData(String nombre, String primerApellido, String segundoApellido, String genero,
                          String fechaNacimiento, String idEstadoNacimiento) {
            Nombre = nombre;
            PrimerApellido = primerApellido;
            SegundoApellido = segundoApellido;
            Genero = genero;
            FechaNacimiento = fechaNacimiento;
            IdEstadoNacimiento = idEstadoNacimiento;
        }
    }
}
