//ONEIL853

public class Bin {
    private double interval;
    private int count;
    private double cumProbability;

    public Bin() { };
    public Bin(double inputInterval, int inputCount, double inputCumProbability) {
        interval = inputInterval;
        count = inputCount;
        cumProbability = inputCumProbability;
    }

    public void setInterval(double inputInterval) {
        interval = inputInterval;
    }
    public void setCount(int inputCount) {
        count = inputCount;
    }
    public void setCumProbability(double inputCumProbability) {
        cumProbability = inputCumProbability;
    }
    public double getInterval() {return interval;}
    public int getCount() {return count;}
    public double getCumProbability() {return cumProbability;}
}
