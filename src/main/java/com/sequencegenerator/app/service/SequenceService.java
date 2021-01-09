package com.sequencegenerator.app.service;

import com.sequencegenerator.app.exception.BadRequestException;
import com.sequencegenerator.app.exception.ResourceNotFoundException;
import com.sequencegenerator.app.model.domain.Sequence;
import com.sequencegenerator.app.model.domain.SequenceTask;
import com.sequencegenerator.app.model.domain.Status;
import com.sequencegenerator.app.repository.SequenceRepository;
import com.sequencegenerator.app.repository.SequenceTaskRepository;
import com.sequencegenerator.app.request.SequenceGenerationRequest;
import com.sequencegenerator.app.response.SequenceGenerationResponse;
import com.sequencegenerator.app.response.TaskSequenceResponse;
import com.sequencegenerator.app.response.TaskStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

@Service
public class SequenceService {

    private static final Logger logger = Logger.getLogger(SequenceService.class.getName());

    @Autowired
    SequenceRepository sequenceRepository;

    @Autowired
    SequenceTaskRepository sequenceTaskRepository;

    /**
     * Generate New Sequence
     * @param sequenceGenerationRequest
     */
    public SequenceGenerationResponse generateSequence(SequenceGenerationRequest sequenceGenerationRequest) {
        int goal = parseInt(sequenceGenerationRequest.getGoal());
        int step = parseInt(sequenceGenerationRequest.getStep());
        String generatedSequence = getSequence(goal, step);
        SequenceTask sequenceTask = new SequenceTask(Status.IN_PROGRESS, false);
        sequenceTaskRepository.save(sequenceTask);
        Sequence sequence = new Sequence(sequenceTask, goal, step, generatedSequence);
        sequenceRepository.save(sequence);
        sequenceTask.setStatus(Status.SUCCESS);
        sequenceTaskRepository.save(sequenceTask);
        return new SequenceGenerationResponse(sequenceTask.getId());
    }

    /**
     * Bulk Generate New Sequences
     * @param sequenceGenerationRequestList
     */
    public SequenceGenerationResponse bulkGenerateSequence(List<SequenceGenerationRequest> sequenceGenerationRequestList) {
        SequenceTask sequenceTask = new SequenceTask(Status.IN_PROGRESS, true);
        sequenceTaskRepository.save(sequenceTask);
        sequenceGenerationRequestList.stream().parallel().forEach(sequenceGenerationRequest -> {
            int goal = parseInt(sequenceGenerationRequest.getGoal());
            int step = parseInt(sequenceGenerationRequest.getStep());
            String generatedSequence = getSequence(goal, step);
            Sequence sequence = new Sequence(
                    sequenceTask,
                    goal,
                    step,
                    generatedSequence
            );
            sequenceRepository.save(sequence);
        });
        sequenceTask.setStatus(Status.SUCCESS);
        sequenceTaskRepository.save(sequenceTask);
        return new SequenceGenerationResponse(sequenceTask.getId());
    }

    /**
     * Get task Status
     * @param taskId
     */
    public TaskStatusResponse getTaskStatus(UUID taskId) {
        Optional<SequenceTask> sequenceTask = sequenceTaskRepository.findById(taskId);
        if (sequenceTask.isEmpty()) {
            throw new ResourceNotFoundException("SequenceTask", "taskId", taskId);
        }
        return new TaskStatusResponse(sequenceTask.get().getStatus().toString());
    }

    /**
     * Get task sequence for single and bulk sequences
     * @param taskId, action
     */
    public TaskSequenceResponse getTaskSequenceResponse(UUID taskId, String action) {

        if (!action.equals("get_numlist")) {
            throw new BadRequestException("Invalid Action provided");
        }

        Optional<SequenceTask> sequenceTask = sequenceTaskRepository.findById(taskId);
        if (sequenceTask.isEmpty()) {
            throw new ResourceNotFoundException("SequenceTask", "taskId", taskId);
        }
        if (!sequenceTask.get().getIsBulk()) {
            List<Sequence> sequenceList = sequenceTask.get().getSequenceList();
            Sequence sequence = sequenceList.iterator().next();
            return new TaskSequenceResponse(sequence.getSequence());
        } else {
            List<Sequence> sequenceList = sequenceTask.get().getSequenceList();
            List<String> sequences = new ArrayList<>();
            for (Sequence sequence: sequenceList) {
                sequences.add(sequence.getSequence());
            }
            return new TaskSequenceResponse(sequences);
        }

    }

    /**
     * Get sequence string by converting list to string and removing outer square brackets
     * @param goal, step
     */
    private String getSequence(int goal, int step) {
        return removeSquareBrackets(IntStream.iterate(goal, i -> i >= 0, i -> i - step).boxed().collect(Collectors.toList()).toString());
    }

    /**
     * Remove outer square brackets from sequence string
     * @param sequenceString
     */
    private String removeSquareBrackets(String sequenceString) {
        return sequenceString.substring(1, sequenceString.length() - 1);
    }

}
