package org.sessioncontainer.client.componet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sessioncontainer.client.ClientConfig;
import org.sessioncontainer.client.Utils;
import org.sessioncontainer.client.service.SessionService;

/**
 * Servlet Filter implementation class SessionFilter
 */
public class HttpSessionFilter implements Filter {
	private final SessionService sessionService = SessionService.getInstance();
	/*private String sessionHolderKey = "SESSIONHOLDERKEY";
	private String authorizedKey = "SIMPLEAUTHORIZEDKEY";
	private int    authorizedLength = 16;
	
	//cookie的一些设置，为了避免一些奇怪bug的出现，直接写死
	private final String cookieDomain = "";
	private final String cookiePath = "/";
	private final int    cookieMaxAge = -1;*/
    /**
     * Default constructor. 
     */
    public HttpSessionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String sessionContainerId = sessionService.getSessionContainerId(request.getSession().getId());
		chain.doFilter(new HttpRequestWrapper(sessionContainerId, request),servletResponse);
		/*String sessionHolderId = getSessionHolderId(request, response);
		//一个新的会话，则新建sessionid并存储
		if (Utils.StringIsEmpty(sessionHolderId)) {
			sessionHolderId = buildSessionHolderId(request,response);
		}//现有会话
		else{
			if(!checkSessionHolderId(sessionHolderId)){//检查合法性
				response.getWriter().print("Sessoin is invalid!");
				return;
			}
		}*/
		
	}
	/*//从cookie中获取sessionid
	private String getSessionHolderId(HttpServletRequest request,HttpServletResponse response){
		Cookie cookies[] = request.getCookies();
		String sessionId = "";
		if (cookies != null && cookies.length > 0) {
			for(Cookie cookie: cookies){
				if(sessionHolderKey.equals(cookie.getName())){
					sessionId = cookie.getValue();
				}
			}
		}
		return sessionId;
	}
	//将sessionid写入cookie中,并返回
	private String buildSessionHolderId(HttpServletRequest request,HttpServletResponse response){
		String sessionId = new StringBuilder(60).append(authorizedKey).append("-")
							.append(Utils.getUUIDInUpperCase()).toString();
		Cookie mycookie = new Cookie(sessionHolderKey, sessionId);
		mycookie.setMaxAge(cookieMaxAge);
		mycookie.setPath(this.cookiePath);
		if (!Utils.StringIsEmpty(this.cookieDomain)){mycookie.setDomain(this.cookieDomain);}
		response.addCookie(mycookie);
		return sessionId;
	}
	//从cookie中删除sessionid
	private void removeSessionId(HttpServletRequest request,HttpServletResponse response){
		Cookie mycookie = new Cookie(sessionHolderKey, null);
		mycookie.setMaxAge(0);
		mycookie.setPath(cookiePath);
		if (!Utils.StringIsEmpty(this.cookieDomain)){mycookie.setDomain(this.cookieDomain);}
		response.addCookie(mycookie);
	}
	//检查sessionid
	private boolean checkSessionHolderId(String sessionId){
		if(sessionId.length()<authorizedLength				//长度不够
		||!authorizedKey.equals(sessionId.substring(0, authorizedLength))){//authorizedKey的md5值不同
			return false;
		}else{
			return true;
		}
	}*/
	/*//检查退出操作，如果退出，则删除对应cookie
	private void checkLogoutAction(HttpServletRequest request,HttpServletResponse response){
		String context = request.getContextPath();
		String uri = request.getRequestURI();
		if(!Utils.StringIsEmpty(context)){uri=uri.substring(context.length(),uri.length());}
		if(logoutSet.contains(uri)){
			removeSessionId(request,response);//remove cookie
			
			//delete session info from sessionholder
			HttpSession session=request.getSession();
			if(session!=null){
				if(session instanceof HttpSessionWrapper){
					((HttpSessionWrapper)session).deleteSession();
				}
			}
		}
	}*/
	
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	
	public void init(FilterConfig config) throws ServletException {
		/*this.sessionHolderKey = ClientConfig.getInstance().getSessionKey();
		this.authorizedKey=Utils.StringTo16Md5(ClientConfig.getInstance().getAuthorizedKey());*/
	}

}
