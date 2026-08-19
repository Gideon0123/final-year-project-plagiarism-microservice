package com.example.PLAGIARISM_SERVICE.utils;

public final class RabbitMQConstants {

    private RabbitMQConstants(){}

    public static final String PLAGIARISM_EXCHANGE = "plagiarism.exchange";

    public static final String PLAGIARISM_CHECK_QUEUE = "plagiarism.check.queue";

    public static final String PLAGIARISM_CHECK_ROUTING_KEY = "plagiarism.check";
}