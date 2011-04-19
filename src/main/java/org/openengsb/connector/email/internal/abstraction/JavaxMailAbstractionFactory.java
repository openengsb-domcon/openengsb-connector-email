package org.openengsb.connector.email.internal.abstraction;

public class JavaxMailAbstractionFactory implements MailAbstractionFactory {

    @Override
    public MailAbstraction newInstance() {
        return new JavaxMailAbstraction();
    }

}
