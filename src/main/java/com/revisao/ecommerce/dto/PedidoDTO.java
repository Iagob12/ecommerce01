package com.revisao.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.revisao.ecommerce.entities.ItemDoPedido;
import com.revisao.ecommerce.entities.Pedido;
import com.revisao.ecommerce.entities.StatusDoPedido;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant momento;
    private StatusDoPedido status;
    private Long usuarioid; // Renomeado de clienteId para usuarioid

    private List<ItemDoPedidoDTO> items = new ArrayList<>();

    // Construtores

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, Instant momento, StatusDoPedido status, Long usuarioid) {
        this.id = id;
        this.momento = momento;
        this.status = status;
        this.usuarioid = usuarioid;
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.momento = pedido.getMomento();
        this.status = pedido.getStatus();
        this.usuarioid = pedido.getCliente().getId(); // Atualizado para usuarioid
        for (ItemDoPedido item : pedido.getItems()) {
            ItemDoPedidoDTO itemDto = new ItemDoPedidoDTO(item);
            items.add(itemDto);
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMomento() {
        return momento;
    }

    public void setMomento(Instant momento) {
        this.momento = momento;
    }

    public StatusDoPedido getStatus() {
        return status;
    }

    public void setStatus(StatusDoPedido status) {
        this.status = status;
    }

    public Long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Long usuarioid) {
        this.usuarioid = usuarioid;
    }

    public List<ItemDoPedidoDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDoPedidoDTO> items) {
        this.items = items;
    }
}