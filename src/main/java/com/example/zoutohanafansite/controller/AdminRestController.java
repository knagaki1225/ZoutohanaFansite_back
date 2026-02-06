package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.admin.notification.NotificationTemplateContent;
import com.example.zoutohanafansite.entity.admin.notification.NotificationTemplateList;
import com.example.zoutohanafansite.entity.notificationtemplate.NotificationTemplate;
import com.example.zoutohanafansite.service.NotificationTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final NotificationTemplateService notificationTemplateService;

    public AdminRestController(NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @GetMapping("/notification/template/list")
    public ResponseEntity<List<NotificationTemplateList>> getNotificationTemplateList(){
        List<NotificationTemplate> notificationTemplates = notificationTemplateService.selectAllNotificationTemplate();
        List<NotificationTemplateList> notificationTemplateLists = new ArrayList<>();
        for(NotificationTemplate notificationTemplate : notificationTemplates){
            NotificationTemplateList notificationTemplateList = new NotificationTemplateList();
            notificationTemplateList.setId(notificationTemplate.getId());
            notificationTemplateList.setTitle(notificationTemplate.getTitle());
            notificationTemplateLists.add(notificationTemplateList);
        }
        return ResponseEntity.ok(notificationTemplateLists);
    }

    @GetMapping("/notification/template/{id}")
    public ResponseEntity<NotificationTemplateContent>  getNotificationTemplate(@PathVariable long id){
        NotificationTemplate notificationTemplate = notificationTemplateService.selectNotificationTemplateById(id);
        NotificationTemplateContent notificationTemplateContent = new NotificationTemplateContent();
        notificationTemplateContent.setId(id);
        notificationTemplateContent.setTitle(notificationTemplate.getTitle());
        notificationTemplateContent.setContent(notificationTemplate.getContent());
        return ResponseEntity.ok(notificationTemplateContent);
    }
}
