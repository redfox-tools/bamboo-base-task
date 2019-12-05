package tools.redfox.bamboo.base.builder;

import com.atlassian.bamboo.specs.api.builders.task.Task;
import org.jetbrains.annotations.Nullable;
import tools.redfox.bamboo.base.model.BaseTaskProperties;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseTask<B extends BaseTask<B, P>, P extends BaseTaskProperties> extends Task<B, P> {
    @Nullable
    protected String options;
    @Nullable
    protected String runtime;
    @Nullable
    protected String output;
    @Nullable
    protected String workingSubDirectory;
    @Nullable
    protected String environmentVariables;

    protected Map<String, String> configuration = new HashMap<>();

    public B runtime(@Nullable String runtime) {
        this.runtime = runtime;
        configuration.put("runtime", runtime);
        return (B) this;
    }

    public B options(@Nullable String options) {
        this.options = options;
        configuration.put("options", options);
        return (B) this;
    }

    public B output(@Nullable String output) {
        this.output = output;
        configuration.put("output", output);
        return (B) this;
    }

    public B workingSubDirectory(@Nullable String workingSubDirectory) {
        this.workingSubDirectory = workingSubDirectory;
        configuration.put("workingSubDirectory", workingSubDirectory);
        return (B) this;
    }

    public B environmentVariables(@Nullable String environmentVariables) {
        this.environmentVariables = environmentVariables;
        configuration.put("environmentVariables", environmentVariables);
        return (B) this;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }
}
