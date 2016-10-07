/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.util;

import br.com.snmp.model.ComparateValue;
import br.com.snmp.model.ResultSnmp;
import br.com.snmp.model.ReturnSnmp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author carlos.macedo
 */
public class Util {

    private BufferedWriter buffWrite;
    private BufferedReader buffReader;
    private File file;

    public void leitor(String path) throws IOException {

        BufferedReader buffRead = new BufferedReader(new FileReader(path + "/TestesWebDir/TestesWeb.txt"));
        String linha = "";
        while (true) {
            if (linha != null) {
                System.out.println(linha);

            } else {
                break;
            }
            linha = buffRead.readLine();
        }
        buffRead.close();
    }

    public void escritor(StringBuffer sb, String path) throws IOException {

        File file = new File(path + "/TestesWebDir");
        if (!file.exists()) {
            file.mkdir();
        } else {
            buffWrite = new BufferedWriter(new FileWriter(path + "/TestesWebDir/TestesWeb.txt"));
            buffWrite.append(sb);
            buffWrite.close();
        }
        System.out.println("Path File: " + file.getAbsolutePath());

//        boolean success = (new File("teste_dir")).mkdirs();
//        file = new File("teste_dir");
//        if (file.exists()) {
//            buffWrite = new BufferedWriter(new FileWriter("teste_dir/TestesWeb.txt"));
//            buffWrite.append(sb);
//            buffWrite.close();
//        }
    }

    public boolean fileExists(String path) throws FileNotFoundException {
        file = new File(path + "/TestesWebDir/file.json");
        boolean exists = file.exists();
        return exists;
    }

    public void writerJson(List<ReturnSnmp> rs, String path) throws IOException {
        BlowFish bf = new BlowFish();
        File file = new File(path + "/TestesWebDir");
        if (!file.exists()) {
            file.mkdir();
        }
        if (file.exists()) {
            Gson gson = new Gson();
            // converte objetos Java para JSON e retorna JSON como String
            String json = gson.toJson(rs);
            String jsonCript = bf.cript(json);
            //System.out.println("Valor JsonCript: " + jsonCript);
            try {
                //Escreve Json convertido em arquivo chamado "file.json"
                buffWrite = new BufferedWriter(new FileWriter(path + "/TestesWebDir/file.json"));
                buffWrite.write(jsonCript);
                buffWrite.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Path File: " + file.getAbsolutePath());
    }

    public List<ReturnSnmp> readerJson(String path) throws IOException {
        BlowFish bf = new BlowFish();
        Gson gson = new Gson();
        try {
            buffReader = new BufferedReader(new FileReader(path + "/TestesWebDir/file.json"));

            //Converte String JSON para objeto Java
            // ReturnSnmp obj = gson.fromJson(buffReader, ReturnSnmp.class);
            if (buffReader != null) {
                Type listType = new TypeToken<ArrayList<ReturnSnmp>>() {
                }.getType();

                //------------------------------------------------------------
                StringBuilder builder = new StringBuilder();
                String aux = "";
                while ((aux = buffReader.readLine()) != null) {
                    builder.append(aux);
                }
                String text = builder.toString();
                String jsonDeCript = bf.decript(text);
                //------------------------------------------------------------
                List<ReturnSnmp> founderList = gson.fromJson(jsonDeCript, listType);
                return founderList;
            } else {
                return new ArrayList<ReturnSnmp>();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Esse método se mostra mais eficiente por tambem analisar os ids dos devices, dessa forma posso saber qual dispositivo teve alteração pelo seu id
    //O método vai retornar uma lista contendo os valores que foram alterados, o parametro vai seguir a ordem, onde l1 são os 
    //novos valores e l2 será o arquivo que foi gerado, ou seja, os valores antigos.
    public List<ComparateValue> compareList(List<ReturnSnmp> l1, List<ReturnSnmp> l2) throws IOException {
        Util util = new Util();
        List<ComparateValue> resultado1 = new ArrayList<>();

        for (ReturnSnmp dev : l1) {
            for (ResultSnmp result1 : dev.getResult()) {
                ComparateValue value = new ComparateValue();
                value.setDevice_id(dev.getDevice_id());
                value.setResult(result1);
                resultado1.add(value);

            }
        }

        List<ComparateValue> resultado2 = new ArrayList<>();
        for (ReturnSnmp dev : l2) {
            for (ResultSnmp result2 : dev.getResult()) {
                ComparateValue value = new ComparateValue();
                value.setDevice_id(dev.getDevice_id());
                value.setResult(result2);
                resultado2.add(value);

            }

        }
        //Dessa forma consigo criar uma lista com os valores 
        List<ComparateValue> valuesUpdated = new ArrayList<>();
        boolean resultValue;
        //resultado1.size() == resultado2.size() &&
        if (resultado1 != null && resultado2 != null) {
            // make a copy of the list so the original list is not changed, and remove() is supported
            ArrayList<ComparateValue> cp = new ArrayList<>(resultado1);
            for (ComparateValue value : resultado2) {
                if (!cp.remove(value)) {
                    valuesUpdated.add(value);
                    resultValue = false;
                }
            }
            resultValue = cp.isEmpty();
        } else {
            return new ArrayList<>();
        }

        return valuesUpdated;
    }

    public Timestamp dateToTimestamp(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormat = sdf.format(date.getTime());
            Timestamp timeStampDate = convertStringToTimestamp(dataFormat);
            return timeStampDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Timestamp convertStringToTimestamp(String str_date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = sdf.parse(str_date);
            Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    public Timestamp getDateTimestamp() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormat = sdf.format(date.getTime());
            Timestamp timeStampDate = convertStringToTimestamp(dataFormat);
            return timeStampDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void compareState(List<ReturnSnmp> list) {

    }
}
