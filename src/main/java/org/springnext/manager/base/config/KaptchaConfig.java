package org.springnext.manager.base.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {

	@Bean(name = "defaultKaptcha")
	public DefaultKaptcha createDefaultKaptcha() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		//验证码宽度
		properties.put("kaptcha.image.width", 110);
		//验证码高度
		properties.put("kaptcha.image.height", 50);
		//生成验证码内容范围
		properties.put("kaptcha.textproducer.char.string", "23456789ABCDEFGHJKLMNPQRSTUVWXYZ");
		//验证码个数
		properties.put("kaptcha.textproducer.char.length", 4);
         //验证码个数
         properties.put("kaptcha.textproducer.char.length","4");  
         //是否有边框
         properties.put("kaptcha.border","no");  
         //边框颜色
         properties.put("kaptcha.border.color","105,179,90");  
         //边框厚度
         properties.put("kaptcha.border.thickness","1");  
         //验证码字体颜色
         properties.put("kaptcha.textproducer.font.color","black");  
         //验证码字体大小
         properties.put("kaptcha.textproducer.font.size","30");  
         //验证码所属字体样式
         properties.put("kaptcha.textproducer.font.names","楷体");  
         //干扰线颜色
         properties.put("kaptcha.noise.color","black");  
         //验证码文本字符间距
         properties.put("kaptcha.textproducer.char.space","3");  
         //图片样式 :阴影-->  
         properties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy"); 
		
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		
		return defaultKaptcha;
	}

}
