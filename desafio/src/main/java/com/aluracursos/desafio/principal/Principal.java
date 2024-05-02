package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.modelos.DatosLibro;
import com.aluracursos.desafio.modelos.ListaLibros;
import com.aluracursos.desafio.servicios.ConexionApi;
import com.aluracursos.desafio.servicios.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConexionApi conexionApi = new ConexionApi();
    private final String URLBASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();

    //métodos

    //filtro: búsqueda por nombre del libro.

public void busquedaTitulo() throws JsonProcessingException {
    System.out.println("Ingresa el título del libro que deseas buscar");
    var busqueda = scanner.nextLine();

    var json = conexionApi.conectar(URLBASE);
    var datos = convierteDatos.obtenerDatos(json, ListaLibros.class);

    List<DatosLibro> libros = new ArrayList<>(datos.resultado());
    Optional<DatosLibro> libroBuscado = libros.stream()
            .filter(e->e.titulo().toUpperCase().contains(busqueda.toUpperCase())).findFirst();
    if (libroBuscado.isPresent()){
        System.out.println("Libro encontrado");
        System.out.println("Datos del libro \n" + libroBuscado.get() + "\n");
    }else {
        System.out.println("Libro no encontrado" + "\n");
    }
}

   //Top 5 mejores libros
    public void topCinco() throws JsonProcessingException {
        var json = conexionApi.conectar(URLBASE);
        var datos = convierteDatos.obtenerDatos(json, ListaLibros.class);
        System.out.println("Top 5 libros más descargados");
        List<DatosLibro> libros = new ArrayList<>(datos.resultado());
        libros.stream()
                .sorted(Comparator.comparing(DatosLibro::cuentaDescargas).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    //Filtro por autor y libro

    public void buscarAutor() throws JsonProcessingException {
        System.out.println("Ingresa el nombre del autor o libro que deseas buscar");
        var busqueda = scanner.nextLine();

        var json = conexionApi.conectar(URLBASE + "?search=" + busqueda.replace(" ","+"));
        var datos = convierteDatos.obtenerDatos(json, ListaLibros.class);
        List<DatosLibro> libros;
            try{
            libros = new ArrayList<>(datos.resultado());
                if (libros.size() == 0){
                    System.out.println("Sin resultados" + "\n");
                }else{
                    libros.forEach(System.out::println);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
}

    //Estadisticas de descargas

    public void estadisticas() throws JsonProcessingException {
        var json = conexionApi.conectar(URLBASE);
        var datos = convierteDatos.obtenerDatos(json, ListaLibros.class);
        List<DatosLibro> libros = new ArrayList<>(datos.resultado());
        IntSummaryStatistics est = libros.stream()
                .collect(Collectors.summarizingInt(DatosLibro::cuentaDescargas));

        System.out.println("Libro con más descargas");
        libros.stream().filter(e->e.cuentaDescargas()== est.getMax()).map(DatosLibro::titulo).forEach(System.out::println);
        System.out.println("No. Descargas: " + est.getMax() +"\n");
        System.out.println("Libro con menos descargas");
        libros.stream().filter(e->e.cuentaDescargas()== est.getMin()).map(DatosLibro::titulo).forEach(System.out::println);
        System.out.println("No. Descargas: " + est.getMin() +"\n");

        System.out.println("Estadísticas");
        System.out.println("Mayor número de descargas: " + est.getMax());
        System.out.println("Menor número de descargas: " + est.getMin());
        System.out.println("Media de descargas: " + est.getAverage() +"\n");

    }



}
