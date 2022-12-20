package cn.iosd.starter.freemarker.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import java.util.Map;

/**
 * 获取访问地址，返回页面，静态资源地址使用
 *
 * @author ok1996
 */
public class MyFreemarkerView extends FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        String base = getBasePath(request);
        model.put("base", base);
        super.exposeHelpers(model, request);
    }

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        return basePath;
    }
}
