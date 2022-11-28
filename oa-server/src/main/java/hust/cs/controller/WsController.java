package hust.cs.controller;

import hust.cs.pojo.Admin;
import hust.cs.pojo.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * ClassName: WsController
 * PackageName:hust.cs.server.controller
 * Description:
 * date: 2022/3/29 22:36
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Controller
public class WsController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handMsg(Authentication authentication, ChatMsg chatMsg){
        Admin principal = (Admin) authentication.getPrincipal();
        chatMsg.setFrom(principal.getUsername());
        chatMsg.setFromNickName(principal.getName());
        chatMsg.setDate(LocalDateTime.now());
        simpMessagingTemplate
                .convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
    }
}
