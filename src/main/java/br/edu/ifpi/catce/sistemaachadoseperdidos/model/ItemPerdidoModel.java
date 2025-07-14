package br.edu.ifpi.catce.sistemaachadoseperdidos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item_perdido")
public class ItemPerdidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private boolean encontrado = false;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private AlunoModel aluno;

    public boolean isEncontrado() { return encontrado; }

}
