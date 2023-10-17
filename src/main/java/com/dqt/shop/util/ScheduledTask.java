package com.dqt.shop.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 60000) // Lập lịch thực hiện mỗi phút
    public void printScheduledMessage() {
        System.out.println("This message is printed every minute: " + LocalDateTime.now());


    }
}
