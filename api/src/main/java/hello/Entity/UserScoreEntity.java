package hello.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_score", schema = "womansafety", catalog = "")
public class UserScoreEntity {
    private int idScore;
    private String userPhone;
    private int score;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserScoreEntity that = (UserScoreEntity) o;
        return idScore == that.idScore &&
                score == that.score &&
                Objects.equals(userPhone, that.userPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idScore, userPhone, score);
    }
}
