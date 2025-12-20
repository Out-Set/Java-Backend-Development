package com.tcl.messageService.service;

import com.tcl.messageService.entity.MessageTemplates;
import com.tcl.messageService.repository.MessageTemplatesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MessageTemplatesService {

    @Autowired
    private MessageTemplatesRepo messageTemplatesRepo;

    public String create(String templateFileName, String templateString, MultipartFile file) throws IOException {
        if (file == null) {
            MessageTemplates messageTemplates = new MessageTemplates();
            messageTemplates.setTemplate(null);
            messageTemplates.setTemplateFileName(templateFileName);
            messageTemplates.setTemplateString(templateString);
            messageTemplatesRepo.save(messageTemplates);
            return "Template String saved, File is empty";
        } else {
            String originalFilename = file.getOriginalFilename();
            Path filePath = null;
            if(originalFilename.endsWith(".html")){
                Path uploadDir = Paths.get("src", "main", "resources", "templates", "html");
                filePath = uploadDir.resolve(originalFilename);
            }
            else if (originalFilename.endsWith(".txt")){
                Path uploadDir = Paths.get("src", "main", "resources", "templates", "text");
                filePath = uploadDir.resolve(originalFilename);
            }
            return saveIntoDbAndDir(file, originalFilename, filePath);
        }
    }

    public String saveIntoDbAndDir(MultipartFile file, String originalFilename, Path filePath) throws IOException {

        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            return "Failed to store file " + originalFilename;
        }

        MessageTemplates messageTemplates = new MessageTemplates();
        messageTemplates.setTemplateFileName(file.getOriginalFilename());
        messageTemplates.setTemplate(file.getBytes());

        messageTemplatesRepo.save(messageTemplates);
        return originalFilename+" uploaded successfully";
    }

    public File readById(int id) throws FileNotFoundException {
        MessageTemplates messageTemplates = messageTemplatesRepo.findById(id).get();

        String originalFilename = messageTemplates.getTemplateFileName();
        Path uploadDir = null;
        if(originalFilename.endsWith(".html")){
            uploadDir = Paths.get("src", "main", "resources", "templates", "html");
        }
        else if (originalFilename.endsWith(".txt")){
            uploadDir = Paths.get("src", "main", "resources", "templates", "text");
        }
        return getFileByName(originalFilename, uploadDir);
    }
    public File getFileByName(String fileName, Path filePath) throws FileNotFoundException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        Path fullPath = filePath.resolve(fileName);
        File file = fullPath.toFile();

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fullPath);
        }
        return file;
    }

    public List<MessageTemplates> readAll(){
        return messageTemplatesRepo.findAll();
    }

    public String update(MessageTemplates messageTemplates){
        MessageTemplates existingMessageTemplates = messageTemplatesRepo.findById(messageTemplates.getId()).orElse(null);
        if (existingMessageTemplates != null){
            messageTemplatesRepo.save(existingMessageTemplates);
            return "File updated successfully";
        }
        return "File not found";
    }

    public String delete(int id) throws IOException {
        MessageTemplates messageTemplates = messageTemplatesRepo.findById(id).get();
        String originalFilename = messageTemplates.getTemplateFileName();

        Path filePathToDelete = null;
        if(originalFilename.endsWith(".html")){
            filePathToDelete = Paths.get("src", "main", "resources", "templates", "html", originalFilename);
        }
        else if (originalFilename.endsWith(".txt")){
            filePathToDelete = Paths.get("src", "main", "resources", "templates", "text", originalFilename);
        }
        Files.delete(filePathToDelete);
        messageTemplatesRepo.deleteById(id);
        return "File deleted successfully";
    }

    public String findTemplateStringByTemplateName(String templateName){
        return messageTemplatesRepo.findTemplateStringByTemplateName(templateName);
    }
}
