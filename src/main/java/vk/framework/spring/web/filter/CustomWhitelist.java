package vk.framework.spring.web.filter;

import org.jsoup.safety.Whitelist;

public class CustomWhitelist extends Whitelist {
	public static Whitelist full() {
		return (new Whitelist())
				.addTags(new String[]{"a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd",
						"div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre",
						"q", "small", "span", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th",
						"thead", "tr", "u", "style", "map", "area", "ul"})
				.addAttributes("span", new String[]{"style"}).addAttributes("div", new String[]{"style"})
				.addAttributes("em", new String[]{"style"})
				.addAttributes("a", new String[]{"href", "title", "target", "style"})
				.addAttributes("blockquote", new String[]{"cite"}).addAttributes("col", new String[]{"span", "width"})
				.addAttributes("colgroup", new String[]{"span", "width"})
				.addAttributes("img",
						new String[]{"align", "alt", "height", "src", "title", "width", "style", "usemap", "class"})
				.addAttributes("ol", new String[]{"start", "type"}).addAttributes("q", new String[]{"cite"})
				.addAttributes("table",
						new String[]{"summary", "width", "height", "style", "border", "cellpadding", "cellspacing",
								"background"})
				.addAttributes("td", new String[]{"abbr", "axis", "colspan", "rowspan", "width", "height", "style"})
				.addAttributes("th",
						new String[]{"abbr", "axis", "colspan", "rowspan", "scope", "width", "height", "style"})
				.addAttributes("ul", new String[]{"type", "style", "class"})
				.addAttributes("li", new String[]{"type", "style", "class"})
				.addAttributes("map", new String[]{"name", "id"})
				.addAttributes("area", new String[]{"shape", "coords", "href", "title", "target"})
				.addAttributes("p", new String[]{"class", "style"})
				.addProtocols("a", "href", new String[]{"ftp", "http", "https", "mailto", "/"})
				.addProtocols("blockquote", "cite", new String[]{"http", "https"})
				.addProtocols("cite", "cite", new String[]{"http", "https"})
				.addProtocols("img", "src", new String[]{"http", "https", "/"})
				.addProtocols("q", "cite", new String[]{"http", "https"});
	}
}