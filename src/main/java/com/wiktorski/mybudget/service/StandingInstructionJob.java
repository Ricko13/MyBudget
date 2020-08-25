package com.wiktorski.mybudget.service;

import com.querydsl.core.BooleanBuilder;
import com.wiktorski.mybudget.model.entity.QStandingInstructionEntity;
import com.wiktorski.mybudget.model.entity.StandingInstructionEntity;
import com.wiktorski.mybudget.repository.StandingInstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StandingInstructionJob {

    @Autowired
    private StandingInstructionService service;

    @Autowired
    private StandingInstructionRepository repository;

    @Scheduled(fixedRate = 10000)
    public void processStandingInstructions() {
        System.err.println("****************************** SCHEDULED TASK ******************************");
        QStandingInstructionEntity qStanding = QStandingInstructionEntity.standingInstructionEntity;
        BooleanBuilder where = new BooleanBuilder();
        where.and(qStanding.latestExecution.isNull()
                .or(qStanding.latestExecution.before(LocalDate.now().withDayOfMonth(1))));
        List<StandingInstructionEntity> list = repository.findAll(where);
        System.err.println("Processed SI amount: " + list.size());
        list.forEach(service::processStandingInstruction);
    }

}
