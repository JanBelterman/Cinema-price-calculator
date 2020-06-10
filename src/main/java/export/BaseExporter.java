package export;

import java.io.FileWriter;
import java.io.IOException;

public abstract class BaseExporter {

    protected String location = "/Users/janbe/Documents/";
    protected ExportFormat exportFormat;

    public void export(String content, String fileName) {
        try (FileWriter file = new FileWriter(location + fileName + exportFormat.getExtension())) {
            file.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
