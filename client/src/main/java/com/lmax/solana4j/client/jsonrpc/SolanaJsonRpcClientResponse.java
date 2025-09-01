package com.lmax.solana4j.client.jsonrpc;

import com.lmax.solana4j.client.api.SolanaClientResponse;
import com.lmax.solana4j.client.api.SolanaRpcResponse;

import java.util.Optional;

final class SolanaJsonRpcClientResponse<T> implements SolanaClientResponse<T> {
    private final SolanaRpcResponse.Context context;
    private final T response;
    private final SolanaClientError error;

    private SolanaJsonRpcClientResponse(final SolanaRpcResponse.Context context,
                                        final T response,
                                        final SolanaClientError error) {
        this.context = context;
        this.response = response;
        this.error = error;
    }

    static <T> SolanaClientResponse<T> createSuccessResponse(final T response) {
        return createSuccessResponse(null, response);
    }

    static <T> SolanaClientResponse<T> createSuccessResponse(final SolanaRpcResponse.Context context,
                                                             final T response) {
        return new SolanaJsonRpcClientResponse<>(context, response, null);
    }

    static <T> SolanaClientResponse<T> creatErrorResponse(final SolanaClientError error) {
        return new SolanaJsonRpcClientResponse<>(null, null, error);
    }

    @Override
    public Optional<SolanaRpcResponse.Context> getContext() {
        return Optional.ofNullable(context);
    }

    @Override
    public T getResponse() {
        return response;
    }

    @Override
    public SolanaClientError getError() {
        return error;
    }

    @Override
    public boolean isSuccess() {
        return error == null;
    }

    @Override
    public String toString() {
        return "SolanaJsonRpcClientResponse{" +
                "response=" + response +
                ", error=" + error +
                '}';
    }
}
