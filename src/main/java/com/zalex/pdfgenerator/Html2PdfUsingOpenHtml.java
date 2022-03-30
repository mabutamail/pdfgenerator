package com.zalex.pdfgenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Slf4j
public class Html2PdfUsingOpenHtml {

    Integer a1, a2, s2;

//    private static final String htmlFileName = "font.html";
//    private static final String htmlFileName = "html5.html";
//    private static final String htmlFileName = "Google.html";
//    private static final String htmlFileName = "ya.html";
//    private static final String htmlFileName = "tern.html";
//    private static final String htmlFileName = "htmlTestFromArtem.html";
        private static final String htmlFileName = "htmlforopenpdf.html";
    private static final String HTML_INPUT = "src/main/resources/sourceHtml/" + htmlFileName;
    private static final String PDF_OUTPUT = "src/main/resources/resultPDF/html2pdf.pdf";

    public static void main() {
        log.info("Html2PdfUsingOpenHtml START");
        try {
            Html2PdfUsingOpenHtml htmlToPdf = new Html2PdfUsingOpenHtml();
            htmlToPdf.generateHtmlToPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Html2PdfUsingOpenHtml END");
    }

    private void generateHtmlToPdf() throws IOException {
        File inputHTML = new File(HTML_INPUT);
        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, PDF_OUTPUT);
    }

    private Document createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private void xhtmlToPdf(Document doc, String outputPdf) throws IOException {
        try (OutputStream os = new FileOutputStream(outputPdf)) {
            String baseUri = FileSystems.getDefault()
                    .getPath("src/main/resources/")
                    .toUri()
                    .toString();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdf);
            //  TODO: https://stackoverflow.com/questions/55339298/openhtmltopdf-embed-a-custom-font-into-pdf-created-out-of-html
//            builder.useFont(new File("sourceHtml/SourceSansPro-Regular.ttf"), "source-sans");
            builder.useFont(new File("sourceHtml/ofont.ru_Source Sans Pro.ttf"), "source-sans");
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
            log.info("Файл PDF создан");
        }
    }
}