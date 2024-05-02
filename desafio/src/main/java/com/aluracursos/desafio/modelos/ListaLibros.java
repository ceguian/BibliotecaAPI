package com.aluracursos.desafio.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaLibros(
        @JsonAlias("results") List<DatosLibro> resultado) {
    @Override
    public String toString() {
        return
                "Resultado = " + resultado;
    }
}
