package com.chakray.microservice.router;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.chakray.microservice.pojo.Instruction;

@Component
public class FuseRouteBuilder extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    restConfiguration().component("servlet").bindingMode(RestBindingMode.json_xml);


    rest("/instruction").post().consumes(MediaType.APPLICATION_JSON_VALUE).type(Instruction.class).to("direct:transform");

    from("direct:transform").log("body: ${body}").choice().when().simple("${body.destination} == 'Salesforce'").to("direct:sendToSalesforce").when()
        .simple("${body.destination} == 'Navision'").to("direct:notImplemented");

    from("direct:sendToSalesforce").log("Salesforce").setHeader("Authorization", simple("Bearer {{salesforce.bearer.token}}")).process(exchange -> {
      final Instruction instruction = exchange.getIn().getBody(Instruction.class);
      exchange.getIn().setBody(instruction.getInformation());
    }).marshal().json().toD("{{salesforce.host}}/services/data/v56.0/sobjects/Instruction?bridgeEndpoint=true").to("direct:respond");

    from("direct:respond").log("responding").setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).setBody(constant(null));

    from("direct:notImplemented").log("Not Implemented").setHeader(Exchange.HTTP_RESPONSE_CODE, constant(501))
        .setBody(simple("{\"status\":\"Not Implemented\",\"message\":\"Service not implemented yet. Contact support team\"}")).unmarshal().json();

  }

}
