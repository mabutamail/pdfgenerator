//import com.openhtmltopdf.extend.FSSupplier;
//import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
//import org.jsoup.Jsoup;
//import org.jsoup.helper.W3CDom;
//import org.w3c.dom.Document;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.Objects;
//
//public class main {
//    public static void main(String[] args) {
//        System.out.println("Starting");
//
//        try {
//
//            final W3CDom w3cDom = new W3CDom();
//            final Document w3cDoc = w3cDom.fromJsoup(Jsoup.parse(readFile()));
//            final OutputStream outStream = new FileOutputStream("test.pdf");
//
//            final PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();
//            pdfBuilder.useFastMode();
//            pdfBuilder.withW3cDocument(w3cDoc, "/");
//            pdfBuilder.useFont(new File(main.class.getClassLoader().getResource("fonts/SourceSansPro-Regular.ttf").getFile()), "source-sans");
//            pdfBuilder.toStream(outStream);
//
//            pdfBuilder.run();
//            outStream.close();
//
//        } catch (Exception e) {
//            System.out.println("PDF could not be created: " + e.getMessage());
//        }
//
//        System.out.println("Finish.");
//    }
//
//
//    private static String readFile() throws IOException {
//        final ClassLoader classLoader = main.class.getClassLoader();
//        final InputStream inputStream = classLoader.getResourceAsStream("test.html");
//        final StringBuilder sb = new StringBuilder();
//        final Reader r = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
//        char[] buf = new char[1024];
//        int amt = r.read(buf);
//        while(amt > 0) {
//            sb.append(buf, 0, amt);
//            amt = r.read(buf);
//        }
//        return sb.toString();
//    }
//}