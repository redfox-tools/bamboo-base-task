package tools.redfox.bamboo.base.configuration;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskConfiguratorHelper;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.task.TaskRequirementSupport;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import com.atlassian.bamboo.v2.build.agent.capability.RequirementImpl;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseTaskConfiguration extends AbstractTaskConfigurator implements TaskRequirementSupport {
    protected UIConfigSupport uiConfigSupport;

    public BaseTaskConfiguration(UIConfigSupport uiConfigSupport, TaskConfiguratorHelper taskConfiguratorHelper) {
        super();
        this.uiConfigSupport = uiConfigSupport;
        this.taskConfiguratorHelper = taskConfiguratorHelper;
    }

    @NotNull
    public Set<Requirement> calculateRequirements(@NotNull TaskDefinition taskDefinition) {
        String runtime = taskDefinition.getConfiguration().get("runtime");
        Set<Requirement> requirements = Collections.emptySet();
        if (StringUtils.isNotBlank(runtime)) {
            return Collections.singleton(new RequirementImpl(String.format("system.builder.%s.%s", getExecutableName(), runtime), true, ".*"));
        }
        return requirements;
    }

    @NotNull
    public Map<String, String> generateTaskConfigMap(@NotNull ActionParametersMap params, @Nullable TaskDefinition previousTaskDefinition) {
        Map<String, String> map = super.generateTaskConfigMap(params, previousTaskDefinition);
        this.taskConfiguratorHelper.populateTaskConfigMapWithActionParameters(map, params, this.getFieldsToCopy());
        return map;
    }

    public void populateContextForCreate(@NotNull Map<String, Object> context) {
        super.populateContextForCreate(context);
        populateContext(context);
        context.putAll(this.getDefaultFieldValues());
    }

    public void populateContextForEdit(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        populateContext(context);
        this.taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition, this.getFieldsToCopy());
    }

    protected void populateContext(@NotNull Map<String, Object> context) {
        context.put("executableLabels", executableLabels());
        context.put("executable", getExecutableName());
    }

    protected List<String> executableLabels() {
        return uiConfigSupport.getExecutableLabels(getExecutableName());
    }

    @NotNull
    protected Map<String, Object> getDefaultFieldValues() {
        return Collections.emptyMap();
    }

    @NotNull
    protected Set<String> getFieldsToCopy() {
        return Sets.newHashSet("environmentVariables", "workingSubDirectory", "options", "runtime", "output");
    }

    @NotNull
    protected abstract String getExecutableName();
}
