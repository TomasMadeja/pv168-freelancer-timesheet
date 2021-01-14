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
public class WorkTypeImpl implements WorkType{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "description", nullable=false)
    private String description;

    @Column(name = "hourly_rate", nullable=false)
    private String hourlyRate;

    @OneToMany(mappedBy="workType")
    private Collection<WorkImpl> works;

    @Override
    public Integer getId() {
        return id;
    }

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

    public static WorkTypeImpl createWorkType(String name, String description, BigDecimal hourlyRate) {
        WorkTypeImpl instance = new WorkTypeImpl();
        instance.setName(name);
        instance.setDescription(description);
        instance.setHourlyRate(hourlyRate);
        return instance;
    }

    public static WorkTypeImpl createWorkType(String name, String description, String hourlyRate) {
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
        return Objects.equals(id, workType.id) &&
                Objects.equals(name, workType.name) &&
                Objects.equals(description, workType.description) &&
                (new BigDecimal(hourlyRate)).compareTo(new BigDecimal(workType.hourlyRate)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, new BigDecimal(hourlyRate).stripTrailingZeros());
    }

    public int compareTo(WorkType o) {
        int i;
        Integer otherId = o.getId();
        if (id != null && otherId != null && (i = Integer.compare(id, o.getId())) != 0) {
            return i;
        } else if (id == null && otherId != null) {
            return -1;
        } else if (otherId == null && id != null){
            return 1;
        }
        if ((i = name.compareTo(o.getName())) != 0) return i;
        if ((i = description.compareTo(o.getDescription())) != 0) return i;
        return (new BigDecimal(hourlyRate)).compareTo(o.getHourlyRate().stripTrailingZeros());
    }

    @Override
    public void validateAttributes() {
        validateName(name);
        validateDescription(description);
        validateHourlyRate(hourlyRate);
    }
}
