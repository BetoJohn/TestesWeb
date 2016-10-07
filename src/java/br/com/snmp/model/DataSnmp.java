/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author carlos.macedo
 */
public class DataSnmp {

    private int id;
    private int port;
    private int value;
    private String status;
    private int device_id;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.port;
        hash = 13 * hash + this.value;
        hash = 13 * hash + Objects.hashCode(this.status);
        hash = 13 * hash + this.device_id;
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
        final DataSnmp other = (DataSnmp) obj;
        if (this.port != other.port) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (this.device_id != other.device_id) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }

    
}
