package solid.dip.solution;

public class Pilot {

    private Vehicle vehicle;

    public Pilot(final Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public void increaseSpeed(){
        vehicle.accelerate();
    }
}