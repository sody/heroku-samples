package com.example.ui.internal;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SocialProfile {
    private final Type type;
    private final String id;
    private final String name;
    private final String gender;
    private final String locale;
    private final String link;
    private final String imageLink;

    public SocialProfile(final Type type,
                         final String id,
                         final String name,
                         final String gender,
                         final String locale,
                         final String link,
                         final String imageLink) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.locale = locale;
        this.link = link;
        this.imageLink = imageLink;
    }

    public Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getLocale() {
        return locale;
    }

    public String getLink() {
        return link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public enum Type {
        FACEBOOK,
        TWITTER,
        GOOGLE
    }
}
