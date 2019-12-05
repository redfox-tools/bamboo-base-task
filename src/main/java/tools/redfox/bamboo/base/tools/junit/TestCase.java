package tools.redfox.bamboo.base.tools.junit;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "testcase")
public class TestCase {
    private String name;
    private Failure failure;
    private Skipped skipped;
    private Success success;

    public TestCase() {
    }

    public TestCase(String name) {
        this.name = name;
    }

    public TestCase(String name, Failure failure) {
        this.name = name;
        this.failure = failure;
    }

    public TestCase(String name, Skipped skipped) {
        this.name = name;
        this.skipped = skipped;
    }

    public TestCase(String name, Success success) {
        this.name = name;
        this.success = success;
    }

    public void setFailure(Failure failure) {
        this.failure = failure;
    }

    public void setSkipped(Skipped skipped) {
        this.skipped = skipped;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlElement
    public Failure getFailure() {
        return failure;
    }

    @XmlElement
    public Skipped getSkipped() {
        return skipped;
    }

    @XmlElement
    public Success getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        return getSuccess() != null;
    }

    public boolean isFailure() {
        return getFailure() != null;
    }
}
