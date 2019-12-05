package tools.redfox.bamboo.base.tools.output.handler;

import java.io.File;

public interface ProcessorOutput {
    void save(File output);

    File getFile(String output);
}
