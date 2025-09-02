package com.lmax.solana4j.client.jsonrpc;

import com.lmax.solana4j.client.api.AccountInfo;
import com.lmax.solana4j.client.api.SolanaApi;
import com.lmax.solana4j.client.api.SolanaClientResponse;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetMultipleAccountsContractTest {
    private static final String SOLANA_RPC_URL = "https://mainnet.helius-rpc.com/?api-key=49d92c95-1757-4eb6-9266-feefad7a9016";

    private final SolanaApi SOLANA_API = new SolanaJsonRpcClient(HttpClient.newHttpClient(), SOLANA_RPC_URL, true);

    @Test
    void shouldGetMultipleAccountsDefaultOptionalParams() throws SolanaJsonRpcClientException {
        final List<String> addresses = List.of("E2BcoCeJLTa27mAXDA4xwEq3pBUcyH6XXEHYk4KvKYTv",
                "HfERMT5DRA6C1TAqecrJQFpmkf3wsWTMncqnj3RDg5aw");

        final SolanaClientResponse<List<AccountInfo>> response = SOLANA_API.getMultipleAccounts(addresses);
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getContext()).isPresent();
        assertThat(response.getResponse().size()).isEqualTo(2);
    }
}
