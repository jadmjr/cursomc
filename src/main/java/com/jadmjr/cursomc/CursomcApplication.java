package com.jadmjr.cursomc;

import java.util.ArrayList;
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
import com.jadmjr.cursomc.domain.Produto;
import com.jadmjr.cursomc.domain.enums.TipoCliente;
import com.jadmjr.cursomc.repositories.CategoriaRepository;
import com.jadmjr.cursomc.repositories.CidadeRepository;
import com.jadmjr.cursomc.repositories.ClienteRepository;
import com.jadmjr.cursomc.repositories.EnderecoRepository;
import com.jadmjr.cursomc.repositories.EstadoRepository;
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

	}

}
