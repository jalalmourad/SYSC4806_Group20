package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private static final String UPLOAD_DIR = "uploads/";

    // Endpoint to handle file uploads
    @PostMapping("/upload")
    public ResponseEntity<String> uploadReport(@RequestParam("studentId") String studentId,
                                               @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty. Please upload a valid file.");
        }

        try {
            // Ensure upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the upload directory
            String fileName = studentId + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    // Endpoint to list all uploaded files
    @GetMapping("/files")
    public ResponseEntity<String[]> listUploadedFiles() {
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists() || folder.listFiles() == null) {
            return ResponseEntity.ok(new String[]{});
        }
        String[] files = folder.list((dir, name) -> name.endsWith(".pdf"));
        return ResponseEntity.ok(files);
    }
}
