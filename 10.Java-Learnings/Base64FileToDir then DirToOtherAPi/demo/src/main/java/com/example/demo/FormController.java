package com.example.demo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import static com.example.demo.FileUploadService.delete;
import static com.example.demo.FileUploadService.upload;

@RestController
@RequestMapping("/api")
public class FormController {
    String fileName;
    @PostMapping("/upload")
    public String uploadFiles(@RequestBody SwaggerModel swaggerModel) throws IOException {

        // Static dir
        String STATIC_UPLOAD_DIR = new ClassPathResource("static/image").getFile().getAbsolutePath();

        // Path outside
        String OUTSIDE_UPLOAD_DIR = "/home/sawan/Desktop/Finfinity";

        String fileName = swaggerModel.getDocName()+".jpg";
        String resp =  convertBase64ToImage(STATIC_UPLOAD_DIR, fileName, swaggerModel); // save to dir


        File tempFile = new File(STATIC_UPLOAD_DIR, fileName);
        int respCode = upload(fileName, tempFile);
        return resp+", and Uploaded successfully with response code :: "+respCode;
    }

    public static String convertBase64ToImage(String OUTSIDE_UPLOAD_DIR, String fileName, SwaggerModel swaggerModel){
        try {
            byte[] imageBytes = Base64.getDecoder().decode(swaggerModel.getDocFile());
            Files.write(Paths.get(OUTSIDE_UPLOAD_DIR, fileName), imageBytes);  // Append filename to the directory path
            return "Image saved successfully to: " + OUTSIDE_UPLOAD_DIR + "/" + fileName;  // Return the complete file path
        } catch (IOException e) {
            System.err.println("Error while saving image: " + e.getMessage());
            e.printStackTrace();
        }
        return "Some Error Occurred!";
    }

    @GetMapping("/delete")
    public String deleteFile() throws IOException {
//        String STATIC_UPLOAD_DIR = new ClassPathResource("static/image").getFile().getAbsolutePath();
        String OUTSIDE_UPLOAD_DIR = "C:\\Finfinity\\temp";
        return delete(fileName, OUTSIDE_UPLOAD_DIR);
    }
}