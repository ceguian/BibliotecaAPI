package com.aluracursos.desafio.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors")List<DatosAutores> autores,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("download_count") int cuentaDescargas) {
    @Override
    public String toString() {
        return  "********************************************" + "\n" +
                 "Titulo = " + titulo + "\n " +
                "Autor(es) = " + autores + "\n " +
                "Idioma(s) = " + idiomas + "\n " +
                "Cuenta de descargas = " + cuentaDescargas + "\n" +
                "********************************************" + "\n";
    }
}
