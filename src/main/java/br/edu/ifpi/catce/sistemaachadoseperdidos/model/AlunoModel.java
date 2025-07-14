package br.edu.ifpi.catce.sistemaachadoseperdidos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "aluno")
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String identificador;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<ItemPerdidoModel> itensPerdidos;

}
