package com.sequencegenerator.app.controller;

import com.sequencegenerator.app.constant.Constants;
import com.sequencegenerator.app.request.SequenceGenerationRequest;
import com.sequencegenerator.app.response.SequenceGenerationResponse;
import com.sequencegenerator.app.response.TaskSequenceResponse;
import com.sequencegenerator.app.response.TaskStatusResponse;
import com.sequencegenerator.app.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/api")
public class SequenceController extends BaseController {

    private static final Logger logger = Logger.getLogger(SequenceController.class.getName());

    @Autowired
    SequenceService sequenceService;

    /**
     * Generate New Sequence
     * @param sequenceGenerationRequest
     */
    @PostMapping("/generate")
    public ResponseEntity<SequenceGenerationResponse> generateSequence(@RequestBody @Valid SequenceGenerationRequest sequenceGenerationRequest) {
        logger.log(Level.INFO, "Got generate sequence request for: {0}", sequenceGenerationRequest.toString());
        return new ResponseEntity<>(sequenceService.generateSequence(sequenceGenerationRequest), HttpStatus.ACCEPTED);
    }

    /**
     * Bulk Generate New Sequences
     * @param bulkSequenceGenerationRequest
     */
    @PostMapping("bulkGenerate")
    public ResponseEntity<SequenceGenerationResponse> generateBulkSequence(
            @RequestBody
            @NotEmpty(message = Constants.ERROR_NOT_EMPTY)
            List<@Valid SequenceGenerationRequest> bulkSequenceGenerationRequest
    ) {
        logger.log(Level.INFO, "Got bulk generate request for: {0}", bulkSequenceGenerationRequest);
        return new ResponseEntity<>(sequenceService.bulkGenerateSequence(bulkSequenceGenerationRequest), HttpStatus.ACCEPTED);
    }

    /**
     * Get task Status
     * @param taskId
     */
    @GetMapping("/tasks/{taskId}/status")
    public TaskStatusResponse getTaskStatus(@PathVariable UUID taskId) {
        logger.log(Level.INFO, "Get Task Status Request for taskId: {0}", taskId);
        return sequenceService.getTaskStatus(taskId);
    }

    /**
     * Get task sequence for single and bulk sequences
     * @param taskId, action
     */
    @GetMapping("/tasks/{taskId}")
    public TaskSequenceResponse getTaskSequence(@PathVariable UUID taskId, @RequestParam String action) {
        logger.log(Level.INFO, String.format("Get Task Sequence Request for taskId: {0} for action: {1}", taskId, action));
        return sequenceService.getTaskSequenceResponse(taskId, action);
    }


}
