package vk.framework.support.common.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.net.URL;

public class PdfPageFooterEvent extends PdfPageEventHelper {
	public String backgroundImageUrl = "";

	public PdfPageFooterEvent(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public void onEndPage(PdfWriter writer, Document document) {
		try {
			Image e = Image.getInstance(new URL(this.backgroundImageUrl));
			float width = document.getPageSize().getWidth();
			float height = document.getPageSize().getHeight();
			writer.getDirectContentUnder().addImage(e, width, 0.0F, 0.0F, height, 0.0F, 0.0F);
		} catch (Exception arg5) {
			arg5.printStackTrace();
		}

	}
}