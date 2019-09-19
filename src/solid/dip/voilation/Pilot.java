package solid.dip.voilation;

public class Pilot {

    private RacingCar car;

    public Pilot(){
        this.car = new RacingCar(100);
    }

    public void increaseSpeed(){
        car.accelerate();
    }
}