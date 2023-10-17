//package com.dqt.shop.config;
//
//import com.dqt.shop.constant.Constant;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Locale;
//
//@Configuration
//public class I18nConfiguration extends AcceptHeaderLocaleResolver {
//
//    @Value("${shop.default.language}")
//    private String defaultLanguage;
//
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename(Constant.FOLDER_MESSAGE_I18N);
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//
//
//    @Override
//    public Locale resolveLocale(HttpServletRequest request) {
//        String lang = request.getHeader("Accept-Language");
//
//        return (lang == null || lang.isEmpty())
//                ? Locale.getDefault()
//                : Locale.forLanguageTag(defaultLanguage);
//    }
//}