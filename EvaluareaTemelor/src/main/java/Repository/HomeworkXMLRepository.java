package Repository;

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

public class HomeworkXMLRepository extends AbstractRepository<Integer, Homework> {
    public String fileName;

    public HomeworkXMLRepository(Validator<Homework> validator, String fileName){
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
            NodeList nList = doc.getElementsByTagName("homework");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String idString = eElement.getAttribute("id");
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    String startWeek =  eElement.getElementsByTagName("startWeek").item(0).getTextContent();
                    String deadlineWeek = eElement.getElementsByTagName("deadlineWeek").item(0).getTextContent();
                    Homework hm = new Homework(Integer.parseInt(idString), description, Integer.parseInt(startWeek), Integer.parseInt(deadlineWeek));
                    super.save(hm);
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

            Iterable<Homework> all = super.findAll();
            for (Homework s : all) {

                Element staff = doc.createElement("homework");
                rootElement.appendChild(staff);

                Attr attr = doc.createAttribute("id");
                attr.setValue(Integer.toString(s.getId()));
                staff.setAttributeNode(attr);

                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(s.getDescription()));
                staff.appendChild(description);

                Element startWeek = doc.createElement("startWeek");
                startWeek.appendChild(doc.createTextNode(Integer.toString(s.getStartWeek())));
                staff.appendChild(startWeek);

                Element deadlineWeek = doc.createElement("deadlineWeek");
                deadlineWeek.appendChild(doc.createTextNode(Integer.toString(s.getDeadlineWeek())));
                staff.appendChild(deadlineWeek);
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
    public Homework save(Homework elem) throws ValidationException {
        super.save(elem);
        saveToFile();
        return null;
    }
}

