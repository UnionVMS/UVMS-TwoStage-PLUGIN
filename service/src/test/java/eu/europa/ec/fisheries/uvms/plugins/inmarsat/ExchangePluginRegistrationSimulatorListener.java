package eu.europa.ec.fisheries.uvms.plugins.inmarsat;


import eu.europa.ec.fisheries.schema.exchange.plugin.types.v1.PluginType;
import eu.europa.ec.fisheries.schema.exchange.registry.v1.ExchangeRegistryBaseRequest;
import eu.europa.ec.fisheries.schema.exchange.registry.v1.RegisterServiceRequest;
import eu.europa.ec.fisheries.schema.exchange.registry.v1.UnregisterServiceRequest;
import eu.europa.ec.fisheries.schema.exchange.service.v1.ServiceResponseType;
import eu.europa.ec.fisheries.schema.exchange.service.v1.ServiceType;
import eu.europa.ec.fisheries.schema.exchange.service.v1.SettingListType;
import eu.europa.ec.fisheries.schema.exchange.service.v1.SettingType;
import eu.europa.ec.fisheries.uvms.commons.message.api.MessageConstants;
import eu.europa.ec.fisheries.uvms.exchange.model.constant.ExchangeModelConstants;
import eu.europa.ec.fisheries.uvms.exchange.model.constant.FaultCode;
import eu.europa.ec.fisheries.uvms.exchange.model.exception.ExchangeModelMapperException;
import eu.europa.ec.fisheries.uvms.exchange.model.exception.ExchangeModelMarshallException;
import eu.europa.ec.fisheries.uvms.exchange.model.mapper.ExchangePluginResponseMapper;
import eu.europa.ec.fisheries.uvms.exchange.model.mapper.JAXBMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Observes;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;


@MessageDriven(mappedName = MessageConstants.EVENT_BUS_TOPIC, activationConfig = {
        @ActivationConfigProperty(propertyName = MessageConstants.MESSAGE_SELECTOR_STR, propertyValue = ExchangeModelConstants.SERVICE_NAME + " = '" + ExchangeModelConstants.EXCHANGE_REGISTER_SERVICE + "'"),
        @ActivationConfigProperty(propertyName = MessageConstants.MESSAGING_TYPE_STR, propertyValue = MessageConstants.CONNECTION_TYPE),
        @ActivationConfigProperty(propertyName = MessageConstants.SUBSCRIPTION_DURABILITY_STR, propertyValue = MessageConstants.DURABLE_CONNECTION),
        @ActivationConfigProperty(propertyName = MessageConstants.DESTINATION_TYPE_STR, propertyValue = MessageConstants.DESTINATION_TYPE_TOPIC),
        @ActivationConfigProperty(propertyName = MessageConstants.DESTINATION_STR, propertyValue = MessageConstants.EVENT_BUS_TOPIC_NAME),
        @ActivationConfigProperty(propertyName = MessageConstants.DESTINATION_JNDI_NAME, propertyValue = MessageConstants.EVENT_BUS_TOPIC),
        @ActivationConfigProperty(propertyName = MessageConstants.SUBSCRIPTION_NAME_STR, propertyValue = ExchangeModelConstants.EXCHANGE_REGISTER_SERVICE),
        @ActivationConfigProperty(propertyName = MessageConstants.CLIENT_ID_STR, propertyValue = ExchangeModelConstants.EXCHANGE_REGISTER_SERVICE),
        @ActivationConfigProperty(propertyName = MessageConstants.CONNECTION_FACTORY_JNDI_NAME, propertyValue = MessageConstants.CONNECTION_FACTORY)
})
public class ExchangePluginRegistrationSimulatorListener implements MessageListener {

    final static Logger LOG = LoggerFactory.getLogger(ExchangePluginRegistrationSimulatorListener.class);



    @Override
    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;
        ServiceType settings = null;
        try {
            ExchangeRegistryBaseRequest request = JAXBMarshaller.unmarshallTextMessage(textMessage, ExchangeRegistryBaseRequest.class);
            switch (request.getMethod()) {
                case REGISTER_SERVICE:
                    RegisterServiceRequest regReq = JAXBMarshaller.unmarshallTextMessage(textMessage, RegisterServiceRequest.class);
                    settings = regReq.getService();
                    registerService(textMessage);
                    break;
                case UNREGISTER_SERVICE:
                    UnregisterServiceRequest unRegReq = JAXBMarshaller.unmarshallTextMessage(textMessage, UnregisterServiceRequest.class);
                    settings = unRegReq.getService();
                    unregisterService(textMessage);
                    break;
                default:
                    throw new Exception("[ Not implemented method consumed: " + request.getMethod() + " ]");
            }
        } catch (Exception  e) {
            LOG.error("[ Error when receiving message on topic in exchange: {}] {}",message,e);
            // CRAP errorEvent.fire(new PluginMessageEvent(textMessage, settings, ExchangePluginResponseMapper.mapToPluginFaultResponse(FaultCode.EXCHANGE_TOPIC_MESSAGE.getCode(), "Error when receiving message in exchange " + e.getMessage())));
        }
    }

    private void unregisterService(TextMessage textMessage) {
        try {
            UnregisterServiceRequest unregister = JAXBMarshaller.unmarshallTextMessage(textMessage, UnregisterServiceRequest.class);
        } catch (ExchangeModelMarshallException e) {
            e.printStackTrace();
        }

    }


    public void registerService(TextMessage textMessage) {
        RegisterServiceRequest register = null;
        try {
            register = JAXBMarshaller.unmarshallTextMessage(textMessage, RegisterServiceRequest.class);
            String messageId = textMessage.getJMSMessageID();
            boolean sendMessage;
        } catch (Exception  e) {
        }
    }








}




