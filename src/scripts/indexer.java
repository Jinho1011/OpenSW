package scripts;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class indexer {

    private static String input = "./index.xml";
    private static String output = "./index.post";

    public indexer(String file) {
        this.input = file;
    }

    public void makePost() throws IOException, SAXException, ParserConfigurationException {
        SimpleDocument index = new SimpleDocument(input);
        NodeList docList = index.getNodeList("doc");

        FileOutputStream fileOutputStream = new FileOutputStream(output);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        HashMap<String, HashMap> originalHash = new HashMap();
        HashMap keywordHash = new HashMap();

        int docLength = docList.getLength();

        for (int i = 0; i < docLength; i++) {
            Node node = docList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String bodyString = element.getElementsByTagName("body").item(0).getTextContent();
                String id = element.getAttribute("id");

                String[] splited = bodyString.split("#");

                for (String split : splited) {
                    if (split.trim().length() > 0) {
                        String[] data = split.split(":");
                        String keyword = data[0].trim();
                        int freq = Integer.parseInt(data[1].trim());

                        if (!originalHash.containsKey(keyword)) {
                            HashMap<String, Integer> freqs = new HashMap();
                            freqs.put(id, freq);
                            originalHash.put(keyword, freqs);
                        } else {
                            HashMap freqs = originalHash.get(keyword);
                            freqs.put(id, freq);
                            originalHash.put(keyword, freqs);
                        }
                    }
                }
            }
        }

        Iterator<String> it = originalHash.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            String result = "";
            HashMap freqs = originalHash.get(key);
            Iterator<String> keys = freqs.keySet().iterator();

            while (keys.hasNext()) {
                String id = keys.next();
                Integer freq = (Integer) freqs.get(id);


                float weight = (float) (freq * Math.log((float)docLength / freqs.size()));
                weight = (float) (Math.round(weight * 100) / 100.0);

                result += id + " " + weight + " ";
            }

//            System.out.println(key + " -> " + result);

            keywordHash.put(key, result);

        }

        objectOutputStream.writeObject(keywordHash);

        objectOutputStream.close();
    }
}
