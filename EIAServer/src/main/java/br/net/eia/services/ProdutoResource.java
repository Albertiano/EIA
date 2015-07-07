package br.net.eia.services;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.eia.produto.Produto;
import br.net.eia.produto.ProdutoDAO;
import br.net.eia.produto.Produtos;

@Service
@Path("/produto")
public class ProdutoResource extends BaseService<Produto, Produtos> {
	@Autowired
	ProdutoDAO dao;

	public ProdutoResource() {
		super(Produtos.class);
	}

	@Autowired
	public void setPersonDAO(ProdutoDAO dao) {
		setDao(dao);
	}

	@GET
	@Path("/emitente={emitente}&codigo={codigo}&descricao={descricao}&unidade={unidade}")
	public Produtos filtrar(@PathParam("emitente") Integer emitente, @PathParam("codigo") String codigo,
			@PathParam("descricao") String descricao, @PathParam("unidade") String unidade) {
		
		Collection<Produto> entities = dao.filtrar(emitente, codigo, descricao, unidade);
		for (Produto entity : entities) {
			entity.createStandardLinks();
		}
		Produtos collection = encapsulateEntities(entities);
		return collection;
	}
	
	@GET
	@Path("/fornecedor={idFornecedor}&codFornecedor={codFornecedor}")
	public Response pesquisar(@PathParam("idFornecedor") Long idFornecedor, @PathParam("codFornecedor") String codFornecedor) {
		Produto entity = dao.pesquisar(idFornecedor,codFornecedor);
		if (entity != null) {
			entity.createStandardLinks();
			return Response.ok(entity).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/emitente={emitente}&codigo={codigo}")
	public Response pesquisar(@PathParam("emitente") Integer emitente, @PathParam("codigo") String codigo) {
		Produto entity = dao.pesquisar(emitente, codigo);
		if (entity != null) {
			entity.createStandardLinks();
			return Response.ok(entity).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	
}
