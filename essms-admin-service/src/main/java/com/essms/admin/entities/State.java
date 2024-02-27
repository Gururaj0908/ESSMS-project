/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.entities;

import java.util.Optional;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.essms.core.annotation.SkipAudit;
import com.essms.hibernate.core.entities.AuditableBaseEntity;
import com.essms.hibernate.core.models.ListEntity;

/**
 * @author gaurav
 *
 */
@Entity
@SuppressWarnings("serial")
public class State extends AuditableBaseEntity implements ListEntity {

	@Id
	@SkipAudit
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 4)
	private String code;

	@Column(length = 4)
	private String tin;

	@Column(length = 100, nullable = false, unique = true)
	private String name;

	@Transient
	@SkipAudit
	private StateBuilder<?> stateBuilder;

	public State() {
		// Do Nothing
	}

	public State(StateBuilder<? extends StateBuilder<?>> stateBuilder) {
		super(stateBuilder);
		this.id = stateBuilder.id;
		this.code = stateBuilder.code;
		this.tin = stateBuilder.tin;
		this.name = stateBuilder.name;
	}

	/**
	 * Initialize a new builder. To be used for creating a new entity
	 * 
	 * @param invoiceBuilder
	 * @param entity
	 * @return
	 */
	public static StateBuilder<?> initializeBuilder() {
		StateBuilder<?> invoiceBuilder = new StateBuilder<>();
		return invoiceBuilder;
	}

	/**
	 * Get Builder for the existing entity. To be used for updating an entity
	 * 
	 * @param invoiceBuilder
	 * @param entity
	 * @return
	 */
	public StateBuilder<?> getModifier() {
		if (stateBuilder == null) {
			stateBuilder = new StateBuilder<>(this);
		}
		return stateBuilder;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	private void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the code
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code
	 * 
	 * @param code
	 *            the code to set
	 */
	private void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the tin
	 * 
	 * @return the tin
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * Sets the tin
	 * 
	 * @param tin
	 *            the tin to set
	 */
	private void setTin(String tin) {
		this.tin = tin;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public static class StateBuilder<B extends StateBuilder<B>> extends AuditableBaseEntityBuilder<B> {

		private Long id;
		private String code;
		private String tin;
		private String name;

		private final Optional<State> optionalEntity;

		public StateBuilder() {
			this.optionalEntity = Optional.empty();
		}

		public StateBuilder(State entity) {
			super(entity);
			this.optionalEntity = Optional.of(entity);
		}

		/**
		 * @param id the id to set
		 */
		public B setId(Long id) {
			if (optionalEntity.isPresent()) {
				optionalEntity.get().setId(id);
			}
			this.id = id;
			return (B) this;
		}

		/**
		 * Set code if not blank
		 * 
		 * @param code the code to set
		 */
		public B setCode(String code) {
			if (StringUtils.isNotBlank(code)) {
				if (optionalEntity.isPresent()) {
					optionalEntity.get().setCode(code);
				}
				this.code = code;
			}
			return (B) this;
		}

		/**
		 * Set tin if not blank
		 * 
		 * @param tin the tin to set
		 */
		public B setTin(String tin) {
			if (StringUtils.isNotBlank(tin)) {
				if (optionalEntity.isPresent()) {
					optionalEntity.get().setTin(tin);
				}
				this.tin = tin;
			}
			return (B) this;
		}

		/**
		 * Set name if not blank
		 * 
		 * @param name the name to set
		 */
		public B setName(String name) {
			if (StringUtils.isNotBlank(name)) {
				if (optionalEntity.isPresent()) {
					optionalEntity.get().setName(name);
				}
				this.name = name;
			}
			return (B) this;
		}

		/**
		 * Returns a built entity
		 * 
		 * @return
		 */
		public State build() {
			if (optionalEntity.isPresent()) {
				return optionalEntity.get();
			}
			return new State(this);
		}

	}

	@Override
	@PostConstruct
	public void setTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}
}
