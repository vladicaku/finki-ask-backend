package finki.ask.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.view.View;

@MappedSuperclass
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Public.class)
	protected Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity be = (BaseEntity) obj;

		if (this.getId() == null || be.getId() == null) {
			return super.equals(obj);
		} else {
			return this.getId().equals(be.getId());
		}
	}

	@Override
	public int hashCode() {
		return this.id != null ? (this.getClass() + "-" + this.id).hashCode()
				: super.hashCode();
	}
}
