package sketchoogiri.domain.service.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

@Service
public class ImageStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public ImageStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public Path store(String originalFileName, String contentType, byte[] content) {
		try {
			if (content.length == 0) {
				throw new StorageException("Failed to store empty file.");
			}
			if (!contentType.matches("image/png|image/jpeg")) {
				throw new StorageContentTypeNotSupportedException("This content-type is not supported.");
			}
			Path destinationFile = createUniqueFile(originalFileName);
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				System.out.println(destinationFile.getParent());
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = new ByteArrayInputStream(content)) {
				Files.copy(inputStream, destinationFile);
			}
			return destinationFile;
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
	
	public Path createUniqueFile(String originalFileName) {
		// 現在の日付時刻を利用してユニークなファイルを作成
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
       String datetime = LocalDateTime.now().format(formatter);
       // String originalFileName = file.getOriginalFilename();
       String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
       Path uniqueFile = this.rootLocation.resolve(
    		   Paths.get(datetime + extention))
    		   .normalize().toAbsolutePath();
       int prefix = 0;
       while(Files.exists(uniqueFile)){
    	   prefix++;
    	   uniqueFile = this.rootLocation.resolve(
    			   Paths.get(datetime + "-" + String.valueOf(prefix) + extention))
    			   .normalize().toAbsolutePath();
        }
       return uniqueFile;
	}
}
