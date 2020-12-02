package cz.muni.fi.pv168.freelancertimesheet.backend.orm;

import cz.muni.fi.pv168.freelancertimesheet.backend.Constants;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.StringUtils;

@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllWorks",
                        query = "from WorkImpl"
                )
        }
)
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

    public WorkImpl(String name, String description, ZonedDateTime startTime, ZonedDateTime endTime, WorkTypeImpl workType) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workType = workType;
    }

    public WorkImpl() {

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

    public static Work createWork(String name, String description, ZonedDateTime startTime, ZonedDateTime endTime, WorkType workType) {
        return new WorkImpl(name, description, startTime, endTime, testWorkType(workType));
    }

    @Override
    public int compareTo(Object o) {
        WorkImpl t = (WorkImpl) o;
        return name.compareTo(t.name) +
                description.compareTo(t.description) +
                startTime.compareTo(t.startTime) +
                endTime.compareTo(t.endTime) +
                workType.compareTo(t.workType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkImpl work = (WorkImpl) o;
        return name.equals(work.name) &&
                startTime.isEqual(work.startTime) &&
                endTime.isEqual(work.endTime) &&
                description.equals(work.description) &&
                workType.equals(work.workType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startTime, endTime, description, workType);
    }

    public BigDecimal getHours() {
        BigDecimal hours = new BigDecimal(ChronoUnit.HOURS.between(startTime, endTime));
        if (ChronoUnit.MINUTES.between(startTime, endTime) % 60 != 0) {
            hours = hours.add(new BigDecimal("1"));
        }
        return hours;
    }

    public BigDecimal getCost() {
        return getHours().multiply(this.workType.getHourlyRate());
    }

    public String toXML()
    {
        String xml = String.format(
                "            <tr>\n" +
                "                <th class=\"desc\"><h3>%s</h3>%s</th>\n" +
                "                <th class=\"qty\">%s</th>\n" +
                "                <th class=\"unit\">%s</th>\n" +
                "                <th class=\"total\">%s %s</th>\n" +
                "            </tr>\n",
                StringUtils.capitalize(name),
                StringUtils.capitalize(description),
                workType.getHourlyRate().toString(),
                getHours().toString(),
                getCost().toString(),
                Constants.getCurrency()
        );
        return xml;
    }


    @Override
    public String toString() {
        return "WorkImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", workType=" + workType +
                '}';
    }

    @Override
    public void validateAttributes() {
        validateName(name);
        validateDescription(description);
        validateWorkType(workType);
    }
}
