/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.essms.admin.enums.CustomerPageRoute;
import com.essms.core.enums.CustomerType;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.request.IdRequest;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.models.response.ListWithCountResponse;

/**
 * @author gaurav
 *
 */
public interface CustomerManagement {
	/**
	 *
	 * @param jsonQueryString
	 * @param includeDeActive
	 * @param pageNumber
	 * @param pageSize
	 * @param orderBy
	 * @param desc
	 * @return
	 */
	ListWithCountResponse list(String jsonQueryString, Boolean includeDeActive, Integer pageNumber, Integer pageSize,
			String orderBy, Boolean desc);

	/**
	 * Searches customer based on optional json query string and generates optional
	 * paginated response based on paginated parameters passed
	 * 
	 * @param jsonQueryString
	 * @param includeDeActive
	 * @param pageNumber
	 * @param pageSize
	 * @param orderBy
	 * @param desc
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	ListWithCountAndFormsResponse listWithForms(String jsonQueryString, Boolean includeDeActive, Integer pageNumber,
			Integer pageSize, String orderBy, Boolean desc) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * Get Detail of customer
	 * 
	 * @param customerRegNo
	 * @return
	 */
	List<DetailModel> detail(String customerRegNo);

	/**
	 * @param jsonQueryString
	 * @param includeDeActive
	 * @param pageNumber
	 * @param pageSize
	 * @param orderBy
	 * @param desc
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	ListWithCountAndFormsResponse bookConsumptionListWithForms(String itemType, String jsonQueryString,
			Boolean includeDeActive, Integer pageNumber, Integer pageSize, String orderBy, Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException;

	/**
	 * @param itemType
	 * @param idRequests
	 * @param jsonQueryString
	 * @param includeDeActive
	 * @param pageNumber
	 * @param pageSize
	 * @param orderBy
	 * @param desc
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	ListWithCountAndFormsResponse quickSaleCustomers(String itemType, List<IdRequest> idRequests,
			String jsonQueryString, Boolean includeDeActive, Integer pageNumber, Integer pageSize, String orderBy,
			Boolean desc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * @param itemType
	 * @param idRequests
	 * @param jsonQueryString
	 * @param includeDeActive
	 * @param pageNumber
	 * @param pageSize
	 * @param orderBy
	 * @param desc
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	ListWithCountAndFormsResponse bookConsumptionFromListInventory(String itemType, List<IdRequest> idRequests,
			String jsonQueryString, Boolean includeDeActive, Integer pageNumber, Integer pageSize, String orderBy,
			Boolean desc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException;

	/**
	 * 
	 * @param customerGUID
	 * @param customerType
	 * @param customerPageRoute
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	FormList form(String customerGUID, CustomerType customerType, CustomerPageRoute customerPageRoute)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException;
}
