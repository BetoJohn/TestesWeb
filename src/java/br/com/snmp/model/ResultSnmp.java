/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

import java.util.Objects;

/**
 *
 * @author carlos.macedo
 */
public class ResultSnmp {

    private int port;
    private int value;
    private String status;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     /***
     * Calcula o hashcode do objeto
     * Método necessário para a verificação de igualdade entre o arquivo json e a nova consulta Snmp
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.port;
        hash = 17 * hash + this.value;
        hash = 17 * hash + Objects.hashCode(this.status);
        return hash;
    }

    /***
     * Verifica a igualdade entre os objetos
     * Método necessário para a verificação de igualdade entre o arquivo json e a nova consulta Snmp
     * @param obj
     * @return true se forem iguais
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultSnmp other = (ResultSnmp) obj;
        if (this.port != other.port) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }


}
