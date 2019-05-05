package com.jadmjr.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jadmjr.cursomc.domain.Categoria;
import com.jadmjr.cursomc.domain.Cidade;
import com.jadmjr.cursomc.domain.Cliente;
import com.jadmjr.cursomc.domain.Endereco;
import com.jadmjr.cursomc.domain.Estado;
import com.jadmjr.cursomc.domain.ItemPedido;
import com.jadmjr.cursomc.domain.Pagamento;
import com.jadmjr.cursomc.domain.PagamentoComBoleto;
import com.jadmjr.cursomc.domain.PagamentoComCartao;
import com.jadmjr.cursomc.domain.Pedido;
import com.jadmjr.cursomc.domain.Produto;
import com.jadmjr.cursomc.domain.enums.EstadoPagamento;
import com.jadmjr.cursomc.domain.enums.TipoCliente;
import com.jadmjr.cursomc.repositories.CategoriaRepository;
import com.jadmjr.cursomc.repositories.CidadeRepository;
import com.jadmjr.cursomc.repositories.ClienteRepository;
import com.jadmjr.cursomc.repositories.EnderecoRepository;
import com.jadmjr.cursomc.repositories.EstadoRepository;
import com.jadmjr.cursomc.repositories.ItemPedidoRepository;
import com.jadmjr.cursomc.repositories.PagamentoRepository;
import com.jadmjr.cursomc.repositories.PedidoRepository;
import com.jadmjr.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	PagamentoRepository pagamentoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Impressora", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "MINAS GERAIS");
		Estado est2 = new Estado(null, "SAO PAULO");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));

		Cliente cli1 = new Cliente(null, "MARIA SILVA", "jadmjr@gmail.com", "10653063695", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("3432177247", "34992881747"));

		Endereco e1 = new Endereco(null, "RUA PASCHOAL BRUNO", "768", "CASA", "SANTA LUZIA", "38408-714", cli1, cid1);
		Endereco e2 = new Endereco(null, "AV FARROUPILHAS", "701", "CASA FUNDO", "NOSSA SENHORA DAS GRAÇAS",
				"38402-702", cli1, cid2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:ss");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pgmt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgmt1);

		Pagamento pgmt2 = new PagamentoComBoleto(null, EstadoPagamento.PEDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgmt2);

		cli1.getPedidos().addAll((Arrays.asList(ped1, ped2)));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgmt1, pgmt2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));

	}

}
