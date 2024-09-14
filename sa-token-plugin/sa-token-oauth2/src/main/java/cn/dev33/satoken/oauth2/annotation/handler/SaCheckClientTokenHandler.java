/*
 * Copyright 2020-2099 sa-token.cc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.dev33.satoken.oauth2.annotation.handler;

import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.annotation.SaCheckClientToken;

import java.lang.reflect.Method;

/**
 * 注解 SaCheckAccessToken 的处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class SaCheckClientTokenHandler implements SaAnnotationHandlerInterface<SaCheckClientToken> {

    @Override
    public Class<SaCheckClientToken> getHandlerAnnotationClass() {
        return SaCheckClientToken.class;
    }

    @Override
    public void checkMethod(SaCheckClientToken at, Method method) {
        _checkMethod(at.scope());
    }

    public static void _checkMethod(String[] scope) {
        String clientToken = SaOAuth2Manager.getDataResolver().readClientToken(SaHolder.getRequest());
        SaOAuth2Manager.getTemplate().checkClientTokenScope(clientToken, scope);
    }

}