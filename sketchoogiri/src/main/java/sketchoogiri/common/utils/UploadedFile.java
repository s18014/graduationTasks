package sketchoogiri.common.utils;

public class UploadedFile {
    private final String originalFilename;
    private final String contentType;
    private final byte[] data;

    public UploadedFile(String fileName, String contentType, byte[] data) {
        this.originalFilename = fileName;
        this.contentType = contentType;
        this.data = data.clone();
    }

    public String getOriginalFilename() {
        return originalFilename;
    }
    
    public String getContentType() {
    	return contentType;
    }

    public byte[] getBytes() {
        return data.clone();
    }

    public int getSize() {
        return data.length;
    }
}