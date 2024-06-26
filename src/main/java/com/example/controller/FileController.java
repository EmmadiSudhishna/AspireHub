package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/files")
public class FileController {
	@Autowired
	private ServletContext servletContext;


   // private final String uploadDir = "./uploads";
	//String uploadDir="src/main/resources/static/uploads/";
	
	/*@PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("filename")String filename) {
        try {
            // Create the upload directory if not exists
        	String uploadDir="src/main/resources/static/uploads/";
            Files.createDirectories(Path.of(uploadDir));

            // Save the file to the upload directory
            Path filePath = Path.of(uploadDir, filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }*/


	
	@PostMapping("/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("filename") String filename) {
		try {
			// Get the path to the root directory of the application
			String rootDir = servletContext.getRealPath("/");
			// Construct the path to the uploads directory within WEB-INF/classes/static
			String uploadDir = rootDir + "WEB-INF" + File.separator + "classes" + File.separator + "static"
					+ File.separator + "uploads";
			// Create the uploads directory if it doesn't exist
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Define the file path within the uploads directory
			Path filePath = Paths.get(uploadDir, filename);
			// Save the file to the uploads directory
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			return ResponseEntity.ok("File uploaded successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
		}
	}

	
	  

   @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> handleFileDownload(@PathVariable String fileName) throws IOException {
        // Load file as Resource
	   
        Path filePath = Path.of(fileName, fileName);
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

        // Check if file exists
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } 
        else {
        	return ResponseEntity.notFound().build();
        }
    }
   
   
   
   
}
