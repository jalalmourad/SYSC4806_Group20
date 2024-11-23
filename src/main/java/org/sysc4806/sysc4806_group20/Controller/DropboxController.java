package org.sysc4806.sysc4806_group20.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sysc4806.sysc4806_group20.Model.Student;
import org.sysc4806.sysc4806_group20.Service.StudentService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/dropbox")
public class DropboxController {

    private static final String UPLOAD_DIR = "uploads/";
    private StudentService studentService;

    public DropboxController(StudentService studentService){
        this.studentService =  studentService;
    }

    // Endpoint to handle file uploads
    @PostMapping("/upload")
    public ResponseEntity<?> uploadReport(@RequestParam("studentId") Long studentId,
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

            Student student = studentService.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with this ID not found"));
            student.addUpload(fileName);
            studentService.save(student);


        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/" + studentId + "/studentprofile");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName) {
        try {
            // Resolve the file path
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();

            // Check if the file exists and is readable
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new RuntimeException("File not found or not readable: " + fileName);
            }

            // Load the file as a resource
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File not found or not readable: " + fileName);
            }

            // Return the file as a downloadable response
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Error while processing file: " + fileName, e);
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
