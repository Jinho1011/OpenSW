package scripts;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 3주차 실습 코드
 * 
 * kkma 형태소 분석기를 이용하여 index.xml 파일을 생성하세요.
 * 
 * index.xml 파일 형식은 아래와 같습니다.
 * (키워드1):(키워드1에 대한 빈도수)#(키워드2):(키워드2에 대한 빈도수)#(키워드3):(키워드3에 대한 빈도수) ... 
 * e.g., 라면:13#밀가루:4#달걀:1 ...
 * 
 * input : collection.xml
 * output : index.xml 
 */

public class makeKeyword {

	private static String input = "./collection.xml";
	private static String output = "./index.xml";

	public makeKeyword(String file) {
		this.input = file;
	}

	public SimpleDocument convertXml() throws IOException, SAXException, ParserConfigurationException, TransformerException {
		KeywordExtractor keywordExtractor = new KeywordExtractor();
		SimpleDocument collection = new SimpleDocument("collection.xml");
		SimpleDocument index = new SimpleDocument();
		NodeList collectionDocList = collection.getNodeList("doc");
		Element docs = index.addChild("docs");

		for (int i = 0; i < collectionDocList.getLength(); i++) {
			Node node = collectionDocList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String id = element.getAttribute("id");

				String titleData = element.getElementsByTagName("title").item(0).getTextContent();
				String bodyString = element.getElementsByTagName("body").item(0).getTextContent();

				KeywordList keywordList = keywordExtractor.extractKeyword(bodyString, true);

				String bodyData = "";

				for (int j = 0; j < keywordList.size(); j++) {
					Keyword keyword = keywordList.get(j);
					bodyData += keyword.getString() + ":" + keyword.getCnt() + "#";
				}

				Element doc = index.addChild(docs, "doc");
				doc.setAttribute("id", id);


				Element title = index.addChild(doc, "title");
				index.addTextChild(title, titleData);

				Element body = index.addChild(doc, "body");
				index.addTextChild(body, bodyData);
			}
		}

		index.saveDocument("index.xml");

		return index;
	}

}
