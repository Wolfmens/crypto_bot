package com.skillbox.cryptobot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchedulerDto {

    private Runnable task;

    private Duration duration;

}
