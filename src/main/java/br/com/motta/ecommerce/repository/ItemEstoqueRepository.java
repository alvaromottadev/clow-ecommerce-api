package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
}
