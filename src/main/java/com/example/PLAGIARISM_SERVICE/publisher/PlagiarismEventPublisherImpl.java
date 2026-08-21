package com.example.PLAGIARISM_SERVICE.publisher;

import com.example.PLAGIARISM_SERVICE.dto.events.PlagiarismCheckCompletedEvent;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.utils.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlagiarismEventPublisherImpl implements PlagiarismEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishCompleted(
            PlagiarismCheck check
    ) {
        PlagiarismCheckCompletedEvent event =
                PlagiarismCheckCompletedEvent.builder()
                        .checkId(check.getId())
                        .paperId(check.getPaperId())
                        .authorId(check.getAuthorId())
                        .similarityPercentage(check.getSimilarityPercentage())
                        .result(check.getResult().name())
                        .summary(check.getSummary())
                        .completedAt(check.getCompletedAt())
                        .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.PLAGIARISM_EXCHANGE,
                RabbitMQConstants.PLAGIARISM_ROUTING_KEY,
                event
        );

        log.info(
                "Published plagiarism event checkId={}",
                check.getId()
        );
    }
}
