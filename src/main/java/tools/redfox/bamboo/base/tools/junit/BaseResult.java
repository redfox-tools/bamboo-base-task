package tools.redfox.bamboo.base.tools.junit;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class BaseResult {
    protected String message;
    protected String type;

    public BaseResult(String message, String type) {
        this.message = message;
        this.type = type;
    }

    @XmlAttribute
    public String getMessage() {
        return message;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }
}
