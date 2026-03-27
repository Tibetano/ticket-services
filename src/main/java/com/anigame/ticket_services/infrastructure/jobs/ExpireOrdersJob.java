package com.anigame.ticket_services.infrastructure.jobs;

import com.anigame.ticket_services.usecase.order.ExpireOrdersUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpireOrdersJob {

    private final ExpireOrdersUseCase expireOrdersUseCase;
    private static final Logger log =
            LoggerFactory.getLogger(ExpireOrdersJob.class);

    public ExpireOrdersJob(ExpireOrdersUseCase expireOrdersUseCase) {
        this.expireOrdersUseCase = expireOrdersUseCase;
    }

    @Scheduled(fixedDelay = 60000)
    public void run() {
        log.info("Running expire orders job");
        expireOrdersUseCase.execute();
    }
}
