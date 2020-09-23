package com.fiek.travelGuide.utility;

import com.fiek.travelGuide.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    @Autowired
    private TemplateEngine templateEngine;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user, String password
    ) {
        String url = contextPath + "/newAccount?token=" + token;
        String message = "\nPlease click on this link to verify your email and edit your personal information. Your password is:\n" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("KosovaTickets - New User");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public MimeMessagePreparator constructOrderConfirmation(User user,Order order, Locale locale){

        Context context = new Context();
        context.setVariable("order",order);
        context.setVariable("user",user);
        context.setVariable("cartItemList",order.getCartItemList());
        context.setVariable("shoppingCart",order.getUser().getShoppingCart());
//        context.setVariable("shoppingCart",user.getShoppingCart());
        String text = templateEngine.process("orderConfirmationEmailTemplate",context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getEmail());
                email.setSubject("Order confirmation - " + order.getId());
                email.setText(text,true);
                email.setFrom(new InternetAddress("kosovatickets@gmail.com"));
            }
        };

        return messagePreparator;
    }

}
