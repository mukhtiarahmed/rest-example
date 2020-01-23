package com.hackerrank.assignment.controller;


import com.hackerrank.assignment.dto.ResponseDTO;
import com.hackerrank.assignment.dto.UserDTO;
import com.hackerrank.assignment.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hackerrank.assignment.util.AssignmentHelper.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * The Authentication REST controller.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseDTO<String> authenticate(@RequestBody UserDTO user) {
        UserDTO userDTO = jwtTokenUtil.getUserDTOByUserName(user.getUserName());

        if (userDTO != null && userDTO.getPassword().equals(user.getPassword())) {

            String token = jwtTokenUtil.generateToken(userDTO);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMessage("Authenticate successfully");
            responseDTO.setData(token);
            responseDTO.setStatus(SUCCESS);
            return responseDTO;

        } else {
            throw new BadCredentialsException("Invalid username and/or password");
        }
    }
}
