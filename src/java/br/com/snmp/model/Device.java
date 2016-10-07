/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

import java.io.Serializable;

/**
 *
 * @author carlos.macedo
 */
public class Device implements Serializable{
    private int id;
    private String identificacao;
    private String versao;
    private OID oid;
    private String comunidade;
    private String ip;
    private Integer portInicial;
    private Integer portFinal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getPortInicial() {
        return portInicial;
    }

    public void setPortInicial(Integer portInicial) {
        this.portInicial = portInicial;
    }

    public Integer getPortFinal() {
        return portFinal;
    }

    public void setPortFinal(Integer portFinal) {
        this.portFinal = portFinal;
    }
    
    

    @Override
    public String toString() {
        return "Indentificação: "+identificacao+" | Versão: "+versao+" | Comunidade: "+comunidade+" | IP: "+ip+" | Port Inicial: "+ portInicial+" | Port Final: "+portFinal; 
    }
    
    
    
}
