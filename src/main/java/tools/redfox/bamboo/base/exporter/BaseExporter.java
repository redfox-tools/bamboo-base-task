package tools.redfox.bamboo.base.exporter;

import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
import com.atlassian.bamboo.specs.api.validators.common.ValidationContext;
import com.atlassian.bamboo.specs.api.validators.common.ValidationProblem;
import com.atlassian.bamboo.task.TaskContainer;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.task.export.TaskDefinitionExporter;
import com.atlassian.bamboo.task.export.TaskValidationContext;
import com.atlassian.bamboo.util.Narrow;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tools.redfox.bamboo.base.builder.BaseTask;
import tools.redfox.bamboo.base.model.BaseTaskProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseExporter<P extends BaseTaskProperties, B extends BaseTask<B, P>> implements TaskDefinitionExporter {
    private final Class<P> propertiesClass;

    protected BaseExporter(Class<P> propertiesClass) {
        this.propertiesClass = propertiesClass;
    }

    @NotNull
    @Override
    public Map<String, String> toTaskConfiguration(@NotNull TaskContainer taskContainer, @NotNull TaskProperties taskProperties) {
        P typedTaskProperties = Narrow.downTo(taskProperties, this.propertiesClass);
        Preconditions.checkState(typedTaskProperties != null, "Don't know how to import task properties of type: " + taskProperties.getClass().getName());
        Map<String, String> config = new HashMap<>();
        config.put("environmentVariables", typedTaskProperties.getEnvironmentVariables());
        config.put("workingSubDirectory", typedTaskProperties.getWorkingSubDirectory());
        config.put("options", typedTaskProperties.getOptions());
        config.put("output", typedTaskProperties.getOutput());
        config.put("runtime", typedTaskProperties.getRuntime());
        return config;
    }

    @NotNull
    public B toSpecsEntity(@NotNull TaskDefinition taskDefinition) {
        Map<String, String> config = taskDefinition.getConfiguration();
        return this.toSpecsEntity(config)
                .runtime(config.get("runtime"))
                .options(config.get("options"))
                .output(config.get("output"))
                .environmentVariables(config.get("environmentVariables"))
                .workingSubDirectory(config.get("workingSubDirectory"));
    }

    @NotNull
    public final List<ValidationProblem> validate(@NotNull TaskValidationContext taskValidationContext, @NotNull TaskProperties taskProperties) {
        List<ValidationProblem> validationProblems = new ArrayList<>();

        return validationProblems;
    }

    @NotNull
    protected abstract ValidationContext getValidationContext();

    @NotNull
    protected Map<String, String> toTaskConfiguration(@NotNull P var1) {
        return new HashMap<>();
    }

    @NotNull
    protected abstract B toSpecsEntity(@NotNull Map<String, String> configuration);

    @NotNull
    protected List<ValidationProblem> validate(@NotNull P taskProperties) {
        return new ArrayList<>();
    }
}
