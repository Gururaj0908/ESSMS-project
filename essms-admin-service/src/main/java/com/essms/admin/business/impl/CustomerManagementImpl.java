/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.essms.admin.business.CustomerManagement;
import com.essms.admin.constants.APIConstant;
import com.essms.admin.entities.Customer;
import com.essms.admin.entities.EndCustomer;
import com.essms.admin.entities.EndCustomerAddress;
import com.essms.admin.entities.Outlet;
import com.essms.admin.entities.OutletContactDetail;
import com.essms.admin.entities.OutletSupportingBrand;
import com.essms.admin.enums.CustomerPageRoute;
import com.essms.admin.models.EndCustomerAddressModel;
import com.essms.admin.models.EndCustomerModel;
import com.essms.admin.models.EndCustomerUserModel;
import com.essms.admin.models.EntityContactDetailModel;
import com.essms.admin.models.OutletModel;
import com.essms.admin.models.list.ListCustomer;
import com.essms.admin.models.list.ListCustomerForBookConsumptionFlow1;
import com.essms.admin.models.list.ListCustomerForQuickSale;
import com.essms.admin.models.request.CreateEndCustomerRequest;
import com.essms.admin.models.request.UpdateEndCustomerRequest;
import com.essms.admin.models.request.UpdateOutletRequest;
import com.essms.admin.models.request.wrapper.UpdateEndCustomerRequestWrapper;
import com.essms.admin.models.search.BookConsumptionSearchCustomer;
import com.essms.admin.models.search.SearchCustomer;
import com.essms.admin.utils.ModelUtil;
import com.essms.core.enums.ButtonOps;
import com.essms.core.enums.CustomerType;
import com.essms.core.enums.RouteType;
import com.essms.core.util.UserInfoUtil;
import com.essms.hibernate.core.business.PaginatedListManagement;
import com.essms.hibernate.core.constants.RolePropertyConstant;
import com.essms.hibernate.core.enums.ContentType;
import com.essms.hibernate.core.enums.FormDisplayMode;
import com.essms.hibernate.core.enums.FormEditorType;
import com.essms.hibernate.core.enums.MethodType;
import com.essms.hibernate.core.models.Button;
import com.essms.hibernate.core.models.DetailModel;
import com.essms.hibernate.core.models.FieldHeaderLabelAndEntityProperty;
import com.essms.hibernate.core.models.FormList;
import com.essms.hibernate.core.models.RegisterUserModel;
import com.essms.hibernate.core.models.SearchCriteria;
import com.essms.hibernate.core.models.request.IdRequest;
import com.essms.hibernate.core.models.response.ListWithCountAndFormsResponse;
import com.essms.hibernate.core.models.response.ListWithCountResponse;
import com.essms.hibernate.core.repository.GenericRepository;
import com.essms.hibernate.core.utils.FormGeneratorUtil;
import com.essms.hibernate.core.utils.TableColumnUtil;

/**
 * @author gaurav
 *
 */
@Service
public class CustomerManagementImpl implements CustomerManagement {

	@Autowired
	private PaginatedListManagement paginatedListManagement;

	@Autowired
	private GenericRepository genericRepository;

	@Autowired
	private FormGeneratorUtil formGeneratorUtil;

