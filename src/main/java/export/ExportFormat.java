package export;

public enum ExportFormat {
    PLAINTEXT("txt"),
    JSON("json");

    private String extension;

    ExportFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

}
