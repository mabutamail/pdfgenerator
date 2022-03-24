package com.zalex.convertHTMLtoPDF;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * https://www.baeldung.com/java-html-to-pdf
 */
@Slf4j
public class ConvertHTMLtoPDF {
    private static int count;

    public static void main(String[] args) {
        count++;
        log.info("Start convert HTML to PDF");
        File inputHTML = new File("src/main/resources/sourceHtml/htmlTestFromArtem.html");

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
        try (OutputStream fos = new FileOutputStream("src/main/resources/resultPDF/outputXHTML" + count + ".xhtml")) {
            fos.write(tempXHTML.getBytes());
            log.info("Ваш XHTML файл - Создан!");
        } catch (IOException e) {
            log.error("Ваш XHTML файл - НЕ Создан!");
        }

        try (OutputStream outputStream = new FileOutputStream("src/main/resources/resultPDF/outputPdf" + count + ".pdf")) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            //  TODO: Can't load the XML resource (using TrAX transformer)
//            https://stackoverflow.com/questions/9415468/html-to-pdf-conversion-cant-load-the-xml-resource-error
//            https://ru.wikipedia.org/wiki/DTD
            renderer.setDocumentFromString(tempXHTML);
            renderer.layout();
            renderer.createPDF(outputStream);
            log.info("Ваш PDF файл - Создан!");
        } catch (FileNotFoundException e) {
            log.error("Файл не найден ", e);
        } catch (IOException e) {
            log.error("Ошибка IO ", e);
        }

    }

    public static Document createXML(Document document) {
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

}