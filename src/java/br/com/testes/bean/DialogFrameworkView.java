/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.bean;

import br.com.snmp.model.Device;
import br.com.snmp.model.OID;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author carlos.macedo
 */
@ManagedBean(name = "dialogView")
public class DialogFrameworkView implements Serializable{

    private String identificacao;
    private String versao;
    private OID oid;
    private String comunidade;
    private String ip;

    public void dialogViewCadastroDevice() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("responsive", true);
        options.put("contentHeight", 540);
        options.put("contentWidth", 560);
        RequestContext.getCurrentInstance().openDialog("dialogCadastro", options, null);
       
    }
    
    public void dialogViewEditDevice() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("responsive", true);
//        options.put("contentHeight", 540);
//        options.put("contentWidth", 560);
        RequestContext.getCurrentInstance().openDialog("dialogEditDevice", options, null);
       
    }
    
    public void dialogViewCadastroOID() {
        System.out.println("Identificacao "+ identificacao);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
//        options.put("modal", true);
        options.put("responsive", true);
//        options.put("contentHeight", 540);
//        options.put("contentWidth", 560);
        RequestContext.getCurrentInstance().openDialog("dialogCadastroOID", options, null);
       
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public OID getOid() {
        return oid;
    }

    public void setOid(OID oid) {
        this.oid = oid;
    }

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    
    
}
