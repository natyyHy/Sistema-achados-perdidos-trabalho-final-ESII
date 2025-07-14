package br.edu.ifpi.catce.sistemaachadoseperdidos.repository;

import br.edu.ifpi.catce.sistemaachadoseperdidos.model.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<AlunoModel, Long> {

    @Query("SELECT a FROM AlunoModel a WHERE a.nome like :primeiroNome%")
    List<AlunoModel> findByPrimeiroNome(@Param("primeiroNome") String primeiroNome);

}
