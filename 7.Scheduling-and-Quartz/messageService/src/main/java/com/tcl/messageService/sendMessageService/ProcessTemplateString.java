package com.tcl.messageService.sendMessageService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Component
public class ProcessTemplateString {

    public String process(String templateString, Map<String, Object> data) throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setNumberFormat("computer"); // Use computer format for numbers

        Template template = new Template("template", new StringReader(templateString), cfg);
        StringWriter out = new StringWriter();
        template.process(data, out);
        return out.toString();
    }
}
