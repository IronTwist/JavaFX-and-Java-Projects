package ro.ubb.lab6x.repository.XMLrepository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.ubb.lab6x.model.Entity;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.repository.InMemoryRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Optional;

public abstract class XMLrepository<T extends Entity<Long>> extends InMemoryRepository<Long, T> {
    protected String filepath;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    protected Document document;
    private TransformerFactory transformerFactory;
    protected Transformer transformer;

    public XMLrepository(Validator<T> validator, String filepath) {
        super(validator);
        this.filepath = filepath;
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(filepath);

            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void loadData() {
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element element = (Element) node;
            buildEntityFromElement(element);

        }
    }

    protected abstract void buildEntityFromElement(Element element);

    protected String getTextFromTagName(Element element, String tagName) {

        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }


    protected void saveToXml(T entity) {
        try {

            Element root = document.getDocumentElement();
            Node node = createNodeFromEntity(entity);
            root.appendChild(node);
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(filepath))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected abstract Element createNodeFromEntity(T entity);

    protected void addChildWithTextContent(Element parent, String tagName, String textContent) {
        Element childElement = this.document.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }

    @Override
    public Optional<T> delete(Long aLong) {
        NodeList nodeList = document.getElementsByTagName("id");
        removeElementFromDom(aLong, nodeList);
        try {
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(filepath))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }


        return super.delete(aLong);
    }

    protected void removeElementFromDom(Long aLong, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Long id = Long.parseLong(node.getTextContent());
            if (aLong.equals(id)) {
                node.getParentNode().getParentNode().removeChild(node.getParentNode());
            }

        }
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        Optional<T> optional = super.update(entity);
        NodeList nodeList = document.getElementsByTagName("id");
        removeElementFromDom(entity.getId(), nodeList);
        saveToXml(entity);
        return optional;

    }
}
