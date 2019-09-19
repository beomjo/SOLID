package solid.lsp.solution;

public class LSPTest {
    public static void main(String[] args) {
        Vehicle vehicle =new Plane();
        vehicle.changeGear(Gear.D);
        vehicle.changeGear(Gear.R);
    }
}
