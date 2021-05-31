package com.simbir_soft.controller;

import com.simbir_soft.model.User;
import com.simbir_soft.service.MessageService;
import com.simbir_soft.service.RoomService;
import com.simbir_soft.service.UserService;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UiController {
    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;

    @GetMapping("/login")
    public String login(Model model) throws TemplateModelException {
        TemplateHashModel roles = BeansWrapper.getDefaultInstance().getEnumModels();
        TemplateHashModel myRoles = (TemplateHashModel) roles.get("com.simbir_soft.model.Role");
        model.addAttribute("roles", myRoles);
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("rooms", roomService.findAllByUserId(user.getId()));
        model.addAttribute("message", "");
        model.addAttribute("users", userService.findAll());
        model.addAttribute("bot", true);
        return "main";
    }

    @GetMapping("/main/rooms/{id}")
    public String mainPage(@PathVariable("id") Long id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("rooms", roomService.findAllByUserId(user.getId()));
        model.addAttribute("messages", roomService.getById(id).getMessages());
        model.addAttribute("user", user);
        model.addAttribute("users", roomService.getById(id).getUsers());
        model.addAttribute("roomId", id);
        model.addAttribute("bot", false);
        return "main";
    }
}
