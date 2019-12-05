package tools.redfox.bamboo.base.tools.junit;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "testsuite")
public class TestSuite {
    private List<TestCase> testCases = new ArrayList<>();
    private String name;

    public TestSuite() {
    }

    public TestSuite(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlElement(name = "testcase")
    public List<TestCase> getTestCase() {
        return testCases;
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public void addTestCases(List<TestCase> testCases) {
        this.testCases.addAll(testCases);
    }
}
