package br.net.eia.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.eia.compra.Compra;
import br.net.eia.compra.CompraDAO;
import br.net.eia.compra.Compras;
import br.net.eia.compra.ItemCompra;
import br.net.eia.produto.Produto;
import br.net.eia.produto.ProdutoDAO;

@Service
@Path("/compra")
public class CompraResource extends BaseService<Compra, Compras> {
	@Autowired
	CompraDAO cDao;
	@Autowired
	ProdutoDAO pDao;

	public CompraResource() {
		super(Compras.class);
	}

	@Autowired
	public void setPersonDAO(CompraDAO dao) {
		setDao(dao);
	}
	
	@PUT
	@Path("/atualizarEntrada/emitente={emitente}")
	public Response atualizarEntrada(@PathParam("emitente") String emitente, Compra entity) {
		if (entity == null || entity.getId() == null) {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
		
		try{
		for(ItemCompra i : entity.getItemCompra()){
			Produto p = i.getProduto();
			BigDecimal estoque = p.getEstoque();			
			p.setEstoque(estoque.add(i.getQuantidade()));
			pDao.update(p);
		}
		
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();			
		}		
		entity = cDao.update(entity);
		return Response.ok(entity).build();
	}
	
	@PUT
	@Path("/atualizarRemover/emitente={emitente}")
	public Response atualizarRemover(@PathParam("emitente") String emitente, Compra entity) {
		if (entity == null || entity.getId() == null) {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
		
		try{
		for(ItemCompra i : entity.getItemCompra()){
			Produto p = i.getProduto();
			BigDecimal estoque = p.getEstoque();			
			p.setEstoque(estoque.subtract(i.getQuantidade()));
			pDao.update(p);
		}
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();			
		}		
		cDao.delete(entity);
		return Response.ok(entity).build();
	}
	
	@GET
	@Path("/emitente={emitente}&dataIni={dataIni}&dataFim={dataFim}&pedido={pedido}&notaFiscal={notaFiscal}&fornecedor={fornecedor}")
	public Compras filtrar(@PathParam("emitente") Integer emitente, @PathParam("dataIni") long xDataIni,
			@PathParam("dataFim") long xDataFim, @PathParam("pedido") String pedido, @PathParam("notaFiscal") String notaFiscal, @PathParam("fornecedor") String fornecedor) {
		
		Date dataIni = null;
		Date dataFim = null;
		if(xDataIni > -1){
			dataIni = new Date(xDataIni);
		}
		if(xDataFim > -1){
			dataFim = new Date(xDataFim);
		}
		
		Collection<Compra> entities = cDao.pesquisar(emitente, dataIni, dataFim, pedido, notaFiscal, fornecedor);
		for (Compra entity : entities) {
			entity.createStandardLinks();
		}
		Compras collection = encapsulateEntities(entities);
		return collection;
	}
	
}
