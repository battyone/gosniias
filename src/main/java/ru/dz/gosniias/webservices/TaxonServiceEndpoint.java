package ru.dz.gosniias.webservices;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.dz.gosniias.service.ITaxonService;

/**
 *
 * @author vassaeve
 */
//@SOAPBinding(style = Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.BARE)
//@WebService(serviceName = "TaxonService")
//@Service
public class TaxonServiceEndpoint {//extends SpringBeanAutowiringSupport {

//    @Autowired
    ITaxonService taxonService;
    
    
//    @WebMethod(operationName = "getHelloWorld")
    public String getHelloWorld() {

        return "hello";

    }

//    @PostConstruct
//    @WebMethod(exclude = true)
    public void postConstruct() {
        System.out.println("postconstruct has run.");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
}
