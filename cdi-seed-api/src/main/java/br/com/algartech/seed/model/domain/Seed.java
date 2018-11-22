package br.com.algartech.seed.model.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "seed")
public class Seed extends CommonEntity {
	private static final long serialVersionUID = 1977630426225837914L;

	@NotNull
	@NotEmpty
	private String description;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	public Seed() {
	}

	public Seed(@NotNull @NotEmpty String description, @NotNull @NotEmpty @Email String email) {
		this.description = description;
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void update(Long id, Seed seed) {
		super.setId(id);
		this.description = seed.getDescription();
		this.email = seed.getEmail();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seed other = (Seed) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seed [description=" + description + ", getId()=" + getId() + "]";
	}

}
