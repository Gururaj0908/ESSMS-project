/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author gaurav
 *
 */
public interface FileManagement {

	/**
	 * Store the uploaded file on the given path and return the absolute path
	 *
	 * @param multipartFile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	String saveUploadedFile(MultipartFile multipartFile, String path) throws IOException;

	/**
	 * Move file from source to destination, overrides if the file already exist in
	 * the destination
	 * 
	 * @param oldPath
	 * @param newPath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	String moveFile(String oldPath, String newPath, String fileName) throws IOException;

	/**
	 * Get File from file system repository in byte[] based on the absolute path
	 * 
	 * @param absolutePath
	 * @return
	 * @throws IOException
	 */
	byte[] getFileBytes(String absolutePath) throws IOException;

}
