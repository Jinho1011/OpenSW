package scripts;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.*;
import java.util.*;

// 유사도가 전부 같으면 id 순
// -s index.post -q "라면에는 면, 분말 스프가 있다"

public class searcher {
    private static String input = "./index.post";
    private String query;

    public searcher(String input, String query) {
        this.input = input;
        this.query = query;
    }

    public void calcSim() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(input);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object object = objectInputStream.readObject();
        objectInputStream.close();

        HashMap documents = new HashMap();
        documents.put(0, "떡");
        documents.put(1, "초밥");
        documents.put(2, "파스타");
        documents.put(3, "아이스크림");
        documents.put(4, "라면");

        HashMap hashMap = (HashMap) object;
        Iterator it = hashMap.keySet().iterator();

        KeywordExtractor keywordExtractor = new KeywordExtractor();
        KeywordList keywordList = keywordExtractor.extractKeyword(query, true);

        HashMap sims = new HashMap();
        HashMap keywords = new HashMap();

        for (int i = 0; i < keywordList.size(); i++) {
            Keyword keyword = keywordList.get(i);
            keywords.put(keyword.getString(), keyword.getCnt());
        }

        while (it.hasNext()) {
            String key = (String) it.next();
            if (keywords.containsKey(key)) {
                String freqs = (String) hashMap.get(key);
                String[] splitedFreqs = freqs.split(" ");

                for (int i = 0; i < splitedFreqs.length; i += 2) {
                    String id = splitedFreqs[i];
                    Double freq = Double.parseDouble(splitedFreqs[i + 1]);
                    int weight = (int) keywords.get(key);

                    if (sims.containsKey((id))) {
                        double sim = (double) sims.get(id);
                        sims.put(id, sim + freq * weight);

                    } else {
                        sims.put(id, freq * weight);
                    }
                }

            }
        }

        Map<String, Double> result = sortMapByValue(sims);
        for (Map.Entry<String, Double> entry : result.entrySet()) {
            System.out.println("Document: " + documents.get(Integer.parseInt(entry.getKey())) + ", "
                    + "similarity: " + entry.getValue());
        }

    }

    public static LinkedHashMap<String, Double> sortMapByValue(HashMap<String, Double> map) {
        List<HashMap.Entry<String, Double>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        LinkedHashMap<String, Double> result = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
