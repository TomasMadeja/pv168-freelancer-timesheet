package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.Work;
import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;
import org.hibernate.TypeMismatchException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "works")
public class WorkImpl implements Work {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "start_time", nullable=false)
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable=false)
    private ZonedDateTime endTime;

    @Column(name = "description", nullable=false)
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "work_type_id", referencedColumnName = "id", nullable=false)
    private WorkTypeImpl workType;

    public WorkImpl(ZonedDateTime startTime, ZonedDateTime endTime, WorkTypeImpl workType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workType = workType;
    }

    private static WorkTypeImpl testWorkType(WorkType workType) {
        if (!(workType instanceof WorkTypeImpl)) {
            throw new TypeMismatchException("Unknown WorkType");
        }
        return (WorkTypeImpl) workType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Work setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Work validateName(String name) {
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Work setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Work validateDescription(String description) {
        return this;
    }

    @Override
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    @Override
    public Work setStartTime(ZonedDateTime datetime) {
        this.startTime = datetime;
        return this;
    }

    @Override
    public Work validateStartTime(ZonedDateTime datetime) {
        return this;
    }

    @Override
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    @Override
    public Work setEndTime(ZonedDateTime datetime) {
        this.endTime = datetime;
        return this;
    }

    @Override
    public Work validateEndTime(ZonedDateTime datetime) {
        return this;
    }

    @Override
    public WorkType getWorkType() {
        return workType;
    }

    @Override
    public Work setWorkType(WorkType workType) {
        this.workType = testWorkType(workType);
        return this;
    }

    @Override
    public Work validateWorkType(WorkType workType) {
        return this;
    }

    public static Work createWork(ZonedDateTime startTime, ZonedDateTime endTime, WorkType workType) {
        return new WorkImpl(startTime, endTime, testWorkType(workType));
    }
}
