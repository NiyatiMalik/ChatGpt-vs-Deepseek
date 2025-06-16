package com.deepseek.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Hint {
    private int order;
    private String content;

    public Hint() {}

    public Hint(int order, String content) {
        this.order = order;
        this.content = content;
    }
}
