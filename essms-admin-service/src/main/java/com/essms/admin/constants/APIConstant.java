/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.constants;

/**
 * All constant API URLs for different micro service calls
 *
 * @author gaurav
 *
 */
public interface APIConstant {

	String LIST_USER = "http://essms-auth/user/list";

	String GET_ALL_BRANCH = "/essms-admin/option/branch";

	String GET_ALL_STATE = "/essms-admin/option/state";

	String GET_ALL_ADMIN = "/essms-admin/option/admin";

	String GET_CITY_BY_STATE = "/essms-admin/option/citybystate";

	String GET_AREA_BY_CITY = "/essms-admin/option/areabycity";

	String GET_PRODUCT_RECEIVE_FORM = "/essms-repair/receive/product/form";

	String GET_QUICK_REPAIR_FORM = "/essms-repair/repair/quick/form";

	String GET_EDIT_CUST_FORM = "/essms-admin/customer/form";

	String SEARCH_CUSTOMER = "/essms-admin/customer/list";

	String ADD_ENDCUSTOMER_ADDRESS = "/essms-admin/endcustomer/address/form";

	String SUBMIT_ENDCUSTOMER_ADDRESS = "/essms-admin/endcustomer/address/add";

	String SUBMIT_TENANT_THEME_CONFIG_FORM = "/essms-admin/tenant/theme/configuration/upsert";

	String SUBMIT_OUTLET_UPDATE_FORM = "/essms-admin/tenant/theme/configuration/upsert";

	String SUBMIT_SYNC_DATA_FORM = "/essms-admin/systemconfig/sync/location";

}
