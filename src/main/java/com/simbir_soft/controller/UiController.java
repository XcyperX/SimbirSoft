package com.simbir_soft.controller;

import com.simbir_soft.dto.MessageDTO;
import com.simbir_soft.dto.RoomDTO;
import com.simbir_soft.dto.UserDTO;
import com.simbir_soft.model.User;
import com.simbir_soft.service.MessageService;
import com.simbir_soft.service.RoomService;
import com.simbir_soft.service.UserService;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
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

    private final MapperFacade mapper;

    @GetMapping("/login")
    public String login(Model model) throws TemplateModelException {
        TemplateHashModel roles = BeansWrapper.getDefaultInstance().getEnumModels();
        TemplateHashModel myRoles = (TemplateHashModel) roles.get("com.simbir_soft.model.Role");
        model.addAttribute("roles", myRoles);
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("rooms", mapper.mapAsList(roomService.findAllByUserId(user.getId()), RoomDTO.class));
        model.addAttribute("message", "");
        model.addAttribute("users", mapper.mapAsList(userService.findAll(), UserDTO.class));
        model.addAttribute("bot", true);
        return "main";
    }

    @GetMapping("/main/rooms/{id}")
    public String mainPage(@PathVariable("id") Long id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("rooms", mapper.mapAsList(roomService.findAllByUserId(user.getId()), RoomDTO.class));
        model.addAttribute("messages", mapper.mapAsList(roomService.getById(id).getMessages(), MessageDTO.class));
        model.addAttribute("user", user);
        model.addAttribute("users", mapper.mapAsList(roomService.getById(id).getUsers(), UserDTO.class));
        model.addAttribute("roomId", id);
        model.addAttribute("bot", false);
        return "main";
    }
}
