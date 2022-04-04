package com.zalex.pdfgenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.zalex.convertHTMLtoPDF.ConvertHTMLtoPDF.convertingUsingFlyingSaucer;
import static com.zalex.convertHTMLtoPDF.ConvertHTMLtoPDF.convertingUsingOpenHTML;

@SpringBootApplication
@Slf4j
public class PdfgeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfgeneratorApplication.class, args);
		log.info("Program START ");
//		Html2PdfUsingOpenHtml.main();	//
//		convertingUsingFlyingSaucer();
//		convertingUsingOpenHTML();
		log.info("Program END ");
	}

}