package kr.co.ddamddam.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.ddamddam.chat.dto.request.ChatValidateRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@ServerEndpoint("/socket/chat/{roomId}")
@Slf4j
public class WebSocketChatService {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("open session: {}, clients={}", session.getId(), clients);

        if (!clients.contains(session)) {
            clients.add(session);
            log.info("session open: {}", session.getId());
        } else {
            log.info("이미 연결된 session");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("receive message: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        ChatValidateRequestDTO dto = objectMapper.readValue(message, ChatValidateRequestDTO.class);
        log.info("hahaha: {} ", dto.getMentorIdx());
        // 필드 값 추출 예시
        Long mentorIdx = dto.getMentorIdx();
        Long senderId = dto.getSenderId();
        String name = dto.getName();
        String msg = dto.getMsg();
        String date = dto.getDate();

        // 추출한 필드 값 사용 예시
        log.info("roomId: {}", dto.getRoomId());
        log.info("mentorIdx: {}", mentorIdx);
        log.info("senderId: {}", senderId);
        log.info("name: {}", name);
        log.info("msg: {}", msg);
        log.info("date: {}", date);

        for (Session s : clients) {
            log.info(s.getId());
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("session close: {}, reason: {}", session.getId(), reason.getReasonPhrase());
        clients.remove(session);
    }
}
