package com.essms.admin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.essms.hibernate.core.entities.AuditableBaseEntity;

/**
 */
@Entity
@Table(name = "static_data")
@SuppressWarnings("serial")
// @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class StaticData extends AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Attribute dataType.
	 */
	@Column(name = "data_type", length = 50, nullable = false)
	private String dataType;

	/**
	 * Attribute dataText.
	 */
	@Column(name = "data_text", length = 100, nullable = false)
	private String dataText;

	/**
	 * Attribute dataValue.
	 */
	@Column(name = "data_value", length = 100, nullable = false)
	private String dataValue;

	/**
	 * Will Return the id
	 *
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Pass the id to be set
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            new value for dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return dataText
	 */
	public String getDataText() {
		return dataText;
	}

	/**
	 * @param dataText
	 *            new value for dataText
	 */
	public void setDataText(String dataText) {
		this.dataText = dataText;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return dataValue
	 */
	public String getDataValue() {
		return dataValue;
	}

	/**
	 * @param dataValue
	 *            new value for dataValue
	 */
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}