package com.example.sixosixthityone;

import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.core.FileURIResolver;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.fit.pdfdom.PDFDomTree;
import org.json.JSONObject;
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

@RestController

public class controller {

@GetMapping("/docxToHtml")
    public String convertDocToHtml() throws IOException {
    // Load the .docx file.

    String docPath = "src/main/resources/resume.docx";
    String pdfPath = "src/main/output/new.html";

    XWPFDocument document = new XWPFDocument(new FileInputStream(docPath));

    // Set the options for converting to HTML.
    XHTMLOptions options = XHTMLOptions.create();
//            .URIResolver(new FileURIResolver("images"));
//    options.setExtractor(new FileImageExtractor("images"));

    // Convert the .docx file to HTML.
    OutputStream outputStream = new FileOutputStream(pdfPath);
    XHTMLConverter.getInstance().convert(document, outputStream, options);


    // Close the input and output streams.
    outputStream.close();

    // Create a File object representing the HTML file
    File htmlFile = new File(pdfPath);

// Create a BufferedReader to read the file
    BufferedReader reader = new BufferedReader(new FileReader(htmlFile));

// Read the file into a String
    String htmlString = "";
    String line;
    while ((line = reader.readLine()) != null) {
        htmlString += line;
    }


    return htmlString;
}


@GetMapping("pdfToHtml")
public String pdfToHTML() throws IOException {


    String pdfPath = "src/main/resources/resume.pdf";
    String htmlPath = "src/main/output/asdfgh.html";

        PDDocument pdf = PDDocument.load(new File(pdfPath));
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


    return htmlString;
    }



    @GetMapping("/dh2")
    public String docToHTMLPart2() throws IOException {

    DocToPdf.docToPdf();
    String pdfPath = "src/main/output/b.pdf";

        String htmlPath = "src/main/output/asdfgh.html";

        PDDocument pdf = PDDocument.load(new File(pdfPath));
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


        return htmlString;

    }

    @GetMapping("docToHtml")
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

    @GetMapping("rtfToHtml")
    public String convertRtfToHtml() throws IOException {
        // Load the .docx file.
        String pdfPath = "src/main/output/b.pdf";

        DocToPdf.docToPdf2();

        String htmlPath = "src/main/output/asdfgh.html";

        PDDocument pdf = PDDocument.load(new File(pdfPath));
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


        return htmlString;
    }

    @GetMapping("txtToHtml")
    public String txtToHtml() throws IOException {
        String inputFilePath = "src/main/resources/resume.txt";
        String htmlPath = "src/main/output/txtHTML.html";
    try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            FileWriter writer = new FileWriter(htmlPath);

            writer.write("<html>\n<body>\n<ul>\n");
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write("<li>" + line + "</li>\n");
            }
            writer.write("</ul>\n</body>\n</html>");
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new FileReader(htmlPath));

// Read the file into a String
        String htmlString = "";
        String line;
        while ((line = reader.readLine()) != null) {
            htmlString += line;
        }


        return htmlString;
    }


    @GetMapping("jpegToHtml")
    public String jpegToHtml() throws TesseractException {
        String inputFilePath = "src/main/resources/resume.jpeg";
        File imageFile = new File(inputFilePath);
        ITesseract instance = new Tesseract();
//        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
//        } catch (TesseractException e) {
//            System.err.println(e.getMessage());
//        }
        return result;
    }

    @GetMapping("test")
    public String getSomething() {

        String fileName = "src/main/test/test2.txt";
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String content = sb.toString();
        System.out.println(content);

       return content;

    }



}
