package vk.framework.support.common.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import vk.framework.spring.util.ServerUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractPdfView extends AbstractView {
	@Resource(name = "servletContext")
	private ServletContext servletContext;
	@Autowired
	@Resource(name = "envProp")
	protected Properties envProp;
	@Autowired
	@Resource(name = "fileProp")
	protected Properties fileProp;
	protected static BaseFont fontGungsuhChe;
	protected static BaseFont fontBatangChe;
	protected static BaseFont fontNanumGothicChe;
	protected static BaseFont fontNanumMyeongjoChe;
	protected static String ASSETS_URL = "";
	protected static String ATTACH_URL = "";

	public AbstractPdfView() {
		this.setContentType("application/pdf");
	}

	protected boolean generatesDownloadContent() {
		return true;
	}

	public String getAssetsUrl(HttpServletRequest request) throws Exception {
		ASSETS_URL = this.envProp.getProperty("asset.url") + "/images";
		if (ASSETS_URL.indexOf("//") == -1) {
			ASSETS_URL = ServerUtils.getDoamin(request) + ASSETS_URL;
			if (request.getServerName().contains("dev.") || request.getServerName().contains("local.")) {
				ASSETS_URL = ServerUtils.getDoamin(request) + "/" + this.envProp.getProperty("asset.url") + "/images";
			}
		}

		if (ASSETS_URL.startsWith("//")) {
			ASSETS_URL = "http:" + ASSETS_URL;
		}

		return ASSETS_URL;
	}

	public String getAttachUrl(HttpServletRequest request) throws Exception {
		ATTACH_URL = this.envProp.getProperty("upload.web.url");
		if (ATTACH_URL.indexOf("http") == -1) {
			ATTACH_URL = ServerUtils.getDoamin(request) + ATTACH_URL;
		}

		return ATTACH_URL;
	}

	public void getImportFont() throws Exception {
		String path = this.servletContext.getRealPath("/WEB-INF") + File.separator + "pdf";
		FontFactory.register(path + File.separator + "GungsuhChe.ttf", "GungsuhChe");
		fontGungsuhChe = BaseFont.createFont(path + File.separator + "GungsuhChe.ttf", "Identity-H", false);
		FontFactory.register(path + File.separator + "BareunBatangL.ttf", "BareunBatangL");
		fontBatangChe = BaseFont.createFont(path + File.separator + "BareunBatangL.ttf", "Identity-H", false);
		FontFactory.register(path + File.separator + "NanumGothic.ttf", "NanumGothic");
		fontNanumGothicChe = BaseFont.createFont(path + File.separator + "NanumGothic.ttf", "Identity-H", false);
		FontFactory.register(path + File.separator + "NanumMyeongjoBold.ttf", "NanumMyeongjoBold");
		fontNanumMyeongjoChe = BaseFont.createFont(path + File.separator + "NanumMyeongjoBold.ttf", "Identity-H",
				false);
	}

	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.getAssetsUrl(request);
		this.getAttachUrl(request);
		this.logger.debug("ASSETS_URL :" + ASSETS_URL);
		this.logger.debug("ATTACH_URL :" + ATTACH_URL);
		String userAgent = request.getHeader("User-Agent");
		String downloadFileName = FilenameUtils.getBaseName((String) model.get("downloadFileName")) + ".pdf";
		String downloadYn = (String) model.get("downloadYn");
		Boolean isEncrypt = Boolean
				.valueOf(model.get("encrypt") == null ? true : ((Boolean) model.get("encrypt")).booleanValue());
		String headerTag = downloadYn != null && downloadYn.equals("Y") ? "attachment;" : "inline;";
		if (userAgent.indexOf("Trident") <= -1 && userAgent.indexOf("Edge") <= -1) {
			if (userAgent.indexOf("MSIE") == -1) {
				response.setHeader("Content-Disposition",
						headerTag + "filename=" + new String(downloadFileName.getBytes("UTF-8"), "8859_1") + ";");
			} else {
				response.setHeader("Content-disposition",
						headerTag + "filename=" + new String(downloadFileName.getBytes("EUC-KR"), "8859_1") + ";");
			}
		} else {
			response.setHeader("Content-disposition",
					headerTag + "filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
		}

		ByteArrayOutputStream bos = this.createTemporaryOutputStream();
		Document document = this.newDocument();
		PdfWriter writer = this.newWriter(document, bos);
		if (isEncrypt.booleanValue()) {
			writer.setEncryption((byte[]) null, (byte[]) null, 2052, 1);
			writer.createXmpMetadata();
		}

		this.getImportFont();
		this.prepareWriter(model, writer, request);
		this.buildPdfMetadata(model, document, request);

		try {
			this.buildPdfDocument(model, document, writer);
		} catch (Exception arg12) {
			arg12.printStackTrace();
			this.logger.error("no pages " + arg12.getMessage());
		}

		this.writeToResponse(response, bos);
	}

	public static ModelAndView getModelAndView(String downloadFileName) {
		ModelAndView modelAndView = new ModelAndView("pdfView");
		modelAndView.addObject("downloadFileName", downloadFileName);
		return modelAndView;
	}

	protected Document newDocument() {
		return new Document(PageSize.A4);
	}

	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {
		writer.setViewerPreferences(this.getViewerPreferences());
	}

	protected int getViewerPreferences() {
		return 2053;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	protected abstract void buildPdfDocument(Map<String, Object> arg0, Document arg1, PdfWriter arg2) throws Exception;

	public static Font createFont(BaseFont fontName, int size) {
		return new Font(fontName, (float) size, 0);
	}

	public static Font createFont(BaseFont fontName, int size, int style) {
		return new Font(fontName, (float) size, style);
	}

	public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
		Image img = Image.getInstance(path);
		PdfPCell cell = new PdfPCell(img, true);
		return cell;
	}

	public static PdfPCell createCell(String content, int colspan, int height, int alignment, int fontSize,
			int fontWeight) {
		PdfPCell cell = new PdfPCell(
				new Phrase(defaultString(content), createFont(fontBatangChe, fontSize, fontWeight)));
		cell.setBorderWidth(1.0F);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setVerticalAlignment(5);
		if (height > 0) {
			cell.setFixedHeight((float) height);
		}

		if (alignment == 0) {
			cell.setPaddingLeft(10.0F);
		}

		return cell;
	}

	public static PdfPCell createCell(BaseFont fontName, String content, int colspan, int rowspan, int height,
			int alignment, int fontSize, int fontWeight) {
		PdfPCell cell = new PdfPCell(new Phrase(defaultString(content), createFont(fontName, fontSize, fontWeight)));
		cell.setBorderWidth(1.0F);
		cell.setLeading(0.0F, 1.3F);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		cell.setHorizontalAlignment(alignment);
		cell.setVerticalAlignment(5);
		if (height > 0) {
			cell.setFixedHeight((float) height);
		}

		if (alignment == 0) {
			cell.setPaddingLeft(10.0F);
		}

		return cell;
	}

	public static PdfPCell createCell(BaseFont fontName, String content, int colspan, int height, int alignment,
			int fontSize, int fontWeight) {
		PdfPCell cell = new PdfPCell(new Phrase(defaultString(content), createFont(fontName, fontSize, fontWeight)));
		cell.setBorderWidth(1.0F);
		cell.setLeading(0.0F, 1.3F);
		cell.setColspan(colspan);
		cell.setPaddingBottom(7.0F);
		cell.setHorizontalAlignment(alignment);
		cell.setVerticalAlignment(5);
		if (height > 0) {
			cell.setFixedHeight((float) height);
		}

		if (alignment == 0) {
			cell.setPaddingLeft(10.0F);
		}

		return cell;
	}

	public static PdfPCell createCell(BaseFont fontName, String content, int colspan, int height, int alignment,
			Font fontStyle) {
		PdfPCell cell = new PdfPCell(new Phrase(defaultString(content), fontStyle));
		cell.setBorderWidth(1.0F);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setVerticalAlignment(5);
		if (height > 0) {
			cell.setFixedHeight((float) height);
		}

		if (alignment == 0) {
			cell.setPaddingLeft(10.0F);
		}

		return cell;
	}

	public static PdfPCell createCell(Paragraph paragraph, int colspan, int height, int alignment) {
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setBorderWidth(1.0F);
		cell.setColspan(colspan);
		cell.setHorizontalAlignment(alignment);
		cell.setVerticalAlignment(5);
		if (height > 0) {
			cell.setFixedHeight((float) height);
		}

		if (alignment == 0) {
			cell.setPaddingLeft(10.0F);
		}

		return cell;
	}

	private static String defaultString(String content) {
		if (content == null) {
			content = "";
		}

		if (content != null) {
			content = content.replace("null", "");
		}

		content = content.replace("&quot;", "\"");
		content = content.replace("&amp;", "&");
		content = content.replace("&lt;", "<");
		content = content.replace("&gt;", ">");
		return content;
	}
}