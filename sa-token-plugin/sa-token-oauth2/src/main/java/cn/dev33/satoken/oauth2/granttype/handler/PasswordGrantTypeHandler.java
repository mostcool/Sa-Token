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
package cn.dev33.satoken.oauth2.granttype.handler;

import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.consts.GrantType;
import cn.dev33.satoken.oauth2.consts.SaOAuth2Consts;
import cn.dev33.satoken.oauth2.data.model.AccessTokenModel;
import cn.dev33.satoken.oauth2.data.model.request.RequestAuthModel;
import cn.dev33.satoken.oauth2.exception.SaOAuth2Exception;
import cn.dev33.satoken.stp.StpUtil;

import java.util.List;

/**
 * password grant_type 处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class PasswordGrantTypeHandler implements SaOAuth2GrantTypeHandlerInterface {

    @Override
    public String getHandlerGrantType() {
        return GrantType.password;
    }

    @Override
    public AccessTokenModel getAccessToken(SaRequest req, String clientId, List<String> scopes) {

        // 1、获取请求参数
        String username = req.getParamNotNull(SaOAuth2Consts.Param.username);
        String password = req.getParamNotNull(SaOAuth2Consts.Param.password);

        // 3、调用API 开始登录，如果没能成功登录，则直接退出
        loginByUsernamePassword(username, password);
        Object loginId = StpUtil.getLoginIdDefaultNull();
        if(loginId == null) {
            throw new SaOAuth2Exception("登录失败");
        }

        // 4、构建 ra 对象
        RequestAuthModel ra = new RequestAuthModel();
        ra.clientId = clientId;
        ra.loginId = loginId;
        ra.scopes = scopes;

        // 5、生成 Access-Token
        AccessTokenModel at = SaOAuth2Manager.getDataGenerate().generateAccessToken(ra, true);
        return at;
    }

    /**
     * 根据 username、password 进行登录，如果登录失败请直接抛出异常
     * @param username /
     * @param password /
     */
    public void loginByUsernamePassword(String username, String password) {
        SaOAuth2Manager.getServerConfig().doLoginHandle.apply(username, password);
    }

}