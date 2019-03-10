package self.erp.visitorservice.repositories;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Schema_Visitor")
public class Visit {

    @Id
    @GeneratedValue
    @Column(name = "VISIT_ID")
    private int visitId;

    @Column(name = "VISIT_VISITOR")
    private String visitorName;

    @Column(name = "FROM_STAMP")
    private LocalDateTime fromDate;

    @Column(name = "TO_STAMP")
    private LocalDateTime endDate;

    @Column(name = "VISIT_PURPOSE")
    private String visitPurpose;

    @Column(name = "VISIT_PURPOSE_DESCRIPTION")
    private String visitPurposeDescription;

    @Column(name = "VISIT_PURPOSE_STATUS_TYPE")
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
