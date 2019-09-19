package solid.srp.violation;

public class Vehicle {

    private final int maxFuel;
    private int remainingFuel;

    public Vehicle(int maxFuel) {
        this.maxFuel = maxFuel;
        remainingFuel = maxFuel;
    }

    //연료 충전은 Vehicle의 responsibility가 아니므로 SRC에 위반
    public void reFuel() {
        remainingFuel = maxFuel;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int getRemainingFuel() {
        return remainingFuel;
    }

    public void setRemainingFuel(int remainingFuel) {
        this.remainingFuel = remainingFuel;
    }

    public void accelerate() {
        remainingFuel--;
    }
}