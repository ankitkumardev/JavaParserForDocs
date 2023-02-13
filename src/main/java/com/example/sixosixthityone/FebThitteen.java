package com.example.sixosixthityone;

import com.itextpdf.text.DocumentException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.UUID;

@RestController

public class FebThitteen {

    @GetMapping("hihello")
    public String pdfblobToHtml() throws SQLException, IOException, DocumentException {


        String pdfPath = "src/main/FebThirteen/resume.pdf";
        String htmlPath = "src/main/resources/" + UUID.randomUUID().toString() + ".html";

        Blob blob = convertPdfToBlob(pdfPath);
        byte[] pdfBytes = getBlobAsBytes(blob);

        ByteArrayInputStream bis = new ByteArrayInputStream(pdfBytes);

        PDDocument pdf = PDDocument.load(bis);
        Writer output = new PrintWriter(htmlPath, "utf-8");
        new PDFDomTree().writeText(pdf, output);
        output.close();
        BufferedReader reader = new BufferedReader(new FileReader(htmlPath));


// Read the file into a String
        String htmlString = "";
        String line;
        while ((line = reader.readLine()) != null) {
            htmlString += line;
        }
        reader.close();
        deleteHtmlFile(htmlPath);
        return htmlString;
    }

    @GetMapping("hio")
    public String pdfblobTloHtml() throws SQLException, IOException, DocumentException {


        String pdfPath = "src/main/FebThirteen/resume.pdf";
        String htmlPath = "src/main/resources/" + UUID.randomUUID().toString() + ".html";

        Blob blob = convertPdfToBlob(pdfPath);
        byte[] pdfBytes = getBlobAsBytes(blob);


        String decoded = new String(pdfBytes, "UTF-8");
        return decoded;
    }


    @GetMapping("docToHtml2")
    public String docToHTML() throws IOException, ParserConfigurationException, TransformerException {
        String docPath = "src/main/resources/resume.doc";
        String pdfPath = "src/main/output/new.html";

        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(docPath));

        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());

        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);

        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        out.close();

        String result = new String(out.toByteArray());
        return result;
    }

    private void deleteHtmlFile(String htmlPath) {
        File htmlFile = new File(htmlPath);
        htmlFile.delete();
    }

    public  Blob convertPdfToBlob(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            File pdfFile = new File(filePath);
            FileInputStream fis = new FileInputStream(pdfFile);
            byte[] pdfData = new byte[(int) pdfFile.length()];
            fis.read(pdfData);
            Blob blob = new SerialBlob(pdfData);
            return blob;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getBlobAsBytes (Blob blob) throws SQLException {
        int blobLength = (int) blob.length();
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        return blobAsBytes;
    }



}
