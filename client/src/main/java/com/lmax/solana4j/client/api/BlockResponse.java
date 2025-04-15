package com.lmax.solana4j.client.api;

import java.util.List;

public interface BlockResponse
{
    long getBlockHeight();

    Long getBlockTime();

    String getBlockhash();

    long getParentSlot();

    String getPreviousBlockhash();

    List<Rewards> getRewards();

    List<TransactionResponse> getTransactions();

    interface Rewards
    {
        Long getCommission();
        long getLamports();
        long getPostBalance();
        String getPubkeyBase58();
        String getRewardType();
    }
}
