package data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import makingGame.managers.EntityManager;
import java.io.FileWriter;
import java.io.IOException;

public class Packager {
    /**
     * @author Justin Kim
     * Given an entity manager and an XML string, this class can convert between the two. The reason for having a specific class
     * for serializing and not just accessing the XStream object itself is to prevent any class that has the copy of the XSream object
     * from trying to serialize unserializable objects and trying to cast XML loaded objects as something they are not.
     */
    private XStream serializer;

    public Packager(){

        serializer = new XStream(new DomDriver());
    }

    /**
     * Converts entityManager into an XML string and returns the said string
     * @param entityManager
     * @return
     * @throws IOException
     */
    public String saveEntityManager(EntityManager entityManager) throws IOException {

        return serializer.toXML(entityManager);
    }

    /**
     * Converts an XML string into an entity manager and returns said entity manager
     * @param xmlString
     * @return
     */
    public EntityManager loadEntityManager(String xmlString){
        return (EntityManager)serializer.fromXML(xmlString);
    }

}
