package tools.redfox.bamboo.base.type;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.configuration.ConfigurationMapImpl;
import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;
import tools.redfox.bamboo.base.tools.Command;
import tools.redfox.bamboo.base.tools.output.handler.OutputProcessor;
import tools.redfox.bamboo.base.tools.output.handler.ProcessorOutput;
import tools.redfox.bamboo.base.tools.wrappers.WrappedBuildLogger;
import tools.redfox.bamboo.base.tools.wrappers.WrappedTaskContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseTaskType {
    private final EnvironmentVariableAccessor environmentVariableAccessor;
    private ProcessService processService;
    private CapabilityContext capabilityContext;
    protected ConfigurationMap configurationMap = new ConfigurationMapImpl();
    protected List<OutputProcessor> processors = new ArrayList<>();

    protected BaseTaskType(
            ProcessService processService,
            EnvironmentVariableAccessor environmentVariableAccessor,
            CapabilityContext capabilityContext) {
        this.processService = processService;
        this.environmentVariableAccessor = environmentVariableAccessor;
        this.capabilityContext = capabilityContext;
    }


    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        configurationMap = taskContext.getConfigurationMap();
        assert getExecutablePath() != null;

        WrappedTaskContext wrappedTaskContext = new WrappedTaskContext(taskContext);
        TaskResultBuilder taskResultBuilder = TaskResultBuilder.newBuilder(taskContext);
        Command command = getCommand();

        taskContext.getBuildLogger().addBuildLogEntry("Exec: " + command);

        taskResultBuilder.checkReturnCode(
                this.processService.executeExternalProcess(
                        wrappedTaskContext,
                        buildProcess(command, taskContext)
                ),
                getExpectedExitCode()
        );

        for (OutputProcessor processor : processors) {
            ProcessorOutput output = processor.handle(((WrappedBuildLogger) wrappedTaskContext.getBuildLogger()).getTaskLog());
            if (output != null) {
                output.save(getOutputPathFor(output));
            }
        }

        return getResult(taskResultBuilder);
    }

    protected TaskResult getResult(TaskResultBuilder taskResultBuilder) {
        return taskResultBuilder.build();
    }

    protected Command getCommand() {
        Command command = Command.fromString(
                String.format(
                        "%s %s %s", getExecutablePath(), getBaseCommand(), configurationMap.getOrDefault("options", "")
                )
        );

        addArguments(command);

        return command;
    }

    protected String getExecutablePath() {
        return this.capabilityContext.getCapabilityValue(String.format("system.builder.%s.%s", getName(), configurationMap.get("runtime")));
    }

    protected Map<String, String> getEnvironmentVariables() {
        return this.environmentVariableAccessor.splitEnvironmentAssignments(configurationMap.get("environmentVariables"), false);
    }

    protected int getExpectedExitCode() {
        return 0;
    }

    protected ExternalProcessBuilder buildProcess(Command command, TaskContext taskContext) {
        return new ExternalProcessBuilder()
                .command(command)
                .env(getEnvironmentVariables())
//                .path(getExecutablePath())
                .workingDirectory(taskContext.getWorkingDirectory());
    }

    protected File getOutputPathFor(ProcessorOutput output) {
        return output.getFile(configurationMap.get("output"));
    }

    protected String getBaseCommand() {
        return "";
    }

    protected boolean hasOption(String option) {
        return Strings.isNullOrEmpty(configurationMap.get(option));
    }

    protected boolean hasOutputFile() {
        return hasOption("output");
    }

    protected String getOutputFile() {
        return configurationMap.get("output");
    }

    protected void addArguments(Command command) {
    }

    abstract protected String getName();
}
