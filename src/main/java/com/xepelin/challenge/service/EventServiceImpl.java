package com.xepelin.challenge.service;

import com.xepelin.challenge.model.dao.EventDAO;
import com.xepelin.challenge.repository.EventRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  @NonNull private EventRepository eventRepository;

  @Override
  public Mono<EventDAO> create(EventDAO eventDAO) {
    return eventRepository.save(eventDAO);
  }

}
