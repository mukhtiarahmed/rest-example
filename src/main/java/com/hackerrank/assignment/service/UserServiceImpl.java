package com.hackerrank.assignment.service;

import com.hackerrank.assignment.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, UserDTO> userDTOMap = new HashMap<>();


    @PostConstruct
    public void init() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setUserName("ahmed");
        userDTO.setPassword("password");
        userDTOMap.put(userDTO.getUserName(), userDTO);
        userDTO = new UserDTO();
        userDTO.setRole("ROLE_USER");
        userDTO.setUserName("mukhtiar");
        userDTO.setPassword("password");
        userDTOMap.put(userDTO.getUserName(), userDTO);
        userDTO = new UserDTO();
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setUserName("admin");
        userDTO.setPassword("password");
        userDTOMap.put(userDTO.getUserName(), userDTO);

    }

    public UserDTO getUserDTOByUserName(String userName) {
        return userDTOMap.get(userName);
    }
}
