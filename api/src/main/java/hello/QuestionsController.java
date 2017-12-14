package hello;

import hello.Entity.InstructionsEntity;
import hello.Entity.QuestionsEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionsController {
    int urlParameter = InstructionsEntity.idInstruction;

    @RequestMapping("/api/tests/questions")
    public QuestionsEntity getQuestions() {
        QuestionsEntity setofquestions = new QuestionsEntity();

        //здесь может быть ваш код))0)
        return setofquestions;
    }
}
