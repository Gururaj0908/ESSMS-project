/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.essms.admin.business.TenantThemeManagement;
import com.essms.admin.constants.APIConstant;
import com.essms.admin.entities.TenantConfiguration;
import com.essms.admin.entities.TenantThemeConfiguration;
import com.essms.admin.models.request.UpsertTenantThemeConfigRequest;
import com.essms.admin.utils.CSSUtil;
import com.essms.core.exception.ApplicationException;
import com.essms.core.logger.ApplicationLogger;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.enums.MethodType;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.core.utils.FormGeneratorUtil;

/**
 * @author gaurav
 *
 */
@Service
public class TenantThemeManagementImpl implements TenantThemeManagement {

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Value("${repo.file.system.path}")
	private String repoFileSystem;

	@Override
	public FormList form() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		UpsertTenantThemeConfigRequest upsertTenantThemeConfigRequest = null;
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("tenantId", UserInfoUtil.getTenantId());
		final List<TenantThemeConfiguration> tenantThemeConfigurations = genericRepository
				.findByCriteria(TenantThemeConfiguration.class, criterias);
		if (tenantThemeConfigurations != null && tenantThemeConfigurations.size() > 0) {
			upsertTenantThemeConfigRequest = new UpsertTenantThemeConfigRequest();
			upsertTenantThemeConfigRequest.setId(tenantThemeConfigurations.get(0).getId());
			upsertTenantThemeConfigRequest.setAccent(tenantThemeConfigurations.get(0).getAccent());
			upsertTenantThemeConfigRequest.setAccentLight(tenantThemeConfigurations.get(0).getAccentLight());
			upsertTenantThemeConfigRequest.setFontFamily(tenantThemeConfigurations.get(0).getFontFamily());
			upsertTenantThemeConfigRequest.setPrimary(tenantThemeConfigurations.get(0).getPrimary());
			upsertTenantThemeConfigRequest.setPrimaryLight(tenantThemeConfigurations.get(0).getPrimaryLight());
			upsertTenantThemeConfigRequest.setWarn(tenantThemeConfigurations.get(0).getWarn());
			upsertTenantThemeConfigRequest.setWarnLight(tenantThemeConfigurations.get(0).getWarnLight());
		}
		final FormList formList = formGeneratorUtil.generateFormList(UpsertTenantThemeConfigRequest.class,
				upsertTenantThemeConfigRequest, FormDisplayMode.StrictlyVertical);
		formList.setUrl(APIConstant.SUBMIT_TENANT_THEME_CONFIG_FORM);
		formList.setMethod(MethodType.POST.getLabel());
		formList.setIsReset(false);
		return formList;
	}

	@Override
	@Transactional
	public void upsert(UpsertTenantThemeConfigRequest upsertTenantThemeConfigRequest) {
		TenantThemeConfiguration tenantThemeConfiguration = null;
		if (upsertTenantThemeConfigRequest.getId() != null) {
			tenantThemeConfiguration = genericRepository.getById(TenantThemeConfiguration.class,
					upsertTenantThemeConfigRequest.getId());
		} else {
			tenantThemeConfiguration = new TenantThemeConfiguration();
			tenantThemeConfiguration.setGuid(UUID.randomUUID().toString());
		}
		tenantThemeConfiguration.setAccent(upsertTenantThemeConfigRequest.getAccent());
		tenantThemeConfiguration.setAccentLight(upsertTenantThemeConfigRequest.getAccentLight());
		tenantThemeConfiguration.setFontFamily(upsertTenantThemeConfigRequest.getFontFamily());
		tenantThemeConfiguration.setPrimary(upsertTenantThemeConfigRequest.getPrimary());
		tenantThemeConfiguration.setPrimaryLight(upsertTenantThemeConfigRequest.getPrimaryLight());
		tenantThemeConfiguration.setWarn(upsertTenantThemeConfigRequest.getWarn());
		tenantThemeConfiguration.setWarnLight(upsertTenantThemeConfigRequest.getWarnLight());
		tenantThemeConfiguration.setTenantId(UserInfoUtil.getTenantId());
		genericRepository.saveOrUpdate(tenantThemeConfiguration);
		try {
			generateAndCompressCSS(tenantThemeConfiguration);
		} catch (IOException | IllegalArgumentException | IllegalAccessException e) {
			ApplicationLogger.logError("An error occurred while generating CSS", e);
			throw new ApplicationException("An error occurred while generating CSS");
		}
	}

	private void generateAndCompressCSS(TenantThemeConfiguration tenantThemeConfiguration)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		String templateContent = new String(
				FileCopyUtils.copyToByteArray(new ClassPathResource("template.css").getInputStream()));
		for (Field field : TenantThemeConfiguration.class.getDeclaredFields()) {
			field.setAccessible(true);
			if (String.class.isAssignableFrom(field.getType())) {
				templateContent = templateContent.replace("$" + StringUtils.capitalize(field.getName()) + "$",
						(CharSequence) field.get(tenantThemeConfiguration));
			}
		}
		File file = new File(repoFileSystem + "/theme/" + UserInfoUtil.getTenantId() + ".css");
		FileUtils.write(file, CSSUtil.compress(templateContent), "UTF-8");
	}

	@Override
	public String getThemeCSS(HttpServletRequest request) throws IOException {
		File file = null;
		String tenantId = "horolab";
		String origin = request.getHeader("referer");
		if (StringUtils.isNotBlank(origin)) {
			origin = origin.replaceFirst("^(https://www\\.|http://www\\.|http://|https://|www\\.)", "").replace("/",
					"");
			Map<String, Object> criterias = new HashMap<>();
			criterias.put("uiBaseUrl", "%" + origin + "%");
			List<TenantConfiguration> tenantConfigurations = genericRepository.findByCriteria(TenantConfiguration.class,
					criterias);
			if (tenantConfigurations != null && tenantConfigurations.size() > 0) {
				tenantId = tenantConfigurations.get(0).getTenantId();
			}
		}
		file = new File(repoFileSystem + "/theme/" + tenantId + ".css");
		if (!file.exists()) {
			file = new File(repoFileSystem + "/theme/horolab.css");
		}
		return new String(FileUtils.readFileToByteArray(file));
	}

}
