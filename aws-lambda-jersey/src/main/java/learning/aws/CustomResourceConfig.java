package learning.aws;

import com.amazonaws.serverless.proxy.jersey.suppliers.AwsProxyServletContextSupplier;
import com.amazonaws.serverless.proxy.jersey.suppliers.AwsProxyServletRequestSupplier;
import com.amazonaws.serverless.proxy.jersey.suppliers.AwsProxyServletResponseSupplier;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.jvnet.hk2.guice.bridge.api.GuiceBridge.getGuiceBridge;

public class CustomResourceConfig extends ResourceConfig {
    @Inject
    public CustomResourceConfig(ServiceLocator locator) throws Exception {

        bridge(locator, Guice.createInjector(new Models("development"), new AbstractModule() {
            @Override
            protected void configure() {
                bind(ServiceLocator.class).toInstance(locator);
            }
        }));

        property(org.glassfish.jersey.server.ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        packages("learning.aws.resource");
        register(JacksonFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(AwsProxyServletContextSupplier.class)
                        .to(ServletContext.class).in(RequestScoped.class);
                bindFactory(AwsProxyServletRequestSupplier.class)
                        .to(HttpServletRequest.class).in(RequestScoped.class);
                bindFactory(AwsProxyServletResponseSupplier.class)
                        .to(HttpServletResponse.class).in(RequestScoped.class);
            }
        });
//        register(RoutesFeature.class);
//        register(LoggingFilter.class);
//        register(CORSResponseFilter.class);
    }

    private void bridge(ServiceLocator serviceLocator, Injector injector) {
        getGuiceBridge().initializeGuiceBridge(serviceLocator);
        serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(injector);
    }

}
