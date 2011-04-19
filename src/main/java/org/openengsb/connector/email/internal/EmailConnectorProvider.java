package org.openengsb.connector.email.internal;

import org.openengsb.connector.email.internal.abstraction.MailProperties.SecureMode;
import org.openengsb.core.api.descriptor.AttributeDefinition;
import org.openengsb.core.api.descriptor.ServiceDescriptor;
import org.openengsb.core.api.descriptor.ServiceDescriptor.Builder;
import org.openengsb.core.common.AbstractConnectorProvider;

public class EmailConnectorProvider extends AbstractConnectorProvider {

    @Override
    public ServiceDescriptor getDescriptor() {
        Builder builder = ServiceDescriptor.builder(strings);
        builder.id(this.id);
        builder.name("email.name").description("email.description");
        builder
            .attribute(buildAttribute(builder, "user", "username.outputMode", "username.outputMode.description"))
            .attribute(
                builder.newAttribute().id("password").name("password.outputMode")
                    .description("password.outputMode.description").defaultValue("").required().asPassword().build())
            .attribute(buildAttribute(builder, "prefix", "prefix.outputMode", "prefix.outputMode.description"))
            .attribute(
                builder.newAttribute().id("smtpAuth").name("mail.smtp.auth.outputMode")
                    .description("mail.smtp.auth.outputMode.description").defaultValue("false").asBoolean().build())
            .attribute(
                buildAttribute(builder, "smtpSender", "mail.smtp.sender.outputMode",
                    "mail.smtp.sender.outputMode.description"))
            .attribute(
                buildAttribute(builder, "smtpPort", "mail.smtp.port.outputMode",
                    "mail.smtp.port.outputMode.description"))
            .attribute(
                buildAttribute(builder, "smtpHost", "mail.smtp.host.outputMode",
                    "mail.smtp.host.outputMode.description"))
            .attribute(
                builder.newAttribute().id("secureMode").name("secureMode.outputMode")
                    .description("secureMode.outputMode.description")
                    .option("secureMode.option.starttls", SecureMode.STARTTLS.toString())
                    .option("secureMode.option.ssl", SecureMode.SSL.toString())
                    .option("secureMode.option.plain", SecureMode.PLAIN.toString()).build());

        return builder.build();
    }

    private AttributeDefinition buildAttribute(ServiceDescriptor.Builder builder, String id, String nameId,
            String descriptionId) {
        return builder.newAttribute().id(id).name(nameId).description(descriptionId).defaultValue("").required()
            .build();

    }

}
