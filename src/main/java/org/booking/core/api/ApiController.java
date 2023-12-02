package org.booking.core.api;

import org.springframework.http.ResponseEntity;

public interface ApiController<T> extends BaseApiController<T, ResponseEntity<String>, Long> {
}
