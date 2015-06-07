package org.saumilp.tutorials.rest;

import org.saumilp.tutorials.models.Customer;
import org.saumilp.tutorials.repositories.CustomerRepository;
import org.saumilp.tutorials.support.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API Handler
 */
@RestController
public class RestApiHandler extends AbstractRestHandler {
    @Autowired CustomerRepository customerRepository;
    @Autowired MongoTemplate mongoTemplate;

    @RequestMapping ("/api/customer/{customerId}")
    public @ResponseBody Customer findCustomerById(@PathVariable("customerId") String customerId){
        return customerRepository.findById(customerId);
    }

    @RequestMapping("/api/customer/all")
    public @ResponseBody List findAll(
            @RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "size", required = true) Integer size){
        Page pageOfCustomer = customerRepository.findAll( new PageRequest(page,size));
        return pageOfCustomer.getContent();
    }

    @RequestMapping(value = "/adminapi/customer/add", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public RestResponse addCustomer(@RequestBody Customer customer){
        RestResponse response = new RestResponse("Success", "Customer Object was successfully saved");
        mongoTemplate.save(customer);
        return response;
    }
}
