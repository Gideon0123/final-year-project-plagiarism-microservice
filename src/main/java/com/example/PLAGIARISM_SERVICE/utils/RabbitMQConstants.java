package com.example.PLAGIARISM_SERVICE.utils;

public final class RabbitMQConstants {

    private RabbitMQConstants(){}

    public static final String PLAGIARISM_EXCHANGE = "plagiarism.exchange";

    public static final String PLAGIARISM_ROUTING_KEY = "plagiarism.check.completed";

    public static final String PLAGIARISM_QUEUE = "plagiarism.notification.queue";

    public static final String PLAGIARISM_DLQ = "plagiarism.notification.dlq";

    public static final String PLAGIARISM_DLQ_ROUTING_KEY = "plagiarism.check.failed";
}