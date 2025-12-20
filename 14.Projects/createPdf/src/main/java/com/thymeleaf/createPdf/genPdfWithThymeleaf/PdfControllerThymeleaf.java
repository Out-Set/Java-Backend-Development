package com.thymeleaf.createPdf.genPdfWithThymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// http://localhost:8087/generate-pdf

@RestController
public class PdfControllerThymeleaf {

    @Autowired
    private ParseTemplate parseTemplate;

    @GetMapping("/generate-pdf")
    public String generatePdf() throws IOException {

        List<Person> people = new ArrayList<>();
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));
        people.add(new Person("sawan","sawan@gmail.com","582664","Noida"));
        people.add(new Person("anuj","anuj@gmail.com","789552664","Noida"));
        people.add(new Person("amar","amar@gmail.com","123664","Noida"));
        people.add(new Person("ankit","ankit@gmail.com","3662664","Noida"));
        people.add(new Person("aryan","aryan@gmail.com","58955664","Noida"));
        people.add(new Person("sachin","sachin@gmail.com","583694","Noida"));
        people.add(new Person("bhanu","bhanu@gmail.com","7845664","Noida"));


        String html = parseTemplate.parseThymeleafTemplate(people);
        parseTemplate.generatePdfFromHtml(html);
        return "PDF generated successfully!";
    }
}

