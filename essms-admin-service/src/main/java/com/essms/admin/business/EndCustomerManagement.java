/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.essms.admin.entities.EndCustomer;
import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.request.CreateEndCustomerRequest;
import com.essms.admin.models.request.UpdateEndCustomerRequest;
import com.essms.admin.models.response.UploadFileResponse;
import com.essms.hibernate.core.models.queue.EndCustomerRegistration;

/**
 * @author gaurav
 *
 */
public interface EndCustomerManagement {

	/**
	 *
	 * @param createRequest
	 * @param entity
	 * @return
	 */
	EndCustomer createEndCustomer(CreateEndCustomerRequest createRequest, EndCustomer entity, String roleGUIDs);

	/**
	 *
	 * @param entity
	 * @param createRequest
	 */
	void afterCreate(EndCustomer entity, CreateEndCustomerRequest createRequest);

	/**
	 *
	 * @param updateRequest
	 * @param entity
	 * @return
	 */
	EndCustomer updateEndCustomer(UpdateEndCustomerRequest updateRequest, EndCustomer entity);

	/**
	 * @param entity
	 * @param customerAddressModels
	 */
	void updateAddress(EndCustomer entity, List<EndCustomerAddressModel> customerAddressModels);

	/**
	 * Get Customer Details
	 * 
	 * @param customerGUID
	 * @return
	 */
	EndCustomerRegistration detail(String customerGUID);

	/**
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 *
	 */
	UploadFileResponse uploadImage(MultipartFile multipartFile) throws IOException;

}
