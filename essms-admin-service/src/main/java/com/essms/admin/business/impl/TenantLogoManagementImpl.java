/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essms.admin.business.TenantLogoManagement;
import com.essms.admin.entities.TenantLogo;
import com.essms.admin.models.Logo;
import com.essms.hibernate.core.repository.GenericRepository;

/**
 * @author gaurav
 *
 */
@Service
public class TenantLogoManagementImpl implements TenantLogoManagement {

	@Autowired
	private GenericRepository genericRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.admin.business.TenantLogoManagement#upload(com.ssms.admin.entities.
	 * TenantLogo)
	 */
	@Override
	public void upload(Logo logo) {
		TenantLogo tenantLogo = null;
		List<TenantLogo> tenantLogos = genericRepository.findAll(TenantLogo.class);
		if (tenantLogos != null && !tenantLogos.isEmpty()) {
			tenantLogo = tenantLogos.get(0);
		} else {
			tenantLogo = new TenantLogo();
		}
		tenantLogo.setFileName(logo.getFileName());
		tenantLogo.setFileType(logo.getFileType());
		tenantLogo.setLogo(logo.getBase64EncodedLogo());
		genericRepository.saveOrUpdate(tenantLogo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.admin.business.TenantLogoManagement#view()
	 */
	@Override
	public Logo view() {
		Logo logo = null;
		TenantLogo tenantLogo = null;
		List<TenantLogo> tenantLogos = genericRepository.findAll(TenantLogo.class);
		if (tenantLogos != null && !tenantLogos.isEmpty()) {
			tenantLogo = tenantLogos.get(0);
			logo = new Logo();
			logo.setFileName(tenantLogo.getFileName());
			logo.setFileType(tenantLogo.getFileType());
			logo.setBase64EncodedLogo(tenantLogo.getLogo());
		}
		return logo;
	}

}
