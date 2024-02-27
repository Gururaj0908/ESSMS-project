/**
 * This file is subject to the terms and conditions, as well as
 * the copyright policy defined in file 'LICENSE.txt', which is
 * part of this source code package.
 */
package com.essms.admin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;

import com.yahoo.platform.yui.compressor.CssCompressor;

/**
 * @author gaurav
 *
 */
public class CSSUtil {

	public static String compress(String cssFileContent) throws IOException {
		// TODO delete temp file
		File[] srcFiles = new File[1];
		File file = new File(FileUtils.getTempDirectory() + "/" + System.currentTimeMillis());
		srcFiles[0] = file;
		FileUtils.write(file, cssFileContent, "UTF-8");
		return compress(srcFiles);
	}

	public static String compress(File[] srcFiles) throws IOException {
		StringWriter writer = new StringWriter();
		for (File srcFile : srcFiles) {
			CssCompressor compressor = new CssCompressor(new InputStreamReader(new FileInputStream(srcFile)));
			compressor.compress(writer, -1);
		}
		return writer.toString();
	}
}
