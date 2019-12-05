package tools.redfox.bamboo.base.tools.junit;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "success")
public class Success extends BaseResult {
    public Success(String message, String type) {
        super(message, type);
    }
}
