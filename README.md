# SOLID

**S(단일 책임 원칙)**  
한 클래스는 하나의 책임만 가져야 하고,
 클래스는 자신의 이름이 나타내는 일 하나만을 해야합니다.  

```
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
``` 
#
**O(개방-폐쇄 원칙)**  
소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 합니다.  
즉 ,기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계가 되어야 합니다   

violation
```
public enum DrivingMode {
   SPORT, COMFORT
}
```

```
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
```

solution
```
public interface DrivingMode {
   int getPower();
   int getSuspensionHeight();
}
```
```
public class Comfort implements DrivingMode {

   private static final int POWER = 400;
   private static final int SUSPENSION_HEIGHT = 20;

   @Override
   public int getPower() {
       return POWER;
   }

   @Override
   public int getSuspensionHeight() {
       return SUSPENSION_HEIGHT;
   }
}
```
```
public class Sport implements DrivingMode {

   private static final int POWER = 500;
   private static final int SUSPENSION_HEIGHT = 10;

   @Override
   public int getPower() {
       return POWER;
   }

   @Override
   public int getSuspensionHeight() {
       return SUSPENSION_HEIGHT;
   }
}
```
```
public class Drive {

   private Vehicle vehicle;

   public Drive(final Vehicle vehicle) {
       this.vehicle = vehicle;
   }

   public void changeDrivingMode(final DrivingMode drivingMode){
       vehicle.setPower(drivingMode.getPower());
       vehicle.setSuspensionHeight(drivingMode.getSuspensionHeight());
   }
}
```
#
**L(리스코프 치환 원칙)**  
부모 클래스와 자식 클래스 사이의 행위가 일관성이 있어야 합니다.  
즉,부모 클래스의 인스턴스 대신에 자식 클래스의 인스턴스로 대체해도 프로그램의 의미는 변화되지 않습니다.  
```
Vehicle vehicle =new Car();
vehicle.changeGear(Gear.D);
vehicle.changeGear(Gear.R);
//자동차는 직진중에 후진할 수없다.
```
```
Vehicle vehicle =new Plane();
vehicle.changeGear(Gear.D);
vehicle.changeGear(Gear.R);
//비행기는는 직진중에 후진할 수 있다.
```
```
Vehicle vehicle =new Vehicle();
vehicle.changeGear(Gear.D);
vehicle.changeGear(Gear.R);
//비행기와 자동차의 상위클래스 탈것 클래스로 변경해도 똑같이 동작해야한다
```
#
**I(인터페이스 분리 원칙)**  
하나의 일반적인 인터페이스보다는, 여러 개의 구체적인 인터페이스가 낫다  
한 클래스는 자신이 사용하지 않는 인터페이스는 구현하지 말아야 한다는 원리입니다.  

violation
```
public interface Switches {

   void startEngine();

   void shutDownEngine();

   void turnRadioOn();

   void turnRadioOff();

   void turnCameraOn();

   void turnCameraOff();
}
```
```
public class Car extends Vehicle {
   //Vehicle은 Switches를 구현하고있습니다.
   private boolean radioOn;

   public boolean isRadioOn() {
       return radioOn;
   }

   @Override
   public void turnRadioOn() {
       radioOn = true;
   }

   @Override
   public void turnRadioOff() {
       radioOn = false;
   }

   @Override
   public void turnCameraOn() {
       // 안쓰는 함수를 구현하게됨,ISP위배 
   }

   @Override
   public void turnCameraOff() {
       // 안쓰는 함수를 구현하게됨,ISP위배
   }
}
```
#
**I(의존관계 역전 원칙)**  
“추상화에 의존해야지, 구체화에 의존하면 안된다. 의존성 주입은 이 원칙을 따르는 방법 중 하나입니다.  
위 레벨의 레이어가 하위 레벨의 레이어를 바로 의존하게 하는 것이 아니라 이 둘 사이에 존재하는 추상레벨을 통해 의존해야 한다.   
이를 통해서 상위레벨의 모듈은 하위레벨의 모듈로의 의존성에서 벗어나 그 자체로 재사용 되고 확장성도 보장 받을 수 있습니다.  



violaion
```
public class RacingCar {

   private final int maxFuel;
   private int remainingFuel;
   private int power;

   public RacingCar(final int maxFuel) {
       this.maxFuel = maxFuel;
       remainingFuel = maxFuel;
   }

   public void accelerate(){
       power++;
       remainingFuel--;
   }
}
```
```
public class Pilot {

   private RacingCar car;

   public Pilot(){
       this.car = new RacingCar(100);
   }

   public void increaseSpeed(){
       car.accelerate();
   }
}
```

solution
```
public interface Vehicle {
   void accelerate();
}
```
```
public class RacingCar implements Vehicle{

   private final int maxFuel;
   private int remainingFuel;
   private int power;

   public RacingCar(final int maxFuel) {
       this.maxFuel = maxFuel;
       remainingFuel = maxFuel;
   }

   @Override
   public void accelerate() {
       power++;
       remainingFuel--;
   }
}
```
```
public class Pilot {

   private Vehicle vehicle;

   public Pilot(final Vehicle vehicle){
       this.vehicle = vehicle;
   }

   public void increaseSpeed(){
       vehicle.accelerate();
   }
}
```

