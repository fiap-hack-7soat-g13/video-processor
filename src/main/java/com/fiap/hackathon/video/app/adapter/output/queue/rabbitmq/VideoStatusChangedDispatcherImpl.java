package com.fiap.hackathon.video.app.adapter.output.queue.rabbitmq;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoStatusChangedDispatcher;
import com.fiap.hackathon.video.app.adapter.output.queue.VideoStatusChangedEvent;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VideoStatusChangedDispatcherImpl implements VideoStatusChangedDispatcher {

    private final RabbitTemplate rabbitTemplate;
    private final String videoStatusChangedQueue;

    public VideoStatusChangedDispatcherImpl(RabbitTemplate rabbitTemplate, @Value("${application.queue.videoStatusChanged.name}") String videoStatusChangedQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.videoStatusChangedQueue = videoStatusChangedQueue;
    }

    @Override
    public void dispatch(Long id, VideoStatus status) {

        VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
                .id(id)
                .status(status)
                .build();

        rabbitTemplate.convertAndSend(videoStatusChangedQueue, event);
    }

}
