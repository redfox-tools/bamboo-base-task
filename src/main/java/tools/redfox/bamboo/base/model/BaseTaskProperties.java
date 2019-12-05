package tools.redfox.bamboo.base.model;

import com.atlassian.bamboo.specs.api.model.AtlassianModuleProperties;
import com.atlassian.bamboo.specs.api.model.plan.condition.ConditionProperties;
import com.atlassian.bamboo.specs.api.model.plan.requirement.RequirementProperties;
import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseTaskProperties extends TaskProperties {
    private String output;
    private String runtime;
    private String workingSubDirectory;
    private String environmentVariables;
    protected String options;

    public BaseTaskProperties(){
        super();
        output = null;
        runtime = null;
        environmentVariables = null;
        options = null;
        validate();
    }

    public BaseTaskProperties(
            String description,
            boolean enabled,
            String runtime,
            String options,
            String output,
            String workingSubDirectory,
            String environmentVariables,
            List<RequirementProperties> requirements,
            List<? extends ConditionProperties> conditions
    ) {
        super(description, enabled, requirements, conditions);
        this.options = options;
        this.output = output;
        this.runtime = runtime;
        this.workingSubDirectory = workingSubDirectory;
        this.environmentVariables = environmentVariables;
    }

    public String getOptions() {
        return options;
    }

    public String getOutput() {
        return output;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getWorkingSubDirectory() {
        return workingSubDirectory;
    }

    public String getEnvironmentVariables() {
        return environmentVariables;
    }

    @NotNull
    @Override
    public AtlassianModuleProperties getAtlassianPlugin() {
        return new AtlassianModuleProperties(this.getModulePropertiesName());
    }

    abstract protected String getModulePropertiesName();
}
