package com.example.twitter.controllers;

import com.example.twitter.entitys.Messages;
import com.example.twitter.entitys.User;
import com.example.twitter.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Controller
public class HaupController {

    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}")// берем из application.properties путь к файлу и сохраняем в переменную
    String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Messages> messages = messageRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Messages message = new Messages(text, tag, user);

        if(file != null){
            File uplodDir = new File(uploadPath);
            if(!uplodDir.exists()){// если дирректория не существует, то мы ее создаем
                uplodDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFIlename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFIlename));// Зазгужем файл
            message.setFilename(resultFIlename);


        }

        messageRepo.save(message);

        Iterable<Messages> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }
}