package tools.redfox.bamboo.base.tools.wrappers;

import com.atlassian.bamboo.build.BuildOutputLogEntry;
import com.atlassian.bamboo.build.ErrorLogEntry;
import com.atlassian.bamboo.build.LogEntry;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.build.logger.LogInterceptorStack;
import com.atlassian.bamboo.build.logger.LogMutatorStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WrappedBuildLogger implements BuildLogger {
    private BuildLogger buildLogger;
    private StringBuilder taskLog;
    private StringBuilder taskErrorLog;

    public WrappedBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
        this.taskLog = new StringBuilder();
        this.taskErrorLog = new StringBuilder();
    }

    @NotNull
    @Override
    public List<LogEntry> getBuildLog() {
        return buildLogger.getBuildLog();
    }

    @NotNull
    @Override
    public List<LogEntry> getErrorLog() {
        return buildLogger.getErrorLog();
    }

    @NotNull
    @Override
    public List<LogEntry> getLastNLogEntries(int i) {
        return buildLogger.getLastNLogEntries(i);
    }

    @Override
    public List<String> getStringErrorLogs() {
        return buildLogger.getStringErrorLogs();
    }

    @NotNull
    @Override
    public String addBuildLogEntry(@NotNull LogEntry logEntry) {
        if (logEntry instanceof BuildOutputLogEntry) {
            taskLog.append(logEntry.getLog()).append("\n");
        }
        return buildLogger.addBuildLogEntry(logEntry);
    }

    @Override
    public String addBuildLogEntry(@NotNull String s) {
        taskLog.append(s).append("\n");
        return buildLogger.addBuildLogEntry(s);
    }

    @Override
    public String addBuildLogHeader(String s, boolean b) {
        return buildLogger.addBuildLogHeader(s, b);
    }

    @NotNull
    @Override
    public String addErrorLogEntry(@NotNull LogEntry logEntry) {
        if (logEntry instanceof ErrorLogEntry) {
            taskErrorLog.append(logEntry.getLog()).append("\n");
        }
        return buildLogger.addErrorLogEntry(logEntry);
    }

    @Override
    public String addErrorLogEntry(String s) {
        taskErrorLog.append(s).append("\n");
        return buildLogger.addErrorLogEntry(s);
    }

    @Override
    public void addErrorLogEntry(String s, @Nullable Throwable throwable) {
        taskErrorLog.append(s).append("\n");
        buildLogger.addErrorLogEntry(s, throwable);
    }

    @Override
    public void stopStreamingBuildLogs() {
        buildLogger.stopStreamingBuildLogs();
    }

    @Override
    public void clearBuildLog() {
        buildLogger.clearBuildLog();
    }

    @Override
    public long getTimeOfLastLog() {
        return buildLogger.getTimeOfLastLog();
    }

    @NotNull
    @Override
    public LogInterceptorStack getInterceptorStack() {
        return buildLogger.getInterceptorStack();
    }

    @NotNull
    @Override
    public LogMutatorStack getMutatorStack() {
        return buildLogger.getMutatorStack();
    }

    @Override
    public void close() {
        buildLogger.close();
    }

    public BuildLogger getRawBuildLogger() {
        return buildLogger;
    }

    public String getTaskLog() {
        return taskLog.toString();
    }

    public String getTaskErrorLog() {
        return taskErrorLog.toString();
    }
}
