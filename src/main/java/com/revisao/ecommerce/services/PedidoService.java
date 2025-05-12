package com.revisao.ecommerce.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revisao.ecommerce.dto.ItemDoPedidoDTO;
import com.revisao.ecommerce.dto.PedidoDTO;
import com.revisao.ecommerce.entities.ItemDoPedido;
import com.revisao.ecommerce.entities.Pedido;
import com.revisao.ecommerce.entities.Produto;
import com.revisao.ecommerce.entities.StatusDoPedido;
import com.revisao.ecommerce.entities.Usuario;
import com.revisao.ecommerce.repositories.PedidoRepository;
import com.revisao.ecommerce.repositories.ProdutoRepository;
import com.revisao.ecommerce.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private UsuarioRepository repoUsu;

    @Transactional
public PedidoDTO inserir(PedidoDTO dto) {
    Pedido pedido = new Pedido();
    pedido.setMomento(Instant.now());
    pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);

    if (dto.getUsuarioid() == null) {
        throw new IllegalArgumentException("ID do usuário não pode ser nulo");
    }

    Usuario cliente = repoUsu.findById(dto.getUsuarioid())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    pedido.setCliente(cliente);

    if (dto.getItems() == null || dto.getItems().isEmpty()) {
        throw new IllegalArgumentException("O pedido deve conter pelo menos um item");
    }

    for (ItemDoPedidoDTO itemDto : dto.getItems()) {
        ItemDoPedido item = new ItemDoPedido();
        item.setPedido(pedido);
        item.setQuantidade(itemDto.getQuantidade());
        item.setPreco(itemDto.getPreco());

        if (itemDto.getProdutoId() == null) {
            throw new IllegalArgumentException("ID do produto não pode ser nulo");
        }

        Produto produto = produtoRepository.findById(itemDto.getProdutoId())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        item.setProduto(produto);

        pedido.getItems().add(item);
    }

    pedido = repo.save(pedido);
    return new PedidoDTO(pedido);
    } 

    @Transactional
    public List<PedidoDTO> findAll() {
        List<Pedido> pedidos = repo.findAll();
        return pedidos.stream()
                      .map(PedidoDTO::new)
                      .collect(Collectors.toList());
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> obj = repo.findById(id);
        Pedido pedido = obj.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return new PedidoDTO(pedido);
    }

    @Transactional
    public PedidoDTO update(Long id, PedidoDTO dto) {
        try {
            Pedido pedido = repo.getReferenceById(id);
            pedido.setStatus(dto.getStatus());
            pedido = repo.save(pedido);
            return new PedidoDTO(pedido);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o pedido", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o pedido", e);
        }
    }
}