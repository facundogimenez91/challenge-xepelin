package com.xepelin.challenge.service;

import com.xepelin.challenge.model.dao.EventDAO;
import reactor.core.publisher.Mono;

public interface EventService {

  Mono<EventDAO> create(EventDAO eventDAO);

}
