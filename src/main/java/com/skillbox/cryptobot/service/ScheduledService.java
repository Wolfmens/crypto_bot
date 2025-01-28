package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.dto.SchedulerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final TaskScheduler taskScheduler;

    public void actionBySchedule(SchedulerDto schedulerDto) {
        taskScheduler.scheduleAtFixedRate(
                schedulerDto.getTask(),
                schedulerDto.getDuration());
    }
}
