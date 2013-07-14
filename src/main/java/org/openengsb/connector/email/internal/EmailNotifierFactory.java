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

package org.openengsb.connector.email.internal;

import java.util.Map;

import org.openengsb.connector.email.internal.abstraction.MailAbstractionFactory;
import org.openengsb.core.api.Connector;
import org.openengsb.core.common.AbstractConnectorInstanceFactory;

public class EmailNotifierFactory extends AbstractConnectorInstanceFactory<EmailNotifier> {

    private MailAbstractionFactory factory;

    @Override
    public Connector createNewInstance(String id) {
        return new EmailNotifier(id, factory.newInstance());
    }

    @Override
    public EmailNotifier doApplyAttributes(EmailNotifier notifier, Map<String, String> attributes) {
        notifier.createProperties();

        if (attributes.containsKey("user")) {
            notifier.getProperties().setUser(attributes.get("user"));
        }
        if (attributes.containsKey("password")) {
            notifier.getProperties().setPassword(attributes.get("password"));
        }
        if (attributes.containsKey("prefix")) {
            notifier.getProperties().setPrefix(attributes.get("prefix"));
        }
        if (attributes.containsKey("smtpAuth")) {
            notifier.getProperties().setSmtpAuth(Boolean.parseBoolean(attributes.get("smtpAuth")));
        }
        if (attributes.containsKey("smtpSender")) {
            notifier.getProperties().setSender(attributes.get("smtpSender"));
        }
        if (attributes.containsKey("smtpHost")) {
            notifier.getProperties().setSmtpHost(attributes.get("smtpHost"));
        }
        if (attributes.containsKey("smtpPort")) {
            notifier.getProperties().setSmtpPort(attributes.get("smtpPort"));
        }
        if (attributes.containsKey("secureMode")) {
            notifier.getProperties().setSecureMode(attributes.get("secureMode"));
        }
        if (attributes.containsKey("trustedSites")) {
            notifier.getProperties().setTrustedSites(attributes.get("trustedSites"));
        }
        
        return notifier;
    }

    public void setFactory(MailAbstractionFactory factory) {
        this.factory = factory;
    }
}
