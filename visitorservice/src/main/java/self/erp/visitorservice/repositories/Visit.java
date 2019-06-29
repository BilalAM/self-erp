package self.erp.visitorservice.repositories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Schema_Visitors")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VISIT_ID")
    private int visitId;

    @Column(name = "VISIT_VISITOR")
    @NotBlank(message = "Visitor Name Cannot Be Blank")
    private String visitorName;

    @Column(name = "FROM_STAMP") @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime fromDate;

        @Column(name = "TO_STAMP") @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

        @Column(name = "VISIT_PURPOSE")
    @NotBlank(message = "Visiting Purpose Cannot Be Blank!")
    private String visitPurpose;

        @Column(name = "VISIT_PURPOSE_DESCRIPTION")
    @NotBlank(message = "Visiting purpose description Cannot Be Blank")
    private String visitPurposeDescription;

        @Column(name = "VISIT_PURPOSE_STATUS_TYPE")
    @NotBlank(message = "The Visiting Purpose Status Type Cannot Be Blank")
    private String visitPurposeStatusType;


    public Visit(String visitorName, LocalDateTime fromDate, LocalDateTime endDate, String visitPurpose,
            String visitPurposeDescription, String visitPurposeStatusType) {
        this.visitorName = visitorName;
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.visitPurpose = visitPurpose;
        this.visitPurposeDescription = visitPurposeDescription;
        this.visitPurposeStatusType = visitPurposeStatusType;
    }

    public Visit() {
    }

    public Visit(String foo) {

    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getVisitPurposeDescription() {
        return visitPurposeDescription;
    }

    public void setVisitPurposeDescription(String visitPurposeDescription) {
        this.visitPurposeDescription = visitPurposeDescription;
    }

    public String getVisitPurposeStatusType() {
        return visitPurposeStatusType;
    }

    public void setVisitPurposeStatusType(String visitPurposeStatusType) {
        this.visitPurposeStatusType = visitPurposeStatusType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", visitId).append("visitorName", visitorName)
                .append("from", fromDate).append("to", endDate).append("visitPurpose", visitPurpose)
                .append("visitPurposeDescription", visitPurposeDescription)
                .append("visitPurposeStatusType", visitPurposeStatusType).toString();
    }
}
