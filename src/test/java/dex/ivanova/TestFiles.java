package dex.ivanova;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static com.codeborne.pdftest.PDF.containsText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestFiles {

    @Test
    void zipTest() throws Exception {
        //просмотр зип файла
        ZipFile zf = new ZipFile("src\\test\\resources\\hw.zip");   //!!!!!!!!! путь!

        //проверка csv
        ZipEntry zipEntryCsv = zf.getEntry("hw.csv"); // extract from zip
        try (InputStream stream = zf.getInputStream(zipEntryCsv)) {
                CSVReader reader = new CSVReader(new InputStreamReader(stream));
                List<String[]> list = reader.readAll();
                assertThat(list)
                        .hasSize(3)
                        .contains(
                                new String[] {"Ivanova", "Yuliya"},
                                new String[] {"Ivanova", "Albina"},
                                new String[] {"Kravchenko", "Matvey"}
                        );
        }

        //проверка xlsx
        ZipEntry zipEntryXlsx = zf.getEntry("hw.xlsx"); // extract from zip
        try (InputStream stream = zf.getInputStream(zipEntryXlsx)) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue())
                    .isEqualTo("ivanova");
        }

        //проверка pdf
        ZipEntry zipEntryPdf = zf.getEntry("hw.pdf"); // extract from zip
        try (InputStream stream = zf.getInputStream(zipEntryPdf)) {
            PDF parsed = new PDF(stream);
            assertThat(parsed, containsText("test"));
        }



        }


    }

