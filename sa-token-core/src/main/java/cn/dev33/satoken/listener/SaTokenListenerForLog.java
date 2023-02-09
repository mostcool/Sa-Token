package cn.dev33.satoken.listener;

import static cn.dev33.satoken.SaManager.log;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.util.SaFoxUtil;

/**
 * Sa-Token 侦听器实现：Log 打印 
 * 
 * @author kong
 * @since 2022-11-2
 */
public class SaTokenListenerForLog implements SaTokenListener {

	/**
	 * 每次登录时触发 
	 */
	@Override
	public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
		log.info("账号 {} 登录成功 (loginType={}), 会话凭证Token={}", loginId, loginType, tokenValue);
	}

	/**
	 * 每次注销时触发 
	 */
	@Override
	public void doLogout(String loginType, Object loginId, String tokenValue) {
		log.info("账号 {} 注销登录 (loginType={}), 会话凭证Token={}", loginId, loginType, tokenValue);
	}

	/**
	 * 每次被踢下线时触发
	 */
	@Override
	public void doKickout(String loginType, Object loginId, String tokenValue) {
		log.info("账号 {} 被踢下线 (loginType={}), 会话凭证Token={}", loginId, loginType, tokenValue);
	}

	/**
	 * 每次被顶下线时触发
	 */
	@Override
	public void doReplaced(String loginType, Object loginId, String tokenValue) {
		log.info("账号 {} 被顶下线 (loginType={}), 会话凭证Token={}", loginId, loginType, tokenValue);
	}

	/**
	 * 每次被封禁时触发
	 */
	@Override
	public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
		log.info("账号 {} [{}服务] 被封禁 (loginType={}), 封禁等级={}, 解封时间为 {}", loginId, loginType, service, level, SaFoxUtil.formatAfterDate(disableTime * 1000));
	}

	/**
	 * 每次被解封时触发
	 */
	@Override
	public void doUntieDisable(String loginType, Object loginId, String service) {
		log.info("账号 {} [{}服务] 解封成功 (loginType={})", loginId, service, loginType);
	}
	
	/**
	 * 每次打开二级认证时触发
	 */
	@Override
	public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
		log.info("Token 二级认证成功, 业务标识={}, 有效期={}秒, Token值={}", service, safeTime, tokenValue);
	}

	/**
	 * 每次关闭二级认证时触发
	 */
	@Override
	public void doCloseSafe(String loginType, String tokenValue, String service) {
		log.info("Token 二级认证关闭, 业务标识={}, Token值={}", service, tokenValue);
	}

	/**
	 * 每次创建Session时触发
	 */
	@Override
	public void doCreateSession(String id) {
		log.info("SaSession [{}] 创建成功", id);
	}

	/**
	 * 每次注销Session时触发
	 */
	@Override
	public void doLogoutSession(String id) {
		log.info("SaSession [{}] 注销成功", id);
	}

	/**
	 * 每次Token续期时触发
	 */
	@Override
	public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
		log.info("Token 续期成功, {} 秒后到期, 帐号={}, Token值={} ", timeout, loginId, tokenValue);
	}


	/**
	 * 全局组件载入 
	 * @param comtName 组件名称 
	 * @param comtObj 组件对象 
	 */
	@Override
	public void doRegisterComponent(String comtName, Object comtObj) {
		String canonicalName = comtObj == null ? null : comtObj.getClass().getCanonicalName();
		log.info("全局组件 {} 载入成功: {}", comtName, canonicalName);
	}

	/**
	 * StpLogic 对象替换 
	 * @param stpLogic / 
	 */
	@Override
	public void doSetStpLogic(StpLogic stpLogic) {
		if(stpLogic != null) {
			log.info("会话组件 StpLogic(type={}) 重置成功: {}", stpLogic.getLoginType(), stpLogic.getClass());
		}
	}

	/**
	 * 载入全局配置 
	 * @param config / 
	 */
	@Override
	public void doSetConfig(SaTokenConfig config) {
		if(config != null) {
			log.info("全局配置 {} ", config);
		}
	}

}
