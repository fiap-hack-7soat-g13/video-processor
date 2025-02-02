package com.fiap.hackathon.video.app.adapter.output.queue;

import com.fiap.hackathon.video.core.domain.VideoStatus;

public interface VideoStatusChangedDispatcher {

    void dispatch(Long id, VideoStatus status);

}

