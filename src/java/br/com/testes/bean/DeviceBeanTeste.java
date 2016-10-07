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
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.Context;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.Min;
import org.apache.jasper.tagplugins.jstl.ForEach;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 *
 * @author carlos.macedo
 */
@ManagedBean(name = "deviceBean")
@SessionScoped
//@RequestScoped
public class DeviceBeanTeste implements Serializable {

    private Device device;
    private OID oid;
    private List<Device> listDevices;
    private Device selectedDevice;
    private List<Device> filteredDevices;
    private String filterBy;
    private String search;
    private Scheduler scheduler;
    private JobDetail job;
    private Trigger trigger;
    private Map<String, String> oids;

    @PostConstruct
    public void init() {
        device = new Device();
        oid = new OID();
        device.setOid(oid);
       // listDevices = SnmpBO.getInstance().getAllDevices();
    }

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
                            SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever())
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Status", "" + triggerState()));
        RequestContext.getCurrentInstance().update("messages");
    }

    private TriggerState triggerState() throws SchedulerException {

        TriggerState triggerState = null;
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

    public String reinit() {
        device = new Device();
        oid = new OID();
        device.setOid(oid);
        return null;
    }

    public void createNewDevice(ActionEvent event) throws Exception {
        SnmpBO.getInstance().insertDevice(device);
        System.out.println(device.toString() + " - " + oid.toString());
        RequestContext.getCurrentInstance().update("form:devices");
    }

    public List<Device> getAllDevices() throws Exception {
        List<Device> list = new ArrayList<>();
        list = SnmpBO.getInstance().getAllDevices();
        listDevices = list;
         RequestContext.getCurrentInstance().update("form:devices");
        return list;
    }

    public void editDevice() {
        System.out.println("Device Edit: " + device.toString());
        RequestContext.getCurrentInstance().execute("PF('deviceDialog').hide()");
    }

    public void excluirDevice() {
        System.out.println("Device: " + selectedDevice.getIdentificacao());
        RequestContext.getCurrentInstance().execute("PF('deviceDialog').hide()");
    }

    public void createNewOID(ActionEvent event) throws Exception {
        SnmpBO.getInstance().insertOID(oid);
        System.out.println(device.getOid().toString());
    }

    public List<OID> getAllOID() throws Exception {
        List<OID> list = new ArrayList<>();
        list = SnmpBO.getInstance().getAllOID();
        return list;

    }

    public List<String> completeOID(String query) throws Exception {
        List<String> results = new ArrayList<String>();
        for (OID result : SnmpBO.getInstance().getAllOID()) {
            results.add(result.getDescricao());
        }
        return results;
    }

    public void onRowSelect(SelectEvent eventSelect) {
        oid = selectedDevice.getOid();
        device = selectedDevice;
        device.setOid(oid);
//        FacesMessage msg;
//        msg = new FacesMessage("Device Selected", " " + ((Device) eventSelect.getObject()).getIdentificacao());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("Device Selected: " + ((Device) eventSelect.getObject()).getIdentificacao());

    }

    public void onRowEdit(RowEditEvent event) {
//        FacesMessage msg;
//        msg = new FacesMessage("Device Selected", " " + ((Device) event.getObject()).getIdentificacao());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("Device Selected" + ((Device) event.getObject()).getIdentificacao());

    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Device Unselected", " " + ((Device) event.getObject()).getIdentificacao());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void dialogViewEditDevice() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("responsive", true);
//        options.put("contentHeight", 540);
//        options.put("contentWidth", 560);
        RequestContext.getCurrentInstance().openDialog("Telas/dialogEditDevice", options, null);

    }

    public void getByIdentificacao() {
        device = new Device();
        String filter = filterBy;
        device.setIdentificacao(search);
        // SnmpBO.getInstance().getByIdentificacao(device);
        device = new Device();
    }

    public List getListFilter() {
        List<String> filters = new ArrayList<>();
        filters.add("Id");
        filters.add("Identificação");
        filters.add("Versão");
        filters.add("Comunidade");
        filters.add("Ip");
        return filters;
    }

    

    public List<Device> getListDevices() throws Exception {
        //listDevices = SnmpBO.getInstance().getAllDevices();
        return listDevices;
    }

    public Device getDevice() {
        return device;
    }

    public OID getOid() {
        return oid;
    }

    public Device getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    public List<Device> getFilteredDevices() {
        return filteredDevices;
    }

    public void setFilteredDevices(List<Device> filteredDevices) {
        this.filteredDevices = filteredDevices;
    }

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
