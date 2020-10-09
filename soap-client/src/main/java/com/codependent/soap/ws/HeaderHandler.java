package com.codependent.soap.ws;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;
import java.util.TreeSet;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return new TreeSet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {

            SOAPMessage message = smc.getMessage();

            try {
                SOAPFactory soapFactory = SOAPFactory.newInstance();
                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                //SOAPHeader header = envelope.addHeader();
                //SOAPHeaderElement se = header.addHeaderElement(new QName("http://spring.io/guides/gs-producing-web-service/GetCountryRequest", "Action"));
                //se.setMustUnderstand(true); //Ideal way to set if webservice supports
                //se.addTextNode("some text");
                //se.addAttribute(soapFactory.createName("S:mustUnderstand"), "1"); //S: or s: depending on xmlns

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                SOAPMessage message = smc.getMessage();
                message.writeTo(System.out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }
}
