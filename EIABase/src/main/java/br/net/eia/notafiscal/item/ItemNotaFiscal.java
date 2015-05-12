package br.net.eia.notafiscal.item;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import br.net.eia.entities.BaseEntity;
import br.net.eia.item.Item;

@Entity
public class ItemNotaFiscal extends BaseEntity{
	@Embedded
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public int getnItem() {
		return item.getnItem();
	}

	public void setnItem(int nItem) {
		this.item.setnItem(nItem);
	}		
}
