/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.essms.admin.entities.EndCustomerAddress;

/**
 * @author gaurav
 *
 */
@Transactional
public interface EndCustomerAddressRepository extends CrudRepository<EndCustomerAddress, Long> {

	@Modifying
	@Query("update EndCustomerAddress set isDefault = false where endCustomer = (select id from EndCustomer where guid = ?1) and id != ?2")
	void updateDefault(String userGUID, Long addressId);

}
