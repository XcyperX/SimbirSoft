package com.simbir_soft.config;

import com.simbir_soft.Dto.MessageDTO;
import com.simbir_soft.Dto.RoomDTO;
import com.simbir_soft.Dto.UserDTO;
import com.simbir_soft.model.Message;
import com.simbir_soft.model.Room;
import com.simbir_soft.model.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperConfig extends ConfigurableMapper {
    protected void configure(MapperFactory factory) {

        factory.classMap(User.class, UserDTO.class)
                .byDefault()
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(User user, UserDTO userDTO, MappingContext context) {
                        if (userDTO.getRooms() != null) {
                            List<RoomDTO> roomDTOS = new ArrayList<>();
                            userDTO.getRooms().forEach(room -> {
                                List<UserDTO> userDTOS = getUserDTO(room);
                                List<MessageDTO> messageDTOS = getMessageDTO(room);
                                roomDTOS.add(makeRoomDTO(room, userDTOS, messageDTOS));
                            });
                            userDTO.setRooms(roomDTOS);
                        }
                        super.mapAtoB(user, userDTO, context);
                    }

                    @Override
                    public void mapBtoA(UserDTO userDTO, User user, MappingContext context) {
                        super.mapBtoA(userDTO, user, context);
                    }
                })
                .register();

        factory.classMap(Room.class, RoomDTO.class)
                .field("user.id", "userId")
                .byDefault()
                .customize(new CustomMapper<Room, RoomDTO>() {
                    @Override
                    public void mapAtoB(Room room, RoomDTO roomDTO, MappingContext context) {
                        if (room.getUsers() != null) {
                            room.getUsers().forEach(user -> {
                                user.setRooms(null);
                                user.setMessages(null);
                            });
                        }
                        super.mapAtoB(room, roomDTO, context);
                    }

                    @Override
                    public void mapBtoA(RoomDTO roomDTO, Room room, MappingContext context) {
                        super.mapBtoA(roomDTO, room, context);
                    }
                })
                .register();

        factory.classMap(Message.class, MessageDTO.class)
                .field("user.id", "userId")
                .field("room.id", "roomId")
                .field("user.login", "userName")
                .byDefault()
                .register();
    }

    private List<UserDTO> getUserDTO(RoomDTO roomDTO) {
        List<UserDTO> userDTOS = new ArrayList<>();
        roomDTO.getUsers().forEach(user1 -> {
            UserDTO userDTO1 = new UserDTO();
            userDTO1.setId(user1.getId());
            userDTO1.setLogin(user1.getLogin());
            userDTO1.setPassword(user1.getPassword());
            userDTO1.setRooms(null);
            userDTO1.setMessages(null);
            userDTOS.add(userDTO1);
        });
        return userDTOS;
    }

    private List<MessageDTO> getMessageDTO(RoomDTO roomDTO) {
        List<MessageDTO> messageDTOS = new ArrayList<>();
        roomDTO.getMessages().forEach(message -> {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setDate(message.getDate());
            messageDTO.setText(message.getText());
            messageDTO.setRoomId(message.getRoomId());
            messageDTO.setUserId(message.getUserId());
            messageDTOS.add(messageDTO);
        });
        return messageDTOS;
    }

    private RoomDTO makeRoomDTO(RoomDTO roomDTO, List<UserDTO> userDTOS, List<MessageDTO> messageDTOS) {
        RoomDTO customDTO = new RoomDTO();
        customDTO.setId(roomDTO.getId());
        customDTO.setUserId(roomDTO.getUserId());
        customDTO.setName(roomDTO.getName());
        customDTO.setPrivateMassage(roomDTO.getPrivateMassage());
        customDTO.setUsers(userDTOS);
        customDTO.setMessages(messageDTOS);
        return customDTO;
    }
}
