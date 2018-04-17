package gov.ca.cwds.data;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableMap;

import io.dropwizard.hibernate.UnitOfWorkAspect;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

public class CaresUnitOfWorkAwareProxyFactory extends UnitOfWorkAwareProxyFactory {

  @Override
  public UnitOfWorkAspect newAspect() {
    return super.newAspect();
  }

  @Override
  public UnitOfWorkAspect newAspect(ImmutableMap<String, SessionFactory> sessionFactories) {
    return super.newAspect(sessionFactories);
  }

}
