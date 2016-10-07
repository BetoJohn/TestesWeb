/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.job;

import br.com.snmp.BO.SnmpBO;
import br.com.snmp.model.Device;
import br.com.snmp.model.ResultSnmp;
import br.com.snmp.model.ReturnSnmp;
import br.com.snmp.service.SnmpService;
import br.com.testes.bean.DeviceBeanTeste;
import br.com.testes.main.Teste;
import br.com.testes.util.Util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author carlos.macedo
 */
public class JobGetSnmp implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Logger.getLogger(DeviceBeanTeste.class.getName()).log(Level.INFO, "Servico executado conforme agendamento " + new Date(), "");
        try {
            JobDataMap jobMap = jec.getJobDetail().getJobDataMap();
            String pathRetorno = (String) jobMap.get("path");
            System.out.println("Path: " + pathRetorno);
           
            SnmpService snmp = new SnmpService();
            snmp.getValueSnmp(pathRetorno);

            //Util u = new Util();
            //VERIFICA SE HOUVE ALTERAÇÃO NAS PORTAS
        } catch (Exception ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
