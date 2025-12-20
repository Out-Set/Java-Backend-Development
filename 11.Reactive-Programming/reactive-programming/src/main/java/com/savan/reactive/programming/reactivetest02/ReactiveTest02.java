package com.savan.reactive.programming.reactivetest02;

import reactor.core.publisher.Flux;

public class ReactiveTest02 {
    /*
    static Flux wordFlux(int cases){
        return switch (cases){
            case 1-> Flux.just("hello", "world", "good", "morning").log();
            case 2-> Flux.error(new RuntimeException("some error occurred")).log();
            default -> Flux.empty().log();
        };
    }

    // main method call
    wordFlux(1).subscribe(new FluxSubscriber());
    wordFlux(2).subscribe(new FluxSubscriber());
    wordFlux(10).subscribe(new FluxSubscriber());
    */

    static Flux wordFlux(int cases){
        return Flux.just("hello", "world", "good", "morning")
                .map(word-> {
                    if(word.equals("good")){
                        throw new RuntimeException("an exception occurred from the subscriber");
                    }
                    return word;
                });
    }

    public static void main(String[] args) {
        wordFlux(10).subscribe(word-> System.out.println(word));
    }


}
