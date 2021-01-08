package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.interfaces.WorkType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllWorkTypes",
                        query = "from WorkTypeImpl"
                )
        }
)
@Entity
@Table(name = "work_types")
public class WorkTypeImpl implements WorkType, Comparable {

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
    private String hourlyRate;

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
        return new BigDecimal(hourlyRate).stripTrailingZeros();
    }

    @Override
    public WorkType setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate.stripTrailingZeros().toPlainString();
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

    public static WorkType createWorkType(String name, String description, BigDecimal hourlyRate) {
        WorkType instance = new WorkTypeImpl();
        instance.setName(name);
        instance.setDescription(description);
        instance.setHourlyRate(hourlyRate);
        return instance;
    }

    public static WorkType createWorkType(String name, String description, String hourlyRate) {
        return createWorkType(name, description, new BigDecimal(hourlyRate));
    }

    @Override
    public String toString() {
        return "WorkTypeImpl{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", hourlyRate=" + new BigDecimal(hourlyRate).stripTrailingZeros() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkTypeImpl workType = (WorkTypeImpl) o;
        return Objects.equals(name, workType.name) &&
                Objects.equals(description, workType.description) &&
                (new BigDecimal(hourlyRate)).compareTo(new BigDecimal(workType.hourlyRate)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, new BigDecimal(hourlyRate).stripTrailingZeros());
    }

    @Override
    public int compareTo(Object o) {
        WorkTypeImpl t = (WorkTypeImpl) o;
        int i;
        if ((i = name.compareTo(t.name)) != 0) return i;
        if ((i = description.compareTo(t.description)) != 0) return i;
        return (new BigDecimal(hourlyRate)).compareTo(new BigDecimal(t.hourlyRate).stripTrailingZeros());
    }

    @Override
    public void validateAttributes() {
        validateName(name);
        validateDescription(description);
        validateHourlyRate(hourlyRate);
    }
}
