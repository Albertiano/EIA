package br.net.eia.produto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.net.eia.contato.Contato;
import br.net.eia.contato.ContatoDAO;
import br.net.eia.contato.Contato_;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.produto.unidade.UnidadeDAO;
import br.net.eia.emitente.Emitente;

@Repository
public class ProdutoDAO extends JpaDAO<Produto> {
	@Autowired
	UnidadeDAO uDao;
	@Autowired
	ContatoDAO fDao;
	@Autowired
	EmitenteDAO EDao;

	public ProdutoDAO() {
		super(Produto.class);
	}

	public List<Produto> filtrar(Integer xEmitente, String codigo,
			String descricao, String unidade) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		query.select(from);

		List<Predicate> predicados = new ArrayList<Predicate>();

		Predicate pAtivo = builder.equal(from.get(Produto_.active), true);
		predicados.add(pAtivo);

		try {
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(
					from.<Emitente> get(Produto_.emitente), emitente);
			predicados.add(pEmitente);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

		// varios predicados construidos dinamicamente............
		if ((codigo != null) && (!(codigo.isEmpty()))
				&& (!codigo.equalsIgnoreCase("todos"))) {
			try {
				predicados.add(builder.like(
						builder.upper(from.<String> get(Produto_.codigo)),
						codigo.toUpperCase()));
			} catch (Exception e) {

			}

		}

		if ((descricao != null) && (!(descricao.isEmpty()))
				&& (!descricao.equalsIgnoreCase("todos"))) {
			try {
				predicados.add(builder.like(
						builder.upper(from.<String> get(Produto_.descricao)),
						"%" + descricao.toUpperCase() + "%"));
			} catch (Exception e) {

			}

		}

		if ((unidade != null) && (!(unidade.isEmpty()))
				&& (!unidade.equalsIgnoreCase("todos"))) {
			Unidade pais = uDao.retrieve(Long.parseLong(unidade));
			try {
				Predicate pPais = builder.equal(
						from.<Unidade> get(Produto_.unidade), pais);
				predicados.add(pPais);
			} catch (Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.ERROR,
						e.getLocalizedMessage(), e);
			}

		}

		// cria o where com as clausulas de filtro da select
		if (predicados.size() > 0) {
			query.where(builder.and(predicados.toArray(new Predicate[] {})));
		}

		TypedQuery<Produto> q = em.createQuery(query);

		List<Produto> results = q.getResultList();

		return results;
	}

	@Transactional
	public Produto create(Produto p) {
		if (p.getCodigo() == null || p.getCodigo().isEmpty()) {
			p.setCodigo(String.valueOf(super.maxId() + 1));
		}
		this.em.persist(p);
		this.em.flush();
		return p;

	}

	public Produto pesquisar(Long idFornecedor, String codFornecedor) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);
		query.select(root);

		Contato fornec = fDao.retrieve(idFornecedor);

		Join<Produto, FornecedorProduto> prods = root
				.join(Produto_.fornecedores);

		Join<FornecedorProduto, Contato> fProds = prods
				.join(FornecedorProduto_.fornecedor);

		List<Predicate> predicados = new ArrayList<Predicate>();

		predicados
				.add(builder.equal(root.<Boolean> get(Produto_.active), true));

		predicados
				.add(builder.equal(fProds.get(Contato_.id), fornec.getId()));

		predicados.add(builder.like(
				builder.upper(prods.get(FornecedorProduto_.codFornecedor)),
				codFornecedor.toUpperCase()));

		// cria o where com as clausulas de filtro da select
		if (predicados.size() > 0) {
			query.where(builder.and(predicados.toArray(new Predicate[] {})));
		}

		TypedQuery<Produto> q = em.createQuery(query);
		Produto results = null;
		try {
			results = q.getSingleResult();
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

		return results;
	}

	public Produto pesquisar(Integer xEmitente, String codigo) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		query.select(from);

		List<Predicate> predicados = new ArrayList<Predicate>();

		Predicate pAtivo = builder.equal(from.get(Produto_.active), true);
		predicados.add(pAtivo);

		try {
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(
					from.<Emitente> get(Produto_.emitente), emitente);
			predicados.add(pEmitente);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

		// varios predicados construidos dinamicamente............
		if ((codigo != null) && (!(codigo.isEmpty()))
				&& (!codigo.equalsIgnoreCase("todos"))) {
			try {
				predicados.add(builder.like(
						from.<String> get(Produto_.codigo),
						codigo));
			} catch (Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.ERROR,
						e.getLocalizedMessage(), e);
			}

		}

		// cria o where com as clausulas de filtro da select
		if (predicados.size() > 0) {
			query.where(builder.and(predicados.toArray(new Predicate[] {})));
		}

		TypedQuery<Produto> q = em.createQuery(query);

		Produto results = null;

		try {
			results = q.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erro >> ProdutoDAO.pesquisar(String codigo)");
			System.out.println(e.getMessage());
		}

		return results;
	}

}
