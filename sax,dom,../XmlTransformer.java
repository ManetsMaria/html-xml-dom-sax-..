package main;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Created by NotePad.by on 28.05.2017.
 */
public class XmlTransformer {
    private static final File TO_HTML_STYLE_SHEET = new File("toHtmlTable.xsl");
    private static final File TO_TXT_STYLE_SHEET = new File("toTxt.xsl");

    public static void toTxt(String xmlFileName, String txtFileName) throws TransformerException {
        StreamSource styleSource = new StreamSource(TO_TXT_STYLE_SHEET);
        Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
        t.transform(new StreamSource(new File(xmlFileName)), new StreamResult(txtFileName));
    }

    public static void toHtml(String xmlFileName, String htmlFileName) throws TransformerException {
        StreamSource styleSource = new StreamSource(TO_HTML_STYLE_SHEET);
        Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
        t.transform(new StreamSource(new File(xmlFileName)), new StreamResult(htmlFileName));
    }
}
