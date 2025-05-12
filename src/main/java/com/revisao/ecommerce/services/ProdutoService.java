package com.revisao.ecommerce.services;

import com.revisao.ecommerce.dto.ProdutoDTO;
import com.revisao.ecommerce.entities.Produto;
import com.revisao.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional(readOnly = true)
	public List<ProdutoDTO> findAll() {
		List<Produto> produtos = produtoRepository.findAll();
		return produtos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findPageable(Pageable pageable) {
		Page<Produto> produtos = produtoRepository.findAll(pageable);
		return produtos.map(ProdutoDTO::new);
	}

	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id) {
		Optional<Produto> obj = produtoRepository.findById(id);
		Produto entidade = obj.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		return new ProdutoDTO(entidade);
	}

	@Transactional
	public ProdutoDTO insert(ProdutoDTO dto) {
		Produto entidade = new Produto();
		copiarDtoParaEntidade(dto, entidade);
		entidade = produtoRepository.save(entidade);
		return new ProdutoDTO(entidade);
	}

	@Transactional
	public ProdutoDTO update(Long id, ProdutoDTO dto) {
		try {
			Produto entidade = produtoRepository.getReferenceById(id);
			copiarDtoParaEntidade(dto, entidade);
			entidade = produtoRepository.save(entidade);
			return new ProdutoDTO(entidade);
		} catch (Exception e) {
			throw new RuntimeException("Produto não encontrado para atualização");
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			produtoRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar remover o produto, produto não encontrado");
		}
	}

	// Método auxiliar para copiar os atributos de um ProdutoDTO para uma entidade Produto
	private void copiarDtoParaEntidade(ProdutoDTO dto, Produto entidade) {
		entidade.setNome(dto.getNome());
		entidade.setDescricao(dto.getDescricao());
		entidade.setPreco(dto.getPreco());
		entidade.setImgUrl(dto.getImgUrl());
	}
}