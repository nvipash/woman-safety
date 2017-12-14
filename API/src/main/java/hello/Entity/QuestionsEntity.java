package hello.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "questions", schema = "womansafety", catalog = "")
public class QuestionsEntity {
    private int idQuestion;
    private String question;
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
                points == that.points &&
                Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idQuestion, question, points);
    }
}
