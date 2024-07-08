package transibitorpack;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataTranslation {
    private Map<String, String> dictionary;
    private Map<String, String> videoUrls;
    private String lastInvalidWord;
    private boolean lastTranslationWasInvalid;

    public DataTranslation() {
        dictionary = new HashMap<>();
        videoUrls = new HashMap<>();
        lastInvalidWord = "";
        lastTranslationWasInvalid = false;
        initializeDatabase();
    }

    private void initializeDatabase() {
        loadVideoUrlsFromXml();
        addVideoUrl("babi", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Babi.mp4");
        addVideoUrl("kuda", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Kuda.mp4");
        addVideoUrl("sedih", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Sedih.mp4");
        addVideoUrl("lagu", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Lagu.mp4");
        addVideoUrl("pergi", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Pergi.mp4");
        addVideoUrl("sore", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Sore.mp4");
        addVideoUrl("bagus", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Bagus.mp4");
        addVideoUrl("buku", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Buku.mp4");
        addVideoUrl("cantik", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Cantik.mp4");
        addVideoUrl("datang", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Datang.mp4");
        addVideoUrl("ganteng", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Ganteng.mp4");
        addVideoUrl("hai", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Hai.mp4");
        addVideoUrl("jalan", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Jalan.mp4");
        addVideoUrl("kamu", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Kamu.mp4");
        addVideoUrl("kucing", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Kucing.mp4");
        addVideoUrl("lari", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Lari.mp4");
        addVideoUrl("lompat", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Lompat.mp4");
        addVideoUrl("maaf", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Maaf.mp4");
        addVideoUrl("malam", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Malam.mp4");
        addVideoUrl("selamat", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Selamat.mp4");
        addVideoUrl("senang", "file:///Users/afif/Documents/Afif/FPA/BackUpTransibitor/src/SIBI/Senang.mp4");
    }

    private void loadVideoUrlsFromXml() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("content", DataKonten.class);
        xstream.alias("contents", ArrayList.class);

        try {
            FileInputStream fis = new FileInputStream("konten.xml");
            ArrayList<DataKonten> contents = (ArrayList<DataKonten>) xstream.fromXML(fis);
            fis.close();

            for (DataKonten content : contents) {
                addVideoUrl(content.getWord(), content.getUrl());
            }
        } catch (Exception e) {
            System.err.println("An error occurred while loading video URLs from XML: " + e.getMessage());
        }
    }

    private void addVideoUrl(String word, String url) {
        videoUrls.put(word.toLowerCase(), url);
        dictionary.put(word.toLowerCase(), word);
    }

    public String translate(String word) {
        String lowerCaseWord = word.toLowerCase();

        if (dictionary.containsKey(lowerCaseWord)) {
            lastTranslationWasInvalid = false;
            return dictionary.get(lowerCaseWord);
        } else {
            lastTranslationWasInvalid = true;
            lastInvalidWord = word;
            return "Terjemahan tidak valid";
        }
    }

    public String getVideoUrl(String word) {
        return videoUrls.get(word.toLowerCase());
    }

    public ArrayList<String> getWords() {
        return new ArrayList<>(dictionary.keySet());
    }

    public boolean wasLastTranslationInvalid() {
        return lastTranslationWasInvalid;
    }

    public String getLastInvalidWord() {
        return lastInvalidWord;
    }
}
