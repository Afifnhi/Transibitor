
package transibitorpack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class dataCustomerService {

    public static ArrayList<dataFeedback> feed = new ArrayList<>();

    public ArrayList<dataFeedback> xml2object() {
        XStream xstream = new XStream(new StaxDriver());
        ArrayList<dataFeedback> cetak = new ArrayList<>();
        FileInputStream f = null;

        try {
            f = new FileInputStream("Feedback.xml");
            int isi;
            char c;
            StringBuilder s = new StringBuilder();
            while ((isi = f.read()) != -1) {
                c = (char) isi;
                s.append(c);
            }
            cetak = (ArrayList<dataFeedback>) xstream.fromXML(s.toString());
            f.close();
        } catch (Exception e) {
            System.err.println("An error occurs: " + e.getMessage());
        }
        return cetak;
    }


    public static void removeFromXml(dataFeedback t) {
        XStream xStream = new XStream(new StaxDriver());
        String sxml;

        feed.remove(t); 
        sxml = xStream.toXML(feed);

        FileOutputStream f = null;
        try {
            f = new FileOutputStream("Feedback.xml");
            byte[] bytes = sxml.getBytes("UTF-8");
            f.write(bytes);
            f.close();
        } catch (Exception e) {
            System.err.println("Perhatian: " + e.getMessage());
        }
    }

    
    public ObservableList<dataFeedback> observ() {
        ArrayList<dataFeedback> dataFromXml = xml2object();
        ObservableList<dataFeedback> list = FXCollections.observableArrayList(dataFromXml);
        return list;
    }
}
