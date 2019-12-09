import Validation.ValidationException;

public class Main {
    public static void main(String[] args) throws ValidationException {
        TestEntity e = new TestEntity();
        TestServices testSrv = new TestServices();
        e.TestStudent();
        testSrv.tests();

    }
}
