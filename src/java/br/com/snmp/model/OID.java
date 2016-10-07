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
public class OID implements Serializable {
    private int id;
    private String descricao;
//    private Integer portInicial;
//    private Integer portFinal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

//    public Integer getPortInicial() {
//        return portInicial;
//    }
//
//    public void setPortInicial(Integer portInicial) {
//        this.portInicial = portInicial;
//    }
//
//    public Integer getPortFinal() {
//        return portFinal;
//    }
//
//    public void setPortFinal(Integer portFinal) {
//        this.portFinal = portFinal;
//    }

    @Override
    public String toString() {
        return ""+descricao; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
