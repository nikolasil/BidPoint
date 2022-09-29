package com.bidpoint.backend.chat.controller;

import com.bidpoint.backend.auth.exception.AuthorizationException;
import com.bidpoint.backend.auth.exception.TokenIsMissingException;
import com.bidpoint.backend.auth.service.AuthService;
import com.bidpoint.backend.chat.entity.Message;
import com.bidpoint.backend.chat.entity.MessageOutputDto;
import com.bidpoint.backend.chat.service.impl.MessageServiceImpl;
import com.bidpoint.backend.user.dto.UserOutputDto;
import com.bidpoint.backend.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final MessageServiceImpl messageService;
    private final AuthService authService;
    private final UserServiceImpl userService;
    private final ConversionService conversionService;

    @GetMapping("/users/all")
    public ResponseEntity<List<UserOutputDto>> getUsersAll(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authService.hasAuthorizationHeader(authorizationHeader))
            throw new TokenIsMissingException();
        try {
            String username = authService.decodeAuthorizationHeader(authorizationHeader).getSubject();
            List<UserOutputDto> users = userService.getUsers().stream().map(user->conversionService.convert(user, UserOutputDto.class)).filter(user-> !Objects.equals(user.getUsername(), username)).toList();
            return  ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }

    }

    @CrossOrigin
    @GetMapping("/{sender}")
    public ResponseEntity<List<MessageOutputDto>> getMyMessagesOfSender(@PathVariable String sender, HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authService.hasAuthorizationHeader(authorizationHeader))
            throw new TokenIsMissingException();
        try {
            String username = authService.decodeAuthorizationHeader(authorizationHeader).getSubject();
            List<Message> list0 = messageService.getMessagesReceiverAndSender(username, sender);
            List<Message> list1 = messageService.getMessagesReceiverAndSender(sender, username);
            list0.addAll(list1);
            Collections.sort(list0);
            return ResponseEntity.ok(list0.stream().map(message -> conversionService.convert(message, MessageOutputDto.class)).toList());
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }
    }

}
