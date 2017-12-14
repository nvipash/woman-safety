package hello;

public class TestInfo {
    int testID;
    String testDescription;
    int questionCount;

    public TestInfo () {}

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }
    public String getTestDescription() {
        return testDescription;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
    public int getQuestionCount() {
        return questionCount;
    }

}
