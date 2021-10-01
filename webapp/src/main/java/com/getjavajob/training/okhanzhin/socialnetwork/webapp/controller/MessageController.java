package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.MessageTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping(value = "/account")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final ChatMessageService chatMessageService;
    private final AccountService accountService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(ChatMessageService chatMessageService,
                             AccountService accountService,
                             SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.accountService = accountService;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping(value = "/messenger", method = RequestMethod.GET)
    public ModelAndView getProfileChats(@SessionAttribute("homeAccount") Account homeAccount, HttpSession session) {
        if (session.getAttribute("chatRoom") != null) {
            session.removeAttribute("chatRoom");
        }

        ModelAndView modelAndView = new ModelAndView("homeProfile-messenger");
        modelAndView.addObject("chatList", chatMessageService.getChatList(homeAccount));

        return modelAndView;
    }

    @RequestMapping(value = "/{accountId}/dialogue", method = RequestMethod.GET)
    public ModelAndView openDialogue(@PathVariable("accountId") long interlocutorID,
                                     @SessionAttribute("homeAccount") Account homeAccount, HttpSession session) {
        Account interlocutor = accountService.getByIdFetchPhones(interlocutorID);
        ChatRoom chatRoom;

        if (!chatMessageService.isChatRoomExist(homeAccount, interlocutor)) {
            logger.info("Creating ChatRoom");
            chatRoom = chatMessageService.createChatRoom(homeAccount, interlocutor);
        } else {
            logger.info("Getting ChatRoom");
            chatRoom = chatMessageService.getChatRoom(homeAccount, interlocutor);
        }

        ModelAndView modelAndView = new ModelAndView("homeProfile-chat");
        modelAndView.addObject("chat", chatMessageService.getChatDialogue(chatRoom, homeAccount));

        session.setAttribute("chatRoom", chatRoom);

        return modelAndView;
    }

    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageTransfer msgTransfer, SimpMessageHeaderAccessor headerAccessor) {
        ChatRoom chatRoom = (ChatRoom) requireNonNull(headerAccessor.getSessionAttributes()).get("chatRoom");
        Account source = (Account) requireNonNull(headerAccessor.getSessionAttributes()).get("source");
        Account interlocutor = accountService.getById(msgTransfer.getTargetID());

        chatMessageService.sendMessage(chatRoom, source, interlocutor, msgTransfer);

        logger.info(chatRoom.getChatRoomID() + "/interlocutor/" + interlocutor.getAccountID());
        messagingTemplate.convertAndSendToUser(chatRoom.getChatRoomID().toString(), "/interlocutor/" +
                interlocutor.getAccountID().toString(), msgTransfer);
    }

    @RequestMapping(value = "/deleteDialog", method = RequestMethod.GET)
    public String deleteChat(@RequestParam(value = "chatRoomId") String chatRoomID) {
        chatMessageService.deleteChatRoom(Long.parseLong(chatRoomID));

        return "redirect:/account/messenger";
    }
}
