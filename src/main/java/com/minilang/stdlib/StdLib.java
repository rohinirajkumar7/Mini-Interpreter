package com.minilang.stdlib;

import com.minilang.middle.semantic.Environment;

public class StdLib {
    public static void load(Environment env){
        env.define("print",(BuiltinFunction)(args)->{ System.out.println(args[0]); return null; });
    }
    @FunctionalInterface
    public interface BuiltinFunction{
        Object call(Object[] args);
    }
}
