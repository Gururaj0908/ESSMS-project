/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.subscribers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.essms.auth.entities.SystemUser;
import com.essms.core.logger.ApplicationLogger;
import com.essms.hibernate.core.enums.EntityOperationType;
import com.essms.hibernate.core.models.BaseModel;
import com.essms.hibernate.core.models.queue.EntityOperationModel;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.tenant.context.TenantContext;

/**
 * @author gaurav
 *
 */
@Component
public class EntityOperationQueueSubscriber {

	@Autowired
	private GenericRepository genericRepository;

	@RabbitListener(queues = "${operation.rabbitmq.queue.entity}")
	public void recievedMessage(EntityOperationModel entityOperationModel) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		TenantContext.setCurrentTenant(entityOperationModel.getTenantId());
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("id", StringUtils.join(entityOperationModel.getIds(), ","));
		if (entityOperationModel.getEntityModelClass().getSimpleName().equalsIgnoreCase("SystemUserModel")) {
			performOperation(entityOperationModel.getIds(), SystemUser.class,
					entityOperationModel.getEntityOperationType());
		}
		TenantContext.clear();
	}

	private <T extends BaseModel> void performOperation(Set<?> ids, Class<T> clazz,
			EntityOperationType entityOperationType) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		switch (entityOperationType) {
		case Deleted: {
			deleteEntity(ids, clazz);
			break;
		}
		case Restored: {
			restoreEntity(ids, clazz);
			break;
		}
		default:
			break;
		}
	}

	private <T extends BaseModel> void deleteEntity(Set<?> ids, Class<T> clazz) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		SystemUser systemUser = null;
		Map<String, Object> criterias = new HashMap<>();
		for (Object id : ids) {
			if (clazz.getSimpleName().equalsIgnoreCase("SystemUser")) {
				criterias.put("guid", id);
				systemUser = genericRepository.findByCriteria(SystemUser.class, criterias).get(0);
				systemUser.setEnabled(false);
				genericRepository.saveOrUpdate(systemUser);
			} else {
				try {
					genericRepository.deleteById(clazz, Long.parseLong(String.valueOf(id)));
				} catch (DataIntegrityViolationException ex) {
					ApplicationLogger.logDebug(
							"Could not hard delete, so soft deleting " + clazz.getSimpleName() + " with id : " + id);
					genericRepository.updateDeleteStatusById(clazz, Long.parseLong(String.valueOf(id)), true);
				}
			}
		}
	}

	private <T extends BaseModel> void restoreEntity(Set<?> ids, Class<T> clazz) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		SystemUser systemUser = null;
		Map<String, Object> criterias = new HashMap<>();
		for (Object id : ids) {
			if (clazz.getSimpleName().equalsIgnoreCase("SystemUser")) {
				criterias.put("guid", id);
				systemUser = genericRepository.findByCriteria(SystemUser.class, criterias).get(0);
				systemUser.setEnabled(true);
				genericRepository.saveOrUpdate(systemUser);
			} else {
				genericRepository.updateDeleteStatusById(clazz, Long.parseLong(String.valueOf(id)), false);
			}
		}
	}

}
