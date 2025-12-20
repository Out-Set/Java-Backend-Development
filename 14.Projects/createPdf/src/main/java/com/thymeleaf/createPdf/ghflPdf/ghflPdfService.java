package com.thymeleaf.createPdf.ghflPdf;

import com.lowagie.text.*;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ghflPdfService {

    private Logger logger = LoggerFactory.getLogger(ghflPdfService.class);

    public ByteArrayInputStream createPdf(LinkedHashMap<Object, Object> keyValues) throws IOException {

        logger.info("Create pdf started: ");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        // Footer
        HeaderFooter footer = new HeaderFooter(true, new Phrase(". GRIHAM HOUSING FINANCE"));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        document.setFooter(footer);

        document.open();

        String titleHeader = "RECEIPT";
        Font titleHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Paragraph titleHeaderPara = new Paragraph(titleHeader, titleHeaderFont);
        titleHeaderPara.setAlignment(Element.ALIGN_CENTER);
        document.add(titleHeaderPara);

        // Add image
        String imagePath = "src/main/resources/logo/ghfl-logo.png";
        Image img = Image.getInstance(Files.readAllBytes(Paths.get(imagePath)));
        img.scaleAbsolute(150, 80);

        // Create a table with 2 columns
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Add the image to the first cell (left side)
        PdfPCell cell1 = new PdfPCell(img);
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        // Add "Customer Copy" text to the second cell (right side)
        String customerCopyText = "Customer Copy";
        Font customerCopyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Paragraph phrase = new Paragraph(customerCopyText, customerCopyFont);
        PdfPCell cell2 = new PdfPCell(phrase);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setPaddingTop(15);
        table.addCell(cell2);

        // Add the table to the document
        document.add(table);

        // Add title
        String title = "GRIHAM HOUSING FINANCE";
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
        Paragraph titlePara = new Paragraph(title, titleFont);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);

        // Sub-Title
        String subTitle = "(Formerly, Poonawalla Housing Finance Limited)";
        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Paragraph subTitlePara = new Paragraph(subTitle, subTitleFont);
        subTitlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitlePara);

        // Add content below the table
        String address = "Regd. Office: 602, 6th Floor, Zero One IT Park, Survey No. 79/1, Ghorpadi, Mundhwa Road, Pune - 411 036 Maharashtra, Phone: 1800 266 3201";
        Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Paragraph paragraph = new Paragraph(address, paraFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        // Add space below the title
        document.add(new Paragraph("\n\n"));

        Font keyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        for (Map.Entry<Object, Object> entry : keyValues.entrySet()) {
            Chunk keyChunk = new Chunk(entry.getKey().toString() + ": ", keyFont);
            Chunk valueChunk = new Chunk(entry.getValue().toString(), valueFont);

            Paragraph keyValuePara = new Paragraph();
            keyValuePara.add(keyChunk);
            keyValuePara.add(valueChunk);
            keyValuePara.setSpacingBefore(10);
            document.add(keyValuePara);
        }

        document.close();

        byte[] attachmentFileContent = out.toByteArray();
        System.out.println("Byte Stream :: "+ Arrays.toString(attachmentFileContent));

        return new ByteArrayInputStream(out.toByteArray());
    }

}



