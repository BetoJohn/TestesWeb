/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.service;

import br.com.snmp.BO.SnmpBO;
import br.com.snmp.model.ComparateValue;
import br.com.snmp.model.DataSnmp;
import br.com.snmp.model.Device;
import br.com.snmp.model.ResultSnmp;
import br.com.snmp.model.ReturnSnmp;
import br.com.testes.util.Util;
import static com.oracle.jrockit.jfr.ContentType.Address;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author carlos.macedo
 */
public class SnmpService {
    
    private Snmp snmp = null;
    private String address = null;
    List<ResultSnmp> listResult_DB;

    /**
     * Constructor
     *
     * @param ipDevice
     * @param portInicial
     * @param portFinal
     */
//    public SnmpService(String ipDevice, int portInicial, int portFinal) {
//        this.ipDevice = ipDevice;
//        this.portInicial = portInicial;
//        this.portFinal = portFinal;
//    }
    /**
     * *
     *
     * @param path (o caminho é necessário para ser gerado o arquivo do tipo
     * json)
     * @throws Exception
     *
     * O método faz uma consulta no banco e trás todos os dispositivos
     * cadastrados, em cada dispositivo é verificado o seu status
     */
    public void getValueSnmp(String path) throws Exception {
        try {
            List<ReturnSnmp> mainList = new ArrayList<>();
            
            for (Device device : SnmpBO.getInstance().getAllDevices()) {
                int portInicial = device.getPortInicial();
                int portFinal = device.getPortFinal();
                
                if (portFinal > portInicial) {
                    
                    ReturnSnmp rs = new ReturnSnmp();
                    rs.setDevice_id(device.getId());
                    rs.setTime(new Date());
                    rs.setIpDevice(device.getIp());
                    listResult_DB = new ArrayList<>();
                    for (int i = portInicial; i <= portFinal; i++) {
                        
                        try {
                            address = "udp:" + device.getIp() + "/161";
                            start();
                            //Aqui será feita uma consulta pelo id na tabela OID, para saber nesse dispositivo 
                            //qual o tipo de consulta via SNMP vai ser definada para ele. Dessa maneira o OID está estatico. 
                            String sysDescr = getAsString(new OID("1.3.6.1.2.1.2.2.1.8." + i));
                            ResultSnmp result = new ResultSnmp();
                            result.setPort(i);
                            
                            switch (sysDescr) {
                                case "1":
                                    result.setValue(Integer.parseInt(sysDescr));
                                    result.setStatus("UP");
                                    break;
                                case "2":
                                    result.setValue(Integer.parseInt(sysDescr));
                                    result.setStatus("DOWN");
                                    break;
                                case "noSuchInstance":
                                    result.setValue(3);
                                    result.setStatus("NOSUCHINSTANCE");
                                    break;
                            }
                            //Carrega uma lista de resultados referentes aos dispositivos;
                            listResult_DB.add(result);
                            
                        } catch (Exception ex) {
                            Logger.getLogger(SnmpService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    rs.setResult(listResult_DB);
                    mainList.add(rs);
                    
                } else {
                    Logger.getLogger(SnmpService.class.getName()).log(Level.INFO, "A porta final deve ser maior que a porta inicial", "");
                }
            }
            //A partir daqui ja temos uma lista (mainList) carregada com o objeto de retorno de cada dispositivo analisado 
            Util u = new Util();
            //u.writerJson(null, path);
            //Verifico se o arquivo já existe, senão cria o mesmo ja com mainList
            if (u.fileExists(path)) {
                //Faz a leitura do arquivo json e carrega uma lista somente com os resultados que contem: port, valor e status;
                List<ReturnSnmp> listReturn_File = u.readerJson(path);
                if (!listReturn_File.isEmpty()) {
                    //Aqui posso analisar as duas listas, se forem iguais não precisa alterar a tabela no banco;
                    //Se forem diferentes é porque houve alguma alteração então os dados do banco devem ser alterados tambem;
                    //values vai receber os objetos que foram analisados e são diferentes
                    List<ComparateValue> values = u.compareList(mainList, listReturn_File);
                    //Se teve algum arquivo alterado(a lista de retorno values, só é carregada quando tem um dado diferente)
                    if (!values.isEmpty()) {
                        //Fazer a verificação no banco se ja tem algum resultado referente na tabela snmp_device
                        //vou realizar o inserção/update na tabela snmp_device
                        //para isso terei que realizar uma consulta no banco para saber se tem algum dado                        
                        for (ComparateValue comparateValue : values) {
                            //necessario para saber se é um novo objeto ou se ele já existe
                            DataSnmp snmpDb = SnmpBO.getInstance().getSnmpDeviceByIdDeviceAndPort(comparateValue.getDevice_id(), comparateValue.getResult().getPort());
                            DataSnmp snmp = new DataSnmp();
                            //se não tem então insira, senão atualize
                            if (snmpDb == null) {                                
                                snmp.setDevice_id(comparateValue.getDevice_id());
                                snmp.setPort(comparateValue.getResult().getPort());
                                snmp.setStatus(comparateValue.getResult().getStatus());
                                snmp.setValue(comparateValue.getResult().getValue());
                                SnmpBO.getInstance().insertSnmpDevice(snmp);
                            } else {
                                snmp.setId(snmpDb.getId());
                                snmp.setDevice_id(comparateValue.getDevice_id());
                                snmp.setPort(comparateValue.getResult().getPort());
                                snmp.setStatus(comparateValue.getResult().getStatus());
                                snmp.setValue(comparateValue.getResult().getValue());
                                SnmpBO.getInstance().updateSnmpDevice(snmp);
                            }
                            
                        }

                    }else{
                        //Aqui posso esta pegando a mainList e transformando no novo arquivo local e encriptando o mesmo para a futura analise;
                    }
                }
            } else {
                //aqui vai ser praticamente a primeira inserção, tanto no aqrquivo local como no banco
                //inserção no aqrquivo local
                u.writerJson(mainList, path);
                for (ReturnSnmp returnValue : mainList) {
                    for (ResultSnmp value : returnValue.getResult()) {
                        DataSnmp snmp = new DataSnmp();
                        snmp.setDevice_id(returnValue.getDevice_id());
                        snmp.setPort(value.getPort());
                        snmp.setStatus(value.getStatus());
                        snmp.setValue(value.getValue());
                        //inserção no banco
                        SnmpBO.getInstance().insertSnmpDevice(snmp);
                    }
                    
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SnmpService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Start the Snmp session. If you forget the listen() method you will not
     * get any answers because the communication is asynchronous and the
     * listen() method listens for answers.
     *
     * @throws IOException
     */
    private void start() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
// Do not forget this line!
        transport.listen();
    }

    /**
     * Method which takes a single OID and returns the response from the agent
     * as a String.
     *
     * @param oid
     * @return
     * @throws IOException
     */
    public String getAsString(OID oid) throws IOException {
        ResponseEvent event = get(new OID[]{oid});
        return event.getResponse().get(0).getVariable().toString();
    }

    /**
     * This method is capable of handling multiple OIDs
     *
     * @param oids
     * @return
     * @throws IOException
     */
    public ResponseEvent get(OID oids[]) throws IOException {
        PDU pdu = new PDU();
        for (OID oid : oids) {
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("GET timed out");
    }

    /**
     * This method returns a Target, which contains information about where the
     * data should be fetched and how.
     *
     * @return
     */
    private Target getTarget() {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }
    
}
