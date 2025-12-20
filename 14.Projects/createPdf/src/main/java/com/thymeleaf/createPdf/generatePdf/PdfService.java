package com.thymeleaf.createPdf.generatePdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    private Logger logger = LoggerFactory.getLogger(PdfService.class);

    public ByteArrayInputStream createPdf(){

        logger.info("Create pdf started: ");

        String title = "Welcome here !!!";
        String content = "Table Support: The library facilitates the creation of tables in PDF documents";

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();

        PdfWriter.getInstance(document, out);

        // Footer
        HeaderFooter footer = new HeaderFooter(true, new Phrase(" DEMO"));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        document.setFooter(footer);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
        Paragraph titlePara = new Paragraph(title, titleFont);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);


        Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 18);
        Paragraph paragraph = new Paragraph(content);
        paragraph.add(new Chunk(" Added after creating paragraph"));
        document.add(paragraph);

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
