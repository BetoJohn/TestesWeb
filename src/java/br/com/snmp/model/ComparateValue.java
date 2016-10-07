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
public class ComparateValue {
    private int device_id;
    private ResultSnmp result;

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public ResultSnmp getResult() {
        return result;
    }

    public void setResult(ResultSnmp result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.device_id;
        hash = 59 * hash + Objects.hashCode(this.result);
        return hash;
    }

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
        final ComparateValue other = (ComparateValue) obj;
        if (this.device_id != other.device_id) {
            return false;
        }
        
        if (this.result.getPort() != other.result.getPort()) {
            return false;
        }
        if (this.result.getValue() != other.result.getValue()) {
            return false;
        }        
        if (!Objects.equals(this.result.getStatus(), other.result.getStatus())) {
            return false;
        }
        return true;
    }
   
       
    
}
