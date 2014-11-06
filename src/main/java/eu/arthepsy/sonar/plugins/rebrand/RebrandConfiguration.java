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

import com.google.common.collect.ImmutableList;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;

import java.util.List;

public class RebrandConfiguration {

    public static final String CATEGORY = "Rebranding";

    public static final String ORIGINAL_LOGO_VISIBLE = "sonar.rebrand.original.logo.visible";
    public static final String ORIGINAL_FOOTER_VISIBLE = "sonar.rebrand.original.footer.visible";

    public static final String SIDEBAR_PREFIX = "sonar.rebrand.sidebar.";
    public static final String CONTENT_PREFIX = "sonar.rebrand.content.";

    public static final String IMAGE_SOURCE = "image.src";
    public static final String IMAGE_WIDTH = "image.width";
    public static final String IMAGE_HEIGHT = "image.height";
    public static final String IMAGE_LINK = "image.link";
    // public static final String IMAGE_LOCATION = "image.location";
    public static final String IMAGE_STYLE = "image.style";

    private static List<PropertyDefinition> getImagePropertyDefinitions(String prefix, String subCategory)
    {
        return ImmutableList.of(
                PropertyDefinition.builder(prefix + IMAGE_SOURCE)
                        .category(CATEGORY).subCategory(subCategory)
                        .index(0).name("Image URL")
                        .description("Example: http://www.sonarsource.com/wp-content/themes/sonarsource/images/logo.png")
                        .type(PropertyType.STRING).defaultValue("")
                        .build(),
                PropertyDefinition.builder(prefix + IMAGE_WIDTH)
                        .category(CATEGORY).subCategory(subCategory)
                        .index(1).name("Image width")
                        .description("Image width in pixels. Example: 150")
                        .type(PropertyType.INTEGER).defaultValue("0")
                        .build(),
                PropertyDefinition.builder(prefix + IMAGE_HEIGHT)
                        .category(CATEGORY).subCategory(subCategory)
                        .index(2).name("Image height")
                        .description("Image height in pixels. Example: 50")
                        .type(PropertyType.INTEGER).defaultValue("0")
                        .build(),
                PropertyDefinition.builder(prefix + IMAGE_LINK)
                        .category(CATEGORY).subCategory(subCategory)
                        .index(3).name("Link URL")
                        .description("Example: http://www.sonarsource.com/")
                        .type(PropertyType.STRING).defaultValue("")
                        .build(),
                PropertyDefinition.builder(prefix + IMAGE_STYLE)
                        .category(CATEGORY).subCategory(subCategory)
                        .index(4).name("Image style")
                        .description("Example: padding-top: 10px")
                        .type(PropertyType.STRING).defaultValue("")
                        .build()
        );
    }
    public static List<PropertyDefinition> getPropertyDefinitions() {
        ImmutableList.Builder<PropertyDefinition> properties = ImmutableList.builder();
        final String generalCategory = "#General";
        properties.add(PropertyDefinition.builder(ORIGINAL_LOGO_VISIBLE)
                .category(CATEGORY).subCategory(generalCategory)
                .index(0).name("SonarQube logo")
                .description("Visibility of SonarQube logo")
                .type(PropertyType.BOOLEAN).defaultValue("true")
                .build());
        properties.add(PropertyDefinition.builder(ORIGINAL_FOOTER_VISIBLE)
                .category(CATEGORY).subCategory(generalCategory)
                .index(0).name("SonarQube footer")
                .description("Visibility of SonarQube footer")
                .type(PropertyType.BOOLEAN).defaultValue("true")
                .build());
        properties.addAll(getImagePropertyDefinitions(SIDEBAR_PREFIX, "Sidebar"));
        properties.addAll(getImagePropertyDefinitions(CONTENT_PREFIX, "Content"));
        return properties.build();
    }

}
