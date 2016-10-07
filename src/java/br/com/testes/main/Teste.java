/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.main;

import br.com.snmp.model.ResultSnmp;
import br.com.snmp.model.ReturnSnmp;
import br.com.testes.job.JobGetSnmp;
import br.com.testes.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.in;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import sun.awt.AWTAccessor;

/**
 *
 * @author carlos.macedo
 */
public class Teste {

//    public static void main(String[] a) throws FileNotFoundException, IOException {
//        ReturnSnmp rs = new ReturnSnmp();
//        List<ReturnSnmp> mainList =  new ArrayList<>();
//        List<ResultSnmp> list = new ArrayList<>();
//        rs.setIpDevice("10.104.1.111");
//        ResultSnmp result = new ResultSnmp();
//        result.setPort(1);
//        result.setStatus("UP");
//        result.setValue(1);
//        list.add(result);
//        result = new ResultSnmp();
//        result.setPort(2);
//        result.setStatus("UP");
//        result.setValue(1);
//        list.add(result);
//        result = new ResultSnmp();
//        result.setPort(3);
//        result.setStatus("DOWN");
//        result.setValue(2);
//        list.add(result);
//        result = new ResultSnmp();
//        result.setPort(4);
//        result.setStatus("DOWN");
//        result.setValue(2);
//        list.add(result);
//
//        rs.setResult(list);
//        mainList.add(rs);
//        
//        
//        rs = new ReturnSnmp();
//        list = new ArrayList<>();
//        rs.setIpDevice("10.104.1.112");
//        result = new ResultSnmp();
//        result.setPort(1);
//        result.setStatus("UP");
//        result.setValue(1);
//        list.add(result);
//        result = new ResultSnmp();
//        result.setPort(3);
//        result.setStatus("DOWN");
//        result.setValue(2);
//        list.add(result);
//        result = new ResultSnmp();
//        result.setPort(2);
//        result.setStatus("UP");
//        result.setValue(1);
//        list.add(result);
//        
//        result = new ResultSnmp();
//        result.setPort(4);
//        result.setStatus("DOWN");
//        result.setValue(2);
//        list.add(result);
//
//        rs.setResult(list);
//        mainList.add(rs);
//
//        Util u = new Util();
//        u.writerJson(mainList, "");
//       // ReturnSnmp r = u.readerJson("");
//       // System.out.println(r.getIpDevice()+" - "+r.getResult().size());
//        for (ReturnSnmp returnSnmp : u.readerJson("")) {
//             System.out.println("Ip Device: "+returnSnmp.getIpDevice());
//             for (ResultSnmp resultSnmp : returnSnmp.getResult()) {
//                 System.out.println("Port: "+resultSnmp.getPort()+" | Status: "+resultSnmp.getStatus());
//             }
//        }
//        Gson gson = new Gson();
//        // converte objetos Java para JSON e retorna JSON como String
//        String json = gson.toJson(rs);
//        try {
//            //Escreve Json convertido em arquivo chamado "file.json"
//            FileWriter writer = new FileWriter("file.json");
//            writer.write(json);
//            writer.close();
//
//            //           Type returnListType = new TypeToken<ArrayList<ReturnSnmp>>(){}.getType();
//           
//            ReturnSnmp generalInfoObject = gson.fromJson(json, ReturnSnmp.class);
//            System.out.println(generalInfoObject.getResult().size()+" - "+generalInfoObject.getIpDevice());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(json);

  
//    }

    public static boolean isJobRunning(JobExecutionContext ctx, String jobName, String groupName)
            throws SchedulerException {
        List<JobExecutionContext> currentJobs = ctx.getScheduler().getCurrentlyExecutingJobs();

        for (JobExecutionContext jobCtx : currentJobs) {
            String thisJobName = jobCtx.getJobDetail().getKey().getName();
            String thisGroupName = jobCtx.getJobDetail().getKey().getGroup();
            if (jobName.equalsIgnoreCase(thisJobName) && groupName.equalsIgnoreCase(thisGroupName)
                    && !jobCtx.getFireTime().equals(ctx.getFireTime())) {
                return true;
            }
        }
        return false;
    }

    public static void writeXml() {
        XStream xStream = new XStream(new DomDriver());

        xStream.alias("device", ReturnSnmp.class);
        ReturnSnmp rs = new ReturnSnmp();
       // rs.setIpDevice("10.104.1.111");
//        rs.setPort(1);
//        rs.setStatus("UP");
//        rs.setValue(1);
        System.out.println(xStream.toXML(rs));
    }

    public static void teste1() {
        boolean isOn = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOn) {
                    for (int i = 0; i < 5; i++) {
                        try {

                            System.out.println(i + "º Envio RED");
                            Thread.sleep(1000);
                            if (!isOn) {
                                return;
                            }

                        } catch (Exception e) {

                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        try {
                            System.out.println(i + "º Envio GREEN");
                            Thread.sleep(1000);
                            if (!isOn) {
                                return;
                            }

                        } catch (Exception e) {

                        }
                    }
                }
            }
        }).start();
    }

    public static void teste2() {
        final long time = 36000; // a cada X ms
        Timer timer = new Timer();
        TimerTask tarefa = new TimerTask() {
            public void run() {
                //método
                try {
                    System.out.println("teste");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(tarefa, time, time);
    }

    public static void teste3() throws InterruptedException {
        while (true) {

            Thread.sleep(18000);
            System.out.println("Consulta");
        }

    }

//  INTERESSANTE
    public static void teste4() {
        long TEMPO = (1000 * 20); // atualiza o site a cada 1 minuto

        System.out.println("inicio");
        Timer timer = null;
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    try {
                        System.out.println("Teste agendador");
                        //chamar metodo
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);

        }
    }

//  INTERESSANTE COM BIBLIOTECA QUARTZ
    public static void teste5() {
        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();
            JobDetail job = JobBuilder.newJob(JobGetSnmp.class)
                    .withIdentity("myJob", "group1")
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("dummyTriggerName", "group1")
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
                    .build();
            sched.scheduleJob(job, trigger);
        } catch (Exception e) {
            System.out.println("erro");
            e.printStackTrace();
        }
    }

}
