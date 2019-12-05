package tools.redfox.bamboo.base.tools.wrappers;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.serialization.WhitelistedSerializable;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.bamboo.v2.build.CommonContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

public class WrappedTaskContext implements TaskContext {
    private TaskContext wrappedContext;
    private BuildLogger buildLogger;

    public WrappedTaskContext(TaskContext wrappedContext) {
        this.wrappedContext = wrappedContext;
    }

    @NotNull
    @Override
    public BuildContext getBuildContext() {
        return wrappedContext.getBuildContext();
    }

    @NotNull
    @Override
    public CommonContext getCommonContext() {
        return wrappedContext.getCommonContext();
    }

    @NotNull
    @Override
    public BuildLogger getBuildLogger() {
        if (buildLogger == null) {
            buildLogger = new WrappedBuildLogger(wrappedContext.getBuildLogger());
        }
        return buildLogger;
    }

    @NotNull
    @Override
    public File getRootDirectory() {
        return wrappedContext.getRootDirectory();
    }

    @NotNull
    @Override
    public File getWorkingDirectory() {
        return wrappedContext.getWorkingDirectory();
    }

    @NotNull
    @Override
    public ConfigurationMap getConfigurationMap() {
        return wrappedContext.getConfigurationMap();
    }

    @Nullable
    @Override
    public Map<String, String> getRuntimeTaskContext() {
        return wrappedContext.getRuntimeTaskContext();
    }

    @Nullable
    @Override
    public Map<String, WhitelistedSerializable> getRuntimeTaskData() {
        return wrappedContext.getRuntimeTaskData();
    }

    @Override
    public boolean doesTaskProduceTestResults() {
        return wrappedContext.doesTaskProduceTestResults();
    }

    @Override
    public long getId() {
        return wrappedContext.getId();
    }

    @NotNull
    @Override
    public String getPluginKey() {
        return wrappedContext.getPluginKey();
    }

    @Nullable
    @Override
    public String getUserDescription() {
        return wrappedContext.getUserDescription();
    }

    @Override
    public boolean isEnabled() {
        return wrappedContext.isEnabled();
    }

    @Override
    public boolean isFinalising() {
        return wrappedContext.isFinalising();
    }
}
