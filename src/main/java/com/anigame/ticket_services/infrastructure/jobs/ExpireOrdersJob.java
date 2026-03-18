package com.anigame.ticket_services.infrastructure.jobs;

import com.anigame.ticket_services.usecase.ExpireOrdersUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpireOrdersJob {

    private final ExpireOrdersUseCase expireOrdersUseCase;

    public ExpireOrdersJob(ExpireOrdersUseCase expireOrdersUseCase) {
        this.expireOrdersUseCase = expireOrdersUseCase;
    }

    @Scheduled(fixedDelay = 60000)
    public void run() {
        System.out.println("Chamou !");
        expireOrdersUseCase.execute();
    }
}
