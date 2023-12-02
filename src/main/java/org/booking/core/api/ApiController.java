package org.booking.core.api;

import jakarta.ws.rs.core.Response;

public interface ApiController<T> extends BaseApiController<T, Response, Long> {
}
