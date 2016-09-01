/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author carlos.macedo
 */
public class MessageService {

    public void addMessage(String type, String status, String msg) {
        FacesMessage.Severity facesMessage = null;
        switch (type.toUpperCase()) {
            case "SUCCESS":
                facesMessage = FacesMessage.SEVERITY_INFO;
                break;
            case "ERROR":
                facesMessage = FacesMessage.SEVERITY_ERROR;
                break;
            case "WARN":
                facesMessage = FacesMessage.SEVERITY_WARN;
                break;
            case "FATAL":
                facesMessage = FacesMessage.SEVERITY_FATAL;
                break;
        }

        FacesMessage message = new FacesMessage(facesMessage, status, msg);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
