package com.simbir_soft.config;

import com.simbir_soft.dto.MessageDTO;
import com.simbir_soft.dto.RoomDTO;
import com.simbir_soft.dto.UserDTO;
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
                .register();

        factory.classMap(Room.class, RoomDTO.class)
                .field("user.id", "userId")
                .byDefault()
                .register();

        factory.classMap(Message.class, MessageDTO.class)
                .field("room.id", "roomId")
                .byDefault()
                .register();
    }
}
