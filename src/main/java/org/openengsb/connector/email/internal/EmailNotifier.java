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

import org.apache.commons.lang.StringUtils;
import org.openengsb.connector.email.internal.abstraction.MailAbstraction;
import org.openengsb.connector.email.internal.abstraction.MailProperties;
import org.openengsb.core.api.AliveState;
import org.openengsb.core.common.AbstractOpenEngSBConnectorService;
import org.openengsb.domain.notification.NotificationDomain;
import org.openengsb.domain.notification.model.Notification;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotifier extends AbstractOpenEngSBConnectorService implements NotificationDomain {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotifier.class);

    private final MailAbstraction mailAbstraction;
    private ServiceRegistration serviceRegistration;
    private MailProperties properties;

    public EmailNotifier(String instanceId, MailAbstraction mailAbstraction) {
        super(instanceId);
        this.mailAbstraction = mailAbstraction;
    }

    @Override
    public void notify(Notification notification) {
        LOGGER.info("notifying {} via email...", notification.getRecipient());
        LOGGER.info("Subject: {}", notification.getSubject());
        LOGGER.info("Message: {}", StringUtils.abbreviate(notification.getMessage(), 200));
        mailAbstraction.send(properties, notification.getSubject(), notification.getMessage(), notification
                .getRecipient());
        LOGGER.info("mail has been sent");
    }

    @Override
    public AliveState getAliveState() {
        AliveState aliveState = mailAbstraction.getAliveState();
        if (aliveState == null) {
            return AliveState.OFFLINE;
        }
        return aliveState;
    }

    public ServiceRegistration getServiceRegistration() {
        return serviceRegistration;
    }

    public void setServiceRegistration(ServiceRegistration serviceRegistration) {
        this.serviceRegistration = serviceRegistration;
    }
    
    public MailProperties getProperties() {
        return properties;
    }

    public void createProperties() {
        properties = mailAbstraction.createMailProperties();
    }
}
