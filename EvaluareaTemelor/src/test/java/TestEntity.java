import Entities.Student;

public class TestEntity {

    public void TestStudent(){
        Student s = new Student(1,"ion","ion", 226, "ala", "aafa");
        assert s.getId() == 1;
        assert s.getNume() == "ion";
        assert s.getPrenume() == "ion";
        assert s.getGrupa() == 226;
        assert s.getEmail() == "ala";
        assert s.getCadruDidacticIndrumatorLab() == "aafa";
    }

}
