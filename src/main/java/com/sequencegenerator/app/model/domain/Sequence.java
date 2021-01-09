package com.sequencegenerator.app.model.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "SEQUENCE")
public class Sequence {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SEQUENCE_TASK_ID")
    private SequenceTask sequenceTask;

    @Column(name = "GOAL")
    private int goal;

    @Column(name = "STEP")
    private int step;

    @Column(name = "SEQUENCE")
    private String sequence;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SequenceTask getSequenceTask() {
        return sequenceTask;
    }

    public void setSequenceTask(SequenceTask sequenceTask) {
        this.sequenceTask = sequenceTask;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Sequence() {}

    public Sequence(SequenceTask sequenceTask, int goal, int step, String sequence) {
        this.sequenceTask = sequenceTask;
        this.goal = goal;
        this.step = step;
        this.sequence = sequence;
    }
}
