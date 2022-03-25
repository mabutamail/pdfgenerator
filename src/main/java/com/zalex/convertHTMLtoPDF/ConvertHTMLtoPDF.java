package com.zalex.convertHTMLtoPDF;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * https://www.baeldung.com/java-html-to-pdf
 */
@Slf4j
public class ConvertHTMLtoPDF {
    public static final String pathSource = "src/main/resources/sourceHtml/";
    public static final String pathResult = "src/main/resources/resultPDF/";

//    public static final String fileName = "sampleHTML5.html";    //  ++
    public static final String fileName = "Google.html";   //  -
//    public static final String fileName = "ya.html";   //  --
//    public static final String fileName = "htmlTestFromArtem.html";   //  -+
//    public static final String fileName = "tern.html";   //  -+

    public static void convertingUsingFlyingSaucer() {
        log.info("Start convert HTML to PDF using Flying Saucer()");
        File inputHTML = new File(pathSource + fileName);

        Document document = null;
        try {
            document = Jsoup.parse(inputHTML, "UTF-8");
        } catch (IOException e) {
            log.error("Не удалось распарсить HTML в XHTML ", e);
        }

        assert document != null;
        String tempXHTML = createXML(document).toString();
        log.info("Результат конвертации HTML -> XHTML \n" + tempXHTML);

        //  только для тестов! сохраняем файл XHTML
        try (OutputStream fos = new FileOutputStream(pathResult + fileName + ".xhtml")) {
            fos.write(tempXHTML.getBytes());
            log.info("Ваш XHTML файл - Создан!");
        } catch (IOException e) {
            log.error("Ваш XHTML файл - НЕ Создан!");
        }

        try (OutputStream outputStream = new FileOutputStream(pathResult + fileName + ".pdf")) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            //  TODO: Can't load the XML resource (using TrAX transformer)
//            https://stackoverflow.com/questions/9415468/html-to-pdf-conversion-cant-load-the-xml-resource-error
//            https://ru.wikipedia.org/wiki/DTD
            try {
                renderer.setDocumentFromString(tempXHTML);
                renderer.layout();
                renderer.createPDF(outputStream);
                log.info("Ваш PDF файл - Создан!");
            } catch (Exception e) {
                log.error("ERROR Can't load the XML resource (using TrAX transformer)", e);
            }

        } catch (FileNotFoundException e) {
            log.error("Файл не найден ", e);
        } catch (IOException e) {
            log.error("Ошибка IO ", e);
        }

    }


    public static void convertingUsingOpenHTML() {
        log.info("Start convert HTML to PDF using OpenHTML()");

        File inputHTML = new File(pathSource + fileName);

        Document document = null;
        try {
            document = Jsoup.parse(inputHTML, "UTF-8");
        } catch (IOException e) {
            log.error("Не удалось распарсить HTML в XHTML ", e);
        }

        assert document != null;
        String tempXHTML = createXML(document).toString();
        log.info("Результат конвертации HTML -> XHTML \n" + tempXHTML);

        //  только для тестов! сохраняем файл XHTML
//        try (OutputStream fos = new FileOutputStream(pathResult + fileName + ".xhtml")) {
//            fos.write(tempXHTML.getBytes());
//            log.info("Ваш XHTML файл - Создан! " + fileName);
//        } catch (IOException e) {
//            log.error("Ваш XHTML файл - НЕ Создан! " + fileName);
//        }


        try (OutputStream os = new FileOutputStream(pathResult + fileName + ".pdf")) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(pathResult + fileName + ".pdf");
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
            builder.run();
            log.info("Ваш PDF файл - Создан! " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try (OutputStream outputStream = new FileOutputStream(pathResult + fileName + ".pdf")) {
////            ITextRenderer renderer = new ITextRenderer();
////            SharedContext sharedContext = renderer.getSharedContext();
////            sharedContext.setPrint(true);
////            sharedContext.setInteractive(false);
////            //  TODO: Can't load the XML resource (using TrAX transformer)
//////            https://stackoverflow.com/questions/9415468/html-to-pdf-conversion-cant-load-the-xml-resource-error
//////            https://ru.wikipedia.org/wiki/DTD
////            try {
////                renderer.setDocumentFromString(tempXHTML);
////                renderer.layout();
////                renderer.createPDF(outputStream);
////                log.info("Ваш PDF файл - Создан!");
////            } catch (Exception e) {
////                log.error("ERROR Can't load the XML resource (using TrAX transformer)", e);
////            }
//
//        } catch (FileNotFoundException e) {
//            log.error("Файл не найден ", e);
//        } catch (IOException e) {
//            log.error("Ошибка IO ", e);
//        }

    }


    public static Document createXML(Document document) {
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

}