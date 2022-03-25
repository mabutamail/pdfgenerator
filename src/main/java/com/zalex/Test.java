package com.zalex;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.zalex.convertHTMLtoPDF.ConvertHTMLtoPDF.fileName;
import static com.zalex.convertHTMLtoPDF.ConvertHTMLtoPDF.pathSource;

public class Test {
    static DocumentBuilder builder = null;
    static String realPath = pathSource + fileName;

    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SAXException {
        builder.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId)
                    throws SAXException, IOException {
                if (systemId.contains("xhtml1-transitional.dtd")) {
                    return new InputSource(new FileReader(realPath + "/WEB-INF/dtd/xhtml1-transitional.dtd"));
                } else if (systemId.contains("xhtml-lat1.ent")) {
                    return new InputSource(new FileReader(realPath + "/WEB-INF/dtd/xhtml-lat1.ent"));
                } else if (systemId.contains("xhtml-symbol.ent")) {
                    return new InputSource(new FileReader(realPath + "/WEB-INF/dtd/xhtml-symbol.ent"));
                } else if (systemId.contains("xhtml-special.ent")) {
                    return new InputSource(new FileReader(realPath + "/WEB-INF/dtd/xhtml-special.ent"));
                } else {
                    return null;
                }
            }
        });

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(realPath.getBytes(StandardCharsets.UTF_8));
        final Document doc = builder.parse(inputStream);
        inputStream.close();
        final ITextRenderer renderer = new ITextRenderer(26f * 4f / 3f, 26);

//        renderer.setDocument(doc, request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
        renderer.layout();
    }


}