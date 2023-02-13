package com.example.sixosixthityone;

import java.io.*;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.itext.extension.IPdfWriterConfiguration;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlip;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlipFillProperties;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

public class DocToPdf {
    public static void main(String[] args) throws IOException, DocumentException {
//        docToPdf();
//        docToPdfHavingImage();
//        docToPdfFinal();


        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "[hello, heelo]");
        jsonObject1.put("age", 30);
        jsonArray.put(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "Jane");
        jsonObject2.put("age", 25);
        jsonArray.put(jsonObject2);

        System.out.println(jsonArray.toString());

    }
    public static void docToPdf() {
        try {
            String docPath = "src/main/resources/resume.docx";
            String pdfPath = "src/main/output/b.pdf";

            XWPFDocument xwpfDoc = new XWPFDocument(new FileInputStream(docPath));
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

//            Image image = Image.getInstance("image.jpg");
//            image.setAlignment(Image.ALIGN_CENTER);
//            document.add(image);

            for (XWPFParagraph paragraph : xwpfDoc.getParagraphs()) {
                document.add(new Paragraph(paragraph.getText()));
            }

            document.close();
            System.out.println("PDF created successfully at " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void docToPdf2() {
        try {
            String docPath = "C:\\Users\\akumar1\\OneDrive - MONSTER.Com (India) Pvt Ltd\\Desktop/sample2.rtf";
            String pdfPath = "src/main/output/b.pdf";

            XWPFDocument xwpfDoc = new XWPFDocument(new FileInputStream(docPath));
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

//            Image image = Image.getInstance("image.jpg");
//            image.setAlignment(Image.ALIGN_CENTER);
//            document.add(image);

            for (XWPFParagraph paragraph : xwpfDoc.getParagraphs()) {
                document.add(new Paragraph(paragraph.getText()));
            }

            document.close();
            System.out.println("PDF created successfully at " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void docToPdfHavingImage() {
        try {
            String docPath = "src/main/resources/ab.docx";
            String pdfPath = "src/main/output/c.pdf";

            XWPFDocument xwpfDoc = new XWPFDocument(new FileInputStream(docPath));
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            for (XWPFParagraph paragraph : xwpfDoc.getParagraphs()) {
                document.add(new Paragraph(paragraph.getText()));
            }

            for (XWPFPictureData pictureData : xwpfDoc.getAllPictures()) {
                Image image = Image.getInstance(pictureData.getData() );
                image.setAlignment(Image.ALIGN_CENTER);
                document.add(image);
            }

            document.close();
            System.out.println("PDF created successfully at " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void docToPdfFinal() throws IOException, DocumentException {
        // Load the Word document
        String docPath = "src/main/resources/IdCard.docx";
        String pdfPath = "src/main/output/IdCard.pdf";
        XWPFDocument document = new XWPFDocument(new FileInputStream(docPath));
  //HWPFDocument for doc
// Create a PdfOptions object
        PdfOptions options = PdfOptions.create();
//// Save the Word document as a PDF
        OutputStream out = new FileOutputStream(pdfPath);
        PdfConverter.getInstance().convert(document, out, options);

//        using filePath I can get pdf
//        PDDocument document = PDDocument.load(new File(filePath)

    }


}

