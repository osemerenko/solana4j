package com.lmax.solana4j.client.jsonrpc;

import com.lmax.solana4j.client.api.BlockResponse;
import com.lmax.solana4j.client.api.SolanaClientResponse;
import com.lmax.solana4j.client.api.TransactionResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// https://solana.com/docs/rpc/http/getblock
final class GetBlockContractTest extends SolanaClientIntegrationTestBase
{
    @Test
    void shouldGetBlockDefaultOptionalParams() throws SolanaJsonRpcClientException
    {
        final SolanaClientResponse<Long> slotResponse = SOLANA_API.getSlot();
        assertThat(slotResponse.isSuccess()).isTrue();

        SolanaClientResponse<BlockResponse> blockResponse = SOLANA_API.getBlock(slotResponse.getResponse());
        // It is possible that a block was not generated for the current slot, so we try a few previous slots
        for(int i = 1; !blockResponse.isSuccess() && i < 10; i++)
        {
            blockResponse = SOLANA_API.getBlock(slotResponse.getResponse() - i);
        }
        assertThat(blockResponse.isSuccess()).isTrue();
        final BlockResponse response = blockResponse.getResponse();

        System.out.println(response);

        assertThat(response.getBlockHeight()).isGreaterThan(0);
        assertThat(response.getBlockhash().length()).isBetween(43, 44);
        assertThat(response.getBlockTime()).isGreaterThan(0);
        assertThat(response.getParentSlot()).isGreaterThan(0);
        assertThat(response.getPreviousBlockhash().length()).isBetween(43, 44);

        final List<BlockResponse.Rewards> rewards = response.getRewards();
        assertThat(rewards.size()).isEqualTo(1);
        assertThat(rewards.get(0).getCommission()).isNull();
        assertThat(rewards.get(0).getLamports()).isGreaterThan(0);
        assertThat(rewards.get(0).getPostBalance()).isGreaterThan(0);
        assertThat(rewards.get(0).getPubkeyBase58().length()).isBetween(43, 44);
        assertThat(rewards.get(0).getRewardType()).isEqualTo("Fee");

        final List<TransactionResponse> transactions = response.getTransactions();
        assertThat(transactions.size()).isEqualTo(1);

        final TransactionResponse.TransactionMetadata metadata = transactions.get(0).getMetadata();
        assertThat(metadata.getErr()).isNull();
        assertThat(metadata.getFee()).isGreaterThan(0);
        assertThat(metadata.getInnerInstructions()).isEmpty();
        assertThat(metadata.getLogMessages()).hasSize(2);
        assertThat(metadata.getPreBalances()).hasSize(3);
        assertThat(metadata.getPostBalances()).hasSize(3);
        assertThat(metadata.getPreTokenBalances()).isEmpty();
        assertThat(metadata.getPostTokenBalances()).isEmpty();
        assertThat(metadata.getRewards()).isEmpty();
        assertThat(metadata.getComputeUnitsConsumed()).isGreaterThan(0);
        assertThat(metadata.getLoadedAddresses().getReadonly()).isEmpty();
        assertThat(metadata.getLoadedAddresses().getWritable()).isEmpty();
        assertThat(metadata.getStatus().getKey()).isEqualTo("Ok");
    }

    @Test
    void shouldReturnErrorForNonExistentSlot() throws SolanaJsonRpcClientException
    {
        final SolanaClientResponse<Long> slotResponse = SOLANA_API.getSlot();
        assertThat(slotResponse.isSuccess()).isTrue();

        SolanaClientResponse<BlockResponse> blockResponse = SOLANA_API.getBlock(slotResponse.getResponse() + 5);
        assertThat(blockResponse.isSuccess()).isFalse();
    }
}
