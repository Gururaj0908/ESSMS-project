/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.subscribers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.essms.admin.entities.Branch;
import com.essms.admin.entities.BranchEvent;
import com.essms.admin.entities.SystemEvent;
import com.essms.core.exception.ApplicationException;
import com.essms.hibernate.core.models.queue.SystemEventModel;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.tenant.context.TenantContext;

/**
 * @author gaurav
 *
 */
@Component
public class SystemEventQueueSubscriber {

	@Autowired
	private GenericRepository genericRepository;

	@Transactional
	@RabbitListener(queues = "${admin.rabbitmq.queue.systemevent}")
	public void recievedMessage(SystemEventModel systemEventModel) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (StringUtils.isBlank(systemEventModel.getBranchGUId())) {
			throw new ApplicationException("Branch GUID not passed");
		}
		TenantContext.setCurrentTenant(systemEventModel.getTenantId());
		Map<String, Object> criterias = new HashMap<>();
		criterias.put("branch.guid", systemEventModel.getBranchGUId());
		criterias.put("systemEvent.systemEventType", systemEventModel.getSystemEventType());
		List<BranchEvent> branchEvents = genericRepository.findByCriteria(BranchEvent.class, criterias);
		BranchEvent branchEvent = null;
		if (branchEvents != null && branchEvents.size() > 0) {
			branchEvent = branchEvents.get(0);
		} else {
			branchEvent = new BranchEvent();
			branchEvent.setBranch(genericRepository.getByGUID(Branch.class, systemEventModel.getBranchGUId()));
			branchEvent.setCount(0l);
			criterias = new HashMap<>();
			criterias.put("systemEventType", systemEventModel.getSystemEventType());
			List<SystemEvent> systemEvents = genericRepository.findByCriteria(SystemEvent.class, criterias);
			if (systemEvents == null || systemEvents.size() == 0) {
				throw new ApplicationException("System event not set : " + systemEventModel.getSystemEventType());
			}
			branchEvent.setSystemEvent(systemEvents.get(0));
		}
		switch (systemEventModel.getOperationType()) {
		case Add:
			if (branchEvent.getCount() == null) {
				branchEvent.getSystemEvent().setTotalCount(1l);
				branchEvent.setCount(1l);
			} else {
				branchEvent.getSystemEvent().setTotalCount(branchEvent.getSystemEvent().getTotalCount() == null ? 0
						: branchEvent.getSystemEvent().getTotalCount() + 1);
				branchEvent.setCount(branchEvent.getCount() + 1);
			}
			break;
		case Minus:
			branchEvent.getSystemEvent().setTotalCount(branchEvent.getSystemEvent().getTotalCount() - 1);
			branchEvent.setCount(branchEvent.getCount() - 1);
			if (branchEvent.getCount() <= 0) {
				branchEvent.setCount(0l);
			}
			if (branchEvent.getSystemEvent().getTotalCount() <= 0) {
				branchEvent.getSystemEvent().setTotalCount(0l);
			}
			break;
		default:
			break;
		}
		genericRepository.saveOrUpdate(branchEvent);
		TenantContext.clear();
	}

}
