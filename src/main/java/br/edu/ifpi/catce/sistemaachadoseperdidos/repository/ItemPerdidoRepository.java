package br.edu.ifpi.catce.sistemaachadoseperdidos.repository;

import br.edu.ifpi.catce.sistemaachadoseperdidos.model.ItemPerdidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPerdidoRepository extends JpaRepository<ItemPerdidoModel, Long> {
}
