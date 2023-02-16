/*
 * Decompiled with CFR 0.152.
 */
package dev.zenhao.xin.manager;

public class CheckManager {
    private final Result result;
    private String subCheck;
    private String message;
    private int data;

    public CheckManager(Result result, String message, int data) {
        this(result, message);
        this.data = data;
    }

    public CheckManager(Result result, String subcheck, String message) {
        this(result);
        this.subCheck = subcheck;
        this.message = message;
    }

    public CheckManager(Result result, String message) {
        this(result);
        this.message = message;
    }

    public CheckManager(Result result) {
        this.result = result;
    }

    public boolean failed() {
        return this.result == Result.FAILED;
    }

    public String getSubCheck() {
        return this.subCheck;
    }

    public String getMessage() {
        return this.message;
    }

    public Result getResult() {
        return this.result;
    }

    public int getData() {
        return this.data;
    }

    public static enum Result {
        PASSED,
        FAILED;

    }
}

