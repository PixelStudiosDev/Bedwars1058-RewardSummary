package me.leoo.bedwars.rewardsummary.reward;

import lombok.Data;

import java.util.UUID;

@Data
public class RewardPlayer {

    private final UUID uuid;

    private final int startMoney;
    private int endMoney;

    private final int startExp;
    private int endExp;

    private int levelUpExp;


}
