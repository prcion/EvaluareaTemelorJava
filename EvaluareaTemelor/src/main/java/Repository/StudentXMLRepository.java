package Repository;

import Entities.Student;
import Validation.ValidationException;
import Validation.ValidationStudents;
import Validation.Validator;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class StudentXMLRepository extends AbstractRepository<Integer, Student> {
    public String fileName;

    public StudentXMLRepository(Validator<Student> validator, String fileName){
        super(validator);
        this.fileName = fileName;
        loadFromFile();
    }

    private void loadFromFile(){
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("student");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String idString = eElement.getAttribute("id");
                    String firstName = eElement.getElementsByTagName("firstname").item(0).getTextContent();
                    String lastName =  eElement.getElementsByTagName("lastname").item(0).getTextContent();
                    String group = eElement.getElementsByTagName("group").item(0).getTextContent();
                    String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    String professor = eElement.getElementsByTagName("professor").item(0).getTextContent();
                    Student s = new Student(Integer.parseInt(idString), firstName, lastName, Integer.parseInt(group), email, professor);
                    super.save(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("class");
            doc.appendChild(rootElement);

            Iterable<Student> all = super.findAll();
            for (Student s : all) {

                Element staff = doc.createElement("student");
                rootElement.appendChild(staff);

                Attr attr = doc.createAttribute("id");
                attr.setValue(Integer.toString(s.getId()));
                staff.setAttributeNode(attr);

                Element firstname = doc.createElement("firstname");
                firstname.appendChild(doc.createTextNode(s.getNume()));
                staff.appendChild(firstname);

                Element lastname = doc.createElement("lastname");
                lastname.appendChild(doc.createTextNode(s.getPrenume()));
                staff.appendChild(lastname);

                Element group = doc.createElement("group");
                group.appendChild(doc.createTextNode(Integer.toString(s.getGrupa())));
                staff.appendChild(group);

                Element email = doc.createElement("email");
                email.appendChild(doc.createTextNode(s.getEmail()));
                staff.appendChild(email);

                Element professor = doc.createElement("professor");
                professor.appendChild(doc.createTextNode(s.getCadruDidacticIndrumatorLab()));
                staff.appendChild(professor);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public Student save(Student elem) throws ValidationException {
        super.save(elem);
        saveToFile();
        return null;
    }

    public Student delete(Integer id){
        Student s = super.delete(id);
        saveToFile();
        return s;
    }

    public Student update(Student st){
        Student s = super.update(st);
        saveToFile();
        return s;
    }

    public Iterable<Student> findAll(){
        return super.findAll();
    }
}

