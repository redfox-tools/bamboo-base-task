package tools.redfox.bamboo.base.tools.output.handler;

import tools.redfox.bamboo.base.tools.junit.TestSuite;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class JUnitOutput implements ProcessorOutput {
    private TestSuite testSuite;

    public JUnitOutput(TestSuite testSuite) {
        this.testSuite = testSuite;
    }

    public void save(File output) {
        JAXBContext contextObj = null;
        try {
            contextObj = JAXBContext.newInstance(TestSuite.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(testSuite, new FileOutputStream(output));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile(String output) {
        return null;
    }
}
