/*
 * Rebrand
 * Copyright (C) 2014 Andris Raugulis
 * moo@arthepsy.eu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.arthepsy.sonar.plugins.rebrand;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.web.Footer;

public class ImagesFooter implements Footer {
    private final Settings settings;

    public ImagesFooter(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String getHtml() {
        ImageSettings sidebarImage = new ImageSettings(this.settings, RebrandConfiguration.SIDEBAR_PREFIX);
        ImageSettings contentImage = new ImageSettings(this.settings, RebrandConfiguration.CONTENT_PREFIX);
        boolean originalLogoVisible = settings.getBoolean(RebrandConfiguration.ORIGINAL_LOGO_VISIBLE);

        String sidebarImageUrl = sidebarImage.getImageUrl();
        boolean hasSidebarImage = !StringUtils.isEmpty(sidebarImageUrl);
        String contentImageUrl = contentImage.getImageUrl();
        boolean hasContentImage = !StringUtils.isEmpty(contentImageUrl);

        if (originalLogoVisible && !hasSidebarImage && !hasContentImage) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("\n<script>\n");
        sb.append("\t$j(document).ready(function() {\n");
        if (hasSidebarImage) {
            sb.append(this.createImageHtml(sidebarImage));
            sb.append("\tvar _sidebar = $j('div#sidebar');\n");
            sb.append("\tvar _div = $j('<div>');\n");
            sb.append("\t_div.attr('style', '").append(sidebarImage.getCssStyle()).append("');\n");
            sb.append("\t_div.append(_img);\n");
            sb.append("\tvar _center = $j('<center>');\n");
            sb.append("\t_center.append(_div);\n");
            sb.append("\t_sidebar.append(_center);\n");
        }
        if (hasContentImage) {
            sb.append(this.createImageHtml(contentImage));
            sb.append("\tvar _content= $j('#error').first().parent();\n");
            sb.append("if (_content) {\n");
            sb.append("\t\tvar _div=$j('<div>');\n");
            sb.append("\t\t_div.attr('style', '").append(sidebarImage.getCssStyle()).append("');\n");
            sb.append("\t\t_div.append(_img);\n");
            sb.append("\t\t_content.prepend(_div);\n");
            sb.append("}\n");
        }
        if (! originalLogoVisible) {
            sb.append("\t$j('img[src^=\"/images/logo\"]').parent().remove();\n");
        }
        sb.append("\t});\n");
        sb.append("</script>\n");

        return sb.toString();
    }

    private String createImageHtml(ImageSettings imageSettings) {
        StringBuilder sb = new StringBuilder();
        String padding = "\t\t";

        sb.append(padding).append("var _img = $j('<img>');\n");
        sb.append(padding).append("_img.attr('src', '").append(imageSettings.getImageUrl()).append("');\n");
        int height = imageSettings.getHeight();
        if (height > 0) {
            sb.append(padding).append("_img.attr('height', ").append(height).append(");\n");
        }
        int width = imageSettings.getWidth();
        if (width > 0) {
            sb.append(padding).append("_img.attr('width', ").append(width).append(");\n");
        }
        sb.append(padding).append("_img.attr('alt', '');\n");
        sb.append(padding).append("_img.attr('title', '');\n");

        String linkUrl = imageSettings.getLinkUrl();
        if (! StringUtils.isEmpty(linkUrl)) {
            sb.append(padding).append("var _link = $j('<a>');\n");
            sb.append(padding).append("_link.attr('style', 'border-bottom: 0px;');\n");
            sb.append(padding).append("_link.attr('href', '").append(linkUrl).append("');\n");
            sb.append(padding).append("_link.append(_img);\n");
            sb.append(padding).append("_img = _link;\n");
        }

        return sb.toString();
    }

}
