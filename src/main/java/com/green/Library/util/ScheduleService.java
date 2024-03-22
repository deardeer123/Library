package com.green.Library.util;

import com.green.Library.web.board.service.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@EnableScheduling
@Component
public class ScheduleService {
    @Autowired
    private BoardServiceImpl boardService;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void run(){
        boardService.updateStatus();

    }


}
