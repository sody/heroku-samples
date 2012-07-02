package com.example.ui.pages;

import com.example.ui.base.BasePage;
import com.example.ui.internal.SocialProfile;
import com.example.ui.internal.social.google.Google;
import com.example.ui.internal.social.google.GoogleProfile;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Social extends BasePage {

    @InjectService("facebookService")
    private OAuth2ServiceProvider<Facebook> facebookService;

    @InjectService("twitterService")
    private OAuth1ServiceProvider<Twitter> twitterService;

    @InjectService("googleService")
    private OAuth2ServiceProvider<Google> googleService;

    @Persist
    private SocialProfile profile;

    @Persist
    @Property
    private String errorMessage;

    public SocialProfile getProfile() {
        return profile;
    }

    public String getFacebookButtonClass() {
        return profile != null && profile.getType() == SocialProfile.Type.FACEBOOK
                ? "active"
                : null;
    }

    public String getTwitterButtonClass() {
        return profile != null && profile.getType() == SocialProfile.Type.TWITTER
                ? "active"
                : null;
    }

    public String getGoogleButtonClass() {
        return profile != null && profile.getType() == SocialProfile.Type.GOOGLE
                ? "active"
                : null;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "facebook")
    void facebookConnected(final String accessToken) {
        final FacebookProfile facebookProfile = facebookService.getApi(accessToken)
                .userOperations()
                .getUserProfile();

        errorMessage = null;
        profile = new SocialProfile(
                SocialProfile.Type.FACEBOOK,
                facebookProfile.getId(),
                facebookProfile.getName(),
                facebookProfile.getGender(),
                String.valueOf(facebookProfile.getLocale()),
                facebookProfile.getLink(), Facebook.GRAPH_API_URL + facebookProfile.getId() + "/picture?type=large");
    }

    @OnEvent(value = EventConstants.FAILURE, component = "facebook")
    void facebookFailure() {
        profile = null;
        errorMessage = format("error.connection-denied", "Facebook");
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "twitter")
    void twitterConnected(final String accessToken, final String accessTokenSecret) {
        final TwitterProfile twitterProfile = twitterService.getApi(accessToken, accessTokenSecret)
                .userOperations()
                .getUserProfile();

        errorMessage = null;
        profile = new SocialProfile(
                SocialProfile.Type.TWITTER,
                String.valueOf(twitterProfile.getId()),
                twitterProfile.getName(),
                "",
                twitterProfile.getLanguage(),
                twitterProfile.getProfileUrl(), twitterProfile.getProfileImageUrl());
    }

    @OnEvent(value = EventConstants.FAILURE, component = "twitter")
    void twitterFailure() {
        profile = null;
        errorMessage = format("error.connection-denied", "Twitter");
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "google")
    void googleConnected(final String accessToken) {
        final GoogleProfile googleProfile = googleService.getApi(accessToken)
                .userOperations()
                .getUserProfile();

        errorMessage = null;
        profile = new SocialProfile(
                SocialProfile.Type.GOOGLE,
                String.valueOf(googleProfile.getId()),
                googleProfile.getName(),
                "",
                String.valueOf(googleProfile.getLocale()),
                googleProfile.getLink(), googleProfile.getPictureUrl());
    }

    @OnEvent(value = EventConstants.FAILURE, component = "google")
    void googleFailure(final String error, final String errorDescription) {
        profile = null;
        errorMessage = format("error.connection-denied", "Google");
    }
}
