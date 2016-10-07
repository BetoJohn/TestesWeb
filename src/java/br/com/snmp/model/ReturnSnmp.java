/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author carlos.macedo
 */
public class ReturnSnmp {
    private int device_id;
    private Date time;
    private String ipDevice;
    private List<ResultSnmp> result;

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }
   
    public String getIpDevice() {
        return ipDevice;
    }

    public void setIpDevice(String ipDevice) {
        this.ipDevice = ipDevice;
    }

    public List<ResultSnmp> getResult() {
        return result;
    }

    public void setResult(List<ResultSnmp> result) {
        this.result = result;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    
    
    
    
}
