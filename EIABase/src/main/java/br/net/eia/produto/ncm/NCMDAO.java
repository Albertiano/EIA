package br.net.eia.produto.ncm;

import org.springframework.stereotype.Repository;

import br.net.eia.persistence.JpaDAO;

@Repository
public class NCMDAO  extends JpaDAO<NCM> {
	public NCMDAO(){
		super(NCM.class);
	}
}
