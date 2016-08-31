/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.BO;

import br.com.snmp.DAO.SnmpDAO;
import br.com.snmp.model.Device;
import br.com.snmp.model.OID;
import java.util.List;

/**
 *
 * @author carlos.macedo
 */
public class SnmpBO {

    private static SnmpBO snmpBO;

    private SnmpBO() {
    }

    public static SnmpBO getInstance() {
        if (snmpBO == null) {
            snmpBO = new SnmpBO();
        }
        return snmpBO;
    }

    public void saveDevice(Device dev) {
        SnmpDAO.getInstance().saveDevice(dev);
    }

    public List<Device> getAllDevices() {
        return  SnmpDAO.getInstance().getAllDevices();
    }

    public void saveOID(OID oid) {
        SnmpDAO.getInstance().saveOID(oid);
    }

    public List<OID> getAllOID() {
        return SnmpDAO.getInstance().getAllOID();
    }
}
