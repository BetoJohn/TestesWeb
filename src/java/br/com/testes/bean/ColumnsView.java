/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.bean;


import br.com.snmp.BO.SnmpBO;
import br.com.snmp.model.Car;
import br.com.snmp.model.Device;
import br.com.snmp.service.CarService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

 /**
 *
 * @author carlos.macedo
 *  private String identificacao;
    private String versao;
    private OID oid;
    private String comunidade;
    private String ip;
 */
@ManagedBean(name="dtColumnsView")
@ViewScoped
public class ColumnsView implements Serializable {
     
             
    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("id", "identificacao", "versao", "comunidade", "ip");
     
    private String columnTemplate = "id identificacao versao comunidade ip"; //determina quais colunas irão aparecer
     
    private List<ColumnModel> columns;
     
    private List<Car> cars;
    private List<Device> devices;
     
    private List<Car> filteredCars;
    private List<Device> filteredDevices;
     
    @ManagedProperty("#{carService}")
    private CarService service;
 
    @PostConstruct
    public void init() {
        //cars = service.createCars(55);// quantidade de carros criados
     //    devices = SnmpBO.getInstance().getAllDevices();
        createDynamicColumns();
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Device> getFilteredDevices() {
        return filteredDevices;
    }

    public void setFilteredDevices(List<Device> filteredDevices) {
        this.filteredDevices = filteredDevices;
    }
     
    
    public List<Car> getCars() {
        return cars;
    }
 
    public List<Car> getFilteredCars() {
        return filteredCars;
    }
 
    public void setFilteredCars(List<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
 
    public String getColumnTemplate() {
        return columnTemplate;
    }
 
    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }
 
    public List<ColumnModel> getColumns() {
        return columns;
    }
 
    private void createDynamicColumns() {
        // GERA AS COLUNAS DE ACORDO COM O QUE FOI PASSADO NA VARIAVEL columnTemplate
        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<ColumnModel>();   
         
        for(String columnKey : columnKeys) {
            String key = columnKey.trim();
             
            if(VALID_COLUMN_KEYS.contains(key)) { //verifica se as colunas batem 
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
            }
        }
    }
     
    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        table.setValueExpression("sortBy", null);
         
        //update columns
        createDynamicColumns();
    }
     
    static public class ColumnModel implements Serializable {
 
        private String header;
        private String property;
 
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
 
        public String getHeader() {
            return header;
        }
 
        public String getProperty() {
            return property;
        }
    }
}
