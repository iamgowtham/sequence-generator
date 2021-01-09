package com.sequencegenerator.app.model.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SEQUENCE_TASK")
public class SequenceTask {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "IS_BULK")
    private Boolean isBulk;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sequenceTask")
    private List<Sequence> sequenceList;

    public SequenceTask(Status status, Boolean isBulk) {
        this.status = status;
        this.isBulk = isBulk;
    }

    public SequenceTask() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Sequence> getSequenceList() {
        return sequenceList;
    }

    public void setSequenceList(List<Sequence> sequenceList) {
        this.sequenceList = sequenceList;
    }

    public Boolean getIsBulk() {
        return isBulk;
    }

    public void setIsBulk(Boolean bulk) {
        isBulk = bulk;
    }
}
