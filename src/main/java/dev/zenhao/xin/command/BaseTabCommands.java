/*
 * Decompiled with CFR 0.152.
 */
package dev.zenhao.xin.command;

import dev.zenhao.xin.command.BaseCommands;
import java.util.List;

public abstract class BaseTabCommands
extends BaseCommands {
    private List<String> tabCompletions;

    public BaseTabCommands(String name, String usage, String permission) {
        super(name, usage, permission);
    }

    public BaseTabCommands(String name, String usage, String permission, String description) {
        super(name, usage, permission, description);
    }

    public BaseTabCommands(String name, String usage, String permission, String description, String[] subCommands) {
        super(name, usage, permission, description, subCommands);
    }

    public abstract List<String> onTab(String[] var1);

    public List<String> getTabCompletions() {
        return this.tabCompletions;
    }

    public void setTabCompletions(List<String> tabCompletions) {
        this.tabCompletions = tabCompletions;
    }
}

