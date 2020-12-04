package sketchoogiri.domain.service.storage;

public class StorageContentTypeNotSupportedException extends StorageException {

	public StorageContentTypeNotSupportedException(String message) {
		super(message);
	}
	
	public StorageContentTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
}
