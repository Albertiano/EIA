package br.net.eia.movimento;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import br.net.eia.entities.BaseEntity;
import br.net.eia.item.Item;

@Entity
public class ItemMovimento  extends BaseEntity{
	@Embedded
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
