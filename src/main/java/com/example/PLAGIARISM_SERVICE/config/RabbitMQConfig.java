package com.example.PLAGIARISM_SERVICE.config;

import com.example.PLAGIARISM_SERVICE.utils.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange plagiarismExchange() {

        return new TopicExchange(
                RabbitMQConstants.PLAGIARISM_EXCHANGE
        );
    }

    @Bean
    public TopicExchange plagiarismDeadLetterExchange() {

        return new TopicExchange(
                "plagiarism.dlx"
        );
    }

    @Bean
    public Queue plagiarismQueue() {
        Map<String, Object> args = new HashMap<>();

        args.put("x-dead-letter-exchange", "plagiarism.dlx");

        args.put(
                "x-dead-letter-routing-key",
                RabbitMQConstants.PLAGIARISM_DLQ_ROUTING_KEY
        );

        return new Queue(
                RabbitMQConstants.PLAGIARISM_QUEUE, true, false, false, args
        );
    }

    @Bean
    public Queue plagiarismDeadLetterQueue() {

        return new Queue(
                RabbitMQConstants.PLAGIARISM_DLQ,
                true
        );
    }

    @Bean
    public Binding plagiarismBinding() {

        return BindingBuilder
                .bind(plagiarismQueue())
                .to(plagiarismExchange())
                .with(RabbitMQConstants.PLAGIARISM_ROUTING_KEY);
    }

    @Bean
    public Binding plagiarismDeadLetterBinding() {

        return BindingBuilder
                .bind(plagiarismDeadLetterQueue())
                .to(plagiarismDeadLetterExchange())
                .with(
                        RabbitMQConstants.PLAGIARISM_DLQ_ROUTING_KEY
                );
    }
}