	@Value("${" + RolePropertyConstant.ADMIN_ROLE + "}")
	private String adminRoleGUID;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.admin.business.CustomerManagement#list(java.lang.String,
	 * java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public ListWithCountResponse list(String jsonQueryString, Boolean includeDeActive, Integer pageNumber,
			Integer pageSize, String orderBy, Boolean desc) {
		final SearchCriteria searchCriteria = paginatedListManagement.generateSearchCriteria(Customer.class,
				jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc,
				getHeaderLabelAndFieldPropertyMap(), ListCustomer.class, true);
		if (!UserInfoUtil.getUserRoleGUIDs().contains(adminRoleGUID)) {
			searchCriteria.getCriterias().put("branch.guid", UserInfoUtil.getBranchGUID());
		}
		final List<Customer> entities = genericRepository.search(Customer.class, searchCriteria);
		return paginatedListManagement.generateListResponse(setupList(entities, CustomerPageRoute.Receive_Page),
				searchCriteria.getRecordCount());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ssms.admin.business.CustomerManagement#listWithForms(java.lang.String,
	 * java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public ListWithCountAndFormsResponse listWithForms(String jsonQueryString, Boolean includeDeActive,
			Integer pageNumber, Integer pageSize, String orderBy, Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {

		final SearchCriteria searchCriteria = paginatedListManagement.generateSearchCriteria(Customer.class,
				jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc,
				getHeaderLabelAndFieldPropertyMap(), ListCustomer.class, true);
		if (!UserInfoUtil.getUserRoleGUIDs().contains(adminRoleGUID)) {
			searchCriteria.getCriterias().put("branch.guid", UserInfoUtil.getBranchGUID());
		}
		final List<Customer> entities = genericRepository.search(Customer.class, searchCriteria);
		final FormList searchFormList = formGeneratorUtil.generateFormList(SearchCustomer.class, null,
				FormDisplayMode.DistributeEvenly);
		final FormList formList = formGeneratorUtil.generateFormList(CreateEndCustomerRequest.class, null,
				FormDisplayMode.StrictlyHorizontal);
		searchFormList.setUrl(APIConstant.SEARCH_CUSTOMER);
		return paginatedListManagement.generateListResponseWithForms(getHeaderLabelAndFieldPropertyMap(), formList,
				searchFormList, setupList(entities, CustomerPageRoute.Receive_Page), getTableButtons(),
				"/" + UserInfoUtil.getServiceName() + "/auditor/timeline/EndCustomer", searchCriteria.getRecordCount(),
				true);
	}

	public Map<String, FieldHeaderLabelAndEntityProperty> getHeaderLabelAndFieldPropertyMap() {
		final Map<String, FieldHeaderLabelAndEntityProperty> headerLabelAndFieldPropertyMap = new LinkedHashMap<>();
		headerLabelAndFieldPropertyMap.put("id", new FieldHeaderLabelAndEntityProperty("Select", "id"));
		headerLabelAndFieldPropertyMap.put("name", new FieldHeaderLabelAndEntityProperty("Customer", "name"));
		headerLabelAndFieldPropertyMap.put("mobileNo", new FieldHeaderLabelAndEntityProperty("Mobile No", "mobileNo"));
		headerLabelAndFieldPropertyMap.put("emailId", new FieldHeaderLabelAndEntityProperty("Email", "emailId"));
		headerLabelAndFieldPropertyMap.put("branch", new FieldHeaderLabelAndEntityProperty("Branch", "branch.name"));
		headerLabelAndFieldPropertyMap.put("regNo", new FieldHeaderLabelAndEntityProperty("Registration No", "regNo"));
		headerLabelAndFieldPropertyMap.put("customerType",
				new FieldHeaderLabelAndEntityProperty("Customer Type", "customerType"));
		headerLabelAndFieldPropertyMap.put("customerType",
				new FieldHeaderLabelAndEntityProperty("Customer Type", "customerType"));
		headerLabelAndFieldPropertyMap.put("gstinNumber",
				new FieldHeaderLabelAndEntityProperty("GSTIN No", "gstinNumber"));
		return headerLabelAndFieldPropertyMap;
	}

	private List<Button> getTableButtons() {
		final List<Button> tableButtons = new LinkedList<>();
		tableButtons.add(new Button("Quick Repair", APIConstant.GET_QUICK_REPAIR_FORM));
		tableButtons.add(new Button("Receive", APIConstant.GET_PRODUCT_RECEIVE_FORM));
		tableButtons.add(new Button("Product Sale", ""));
		tableButtons.add(new Button("Part Sale", ""));
		tableButtons.add(new Button("Book Product", ""));
		tableButtons.add(new Button("Book Part", ""));
		tableButtons.add(new Button("Edit Customer", APIConstant.GET_EDIT_CUST_FORM));
		tableButtons.add(new Button("Print", null, ButtonOps.Print));
		return tableButtons;
	}

	private List<Button> getTableButtonsForBookConsumption(String itemType) {
		final List<Button> tableButtons = new LinkedList<>();
		if (itemType.equals("PART")) {
			tableButtons.add(new Button("Book Part", "/essms-inventory/bookconsumption/step2/PART", true,
					MethodType.POST.getLabel()));
		} else if (itemType.equals("PRODUCT")) {
			tableButtons.add(new Button("Book Product", "/essms-inventory/bookconsumption/step2/PRODUCT", true,
					MethodType.POST.getLabel()));
		}
		tableButtons.add(new Button("Edit Customer", ""));
		return tableButtons;
	}

	List<ListCustomer> setupList(List<Customer> customers, CustomerPageRoute customerPageRoute) {
		final List<ListCustomer> listCustomers = new ArrayList<>();
		ListCustomer listCustomer = null;
		for (final Customer customer : customers) {
			listCustomer = new ListCustomer();
			listCustomer.setId(TableColumnUtil.generateColumnProperty(customer.getGuid(), FormEditorType.Radio,
					"customerGUID", null, true));
			listCustomer.setCustomerType(TableColumnUtil.generateColumnProperty(customer.getCustomerType(),
					FormEditorType.Span, "customerType", null, false));
			listCustomer.setEmailId(customer.getEmailId());
			listCustomer.setBranch(customer.getBranch().getName());
			listCustomer.setGstinNumber(customer.getGstinNumber());
			listCustomer.setMobileNo(customer.getMobileNo());
			listCustomer.setName(TableColumnUtil.generateColumnProperty(customer.getName(), FormEditorType.Span,
					"customerName", null, false));
			listCustomer.setRegNo(TableColumnUtil.generateColumnProperty(customer.getRegNo(), FormEditorType.Span,
					"customerRegNo", null, false));
			listCustomer.setGstinNumber(TableColumnUtil.generateColumnProperty(customer.getGstinNumber(),
					FormEditorType.Span, "gstinNumber", null, false));
			listCustomer.setCustomerPageRoute(TableColumnUtil.generateColumnProperty(customerPageRoute,
					FormEditorType.Hidden, "customerPageRoute", null, false));
			listCustomer.setUsername(customer.getUsername());
			listCustomers.add(listCustomer);
		}
		return listCustomers;
	}

	List<ListCustomerForQuickSale> setupListForQuickSale(List<Customer> customers, List<IdRequest> cartItemIds) {
		List<ListCustomerForQuickSale> listCustomerForQuickSales = new ArrayList<>();
//		final List<Object> cartItems = new ArrayList<>();
//		for (IdRequest idRequest : cartItemIds) {
//			cartItems.add(TableColumnUtil.generateColumnProperty(idRequest.getId(), FormEditorType.Hidden, "id", null,
//					false));
//		}
		ListCustomerForQuickSale listCustomer = null;
		for (final Customer customer : customers) {
			listCustomer = new ListCustomerForQuickSale();
			listCustomer.setId(TableColumnUtil.generateColumnProperty(customer.getGuid(), FormEditorType.Radio,
					"customerGUID", null, true));
			listCustomer.setCartItems(TableColumnUtil.generateColumnProperty(cartItemIds, FormEditorType.Hidden,
					"cartItems", null, false));
			listCustomer.setCustomerType(TableColumnUtil.generateColumnProperty(customer.getCustomerType(),
					FormEditorType.Span, "customerType", null, "", null));
			listCustomer.setEmailId(customer.getEmailId());
			listCustomer.setMobileNo(customer.getMobileNo());
			listCustomer.setName(TableColumnUtil.generateColumnProperty(customer.getName(), FormEditorType.Span,
					"customerName", null, "", null));
			listCustomer.setRegNo(TableColumnUtil.generateColumnProperty(customer.getRegNo(), FormEditorType.Span,
					"customerRegNo", null, "", null));
			listCustomer.setGstinNumber(TableColumnUtil.generateColumnProperty(customer.getGstinNumber(),
					FormEditorType.Span, "gstinNumber", null, false));
			listCustomer.setUsername(customer.getUsername());
			listCustomerForQuickSales.add(listCustomer);
		}
		return listCustomerForQuickSales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.admin.business.CustomerManagement#detail(java.lang.String)
	 */
	@Override
	public List<DetailModel> detail(String customerRegNo) {
		final List<DetailModel> detailModels = new LinkedList<>();
		final Map<String, Object> criterias = new HashMap<>();
		criterias.put("regNo", customerRegNo);
		final List<Customer> customers = genericRepository.findByCriteria(Customer.class, criterias);
		if (customers == null || customers.size() == 0) {
			detailModels.add(new DetailModel("This Customer do not Exist", ""));
			return detailModels;
		}
		final Customer customer = customers.get(0);
		switch (customer.getCustomerType()) {
		case INDIVIDUAL:
		case INTERNET:
			final EndCustomer endCustomer = (EndCustomer) customer;
			return endCustomerDetail(endCustomer);
		case OUTLET:
			final Outlet outlet = (Outlet) customer;
			return outletDetail(outlet);
		default:
			break;
		}
		return null;
	}

	List<DetailModel> outletDetail(Outlet outlet) {
		final List<DetailModel> detailModels = new LinkedList<>();
		detailModels.add(new DetailModel("Registration Code", outlet.getRegNo()));
		detailModels.add(new DetailModel("Contact Name", outlet.getName()));
		detailModels.add(new DetailModel("Type of Customer", outlet.getCustomerType()));
		detailModels.add(new DetailModel("Mobile No", outlet.getMobileNo()));
		detailModels.add(new DetailModel("Email", outlet.getEmailId()));
		detailModels.add(new DetailModel("Oultet Address", null, null, ContentType.Separator));
		detailModels.add(new DetailModel("Address Line 1", outlet.getAddress().getAddressLine1()));
		detailModels.add(new DetailModel("Address Line 2", outlet.getAddress().getAddressLine2()));
		detailModels.add(new DetailModel("Nearest Landmark", outlet.getAddress().getNearestLandMark()));
		detailModels.add(new DetailModel("Area", outlet.getAddress().getArea().getName()));
		detailModels.add(new DetailModel("City", outlet.getAddress().getCity().getName()));
		detailModels.add(new DetailModel("State", outlet.getAddress().getCity().getState().getName()));
		detailModels.add(new DetailModel("Pin Code", outlet.getAddress().getPinCode()));
		detailModels.add(new DetailModel("Phone No", outlet.getAddress().getPhoneNo()));
		return detailModels;
	}

	List<DetailModel> endCustomerDetail(EndCustomer endCustomer) {
		final List<DetailModel> detailModels = new LinkedList<>();
		detailModels.add(new DetailModel("Registration Code", endCustomer.getRegNo()));
		detailModels.add(new DetailModel("Contact Name", endCustomer.getName()));
		detailModels.add(new DetailModel("Type of Customer", endCustomer.getCustomerType()));
		detailModels.add(new DetailModel("Mobile No", endCustomer.getMobileNo()));
		detailModels.add(new DetailModel("Email", endCustomer.getEmailId()));
		if (endCustomer.getEndCustomerAddresses() != null) {
			for (final EndCustomerAddress endCustomerAddress : endCustomer.getEndCustomerAddresses()) {
				detailModels.add(new DetailModel(endCustomerAddress.getAddressType() + " Address", null, null,
						ContentType.Separator));
				detailModels.add(new DetailModel("Address Line 1", endCustomerAddress.getAddress().getAddressLine1()));
				detailModels.add(new DetailModel("Address Line 2", endCustomerAddress.getAddress().getAddressLine2()));
				detailModels
						.add(new DetailModel("Nearest Landmark", endCustomerAddress.getAddress().getNearestLandMark()));
				detailModels.add(new DetailModel("Area", endCustomerAddress.getAddress().getArea() == null ? "N/A"
						: endCustomerAddress.getAddress().getArea().getName()));
				detailModels.add(new DetailModel("City", endCustomerAddress.getAddress().getCity() == null ? "N/A"
						: endCustomerAddress.getAddress().getCity().getName()));
				detailModels.add(new DetailModel("State", endCustomerAddress.getAddress().getCity() == null ? "N/A"
						: endCustomerAddress.getAddress().getCity().getState().getName()));
				detailModels.add(new DetailModel("Pin Code", endCustomerAddress.getAddress().getPinCode()));
				detailModels.add(new DetailModel("Phone No", endCustomerAddress.getAddress().getPhoneNo()));
			}
		}
		return detailModels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.admin.business.CustomerManagement#bookConsumptionListWithForms(java
	 * .lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer,
	 * java.lang.String, java.lang.Boolean)
	 */
	@Override
	public ListWithCountAndFormsResponse bookConsumptionListWithForms(String itemType, String jsonQueryString,
			Boolean includeDeActive, Integer pageNumber, Integer pageSize, String orderBy, Boolean desc)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		final SearchCriteria searchCriteria = paginatedListManagement.generateSearchCriteria(Customer.class,
				jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc,
				getHeaderLabelAndFieldPropertyMap(), ListCustomer.class, true);
		if (!UserInfoUtil.getUserRoleGUIDs().contains(adminRoleGUID)) {
			searchCriteria.getCriterias().put("branch.guid", UserInfoUtil.getBranchGUID());
		}
		final List<Customer> entities = genericRepository.search(Customer.class, searchCriteria);
		final FormList searchFormList = formGeneratorUtil.generateFormList(BookConsumptionSearchCustomer.class, null,
				FormDisplayMode.StrictlyHorizontal);
		searchFormList.setUrl(APIConstant.SEARCH_CUSTOMER);
		return paginatedListManagement.generateListResponseWithForms(getHeaderLabelAndFieldPropertyMap(), null,
				searchFormList, setupList(entities, CustomerPageRoute.Consumption_Page),
				getTableButtonsForBookConsumption(itemType), searchCriteria.getRecordCount(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.admin.business.CustomerManagement#quickSaleCustomers(java.lang.
	 * String, java.util.List, java.lang.String, java.lang.Boolean,
	 * java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public ListWithCountAndFormsResponse quickSaleCustomers(String itemType, List<IdRequest> idRequests,
			String jsonQueryString, Boolean includeDeActive, Integer pageNumber, Integer pageSize, String orderBy,
			Boolean desc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		final SearchCriteria searchCriteria = paginatedListManagement.generateSearchCriteria(Customer.class,
				jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc,
				getHeaderLabelAndFieldPropertyMap(), ListCustomer.class, true);
		if (!UserInfoUtil.getUserRoleGUIDs().contains(adminRoleGUID)) {
			searchCriteria.getCriterias().put("branch.guid", UserInfoUtil.getBranchGUID());
		}
		final List<Customer> entities = genericRepository.search(Customer.class, searchCriteria);
		final FormList searchFormList = formGeneratorUtil.generateFormList(BookConsumptionSearchCustomer.class, null,
				FormDisplayMode.StrictlyHorizontal);
		searchFormList.setUrl(APIConstant.SEARCH_CUSTOMER);
		return paginatedListManagement.generateListResponseWithForms(getHeaderLabelAndFieldPropertyMap(), null,
				searchFormList, setupListForQuickSale(entities, idRequests), getTableButtonsForQuickSale(itemType),
				searchCriteria.getRecordCount(), true);
	}

	private List<Button> getTableButtonsForQuickSale(String itemType) {
		final List<Button> tableButtons = new LinkedList<>();
		if (itemType.equals("PART")) {
			tableButtons.add(new Button("Sale", "/essms-inventory/quicksale/step3/PART", true,
					MethodType.POST.getLabel(), RouteType.List));
		} else if (itemType.equals("PRODUCT")) {
			tableButtons.add(new Button("Sale", "/essms-inventory/quicksale/step3/PRODUCT", true,
					MethodType.POST.getLabel(), RouteType.List));
		}
		tableButtons.add(new Button("Edit Customer", ""));
		return tableButtons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.essms.admin.business.CustomerManagement#bookConsumptionFromListInventory(
	 * java.lang.String, java.util.List, java.lang.String, java.lang.Boolean,
	 * java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public ListWithCountAndFormsResponse bookConsumptionFromListInventory(String itemType, List<IdRequest> idRequests,
			String jsonQueryString, Boolean includeDeActive, Integer pageNumber, Integer pageSize, String orderBy,
			Boolean desc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		final SearchCriteria searchCriteria = paginatedListManagement.generateSearchCriteria(Customer.class,
				jsonQueryString, includeDeActive, pageNumber, pageSize, orderBy, desc,
				getHeaderLabelAndFieldPropertyMap(), ListCustomer.class, true);
		final List<Customer> entities = genericRepository.search(Customer.class, searchCriteria);
		final FormList searchFormList = formGeneratorUtil.generateFormList(BookConsumptionSearchCustomer.class, null,
				FormDisplayMode.StrictlyHorizontal);
		searchFormList.setUrl(APIConstant.SEARCH_CUSTOMER);
		return paginatedListManagement.generateListResponseWithForms(getHeaderLabelAndFieldPropertyMap(), null,
				searchFormList, setupListForBookConsumptionFlow1(entities, idRequests),
				getTableButtonsForBookConsumptionFlow1(itemType), searchCriteria.getRecordCount(), true);
	}

	List<ListCustomerForBookConsumptionFlow1> setupListForBookConsumptionFlow1(List<Customer> customers,
			List<IdRequest> itemIds) {
		List<ListCustomerForBookConsumptionFlow1> listCustomerForBookConsumptionFlow1s = new ArrayList<>();
//		final List<Object> cartItems = new ArrayList<>();
//		for (IdRequest idRequest : cartItemIds) {
//			cartItems.add(TableColumnUtil.generateColumnProperty(idRequest.getId(), FormEditorType.Hidden, "id", null,
//					false));
//		}
		ListCustomerForBookConsumptionFlow1 listCustomer = null;
		for (final Customer customer : customers) {
			listCustomer = new ListCustomerForBookConsumptionFlow1();
			listCustomer.setId(TableColumnUtil.generateColumnProperty(customer.getGuid(), FormEditorType.Radio,
					"customerGUID", null, true));
			listCustomer.setItemIds(
					TableColumnUtil.generateColumnProperty(itemIds, FormEditorType.Hidden, "itemIds", null, false));
			listCustomer.setCustomerType(TableColumnUtil.generateColumnProperty(customer.getCustomerType(),
					FormEditorType.Span, "customerType", null, "", null));
			listCustomer.setEmailId(customer.getEmailId());
			listCustomer.setGstinNumber(customer.getGstinNumber());
			listCustomer.setMobileNo(customer.getMobileNo());
			listCustomer.setName(TableColumnUtil.generateColumnProperty(customer.getName(), FormEditorType.Span,
					"customerName", null, "", null));
			listCustomer.setRegNo(TableColumnUtil.generateColumnProperty(customer.getRegNo(), FormEditorType.Span,
					"customerRegNo", null, "", null));
			listCustomer.setUsername(customer.getUsername());
			listCustomerForBookConsumptionFlow1s.add(listCustomer);
		}
		return listCustomerForBookConsumptionFlow1s;
	}

	private List<Button> getTableButtonsForBookConsumptionFlow1(String itemType) {
		final List<Button> tableButtons = new LinkedList<>();
		if (itemType.equals("PART")) {
			tableButtons.add(new Button("Book Part", "/essms-inventory/bookconsumption/flow1/step2/PART", true,
					MethodType.POST.getLabel()));
		} else if (itemType.equals("PRODUCT")) {
			tableButtons.add(new Button("Book Product", "/essms-inventory/bookconsumption/flow1/step2/PRODUCT", true,
					MethodType.POST.getLabel()));
		}
		tableButtons.add(new Button("Edit Customer", ""));
		return tableButtons;
	}

	@Override
	public FormList form(String customerGUID, CustomerType customerType, CustomerPageRoute customerPageRoute)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		if (customerType == CustomerType.OUTLET) {
			Outlet outlet = genericRepository.getByGUID(Outlet.class, customerGUID);
			return updateOutletForm(outlet);
		} else {
			EndCustomer endCustomer = genericRepository.getByGUID(EndCustomer.class, customerGUID);
			return updateEndCustForm(endCustomer, customerPageRoute);
		}
	}

	private FormList updateOutletForm(Outlet outlet) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		UpdateOutletRequest outletRequest = new UpdateOutletRequest();
		outletRequest.setId(outlet.getId());
		OutletModel outletModel = new OutletModel();
		outletModel.setGstinNumber(outlet.getGstinNumber());
		outletModel.setWebsiteURL(outlet.getWebsiteURL());
		outletModel.setDescription(outlet.getDescription());
		outletRequest.setOutletModel(outletModel);
		outletRequest.setAddressModel(ModelUtil.generateAddressModel(outlet.getAddress()));
		populateEntityContactDetailModel(outlet.getOutletContactDetails(), outletRequest);
		RegisterUserModel registerUserModel = new RegisterUserModel();
		registerUserModel.setName(outlet.getName());
		registerUserModel.setUsername(outlet.getUsername());
		registerUserModel.setEmailId(outlet.getEmailId());
		registerUserModel.setMobileNo(outlet.getMobileNo());
		outletRequest.setRegisterUserModel(registerUserModel);
		outletRequest.setServiceBranchId(outlet.getBranch().getId());
		populateSupportingBrandGUIDs(outlet.getOutletSupportingBrands(), outletRequest);
		final FormList formList = formGeneratorUtil.generateFormList(UpdateOutletRequest.class, outletRequest,
				FormDisplayMode.DistributeEvenly);
		return formList;
	}

	private FormList updateEndCustForm(EndCustomer endCustomer, CustomerPageRoute customerPageRoute)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException {
		UpdateEndCustomerRequestWrapper updateEndCustomerRequestWrapper = new UpdateEndCustomerRequestWrapper();
		updateEndCustomerRequestWrapper.setCustomerPageRoute(customerPageRoute);

		UpdateEndCustomerRequest updateEndCustomerRequest = new UpdateEndCustomerRequest();
		updateEndCustomerRequest.setId(endCustomer.getId());

		EndCustomerUserModel endCustomerUserModel = new EndCustomerUserModel();
		endCustomerUserModel.setName(endCustomer.getName());
		endCustomerUserModel.setEmailId(endCustomer.getEmailId());
		endCustomerUserModel.setMobileNo(endCustomer.getMobileNo());
		endCustomerUserModel.setUsername(endCustomer.getUsername());
		updateEndCustomerRequest.setEndCustomerUserModel(endCustomerUserModel);

		EndCustomerModel endCustomerModel = new EndCustomerModel();
		endCustomerModel.setGstinNumber(endCustomer.getGstinNumber());
		endCustomerModel.setAltMobileNo(endCustomer.getAltMobileNo());
		endCustomerModel.setAnniversaryDate(endCustomer.getAnniversaryDate());
		endCustomerModel.setBirthDate(endCustomer.getBirthDate());
		endCustomerModel.setMaritalStatus(endCustomer.getMaritalStatus());
		endCustomerModel.setGenderType(endCustomer.getGenderType());
		endCustomerModel.setRegNo(endCustomer.getRegNo());
		endCustomerModel.setGuid(endCustomer.getGuid());
		endCustomerModel.setBranchId(endCustomer.getBranch().getId());
		endCustomerModel.setImagePath(endCustomer.getImagePath());
		updateEndCustomerRequest.setEndCustomerModel(endCustomerModel);

		if (endCustomer.getEndCustomerAddresses() != null && !endCustomer.getEndCustomerAddresses().isEmpty()) {
			List<EndCustomerAddressModel> endCustomerAddressModels = new ArrayList<>();
			EndCustomerAddressModel endCustomerAddressModel = null;
			for (EndCustomerAddress endCustomerAddress : endCustomer.getEndCustomerAddresses()) {
				endCustomerAddressModel = new EndCustomerAddressModel();
				endCustomerAddressModel.setId(endCustomerAddress.getId());
				endCustomerAddressModel.setAddressType(endCustomerAddress.getAddressType());
				endCustomerAddressModel.setIsDefault(endCustomerAddress.getIsDefault());
				endCustomerAddressModel
						.setAddressModel(ModelUtil.generateAddressModel(endCustomerAddress.getAddress()));
				endCustomerAddressModels.add(endCustomerAddressModel);
			}
			updateEndCustomerRequest.setEndCustomerAddressModels(endCustomerAddressModels);
		}

		updateEndCustomerRequestWrapper.setUpdateEndCustomerRequest(updateEndCustomerRequest);
		final FormList formList = formGeneratorUtil.generateFormList(UpdateEndCustomerRequestWrapper.class,
				updateEndCustomerRequestWrapper, FormDisplayMode.DistributeEvenly);
		return formList;
	}

	private void populateEntityContactDetailModel(List<OutletContactDetail> outletContactDetails,
			UpdateOutletRequest outletRequest) {
		Set<EntityContactDetailModel> entityContactDetailModels = new HashSet<>();
		outletContactDetails.forEach(e -> {
			EntityContactDetailModel entityContactDetailModel = new EntityContactDetailModel();
			entityContactDetailModel.setContactDetailModel(ModelUtil.generateContactDetailModel(e.getContactDetail()));
			entityContactDetailModel.setId(e.getId());
			entityContactDetailModel.setIsEmailEnabled(e.getIsEmailEnabled());
			entityContactDetailModel.setIsSMSEnabled(e.getIsSMSEnabled());
			entityContactDetailModels.add(entityContactDetailModel);
		});
		outletRequest.setEntityContactDetailModels(entityContactDetailModels);
	}

	private void populateSupportingBrandGUIDs(List<OutletSupportingBrand> outletSupportingBrands,
			UpdateOutletRequest outletRequest) {
		Set<String> supportingBrandGUIDs = new HashSet<>();
		outletSupportingBrands.forEach(e -> supportingBrandGUIDs.add(e.getBrandGUID()));
		outletRequest.setSupportingBrandGUIDs(supportingBrandGUIDs);
	}

}
