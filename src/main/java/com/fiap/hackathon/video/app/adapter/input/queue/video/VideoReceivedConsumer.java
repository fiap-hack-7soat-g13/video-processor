package com.fiap.hackathon.video.app.adapter.input.queue.video;

import com.fiap.hackathon.video.app.adapter.input.queue.video.dto.VideoReceivedEvent;
import com.fiap.hackathon.video.core.usecase.VideoProcessUseCase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VideoReceivedConsumer {

    private final VideoProcessUseCase videoProcessUseCase;

    @RabbitListener(queues = "${application.queue.videoReceived.name}", concurrency = "${application.queue.videoReceived.consumers}")
    public void consume(VideoReceivedEvent event) {
        videoProcessUseCase.execute(event.getId());
    }

}
