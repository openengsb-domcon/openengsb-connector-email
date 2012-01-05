/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.connector.email.integration;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.openengsb.connector.email.internal.EmailNotifier;
import org.openengsb.connector.email.internal.abstraction.JavaxMailAbstraction;
import org.openengsb.connector.email.internal.abstraction.MailProperties.SecureMode;
import org.openengsb.core.api.DomainMethodExecutionException;
import org.openengsb.core.common.util.ModelUtils;
import org.openengsb.domain.notification.Attachment;
import org.openengsb.domain.notification.Notification;

public class EmailNotifierUT {

    @Test
    public void testToSendAnEmailOverSSL() throws Exception {
        EmailNotifier notifier = createSSLNotifier();
        Notification notification = createNotification();
        notifier.notify(notification);
    }
    
    @Test
    public void testToSendAnEmailWithStartTls() throws Exception {
        EmailNotifier notifier = createStartTlsNotifier();
        Notification notification = createNotification();
        notifier.notify(notification);
    }
    
    @Test
    public void testChangeMailProperties() {
        EmailNotifier notifier = createStartTlsNotifier();
        Notification notification = createNotification();
        notifier.notify(notification);
        
        notifier = toSSLNotifier(notifier);
        notifier.notify(notification);
    }

    @Test(expected = DomainMethodExecutionException.class)
    public void testToSendAnEmailWithWrongUserdata() throws Exception {
        EmailNotifier notifier = createNotifier("notifier2", "pre3: ", true, "doesnotexist", "smtp.gmail.com", 
                "totallyWrong", "doesnotexist", "465");

        Notification notification = createNotification();
        notifier.notify(notification);
    }
    
    private EmailNotifier createSSLNotifier() {
        EmailNotifier notifier = createNotifier("notifier1"); 
        toSSLNotifier(notifier);        
        return notifier;
    }
    
    private EmailNotifier toSSLNotifier(EmailNotifier notifier) {
        updateProperties(notifier, "pre1: ", true, "openengsb.notification.test@gmail.com",
            "smtp.gmail.com", "pwd-openengsb", "openengsb.notification.test@gmail.com", "465");
        notifier.getProperties().setSecureMode(SecureMode.SSL.toString());
        return notifier;
    }
    
    private EmailNotifier createStartTlsNotifier() {
        EmailNotifier notifier = createNotifier("notifier2", "pre2: ", true, "openengsb.notification.test@gmail.com",
            "smtp.gmail.com", "pwd-openengsb", "openengsb.notification.test@gmail.com", "25"); 
        notifier.getProperties().setSecureMode(SecureMode.STARTTLS.toString());
        return notifier;
    }

    private EmailNotifier createNotifier(String id, String prefix, Boolean smtpAuth, String smtpSender,
                                         String smtpHost, String smtpPassword, String smtpUser, String smtpPort) {
        JavaxMailAbstraction mailAbstraction = new JavaxMailAbstraction();
        EmailNotifier notifier = new EmailNotifier(id, mailAbstraction);
        return updateProperties(notifier, prefix, smtpAuth, smtpSender, smtpHost, smtpPassword, smtpUser, smtpPort);
    }
    
    private EmailNotifier createNotifier(String id) {
        JavaxMailAbstraction mailAbstraction = new JavaxMailAbstraction();
        return new EmailNotifier(id, mailAbstraction);
    }

    private EmailNotifier updateProperties(EmailNotifier notifier, String prefix, Boolean smtpAuth, 
            String smtpSender, String smtpHost, String smtpPassword, String smtpUser, String smtpPort) {
        notifier.createProperties();
        notifier.getProperties().setSmtpAuth(smtpAuth);
        notifier.getProperties().setPrefix(prefix);
        notifier.getProperties().setSender(smtpSender);
        notifier.getProperties().setSmtpHost(smtpHost);
        notifier.getProperties().setPassword(smtpPassword);
        notifier.getProperties().setUser(smtpUser);
        notifier.getProperties().setSmtpPort(smtpPort);
        return notifier;
    }

    private Notification createNotification() {
        Notification notification = ModelUtils.createEmptyModelObject(Notification.class);
        notification.setRecipient("openengsb.notification.test@gmail.com");
        notification.setSubject("TestMail send on " + new Date());
        notification.setMessage("This is a test mail");
        notification.setAttachments(new ArrayList<Attachment>());
        return notification;
    }
}
