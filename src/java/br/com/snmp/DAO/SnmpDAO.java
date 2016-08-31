/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.DAO;

import br.com.snmp.model.Device;
import br.com.snmp.model.OID;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos.macedo
 */
public class SnmpDAO {

    private static List<Device> listDevice;
    private static List<OID> listOID;
    private static SnmpDAO dao;

    private SnmpDAO() {
    }

    public static SnmpDAO getInstance() {
        if (dao == null) {
            dao = new SnmpDAO();            
            listDevice = new ArrayList<>();
            Device dev = new Device();
            dev.setId(1);
            dev.setComunidade("alfa");
            dev.setIdentificacao("229829792");
            dev.setVersao("2.1");
            dev.setIp("192.168.10.10");
            OID oid = new OID();
            oid.setDescricao("132.1.1.431.3");
            oid.setPortInicial(2);
            oid.setPortFinal(10);
            dev.setOid(oid);
            listDevice.add(dev);
            listOID = new ArrayList<OID>();
        }
        return dao;
    }
    
    public void saveDevice(Device dev) {
        listDevice.add(dev);
    }
    public List<Device> getAllDevices(){
        return listDevice;
    }

    public void saveOID(OID oid) {
        listOID.add(oid);
    }
    public List<OID> getAllOID(){
        return listOID;
    }

    public List<Device> getDescOID() {
        return listDevice;
    }


}
