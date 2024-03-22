package com.example.pedidosfnlambda.function;

import com.example.pedidosfnlambda.service.PedidoService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class PedidosFunc {

    private final PedidoService service;

    private final StreamBridge streamBridge;


    public PedidosFunc(PedidoService service,
                       StreamBridge streamBridge) {
        this.service = Objects.requireNonNull(service);
        this.streamBridge = Objects.requireNonNull(streamBridge);
    }

    @Bean
    public Supplier<List<PedidoService.PedidoRec>> findAll() {
        return service::findAll;
    }

    @Bean
    public Consumer<String> create() {
        return (service::create);
    }

    @Bean
    public Function<String, String> toUpper() {
        return (str) -> {
            System.out.println("Original: " + str);
            Message<String> message = MessageBuilder
                    .withPayload(str.toUpperCase())
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build();
            streamBridge.send("consumeUpper-in-0", message);
            return str.toUpperCase();
        };
    }

    @Bean
    public Consumer<String> consumeUpper() {
        return (upper) -> {
            System.out.println("Consumed: " + upper);
        };
    }


}
