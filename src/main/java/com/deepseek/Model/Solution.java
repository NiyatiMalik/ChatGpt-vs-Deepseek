package com.deepseek.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Solution {
    private String approach;
    private String code;
    private String explanation;

    public Solution() {}

    public Solution(String approach, String code, String explanation) {
        this.approach = approach;
        this.code = code;
        this.explanation = explanation;
    }

}
