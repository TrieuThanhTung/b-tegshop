package com.project.tegshop.service.mail;

import com.project.tegshop.model.EmailDetails;
import org.springframework.stereotype.Service;

public interface MailService {
    void sendSimpleMail(EmailDetails emailDetails) throws Exception;
}
