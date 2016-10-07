/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.bean;

import br.com.snmp.BO.SnmpBO;
import br.com.snmp.model.Device;
import br.com.snmp.model.OID;
import br.com.testes.job.JobGetSnmp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author carlos.macedo
 */
@ManagedBean(name = "snmpBean")
@SessionScoped
public class SnmpBean {
    
    private Scheduler scheduler;
    private JobDetail job;
    private Trigger trigger;

    
    public void getSnmp() {
        try {

            job = JobBuilder.newJob(JobGetSnmp.class)
                    .withIdentity("myJob", "group1")
                    .build();

            //passando parâmetro para o job
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            job.getJobDataMap().put("path", path);

            trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("dummyTriggerName", "group1")
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(2).repeatForever())
                    .build();

//            List<JobExecutionContext> list = scheduler.getCurrentlyExecutingJobs();
//            System.out.println("List size: " + list.size());
            if (scheduler == null) {
                //schedule it
                scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "O Serviço foi iniciado."));
                RequestContext.getCurrentInstance().update("messages");

                System.out.println("State Trigger: " + triggerState());

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "O Serviço já foi iniciado."));
                RequestContext.getCurrentInstance().update("messages");
            }

        } catch (Exception e) {
            System.out.println("erro");
            e.printStackTrace();
        }
    }

    public void breakGetSnmp() {
        try {
            if (scheduler != null) {
                scheduler.shutdown(true);
                Logger.getLogger(DeviceBeanTeste.class.getName()).log(Level.INFO, "breakGetSnmp()", "");
                scheduler = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Info", "O Serviço foi finalizado."));
                RequestContext.getCurrentInstance().update("messages");

                System.out.println("State Trigger: " + triggerState());

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "O Serviço já está finalizado."));
                RequestContext.getCurrentInstance().update("messages");

            }

        } catch (SchedulerException ex) {
            Logger.getLogger(DeviceBeanTeste.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    public void getStatus() throws SchedulerException {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Status", ""+triggerState()));
        RequestContext.getCurrentInstance().update("messages");
    }

    private Trigger.TriggerState triggerState() throws SchedulerException {

        Trigger.TriggerState triggerState = null;
        if (scheduler != null) {
            JobDetail jobDetail = scheduler.getJobDetail(job.getKey());
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
            for (Trigger trigger : triggers) {
                triggerState = scheduler.getTriggerState(trigger.getKey());

                return triggerState;
            }
        } else {
            triggerState = triggerState.BLOCKED;
        }
        return triggerState;
    }
}
