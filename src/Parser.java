import org.deckfour.xes.in.XesXmlGZIPParser;
import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

class Parser {
    private String fileName;
    private File inputFile;
    private File outputFile;
    private BufferedWriter bfWriter;
    private XesXmlParser parser;

    Parser(String fileName) throws Exception {
        this.fileName = fileName;
        inputFile = new File(fileName);
        outputFile = new File("output");
        bfWriter = new BufferedWriter(new FileWriter(outputFile));
        parser = new XesXmlParser();
    }


    void getIDResultPerformer() throws Exception {
        List<XLog> logs =  parser.parse(inputFile);
        XLog log = logs.get(0);

        for(XTrace trace : log) {
            for(XEvent event : trace) {
                XAttributeMap attrMap = event.getAttributes();
                XAttribute resultAttr = attrMap.get("result");
                String result;
                if(resultAttr == null) {
                    result = "null";
                }
                else {
                    result = resultAttr.toString();
                }
                bfWriter.write(result);
                bfWriter.write("," + attrMap.get("org:resource"));
                bfWriter.write("," + attrMap.get("concept:name"));
                bfWriter.write("->");
            }
            bfWriter.write("\n");
        }
        bfWriter.flush();
        bfWriter.close();
    }


    void getID() throws Exception {
        List<XLog> logs =  parser.parse(inputFile);
        XLog log = logs.get(0);

        for(XTrace trace : log) {
            for(XEvent event : trace) {
                XAttributeMap attrMap = event.getAttributes();
                bfWriter.write(attrMap.get("concept:name").toString());
                bfWriter.write("->");
            }
            bfWriter.write("\n");
        }
        bfWriter.flush();
        bfWriter.close();
    }


    public static void main(String[] args) throws Exception {
        String fileName = "BPIC_2012_complete.xes";
        Parser parser = new Parser(fileName);
        parser.getID();
    }
}