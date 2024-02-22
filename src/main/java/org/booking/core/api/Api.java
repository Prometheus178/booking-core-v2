package org.booking.core.api;

import org.springframework.http.ResponseEntity;

public interface Api<T> extends BaseApi<T, ResponseEntity<T>, Long> {
}
