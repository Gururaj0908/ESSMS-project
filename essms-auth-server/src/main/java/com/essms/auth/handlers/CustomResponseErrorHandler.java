/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.auth.handlers;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * @author gaurav
 *
 */
@Component
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// Do Nothing
	}
}
