package com.qrabbit.youpinghui.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.qrabbit.youpinghui.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class PageListener {

	@Autowired
	private ItemPageService itemPageService;
		
	@JmsListener(destination=JmsConfig.TOPIC_HTML,containerFactory = "jmsListenerContainerTopic")
	public void onPageCreated(Message message) {
		System.out.println("-------->");
		TextMessage textMessage=(TextMessage)message;
		try {
			System.out.println(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			String text = textMessage.getText();
			System.out.println("接收到消息："+text);//商品ID
			boolean b = itemPageService.genItemHtml(Long.parseLong(text));
			System.out.println("网页生成结果："+b);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	@JmsListener(destination=JmsConfig.TOPIC_HTML_DELETE,containerFactory = "jmsListenerContainerTopic")
	public void onPageDeleted(Message message) {
		
		ObjectMessage objectMessage  =(ObjectMessage)message;
		try {
			Long [] goodsIds= (Long[]) objectMessage.getObject();
			System.out.println("接收到消息:"+goodsIds);
			boolean b = itemPageService.deleteItemHtml(goodsIds);		
			System.out.println("删除网页："+b);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
