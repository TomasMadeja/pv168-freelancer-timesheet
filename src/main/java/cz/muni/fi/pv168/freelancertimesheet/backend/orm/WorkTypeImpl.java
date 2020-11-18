package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "work_types")
public class WorkTypeImpl implements WorkType {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "description", nullable=false)
    private String description;

    @Column(name = "hourly_rate", nullable=false)
    private BigDecimal hourlyRate;

    @OneToMany(mappedBy="workType")
    private Collection<WorkImpl> works;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public WorkType setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public WorkType validateName(String name) {
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public WorkType setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public WorkType validateDescription(String description) {
        return this;
    }

    @Override
    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public WorkType setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    @Override
    public WorkType setHourlyRate(String hourlyRate) {
        setHourlyRate(new BigDecimal(hourlyRate));
        return null;
    }

    @Override
    public WorkType validateHourlyRate(BigDecimal hourlyRate) {
        return this;
    }

    @Override
    public WorkType validateHourlyRate(String hourlyRate) {
        return this;
    }

    public static WorkType createWorkType(String description, BigDecimal hourlyRate) {
        WorkType instance = new WorkTypeImpl();
        instance.setDescription(description);
        instance.setHourlyRate(hourlyRate);
        return instance;
    }

    public static WorkType createWorkType(String description, String hourlyRate) {
        return createWorkType(description, new BigDecimal(hourlyRate));
    }

    @Override
    public String toString() {
        return "WorkTypeImpl{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}
