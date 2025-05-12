package com.revisao.ecommerce.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.revisao.ecommerce.dto.RelatorioPedidoDTO;
import com.revisao.ecommerce.entities.Pedido;
import com.revisao.ecommerce.repositories.PedidoRepository;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public void gerarRelatorioServicePDF(String caminho) {
        if (caminho == null || caminho.isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'caminho' é obrigatório e não pode estar vazio.");
        }
    
        try {
            List<Pedido> pedidos = pedidoRepository.findAll();
    
            List<RelatorioPedidoDTO> dados = pedidos.stream()
                .map(RelatorioPedidoDTO::new)
                .collect(Collectors.toList());
    
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
    
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Relatório de Pedidos");
    
            JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/relatorios/relatorio_pedidos.jrxml")
            );
    
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
    
            JasperExportManager.exportReportToPdfFile(jasperPrint, caminho);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o relatório: " + e.getMessage(), e);
        }
    }
    
}