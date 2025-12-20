package com.tcl.messageService.repository;

import com.tcl.messageService.entity.MessageTemplates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTemplatesRepo extends JpaRepository<MessageTemplates, Integer> {

    @Query(value = "select template_string from message_templates where template_file_name =:templateName", nativeQuery = true)
    String findTemplateStringByTemplateName(@Param("templateName")String templateName);
}
