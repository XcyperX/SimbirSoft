package com.SimbirSoft.SimbirSoft.controller;

import com.SimbirSoft.SimbirSoft.Dto.CommandsBotDTO;
import com.SimbirSoft.SimbirSoft.Dto.MessageDTO;
import com.SimbirSoft.SimbirSoft.Dto.RoomDTO;
import com.SimbirSoft.SimbirSoft.Dto.UserDTO;
import com.SimbirSoft.SimbirSoft.model.User;
import com.SimbirSoft.SimbirSoft.service.MessageService;
import com.SimbirSoft.SimbirSoft.service.RoomService;
import com.SimbirSoft.SimbirSoft.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {
    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;


    @PostMapping("/registrations")
    public UserDTO registrationUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        return userService.update(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }


    @PostMapping("/rooms")
    public RoomDTO createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        return roomService.save(roomDTO);
    }

    @PutMapping("/rooms/{id}")
    public RoomDTO updateRoom(@PathVariable("id") Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO.setId(id);
        return roomService.update(roomDTO);
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }


    @PostMapping("/message")
    public MessageDTO createMessage(@RequestBody @Valid MessageDTO messageDTO) {
        return messageService.save(messageDTO);
    }

    @PutMapping("/message/{id}")
    public MessageDTO updateMessage(@PathVariable("id") Long id, @RequestBody MessageDTO messageDTO) {
        messageDTO.setId(id);
        return messageService.update(messageDTO);
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageService.delete(id);
    }

    @GetMapping("/message")
    public ResponseEntity<?> getMessage() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @PostMapping("/command")
    public void readCommand(@AuthenticationPrincipal User user, @RequestBody CommandsBotDTO commandsBotDTO) {
        if (commandsBotDTO != null) {
            switch (commandsBotDTO.getCommand().split(" ")[0]) {
                case ("//room"):
                    switch (commandsBotDTO.getCommand().split(" ")[1]) {
                        case ("create"):
                            RoomDTO roomDTO = new RoomDTO();
                            roomDTO.setName(commandsBotDTO.getCommand().split(" ")[2]);
                            roomDTO.setUserId(user.getId());
                            roomDTO.setPrivateMassage(false);
                            List<UserDTO> userDTOS = new ArrayList<>();
                            userDTOS.add(userService.getById(user.getId()));
                            roomDTO.setUsers(userDTOS);
                            roomService.save(roomDTO);
                            break;
                        case ("remove"):
                            RoomDTO roomDTO1 = roomService.findAllByName(commandsBotDTO.getCommand().split(" ")[2]);
                            roomService.delete(roomDTO1.getId());
                            break;
                        case ("rename"):
                            RoomDTO roomDTO2 = roomService.findAllByName(commandsBotDTO.getCommand().split(" ")[2]);
                            roomDTO2.setName(commandsBotDTO.getCommand().split(" ")[3]);
                            roomService.update(roomDTO2);
                            break;
                        case ("connect"):
                            RoomDTO roomDTO3 = roomService.findAllByName(commandsBotDTO.getCommand().split(" ")[2]);
                            roomDTO3.getUsers().add(userService.findByLogin(commandsBotDTO.getCommand().split("-l")[1].strip()));
                            roomService.update(roomDTO3);
                            break;
                    }
                    break;
                case ("//user"):
                        
                    break;
                case ("//yBot"):

                    break;
                case ("//help"):

                    break;
            }
        }
    }

//    @DeleteMapping("/products/{id}")
//    public void deleteProduct(@PathVariable("id") Long id) {
//        productService.delete(id);
//    }
//
//    @GetMapping("/products/{id}")
//    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(productService.getById(id));
//    }
//
//    @PostMapping("/tables")
//    public SuppliesDTO createTable(@RequestBody @Valid SuppliesDTO suppliesDTO) {
//        return suppliesService.save(suppliesDTO);
//    }
//
//    @PostMapping("/categories")
//    public CategoriesDTO createCategories(@RequestBody @Valid CategoriesDTO categoriesDTO) {
//        return categoriesService.save(categoriesDTO);
//    }
//
//    @PostMapping("/manufacturers")
//    public ManufacturerDTO createManufacturers(@RequestBody @Valid ManufacturerDTO manufacturerDTO) {
//        return manufacturerService.save(manufacturerDTO);
//    }
//
//    @PostMapping("/order/history")
//    public OrderHistoryDTO createOrderHistory(@RequestBody @Valid OrderHistoryDTO orderHistoryDTO) {
//        return orderHistoryService.save(orderHistoryDTO);
//    }
//
//    @GetMapping("/order/history/{id}")
//    public ResponseEntity<?> getOrderHistory(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(orderHistoryService.getById(id));
//    }
//
//    @GetMapping("/order/history")
//    public ResponseEntity<?> getOrderHistory() {
//        return ResponseEntity.ok(orderHistoryService.findAll());
//    }
//
//    @PostMapping("/positions/names")
//    public PositionNameDTO createPositionName(@RequestBody @Valid PositionNameDTO positionNameDTO) {
//        return positionNameService.save(positionNameDTO);
//    }
//
//    @PostMapping("/subdivisions")
//    public SubdivisionDTO createSubdivision(@RequestBody @Valid SubdivisionDTO subdivisionDTO) {
//        return subdivisionService.save(subdivisionDTO);
//    }
//
//    @PostMapping("/products")
//    public ProductDTO createProduct(@RequestBody @Valid ProductDTO productDTO) {
//        return productService.save(productDTO);
//    }
//
//    @PostMapping("/store")
//    public StoreDTO createStore(@RequestBody @Valid StoreDTO storeDTO) {
//        return storeService.save(storeDTO);
//    }
//
//    @PutMapping("/store/{id}")
//    public StoreDTO updateStore(@PathVariable("id") Long id, @RequestBody StoreDTO storeDTO) {
//        storeDTO.setId(id);
//        return storeService.update(storeDTO);
//    }
//
//    @GetMapping("/store")
//    public ResponseEntity<?> getStore() {
//        return ResponseEntity.ok(storeService.findAll());
//    }
//
//    @GetMapping("/customer/order")
//    public ResponseEntity<?> getAllOrder() {
//        return ResponseEntity.ok(customerService.findAll());
//    }
//
//    @PostMapping("/customer/order")
//    public CustomerDTO createOrder(@RequestBody @Valid CustomerDTO customerDTO) {
//        return customerService.save(customerDTO);
//    }
//
//    @PostMapping("/new/customer/order")
//    public OrderHistoryDTO createOrderCustomer(@RequestBody @Valid OrderHistoryDTO orderHistoryDTO) {
//        return orderHistoryService.save(orderHistoryDTO);
//    }
//
//    @GetMapping("/new/customer/order")
//    public ResponseEntity<?> getAllOrderCustomer() {
//        return ResponseEntity.ok(orderHistoryService.findAll());
//    }
//
//    @PostMapping("add/products/orders/{orderId}/stock/{stockId}")
//    public void addProductsInStockByOrder(@PathVariable("orderId") Long orderId, @PathVariable("stockId") Long stockId) {
//        orderHistoryService.addProductsInStock(orderId, stockId);
//    }
}
