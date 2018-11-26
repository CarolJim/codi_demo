package com.pagatodo.network_manager.dtos.sender_yg.results;

public class ValidatePersonResult extends SenderGenericResult {

    private VPResponse Data;

    public VPResponse getData() {
        return Data;
    }

    public  class VPResponse {
        String AMaterno, APaterno, CURP, EntidadNacimiento, Nombre;

        public String getAMaterno() {
            return AMaterno;
        }

        public void setAMaterno(String AMaterno) {
            this.AMaterno = AMaterno;
        }

        public String getAPaterno() {
            return APaterno;
        }

        public void setAPaterno(String APaterno) {
            this.APaterno = APaterno;
        }

        public String getCURP() {
            return CURP;
        }

        public void setCURP(String CURP) {
            this.CURP = CURP;
        }

        public String getEntidadNacimiento() {
            return EntidadNacimiento;
        }

        public void setEntidadNacimiento(String entidadNacimiento) {
            EntidadNacimiento = entidadNacimiento;
        }

        public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }
    }
}
