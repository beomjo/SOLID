package solid.ocp.violation;

public class Drive {

    private Vehicle vehicle;

    public Drive(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void changeDrivingMode(final DrivingMode drivingMode){
        switch (drivingMode){
            case SPORT:
                vehicle.setPower(500);
                vehicle.setSuspensionHeight(10);
                break;
            case COMFORT:
                vehicle.setPower(400);
                vehicle.setSuspensionHeight(20);
                break;
            default:
                vehicle.setPower(400);
                vehicle.setSuspensionHeight(20);
                break;
            //ECO라는 모드가 새롭게 추가되어야할때 , if~else가 계속하여 추가되는 형태가 됨
            //기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계하는 OCP에 위반
        }
    }
}