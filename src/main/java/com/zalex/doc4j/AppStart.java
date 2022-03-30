package com.zalex.doc4j;

public class AppStart {

    private static final String htmlFileName = "html5.html";
    private static final String OUTPUT_PATH = "src/main/resources/doc4j/" + htmlFileName;
    private static final String IMAGE_PATH = "src/main/resources/doc4j/img.png";

    public static void main(String[] args) throws Exception {
        Docx4jExample docx4jExample = new Docx4jExample();
        docx4jExample.createDocumentPackage(OUTPUT_PATH, IMAGE_PATH);
    }
}