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

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "work_type_id", referencedColumnName = "id")
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
