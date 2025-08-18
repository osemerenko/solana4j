package com.lmax.solana4j.client.jsonrpc;

import com.lmax.solana4j.client.api.ClusterNode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

// https://solana.com/docs/rpc/http/getclusternodes
public class GetClusterNodesContractTest extends SolanaClientIntegrationTestBase
{
    @Test
    void shouldGetListOfNodes() throws SolanaJsonRpcClientException
    {
        assertEquals(1, SOLANA_API.getClusterNodes().getResponse().size());
    }

    @Test
    void shouldDecodeImportantFields() throws SolanaJsonRpcClientException
    {
        final ClusterNode clusterNode = SOLANA_API.getClusterNodes().getResponse().get(0);
        assertEquals("127.0.0.1:8899", clusterNode.getRpcAddress());
        assertEquals("127.0.0.1:8001", clusterNode.getGossipAddress());
        assertThat(clusterNode.getFeatureSet()).isGreaterThan(0L);

        // Unless we write a stub, we can't really hardcode the value here
        // A regular expression to check it's at least a valid semver will have to do
        assertThat(clusterNode.getVersion()).matches("\\d+\\.\\d+\\.\\d+");
    }
}
