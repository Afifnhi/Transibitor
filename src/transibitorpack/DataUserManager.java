package transibitorpack;

        import org.w3c.dom.*;
        import javax.xml.parsers.*;
        import javax.xml.transform.*;
        import javax.xml.transform.dom.*;
        import javax.xml.transform.stream.*;
        import java.io.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;

        import java.io.File;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;
        import java.util.ArrayList;
        import java.util.List;

        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;

        import org.w3c.dom.Document;
        import org.w3c.dom.Element;
        import org.w3c.dom.Node;
        import org.w3c.dom.NodeList;

        public class DataUserManager {
            private static final String FILE_NAME = "users.xml";

            public static List<DataUser> readUsersFromFile() {
                List<DataUser> users = new ArrayList<>();
                File file = new File(FILE_NAME);
                if (!file.exists()) {
                    return users;
                }
                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("user");
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            String username = eElement.getElementsByTagName("username").item(0).getTextContent();
                            String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                            String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                            users.add(new DataUser(username, email, password));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return users;
            }
        
            public static void writeUsersToFile(List<DataUser> users) {
                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.newDocument();
                    Element rootElement = doc.createElement("users");
                    doc.appendChild(rootElement);
                    for (DataUser user : users) {
                        Element userElement = doc.createElement("user");
                        rootElement.appendChild(userElement);
                        Element username = doc.createElement("username");
                        username.appendChild(doc.createTextNode(user.getUsername()));
                        userElement.appendChild(username);
                        Element email = doc.createElement("email");
                        email.appendChild(doc.createTextNode(user.getEmail()));
                        userElement.appendChild(email);
                        Element password = doc.createElement("password");
                        password.appendChild(doc.createTextNode(user.getPassword()));
                        userElement.appendChild(password);
                    }
        
                    // Write the content into the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(FILE_NAME));
                    transformer.transform(source, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        
            public static boolean userExists(String username) {
                List<DataUser> users = readUsersFromFile();
                for (DataUser user : users) {
                    if (user.getUsername().equals(username)) {
                        return true;
                    }
                }
                return false;
            }
        
            public static String hashPassword(String password) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hashedBytes = md.digest(password.getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hashedBytes) {
                        sb.append(String.format("%02x", b));
                    }
                    return sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
            