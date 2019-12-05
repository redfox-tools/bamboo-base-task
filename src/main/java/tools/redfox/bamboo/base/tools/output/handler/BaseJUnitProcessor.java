package tools.redfox.bamboo.base.tools.output.handler;

import tools.redfox.bamboo.base.tools.junit.Success;
import tools.redfox.bamboo.base.tools.junit.TestCase;
import tools.redfox.bamboo.base.tools.junit.TestSuite;

import java.util.List;

import static com.atlassian.bamboo.utils.NameProviderFunctions.getName;

public abstract class BaseJUnitProcessor implements OutputProcessor {
    private boolean ensureNotEmpty = true;

    public BaseJUnitProcessor() {
    }

    public BaseJUnitProcessor(boolean ensureNotEmpty) {
        this.ensureNotEmpty = ensureNotEmpty;
    }

    public void setEnsureNotEmpty(boolean ensureNotEmpty) {
        this.ensureNotEmpty = ensureNotEmpty;
    }

    @Override
    public ProcessorOutput handle(String output) {
        TestSuite testSuite = new TestSuite("Dependency check: " + getName());
        testSuite.addTestCases(parseOutput(output));

        if (ensureNotEmpty && testSuite.getTestCase().size() == 0) {
            testSuite.addTestCase(
                    new TestCase(
                            String.format("All %s checks passed.", getName()),
                            new Success("All checks passed.", "success")
                    )
            );
        }
        return new JUnitOutput(testSuite);
    }

    abstract protected List<TestCase> parseOutput(String output);
}
