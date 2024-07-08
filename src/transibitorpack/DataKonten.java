package transibitorpack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataKonten {
    private String word;
    private String url;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static ArrayList<DataKonten> feed = new ArrayList<>();
    public ArrayList<DataKonten> xml2object() {
        XStream xstream = new XStream(new StaxDriver());
        ArrayList<DataKonten> dataList = new ArrayList<>();
        FileInputStream f = null;

        try {
            f = new FileInputStream("konten.xml");
            StringBuilder s = new StringBuilder();
            int isi;
            while ((isi = f.read()) != -1) {
                char c = (char) isi;
                s.append(c);
            }
            dataList = (ArrayList<DataKonten>) xstream.fromXML(s.toString());
            f.close();
        } catch (Exception e) {
            System.err.println("An error occurs while reading XML: " + e.getMessage());
        }
        return dataList;
    }

    public void object2xml(ArrayList<DataKonten> data) {
        XStream xstream = new XStream(new StaxDriver());
        FileOutputStream f = null;

        try {
            f = new FileOutputStream("konten.xml");
            xstream.toXML(data, f);
            f.close();
        } catch (Exception e) {
            System.err.println("An error occurs while writing XML: " + e.getMessage());
        }
    }

    public void addData(String word, String url) {
        url = "file://" + url; // Assuming you want to prepend "file://" to the URL
        ArrayList<DataKonten> dataList = xml2object();
        DataKonten newData = new DataKonten();
        newData.setWord(word);
        newData.setUrl(url);
        dataList.add(newData);
        object2xml(dataList);
    }

    public void updateData(DataKonten updatedData) {
        ArrayList<DataKonten> dataList = xml2object();
        for (int i = 0; i < dataList.size(); i++) {
            DataKonten data = dataList.get(i);
                data.setWord(updatedData.getWord());
                data.setUrl(updatedData.getUrl());
                break;
        }
        object2xml(dataList);
    }

    public static void removeFromXml(DataKonten t) {
        XStream xStream = new XStream(new StaxDriver());
        String sxml;

        feed.remove(t); 
        sxml = xStream.toXML(feed);

        FileOutputStream f = null;
        try {
            f = new FileOutputStream("konten.xml");
            byte[] bytes = sxml.getBytes("UTF-8");
            f.write(bytes);
            f.close();
        } catch (Exception e) {
            System.err.println("Perhatian: " + e.getMessage());
        }
    }


    public ObservableList<DataKonten> getData() {
        ArrayList<DataKonten> dataList = xml2object();
        return FXCollections.observableArrayList(dataList);
    }
}
