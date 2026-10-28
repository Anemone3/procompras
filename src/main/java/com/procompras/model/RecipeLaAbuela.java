package com.procompras.model;

public record RecipeLaAbuela(Long id,
                             String nombre,
                             String url,
                             String ingredientes,
                             String pasos,
                             String pais,
                             String duracion,
                             String porciones,
                             String calorias,
                             String categoria,
                             String contexto,
                             String valoracionYVotos,
                             String comensales,
                             String tiempo,
                             String dificultad,
                             String categoria2) {

    public static RecipeLaAbuela fromRow(String[] row) {
        return new RecipeLaAbuela(
                Long.parseLong(row[0]),
                row[1],
                row[2],
                row[3],
                row[4],
                row[5],
                row[6],
                row[7],
                row[8],
                row[9],
                row[10],
                row[11],
                row[12],
                row[13],
                row[14],
                row[15]
        );
    }
}
