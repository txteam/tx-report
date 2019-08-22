/*
 * Copyright © 2018  o2o.cunshop.com . All rights reserved
 * Support: http://o2o.cunshop.com
 * License: http://o2o.cunshop.com/license
 */
package com.tx.report;


//import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Excel视图
 * 
 * @author gxds Team
 * @version 5.0
 */
public class ExcelView extends AbstractView {

	/**
	 * "强制下载"内容类型
	 */
	private static final String FORCE_DOWNLOAD_CONTENT_TYPE="application/force-download";

	/**
	 * 模板路径
	 */
	private static final String TEMPLATE_LOADER_PATH="WEB-INF/templates/";

	/**
	 * 模板路径
	 */
	private String templatePath;

	/**
	 * 文件名称
	 */
	private String filename;




	/**
	 * 构造方法
	 * 
	 * @param templatePath
	 *            模板路径
	 * @param filename
	 *            文件名称
	 */
	public ExcelView(String templatePath, String filename) {
		this.templatePath = templatePath;
		this.filename = filename;
		setContentType(FORCE_DOWNLOAD_CONTENT_TYPE);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(response.getContentType())) {
			response.setContentType(getContentType());
		}
		if (!response.containsHeader("Content-disposition")) {
			if (StringUtils.isNotEmpty(filename)) {
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			} else {
				response.setHeader("Content-disposition", "attachment");
			}
		}
		InputStream inputStream = null;
		try {
			ServletContext servletContext = request.getSession().getServletContext();
			inputStream = new BufferedInputStream(new FileInputStream(servletContext.getRealPath(TEMPLATE_LOADER_PATH + templatePath)));
			OutputStream outputStream = response.getOutputStream();
			Context context = new Context(model);
			JxlsHelper jxlsHelper = JxlsHelper.getInstance();
			Transformer transformer  = jxlsHelper.createTransformer(inputStream, response.getOutputStream());
			JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
//			Map<String, Object> funcs = new HashMap<String, Object>();
//			funcs.put("fns", new JxlsFunction());
//			evaluator.getJexlEngine().setFunctions(funcs);

			JxlsHelper.getInstance().processTemplate( context,transformer);
			outputStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
//			IOUtils.closeQuietly(inputStream);
		}
	}

}