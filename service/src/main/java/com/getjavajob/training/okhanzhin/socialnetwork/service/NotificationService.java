package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RelationRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Value("${activemq.destination}")
    private String destination;

    private final AccountRepository accountRepository;
    private final RelationRepository relationRepository;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public NotificationService(AccountRepository accountRepository,
                               RelationRepository relationRepository,
                               JmsTemplate jmsTemplate) {
        this.accountRepository = accountRepository;
        this.relationRepository = relationRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(cron = "*/15 * * * * *", zone = "Europe/Moscow")
    private void sendBirthdayNotifications() {
        List<Account> todayBirthdayAccounts = accountRepository.getTodayBirthdayAccounts();

        for (Account account : todayBirthdayAccounts) {
            List<Account> recipients = relationRepository.getFriendsList(account);
            List<String> recipientMails = new ArrayList<>();
            for (Account recipient : recipients) {
                recipientMails.add(recipient.getEmail());
            }
            String content = "Today is " + account.getSurname() + " " + account.getName() +
                    " birthday, don't forget to congratulate him and give him a gift.";
            Notification notification = new Notification(content, recipientMails);
            sendNotification(destination, notification);
        }
    }

    private void sendNotification(String destination, Notification notification) {
        logger.info("Social Network > sending");
        try {
            jmsTemplate.convertAndSend(destination, notification);
        } catch (JmsException e) {
            logger.warn("Unable to enqueue an event to ActiveMQ.");
        }
    }
}