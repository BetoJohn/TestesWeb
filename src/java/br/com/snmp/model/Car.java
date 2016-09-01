/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

/**
 *
 * @author carlos.macedo
 * Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice(), getRandomSoldState())
 *  private String identificacao;
    private String versao;
    private OID oid;
    private String comunidade;
    private String ip;
 */
public class Car {
    private String id;
    private String identificacao;
    private String versao;
    private OID oid;
    private String comunidade;
    private String ip;

    public Car(String id, String identificacao, String versao, OID oid, String comunidade, String ip) {
        this.id = id;
        this.identificacao = identificacao;
        this.versao = versao;
        this.oid = oid;
        this.comunidade = comunidade;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

   
    

    
    
}
