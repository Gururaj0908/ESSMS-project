/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.business.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.essms.admin.business.FileManagement;

/**
 * @author gaurav
 *
 */
@Service
public class FileManagementImpl implements FileManagement {

	@Value("${repo.file.system.path}")
	private String repoFileSystem;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.repair.business.FileUploadManagement#saveUploadedFile(org.
	 * springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	public String saveUploadedFile(MultipartFile multipartFile, String path) throws IOException {
		if (!multipartFile.isEmpty()) {
			File file = new File(repoFileSystem + "/" + path);
			file.mkdirs();
			byte[] bytes = multipartFile.getBytes();
			file = new File(repoFileSystem + "/" + path + "/" + multipartFile.getOriginalFilename());
			Path repoPath = Paths.get(repoFileSystem + "/" + path + "/" + multipartFile.getOriginalFilename());
			Files.write(repoPath, bytes);
			return path + "/" + multipartFile.getOriginalFilename();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ssms.repair.business.FileManagement#moveFile(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String moveFile(String oldPath, String newPath, String fileName) throws IOException {
		File file = new File(repoFileSystem + "/" + newPath);
		file.mkdirs();
		file = new File(repoFileSystem + "/" + newPath + "/" + fileName);
		Files.move(Paths.get(repoFileSystem + "/" + oldPath),
				Paths.get(repoFileSystem + "/" + newPath + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
		return newPath + "/" + fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.essms.repair.business.FileManagement#getFileBytes(java.lang.String)
	 */
	@Override
	public byte[] getFileBytes(String absolutePath) throws IOException {
		return Files.readAllBytes(new File(repoFileSystem + "/" + absolutePath).toPath());
	}

}
