package Repository;

import Entities.Grade;
import Entities.Homework;
import Entities.Student;
import Validation.ValidationException;
import Validation.Validator;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GradeXMLRepository extends AbstractRepository<String, Grade> {
    public String fileName;

    public GradeXMLRepository(Validator<Grade> validator, String fileName){
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
            NodeList nList = doc.getElementsByTagName("grade");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String idString = eElement.getAttribute("id");
                    String student = eElement.getElementsByTagName("student").item(0).getTextContent();
                    String homework = eElement.getElementsByTagName("homework").item(0).getTextContent();
                    String week = eElement.getElementsByTagName("week").item(0).getTextContent();
                    String professor =  eElement.getElementsByTagName("professor").item(0).getTextContent();
                    String grade = eElement.getElementsByTagName("gradeOne").item(0).getTextContent();
                    String feedback = eElement.getElementsByTagName("feedback").item(0).getTextContent();

                    String[] hm = homework.split(" ");
                    Homework h = new Homework(Integer.parseInt(hm[0]), hm[1], Integer.parseInt(hm[2]), Integer.parseInt(hm[3]));

                    String[] st = student.split(" ");
                    Student s = new Student(Integer.parseInt(st[0]), st[1], st[2], Integer.parseInt(st[3]), st[4], st[5]);

                    Grade g = new Grade(s, h, Integer.parseInt(week), professor, Double.parseDouble(grade), feedback);
                    super.save(g);
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

            Iterable<Grade> all = super.findAll();
            for (Grade s : all) {

                Element staff = doc.createElement("grade");
                rootElement.appendChild(staff);

                Attr attr = doc.createAttribute("id");
                attr.setValue(s.getId());
                staff.setAttributeNode(attr);

                Element student = doc.createElement("student");
                student.appendChild(doc.createTextNode(s.getStudent().toString()));
                staff.appendChild(student);

                Element homework = doc.createElement("homework");
                homework.appendChild(doc.createTextNode(s.getHomework().toString()));
                staff.appendChild(homework);

                Element week = doc.createElement("week");
                week.appendChild(doc.createTextNode(Integer.toString(s.getWeek())));
                staff.appendChild(week);

                Element professor = doc.createElement("professor");
                professor.appendChild(doc.createTextNode(s.getProfessor()));
                staff.appendChild(professor);

                Element grade = doc.createElement("gradeOne");
                grade.appendChild(doc.createTextNode(Double.toString(s.getGrade())));
                staff.appendChild(grade);

                Element feedback = doc.createElement("feedback");
                feedback.appendChild(doc.createTextNode(s.getFeedback()));
                staff.appendChild(feedback);
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

    public Grade save(Grade elem) throws ValidationException {
        super.save(elem);
        saveToFile();
        return null;
    }
}

