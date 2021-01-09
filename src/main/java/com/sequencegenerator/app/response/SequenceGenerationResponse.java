package com.sequencegenerator.app.response;

import java.util.UUID;

public class SequenceGenerationResponse {

    private UUID task;

    public UUID getTask() {
        return task;
    }

    public void setTask(UUID task) {
        this.task = task;
    }

    public SequenceGenerationResponse(UUID task) {
        this.task = task;
    }
}
