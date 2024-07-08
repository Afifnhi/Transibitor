package transibitorpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class dataFeedback {
    private int point;
    private String KritikSaran;

    public dataFeedback(String KritikSaran, int point){
        this.point = point;
        this.KritikSaran = KritikSaran;
    }

    public dataFeedback(){}
 
    public void setPoint(int point){
        this.point = point;
    }

    public int getPoint(){
        return this.point;
    }

    public void setKritikSaran(String KritikSaran){
        this.KritikSaran = KritikSaran;
    }

    public String getKritikSaran(){
        return this.KritikSaran;
    }

    public static ArrayList<dataFeedback> feed = new ArrayList<>();
    
    public static void object_XML(dataFeedback t){
        XStream xStream = new XStream(new StaxDriver());
        String sxml;
        
        XML_object(); // Baca data yang sudah ada
        feed.add(t);  // Tambahkan data baru
        sxml = xStream.toXML(feed);
        
        FileOutputStream f = null;
        try {
            f = new FileOutputStream("Feedback.xml");
            byte[] bytes = sxml.getBytes("UTF-8");
            f.write(bytes);
            f.close();
        } catch (Exception e) {
            System.err.println("Perhatian: "+ e.getMessage());
        }
    }
    
    public static void XML_object(){
        XStream xStream = new XStream(new StaxDriver());
        FileInputStream f = null;
        
        try {
            f = new FileInputStream("Feedback.xml");
            
            int isi;
            char c;
            String sxml = "";
            
            while ((isi = f.read()) != -1) {
                c = (char) isi;
                sxml = sxml+c;
            }
            
            ArrayList<dataFeedback> tempFeed = (ArrayList<dataFeedback>) xStream.fromXML(sxml);
            feed.clear();
            feed.addAll(tempFeed);
            f.close();
        } catch (Exception e) {
            System.err.println("Error saat membaca XML: " + e.getMessage());
            // Jika file tidak ada atau error, biarkan feed kosong
        }
    }


}
