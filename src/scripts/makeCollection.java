package scripts;

import org.jsoup.Jsoup;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 2주차 실습 코드
 * 
 * 주어진 5개의 html 문서를 전처리하여 하나의 xml 파일을 생성하세요. 
 * 
 * input : data 폴더의 html 파일들
 * output : collection.xml 
 */

public class makeCollection {

	private static String data_path = "../data";
	private static String output = "./collection.xml";

	public makeCollection(String path) {
		this.data_path = path;
	}

	public static SimpleDocument makeXml() throws ParserConfigurationException, TransformerException, IOException {
		File folder = new File(data_path);
		File[] listOfFiles = folder.listFiles();

		SimpleDocument collection = new SimpleDocument();
		Element docs = collection.addChild("docs");

		int id = 0;

		for (File file : listOfFiles) {
			if (file.isFile()) {
				org.jsoup.nodes.Document html = Jsoup.parse(file, "UTF-8");

				String titleData = html.title();
				String bodyData = html.body().text();

				Element doc = collection.addChild(docs, "doc");
				doc.setAttribute("id", Integer.toString(id++));

				Element title = collection.addChild(doc, "title");
				collection.addTextChild(title, titleData);

				Element body = collection.addChild(doc, "body");
				collection.addTextChild(body, bodyData);
			}
		}

		collection.saveDocument(output);

		return collection;
	}
	
}
