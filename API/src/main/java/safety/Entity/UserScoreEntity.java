package safety.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_score", schema = "womansafety", catalog = "")
public class UserScoreEntity {
    private int idScore;
    private String userPhone;
    private int score;
    private SurveysEntity surveysEntity;

    public UserScoreEntity() {
    }

    public UserScoreEntity(String phone, int score) {
        this.userPhone = phone;
        this.score = score;
    }

    @Id
    @Column(name = "id_score")
    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    @Basic
    @Column(name = "user_phone")
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserScoreEntity that = (UserScoreEntity) object;
        return idScore == that.idScore &&
                score == that.score &&
                Objects.equals(userPhone, that.userPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idScore, userPhone, score);
    }

    @ManyToOne
    @JoinColumn(name = "id_survey", referencedColumnName = "id_survey", nullable = false)
    public SurveysEntity getSurveyBySurvey() {
        return surveysEntity;
    }

    public void setSurveyBySurvey(SurveysEntity surveyBySurvey) {
        this.surveysEntity = surveyBySurvey;
    }
}