package safety.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "questions", schema = "womansafety", catalog = "")
public class QuestionsEntity {
    private int idQuestion;
    private String question;
    private int points_often;
    private int points_seldom;
    private int points_never;
    private int points;

    @Id
    @Column(name = "id_question")
    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "points_often")
    public int getPointsOften() {
        return points_often;
    }

    public void setPointsOften(int points_often) {
        this.points_often = points_often;
    }
    @Basic
    @Column(name = "points_seldom")
    public int getPointsSeldom() {
        return points_seldom;
    }

    public void setPointsSeldom(int points_seldom) {
        this.points_seldom = points_seldom;
    }
    @Basic
    @Column(name = "points_never")
    public int getPointsNever() {
        return points_never;
    }

    public void setPointsNever(int points_never) {
        this.points_often = points_never;
    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionsEntity that = (QuestionsEntity) o;
        return idQuestion == that.idQuestion &&

                points_often == that.points_often &&
                points_seldom == that.points_seldom &&
                points_never == that.points_never &&
                points == that.points &&
                Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestion, question, points_often, points_seldom, points_never);

        return Objects.hash(idQuestion, question, points);
    }
}
