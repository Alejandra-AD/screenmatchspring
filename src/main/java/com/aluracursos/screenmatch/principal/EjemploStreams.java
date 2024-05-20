package com.aluracursos.screenmatch.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public static void main(String[] args){

        List <String> personas = Arrays.asList("Cappu", "Elsa", "Gigi", "Rocco");
        personas.stream()
                .sorted()
                .forEach(System.out::println);

    }
}
