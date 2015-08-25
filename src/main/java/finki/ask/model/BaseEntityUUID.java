package finki.ask.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.view.View;

@MappedSuperclass
public class BaseEntityUUID {
	
	@Id
	@JsonView(View.Public.class)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	protected UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
		return this.id != null ? (this.getClass() + "-" + this.id).hashCode() : super.hashCode();
	}
}
