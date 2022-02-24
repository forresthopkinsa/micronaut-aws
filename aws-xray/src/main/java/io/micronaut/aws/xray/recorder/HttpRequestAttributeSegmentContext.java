/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.aws.xray.recorder;

import com.amazonaws.xray.contexts.ThreadLocalSegmentContext;
import com.amazonaws.xray.entities.Entity;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.context.ServerRequestContext;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Sergio del Amo
 * @since XXXX
 */
public class HttpRequestAttributeSegmentContext extends ThreadLocalSegmentContext {
    public static final String XRAY_SEGMENT_RESOLVER = "XRAY-SEGMENT-RESOLVER";

    @Nullable
    @Override
    public Entity getTraceEntity() {
        return resolveEntity().orElse(null);
    }

    private Optional<Entity> resolveEntity() {
        return ServerRequestContext.currentRequest()
                .flatMap(request -> request.getAttribute(XRAY_SEGMENT_RESOLVER, Entity.class));
    }

    @Override
    public void setTraceEntity(@Nullable Entity entity) {
        if (entity != null && entity.getCreator() != null) {
            entity.getCreator().getSegmentListeners().stream().filter(Objects::nonNull).forEach(l -> {
                l.onSetEntity(getTraceEntity(), entity);
            });
        }
        ServerRequestContext.currentRequest()
                .ifPresent(req -> req.setAttribute(XRAY_SEGMENT_RESOLVER, entity));
    }

    @Override
    public void clearTraceEntity() {
        Entity oldEntity = resolveEntity().orElse(null);
        if (oldEntity != null && oldEntity.getCreator() != null) {
            oldEntity.getCreator().getSegmentListeners().stream().filter(Objects::nonNull).forEach(l -> {
                l.onClearEntity(oldEntity);
            });
        }
        ServerRequestContext.currentRequest()
                .ifPresent(req -> req.removeAttribute(XRAY_SEGMENT_RESOLVER, Entity.class));
    }
}
