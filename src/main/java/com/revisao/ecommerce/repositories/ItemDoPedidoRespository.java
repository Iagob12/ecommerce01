package com.revisao.ecommerce.repositories;

import com.revisao.ecommerce.entities.ItemDoPedido;
import com.revisao.ecommerce.entities.ItemDoPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDoPedidoRespository  extends JpaRepository<ItemDoPedido, ItemDoPedidoPK> {
}
