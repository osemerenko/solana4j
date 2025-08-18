package com.lmax.solana4j.client.jsonrpc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetJsonParsedTransactionTest
{
    private final ObjectMapper mapper = JsonMapper
            .builder()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
            .build();

    @Test
    public void shouldHandleInstructionParsedMap() throws IOException
    {
        final ObjectReader objectReader = mapper.readerFor(new TypeReference<RpcWrapperDTO<TransactionResponseDTO>>()
        {
        });

        final RpcWrapperDTO<TransactionResponseDTO> response = objectReader.readValue(GetJsonParsedTransactionTest.class.getResource("/transactionWithParsedInstructionMap.json"));

        final Map<String, Object> instructionParsed =
                response
                        .getResult()
                        .getTransactionData()
                        .getParsedTransactionData()
                        .getMessage()
                        .getInstructions()
                        .get(0)
                        .getInstructionParsed();

        final Map<String, Object> info = new HashMap<>();
        info.put("destination", "sCR7NonpU3TrqvusEiA4MAwDMLfiY1gyVPqw2b36d8V");
        info.put("lamports", 1000000000);
        info.put("source", "osRAr6v5ujNNmDupwmcdFNV2ob2KB6AVVPidyw49Y9s");

        final Map<String, Object> parsed = new HashMap<>();
        parsed.put("info", info);
        parsed.put("type", "transfer");

        assertThat(instructionParsed).isEqualTo(parsed);
    }

    @Test
    public void shouldHandleInstructionParsedString() throws IOException
    {
        final ObjectReader objectReader = mapper.readerFor(new TypeReference<RpcWrapperDTO<TransactionResponseDTO>>()
        {
        });
        final RpcWrapperDTO<TransactionResponseDTO> response = objectReader.readValue(GetJsonParsedTransactionTest.class.getResource("/transactionWithParsedInstructionString.json"));

        final Map<String, Object> instructionParsed =
                response
                        .getResult()
                        .getTransactionData()
                        .getParsedTransactionData()
                        .getMessage()
                        .getInstructions()
                        .get(0)
                        .getInstructionParsed();

        final Map<String, Object> parsed = new HashMap<>();
        parsed.put("", "thisisjustastringitsnotamap");

        assertThat(instructionParsed).isEqualTo(parsed);
    }

    @Test
    public void shouldHandleNoInstructionParsed() throws IOException
    {
        final ObjectReader objectReader = mapper.readerFor(new TypeReference<RpcWrapperDTO<TransactionResponseDTO>>()
        {
        });
        final RpcWrapperDTO<TransactionResponseDTO> response = objectReader.readValue(GetJsonParsedTransactionTest.class.getResource("/transactionWithNoParsedInstruction.json"));

        final Map<String, Object> instructionParsed =
                response
                        .getResult()
                        .getTransactionData()
                        .getParsedTransactionData()
                        .getMessage()
                        .getInstructions()
                        .get(0)
                        .getInstructionParsed();

        assertThat(instructionParsed).isNull();
    }
}
