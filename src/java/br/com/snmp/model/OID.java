/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

/**
 *
 * @author carlos.macedo
 */
public class OID {
    private String descricao;
    private int portInicial;
    private int portFinal;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPortInicial() {
        return portInicial;
    }

    public void setPortInicial(int portInicial) {
        this.portInicial = portInicial;
    }

    public int getPortFinal() {
        return portFinal;
    }

    public void setPortFinal(int portFinal) {
        this.portFinal = portFinal;
    }

    @Override
    public String toString() {
        return " OID Descrição: "+descricao+" | Port Inicial: "+ portInicial+" | Port Final: "+portFinal; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
