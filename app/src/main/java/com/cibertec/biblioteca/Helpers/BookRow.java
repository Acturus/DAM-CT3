package com.cibertec.biblioteca.Helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class BookRow {
    private String codigo;
    private String titulo;
    private String estado;
    private String tipo;
}
