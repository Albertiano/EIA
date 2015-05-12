package br.net.eia.persistence;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import br.net.eia.entities.BaseEntity;

public abstract class JpaDAO<T extends BaseEntity> extends GenericDao<T> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> managedClass;

	public JpaDAO(Class<T> managedClass) {
		this.managedClass = managedClass;
	}

	public JpaDAO(Class<T> managedClass, EntityManager jpaTemplate) {
		this.managedClass = managedClass;
		this.em = jpaTemplate;
	}

	public Class<T> getManagedClass(){
		return managedClass;
	}
	
	@Transactional
	public T create(T entity) {
		this.em.persist(entity);
		this.em.flush();
		return entity;
	}

	public T retrieve(Long id) {
		T found = this.em.find(managedClass, id);
		if (found != null && !found.getActive()) {
			return null;
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	public Collection<T> retrieve() {
		StringBuilder query = new StringBuilder("select entity from ").append(
				managedClass.getSimpleName()).append(
				" entity where entity.active=true");
		return this.em.createQuery(query.toString()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public Collection<T> retrieve(final int pageNumber) {
		final StringBuilder query = new StringBuilder("select entity from ")
				.append(managedClass.getSimpleName()).append(
						" entity where entity.active=true");
		return this.em.createQuery(query.toString())
		.setFirstResult(pageNumber * getPageSize())
		.setMaxResults(getPageSize())
		.getResultList();
	}

	public long count() {
		final StringBuilder query = new StringBuilder(
				"select count(entity) from ").append(
				managedClass.getSimpleName()).append(
				" entity where entity.active=true");
		return (Long) this.em.createQuery(query.toString()).getSingleResult();

	}
	
	public long maxId() {
		final StringBuilder query = new StringBuilder(
				"select max(entity.id) from ").append(
				managedClass.getSimpleName()).append(
				" entity where entity.active=true");
		long maxId=0;
		Query consulta = this.em.createQuery(query.toString());
		if(consulta.getSingleResult()!=null){
			maxId = (long) consulta.getSingleResult();
		}
		return maxId;

	}

	@Transactional
	public T update(T entity) {

		entity.setUpdateDate(new Date());
		em.merge(entity);
		return entity;
	}

	@Transactional
	public void delete(T entity) {
		// Doesn't really delete, only deactivate it
		entity.setActive(false);
		update(entity);
	}
}
