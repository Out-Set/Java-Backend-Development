package com.savan.reactive.programming.streams;

import java.util.stream.Stream;

public class StreamUtils {
    public static Stream<String> wordStream(){
        return Stream
                .of("hello", "world", "fun", "learning");
    }
}
