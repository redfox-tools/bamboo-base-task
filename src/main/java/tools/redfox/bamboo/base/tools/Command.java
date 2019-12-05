package tools.redfox.bamboo.base.tools;

import com.atlassian.bamboo.process.CommandlineStringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Command extends ArrayList<String> {
    private Command(@NotNull Collection<? extends String> c) {
        super(c);
    }

    public static Command fromString(String command) {
        return new Command(
                CommandlineStringUtils.tokeniseCommandline(command)
        );
    }

    public void add(int idx, String ...args) {
        addAll(idx, Arrays.asList(args));
    }

    public void addParsed(int idx, String args) {
        addAll(idx, CommandlineStringUtils.tokeniseCommandline(args));
    }

    @Override
    public String toString() {
        return String.join(" ", this);
    }
}
