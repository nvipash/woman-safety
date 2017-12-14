package hello;

import hello.Entity.InstructionsEntity;
import hello.Entity.QuestionsEntity;
import hello.Entity.UserScoreEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultsController {
    int urlParameter = InstructionsEntity.idInstruction;
    @RequestMapping("/api/tests/questions")
    public UserScoreEntity setUserScore() {
        UserScoreEntity setUserScore = new UserScoreEntity();
        //здесь может быть ваш код))0)
        return setUserScore;
    }
    public UserScoreEntity getRecommendation() {
        UserScoreEntity getRecommendation = new UserScoreEntity();
        //здесь может быть ваш код))0)
        return getRecommendation;
    }
}
