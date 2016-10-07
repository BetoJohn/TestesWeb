/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.DAO;

import br.com.snmp.model.DataSnmp;
import br.com.snmp.model.Device;
import br.com.snmp.model.OID;
import br.com.snmp.model.ReturnSnmp;
import br.com.testes.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos.macedo
 */
public class SnmpDAO extends DaoBase {

    private static List<Device> listDevice;
    private static SnmpDAO dao;
    private static List<Device> persistDevice;

    public SnmpDAO(Connection con) {
        super(con);
    }

    public void insertDevice(Device dev) throws Exception {
        String sql = "INSERT INTO device(\n"
                + "             identificacao, versao, oid_id, comunidade, ip, porta_inicial, \n"
                + "            porta_final)\n"
                + "    VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = null;
        try {
            ps = createPreparedStatement(sql);
            ps.setObject(1, dev.getIdentificacao());
            ps.setObject(2, dev.getVersao());
            ps.setObject(3, dev.getOid().getId());
            ps.setObject(4, dev.getComunidade());
            ps.setObject(5, dev.getIp());
            ps.setObject(6, dev.getPortInicial());
            ps.setObject(7, dev.getPortFinal());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public List<Device> getAllDevices() throws Exception {
        String sql = "SELECT * FROM device;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = createPreparedStatement(sql);
            rs = ps.executeQuery();
            List<Device> listDevice = new ArrayList<>();
            while (rs.next()) {
                Device dev = new Device();
                dev.setId(rs.getInt("id"));
                dev.setIdentificacao(rs.getString("identificacao"));
                dev.setComunidade(rs.getString("comunidade"));
                dev.setVersao(rs.getString("versao"));
                dev.setPortInicial(rs.getInt("porta_inicial"));
                dev.setPortFinal(rs.getInt("porta_final"));
                dev.setIp(rs.getString("ip"));
                dev.setOid(getOIDById(rs.getInt("oid_id")));
                listDevice.add(dev);
            }

            return listDevice;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
    }

    public int insertOID(OID oid) throws Exception {
        int id = 0;
        String sql = "INSERT INTO oid(\n"
                + "             descricao)\n"
                + "    VALUES (?) returning id;";
        PreparedStatement ps = null;
        try {
            ps = createPreparedStatement(sql);
            ps.setObject(1, oid.getDescricao());
            ResultSet rsId = ps.executeQuery();
            //ResultSet rsId = ps.getGeneratedKeys();

            if (rsId.next()) {
                id = rsId.getInt("id");
                return id;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public List<OID> getAllOID() throws Exception {

        String sql = "SELECT * FROM oid;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = createPreparedStatement(sql);
            rs = ps.executeQuery();
            List<OID> listOID = new ArrayList<>();
            while (rs.next()) {
                OID oid = new OID();
                oid.setId(rs.getInt("id"));
                oid.setDescricao(rs.getString("descricao"));
                listOID.add(oid);
            }

            return listOID;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public OID getOIDById(int id) throws Exception {

        String sql = "SELECT * FROM oid where id = ?;";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = createPreparedStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();

            OID oid = new OID();
            if (rs.next()) {
                oid.setId(rs.getInt("id"));
                oid.setDescricao(rs.getString("descricao"));
            }

            return oid;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public void insertSnmpDevice(DataSnmp snmp) throws Exception {

        String sql = "INSERT INTO snmp_device(\n"
                + "             port, valor, status, device_id, data_hora)\n"
                + "    VALUES ( ?, ?, ?, ?, ?);";
        PreparedStatement ps = null;
        Util util = new Util();
        try {
            ps = createPreparedStatement(sql);
            ps.setObject(1, snmp.getPort());
            ps.setObject(2, snmp.getValue());
            ps.setString(3, snmp.getStatus());
            ps.setObject(4, snmp.getDevice_id());
            ps.setTimestamp(5, util.getDateTimestamp());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public List<DataSnmp> getAllSnmpDevice() throws Exception {

        List<DataSnmp> listReturn_File = new ArrayList<>();
        
        String sql = "SELECT id, port, valor, status, device_id, data_hora\n"
                + "  FROM snmp_device";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = createPreparedStatement(sql);
            rs = ps.executeQuery();

             
            while (rs.next()) {
                DataSnmp snmp = new DataSnmp();
                snmp.setId(rs.getInt("id"));
                snmp.setPort(rs.getInt("port"));
                snmp.setValue(rs.getInt("valor"));
                snmp.setStatus(rs.getString("status"));
                snmp.setDevice_id(rs.getInt("device_id"));
                snmp.setTime(rs.getTimestamp("data_hora"));
                listReturn_File.add(snmp);
            }

            return listReturn_File;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public DataSnmp getSnmpDeviceByIdDeviceAndPort(int device_id, int port) throws Exception {

        String sql = "SELECT id, port, valor, status, device_id, data_hora\n"
                + "  FROM snmp_device where device_id = ? and port = ?;";
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = createPreparedStatement(sql);
            ps.setObject(1, device_id);
            ps.setObject(2, port);
            rs = ps.executeQuery();

            DataSnmp snmp = new DataSnmp();
            if (rs.next()) {
                snmp.setId(rs.getInt("id"));
                snmp.setPort(rs.getInt("port"));
                snmp.setValue(rs.getInt("valor"));
                snmp.setStatus(rs.getString("status"));
                snmp.setDevice_id(rs.getInt("device_id"));
                snmp.setTime(rs.getTimestamp("data_hora"));
            }

            return snmp;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public void updateSnmpDevice(DataSnmp snmp) throws Exception {

        String sql = "UPDATE snmp_device\n"
                + "   SET   valor=?, status=?, data_hora=?\n"
                + " WHERE id = ?;";
        PreparedStatement ps = null;
        Util util = new Util();
        try {
            ps = createPreparedStatement(sql);
            ps.setObject(1, snmp.getValue());
            ps.setObject(2, snmp.getStatus());
            ps.setTimestamp(3, util.getDateTimestamp());
            ps.setObject(4, snmp.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

    }

    public List<Device> getDeviceByIdentificacao(Device dev) {
        List<Device> devices = new ArrayList<>();
        for (Device device : listDevice) {
            if (device.getIdentificacao().contains(dev.getIdentificacao())) {
                devices.add(device);
            }
        }
        listDevice = new ArrayList<>();
        listDevice = devices;

        return devices;
    }

    public void refreshTable() {
        listDevice = persistDevice;
    }

    public static List<Device> getPersistDevice() {
        return persistDevice;
    }

    public static void setPersistDevice(List<Device> persistDevice) {
        SnmpDAO.persistDevice = persistDevice;
    }

}
