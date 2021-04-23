/*
 * Copyright 2017-2021 original authors
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
package io.micronaut.tracing.aws.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * Cloud Watch Metrics configuration.
 * @author Sergio del Amo
 * @since 2.7.0
 */
@ConfigurationProperties(XRayCloudWatchMetricsConfigurationProperties.PREFIX)
public class XRayCloudWatchMetricsConfigurationProperties implements XRayCloudWatchMetricsConfiguration {
    public static final String PREFIX = XRayConfigurationProperties.PREFIX + ".cloud-watch-metrics";


    private static final boolean DEFAULT_ENABLED = true;

    private boolean enabled = DEFAULT_ENABLED;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether this configuration is enabled. Default {@value #DEFAULT_ENABLED}.
     *
     * @param enabled The enabled setting
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
