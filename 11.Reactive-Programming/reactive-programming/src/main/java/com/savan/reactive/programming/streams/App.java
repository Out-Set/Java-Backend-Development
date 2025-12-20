package com.savan.reactive.programming.streams;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Scanner;
import java.util.stream.Stream;

import static reactor.core.publisher.Signal.subscribe;

public class App {

    public static void main(String[] args) {

        // Streams
        /*
        StreamUtils.wordStream()
                .map(word -> word.toUpperCase())
                .forEach(word -> System.out.println(word));

        // Prints nothing useful?, Because stringStream is just a Stream object, and println only calls its toString() method. It does NOT trigger the stream pipeline.
        // Streams are very lazy. streams are lazy, and nothing executes until a terminal operation is called.
        // We need to apply some terminal-operator (i.e.findFirst(), forEach(), collect(), count() ...)  which trigger the stream pipeline and forcing the stream-api to flow its data.
        Stream<String> stringStream = Stream.of("hello", "world", "fun", "learning");
        System.out.println(stringStream); // java.util.stream.ReferencePipeline$Head@3d075dc0

        Stream<String> s = Stream.of("a", "b");
        s.forEach(System.out::println);
        // s.forEach(System.out::println); // stream has already been operated upon or closed

        Stream.of(1,2,3,4,5)
                .peek(no-> System.out.print("A"+no))
                .peek(no-> System.out.print("B"+no))
                .forEach(no-> System.out.print("C"+no+"\t"));
        // A1B1C1	A2B2C2	A3B3C3	A4B4C4	A5B5C5

        Stream.of(1,2,3,4,5)
                .peek(no-> System.out.print("A"+no))
                .limit(2)
                .peek(no-> System.out.print("B"+no))
                .forEach(no-> System.out.print("C"+no+"\t"));
        // A1B1C1	A2B2C2

        Stream.of(1,2,3,4,5)
                .peek(no-> System.out.print("A"+no))
                .skip(2)
                .peek(no-> System.out.print("B"+no))
                .forEach(no-> System.out.print("C"+no+"\t"));
        // A1A2 A3B3C3	A4B4C4	A5B5C5
        */


        // Reactive Streams
        ReactiveStreamUtils.wordReactiveStream()
                .map(word -> word.toUpperCase())
                .subscribe(word -> System.out.println(word));

        // Flux behaves very similar to Java Streams in this case — printing the Flux object does NOT trigger any data processing.
        // Flux executes ONLY when subscribed. No subscription = no execution.
        Flux<String> stringFlux = ReactiveStreamUtils.wordReactiveStream();
        System.out.println(stringFlux); // FluxArray

        // One publisher can have multiple subscribers.
        // Every subscriber gets its own independent data stream.
        // The source is replayed from the beginning for every subscription.
        Flux<Integer> integerFlux = Flux.just(1,2,3,4,5);
        integerFlux.subscribe(no -> System.out.print(no+"\t")); // 1   2   3   4   5
        integerFlux.subscribe(no -> System.out.print(no+"\t")); // 1   2   3   4   5

        // It will emit values synchronously, one by one, to the subscriber.
        Flux.just("hello", "world", "fun", "learning")
                .delayElements(Duration.ofMillis(2000))
                .subscribe(word -> System.out.println(word));
        Scanner scanner = new Scanner(System.in);
        System.out.println("press any key to exit the program");
        scanner.nextLine(); // Hold the terminal till input
    }
}

/*
Add this dependency into the pom.xml for reactive stream
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.8.0</version>
        </dependency>
*/