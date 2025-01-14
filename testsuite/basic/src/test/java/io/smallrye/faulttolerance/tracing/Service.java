/*
 * Copyright 2018 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.smallrye.faulttolerance.tracing;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import io.opentracing.mock.MockTracer;
import io.opentracing.util.GlobalTracer;

/**
 * @author Pavol Loffay
 */
public class Service {

    public static MockTracer mockTracer = new MockTracer();

    static {
        GlobalTracer.register(mockTracer);
    }

    @Fallback(fallbackMethod = "fallback")
    @Timeout(value = 200L)
    @Retry(delay = 100L, maxRetries = 2)
    public String foo() {
        mockTracer.buildSpan("foo").start().finish();
        throw new RuntimeException();
    }

    public String fallback() {
        return "fallback";
    }
}
