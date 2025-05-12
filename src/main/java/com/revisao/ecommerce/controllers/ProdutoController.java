package com.revisao.ecommerce.controllers;

import com.revisao.ecommerce.dto.ProdutoDTO;
import com.revisao.ecommerce.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    @Autowired
    ProdutoService produtoService;

    @PostMapping("/adicionar")
    public ProdutoDTO insert(@RequestBody @Valid ProdutoDTO dto) {
    return produtoService.insert(dto);
    }

    @GetMapping
    public List<ProdutoDTO> findAll() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public ProdutoDTO findById(@PathVariable Long id) {
        return produtoService.findById(id);
    }

    @PutMapping("/{id}")
    public ProdutoDTO update(@PathVariable Long id, @RequestBody @Valid ProdutoDTO dto) {
        return produtoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produtoService.delete(id);
    }
}

