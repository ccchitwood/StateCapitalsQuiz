package edu.uga.cs.statecapitalsquiz;


public class Question {

    private long id;
    private String state;
    private String answer1;
    private String answer2;
    private String answer3;

    public Question() {
        this.id = -1;
        this.state = null;
        this.answer1 = null;
        this.answer2 = null;
        this.answer3 = null;
    }

    public Question (String state, String answer1, String answer2, String answer3) {
        this.id = -1;
        this.state = state;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getState() {return state;}

    public void setState(String state) {this.state = state;}

    public String getAnswer1() {return answer1;}

    public void setAnswer1(String answer1) {this.answer1 = answer1;}

    public String getAnswer2() {return answer2;}

    public void setAnswer2(String answer2) {this.answer2 = answer2;}

    public String getAnswer3() {return answer3;}

    public void setAnswer3(String answer3) {this.answer3 = answer3;}

    public String toString() {
        return id + ", " + state + ", " + answer1 + ", " + answer2 + ", " + answer3;
    }
}

