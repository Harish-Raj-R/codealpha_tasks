import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<Integer> scores;

    public Student(String name) {
        this.name = name;
        this.scores = new ArrayList<>();
    }

    public void addScore(int score) {
        scores.add(score);
    }

    public double getAverage() {
        if (scores.isEmpty()) return 0;
        int sum = 0;
        for (int s : scores) sum += s;
        return (double) sum / scores.size();
    }

    public int getHighest() {
        return scores.stream().max(Integer::compare).orElse(0);
    }

    public int getLowest() {
        return scores.stream().min(Integer::compare).orElse(0);
    }

    public String getLetterGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        else if (avg >= 80) return "B";
        else if (avg >= 70) return "C";
        else if (avg >= 60) return "D";
        else return "F";
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Scores: " + scores +
                ", Avg: " + String.format("%.2f", getAverage()) +
                ", High: " + getHighest() +
                ", Low: " + getLowest() +
                ", Grade: " + getLetterGrade();
    }
}
