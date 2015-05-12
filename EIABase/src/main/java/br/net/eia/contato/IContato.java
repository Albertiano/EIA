package br.net.eia.contato;

import br.net.eia.enums.TpDoc;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.notafiscal.adicionais.TpDestinatario;
import br.net.eia.pais.Pais;

public interface IContato {

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getTpDoc()
	 */
	public abstract TpDoc getTpDoc();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getNumDoc()
	 */
	public abstract String getNumDoc();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getIE()
	 */
	public abstract String getIE();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getNome()
	 */
	public abstract String getNome();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getLogradouro()
	 */
	public abstract String getLogradouro();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getNumero()
	 */
	public abstract String getNumero();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getComplemento()
	 */
	public abstract String getComplemento();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getBairro()
	 */
	public abstract String getBairro();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getCep()
	 */
	public abstract String getCep();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getUf()
	 */
	public abstract UF getUf();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getMunicipio()
	 */
	public abstract Municipio getMunicipio();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getPais()
	 */
	public abstract Pais getPais();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getFone()
	 */
	public abstract String getFone();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getEmail()
	 */
	public abstract String getEmail();

	/* (non-Javadoc)
	 * @see br.net.eia.cliente.IContato#getFantasia()
	 */
	public abstract String getFantasia();

	public abstract TpDestinatario getTpDest();

}