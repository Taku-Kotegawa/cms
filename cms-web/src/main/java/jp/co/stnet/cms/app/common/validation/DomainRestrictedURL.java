/*
 * Copyright(c) 2013 NTT DATA Corporation. Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.co.stnet.cms.app.common.validation;

import org.hibernate.validator.constraints.URL;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {DomainRestrictedURLValidator.class})
@Target(FIELD)
@Retention(RUNTIME)
@Repeatable(DomainRestrictedURL.List.class)
@URL
public @interface DomainRestrictedURL {

    String message() default "{jp.co.stnet.cms.app.common.validation.DomainRestrictedURL.message}";

    Class<?>[] groups() default {};

    String[] allowedDomains() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(FIELD)
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        DomainRestrictedURL[] value();
    }

}
