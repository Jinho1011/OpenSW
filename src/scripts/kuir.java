package scripts;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class kuir {

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException, ClassNotFoundException {
        String command = args[0];
        String path = args[1];

        if (command.equals("-c")) {
            makeCollection collection = new makeCollection(path);
            collection.makeXml();
        } else if (command.equals("-k")) {
            makeKeyword keyword = new makeKeyword(path);
            keyword.convertXml();
        } else if (command.equals("-i")) {
            indexer index = new indexer(path);
            index.makePost();
        }else if (command.equals("-s")) {
            String option = args[2];
            if(option.equals("-q")) {
                String query = args[3];

                searcher searcher = new searcher(path, query);
                searcher.calcSim();
            }


        }
    }
}
