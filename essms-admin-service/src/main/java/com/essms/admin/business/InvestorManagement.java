/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import java.util.List;

import com.essms.hibernate.core.models.DetailModel;

/**
 * @author ekamra.nayak
 *
 */
public interface InvestorManagement {

	/**
	 * @param investorGUID
	 * @return
	 */
	List<DetailModel> getInvestorDetail(String investorGUID);

}
