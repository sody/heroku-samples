package com.example.ui.services;

import com.example.ui.internal.FileUploadFilter;
import com.example.ui.internal.NavigationSourceImpl;
import com.example.ui.internal.social.google.Google;
import com.example.ui.internal.social.google.GoogleServiceProvider;
import com.example.ui.pages.Button;
import com.example.ui.pages.Index;
import com.example.ui.pages.Social;
import com.example.ui.pages.Upload;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookServiceProvider;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterServiceProvider;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UiModule {
    private static final String FACEBOOK_CLIENT_ID = "facebook.client-id";
    private static final String FACEBOOK_CLIENT_SECRET = "facebook.client-secret";

    private static final String TWITTER_CONSUMER_KEY = "twitter.consumer-key";
    private static final String TWITTER_CONSUMER_SECRET = "twitter.consumer-secret";

    private static final String GOOGLE_CLIENT_ID = "google.client-id";
    private static final String GOOGLE_CLIENT_SECRET = "google.client-secret";

    public static void bind(final ServiceBinder binder) {
        binder.bind(NavigationSource.class, NavigationSourceImpl.class);
    }

    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        // created here https://developers.facebook.com/apps
        configuration.add(FACEBOOK_CLIENT_ID, "${env.facebook_id}");
        configuration.add(FACEBOOK_CLIENT_SECRET, "${env.facebook_secret}");
        // created here https://dev.twitter.com/apps/new
        configuration.add(TWITTER_CONSUMER_KEY, "${env.twitter_id}");
        configuration.add(TWITTER_CONSUMER_SECRET, "${env.twitter_secret}");
        //created here https://code.google.com/apis/console
        configuration.add(GOOGLE_CLIENT_ID, "${env.google_id}");
        configuration.add(GOOGLE_CLIENT_SECRET, "${env.google_secret}");
    }

    public OAuth2ServiceProvider<Facebook> buildFacebookService(@Symbol(FACEBOOK_CLIENT_ID) final String clientId,
                                                                @Symbol(FACEBOOK_CLIENT_SECRET) final String clientSecret) {
        return new FacebookServiceProvider(clientId, clientSecret);
    }

    public OAuth1ServiceProvider<Twitter> buildTwitterService(@Symbol(TWITTER_CONSUMER_KEY) final String consumerKey,
                                                              @Symbol(TWITTER_CONSUMER_SECRET) final String consumerSecret) {
        return new TwitterServiceProvider(consumerKey, consumerSecret);
    }

    public OAuth2ServiceProvider<Google> buildGoogleService(@Symbol(GOOGLE_CLIENT_ID) final String clientId,
                                                            @Symbol(GOOGLE_CLIENT_SECRET) final String clientSecret) {
        return new GoogleServiceProvider(clientId, clientSecret);
    }

    @Contribute(NavigationSource.class)
    public void contributeNavigationSource(final OrderedConfiguration<Class> configuration) {
        configuration.add("Home", Index.class);
        configuration.add("Social", Social.class);
        configuration.add("Button", Button.class);
        configuration.add("Upload", Upload.class);
    }

    @Contribute(RequestHandler.class)
    public static void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration) {
        configuration.addInstance("FileUploadFilter", FileUploadFilter.class);
    }
}
