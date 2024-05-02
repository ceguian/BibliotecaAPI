package com.aluracursos.desafio.servicios;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConvierteDatos {
     <T> T obtenerDatos(String json, Class<T> tClass) throws JsonProcessingException;

    }

