/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.subscribers;

import java.lang.reflect.InvocationTargetException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.essms.auth.entities.Branch;
import com.essms.hibernate.core.models.queue.BranchModel;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.tenant.context.TenantContext;

/**
 * @author gaurav
 *
 */
@Component
public class BranchQueueSubscriber {

	@Autowired
	private GenericRepository genericRepository;

	@RabbitListener(queues = "${location.rabbitmq.queue.branch}")
	public void recievedMessage(BranchModel branchModel) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		TenantContext.setCurrentTenant(branchModel.getTenantId());
		Branch branch = new Branch();
		branch.setId(branchModel.getId());
		branch.setCode(branchModel.getCode());
		branch.setName(branchModel.getName());
		branch.setEmailId(branchModel.getEmailId());
		branch.setGstinNumber(branchModel.getGstinNumber());
		branch.setIsServiceCenter(branchModel.getIsServiceCenter());
		branch.setOfficeType(branchModel.getOfficeType());
		branch.setCreatedBy(branchModel.getCreatedBy());
		branch.setCreatedDate(branchModel.getCreatedDate());
		branch.setGuid(branchModel.getGuid());
		branch.setIsActive(branchModel.getIsActive());
		branch.setIsDeleted(branchModel.getIsDeleted());
		branch.setLastModifiedBy(branchModel.getLastModifiedBy());
		branch.setLastModifiedDate(branchModel.getLastModifiedDate());
		genericRepository.saveOrUpdate(branch);
		TenantContext.clear();
	}

}
