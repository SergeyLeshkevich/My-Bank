package by.leshkevich.utils.file;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class FileHandler {

    public static void writeFileTXT(final String filename, String str) {
        File file = new File(filename);

        try (FileWriter writer = new FileWriter(file, false)) {
            file.createNewFile();
            writer.write(str);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void writeFilePDF(final String filename, String str) {
        Document document = new Document();
        File file = new File(filename);
        Font font = FontFactory.getFont(FontFactory.COURIER, 8);
        Paragraph paragraph = new Paragraph(str,font);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(paragraph);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

}
