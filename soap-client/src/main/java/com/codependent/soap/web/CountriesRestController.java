package com.codependent.soap.web;

import com.codependent.soap.ws.*;
import com.codependent.soap.ws.reactive.ReactorAsyncHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CountriesRestController {

    @GetMapping("/countries/{country}")
    public Mono<GetCountryResponse> getCountry(@PathVariable String country, @RequestParam(required = false) boolean async) throws MalformedURLException {
        CountriesPortService service = new CountriesPortService(new URL("http://localhost:8080/ws/countries.wsdl"));
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);
        CountriesPort countriesPortSoap11 = service.getCountriesPortSoap11();
        BindingProvider bindingProvider = (BindingProvider) countriesPortSoap11;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/ws");
        /*final Binding binding = bindingProvider.getBinding();
        List<Handler> handlerList = binding.getHandlerChain();

        if (handlerList == null) {
            handlerList = new ArrayList<>();
        }

        handlerList.add(new HeaderHandler());
        binding.setHandlerChain(handlerList);*/

        if(async){
            return Mono.create(sink -> countriesPortSoap11.getCountryAsync(request, ReactorAsyncHandler.into(sink)));
        } else {
            return Mono.just(countriesPortSoap11.getCountry(request));
        }
    }
}
