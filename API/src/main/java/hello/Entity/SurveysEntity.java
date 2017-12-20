package hello.Entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "surveys", schema = "womansafety", catalog = "")
public class SurveysEntity {
    private int idSurvey;
    private String survey;
    private String description;
    private Collection<UserScoreEntity> userBySurvey;

    @Id
    @Column(name = "id_survey")
    public int getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(int idSurvey) {
        this.idSurvey = idSurvey;
    }

    @Basic
    @Column(name = "survey")
    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveysEntity that = (SurveysEntity) o;
        return idSurvey == that.idSurvey &&
                Objects.equals(survey, that.survey) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idSurvey, survey, description);
    }

    @OneToMany(mappedBy = "surveyBySurvey", fetch = FetchType.EAGER)
    public Collection<UserScoreEntity> getUserBySurvey() {
        return userBySurvey;
    }

    public void setUserBySurvey(Collection<UserScoreEntity> userBySurvey) {
        this.userBySurvey = userBySurvey;
    }
}
