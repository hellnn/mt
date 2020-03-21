package org.example;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.example.api.AccountApi;
import org.example.api.impl.AccountApiImpl;
import org.example.dao.AccountRepository;
import org.example.service.AccountService;

public class MoneyTransferApplication {

    public static void main(String[] args) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(AccountApi.class);
        factoryBean.setResourceProvider(AccountApi.class,
                new SingletonResourceProvider(new AccountApiImpl(new AccountService(new AccountRepository()))));
        factoryBean.setProvider(new JacksonJsonProvider());
        factoryBean.setAddress("http://localhost:8080/");
        factoryBean.create();
    }
}
