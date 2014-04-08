package com.kechuang.wifidog.horizon.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.service.MenuService;
import com.kechuang.wifidog.horizon.model.Tree;
import com.kechuang.wifidog.horizon.model.User;

/**
 * 菜单控制服务
 * 
 * @author
 */
@Service("com.kechuang.wifidog.horizon.service.impl.MenuService")
public class MenuServiceImpl implements MenuService {
	private static final Log LOGGER = LogFactory.getLog(MenuServiceImpl.class);

	private List<Tree> getMenusByUserName(User user) throws Exception {
		List<Tree> treeList = new ArrayList<Tree>();
		String configFile;
		if (user.getUserType() == User.USER_TYPE.SUPERUSER.ordinal()) {
			configFile = MenuServiceImpl.class.getResource("/admin/menus.xml").getPath();
		} else if (user.getUserType() == User.USER_TYPE.VENDERUSER.ordinal()) {
			configFile = MenuServiceImpl.class.getResource("/vender/menus.xml").getPath();
		} else if (user.getUserType() == User.USER_TYPE.BUSINESSUSER.ordinal()) {
			configFile = MenuServiceImpl.class.getResource("/business/menus.xml").getPath();
		} else {
			return new ArrayList<Tree>();
		}
		
		SAXReader reader = new SAXReader();
		File file = new File(configFile);
		if (file.exists()) {
			try {
				Document document = reader.read(file);
				Element root = document.getRootElement();
				Iterator iters = root.elementIterator("menu");
				while (iters.hasNext()) {
					Element itemEle = (Element) iters.next();
					String priority = itemEle.attribute("priority").getText();
					if (priority.equals("1")) {
						continue;
					}
					Tree pTree = new Tree();
					pTree.setId(itemEle.attribute("id").getText());
					// pTree.setPid(itemEle.attribute("pid").getText());
					pTree.setText(itemEle.attribute("text").getText());
					pTree.setIconCls(itemEle.attribute("icon").getText());
					treeList.add(pTree);
					Iterator citr = itemEle.elementIterator("childmenu");
					List<Tree> children = new ArrayList<Tree>();
					while (citr.hasNext()) {
						Element cElement = (Element) citr.next();

						String priority2 = itemEle.attribute("priority")
								.getText();
						if (priority2.equals("1")) {
							continue;
						}

						Tree cTree = new Tree();
						cTree.setId(cElement.attribute("id").getText());
						// cTree.setPid(cElement.attribute("pid").getText());
						cTree.setText(cElement.attribute("text").getText());
						cTree.setIconCls(cElement.attribute("icon").getText());
						Map<String, String> attr = new HashMap<String, String>();
						attr.put("url", cElement.attribute("url").getText());
						cTree.setAttributes(attr);
						cTree.setPid(pTree.getId());
						cTree.setState("open");
						children.add(cTree);
					}

					pTree.setChildren(children);
				}
			} catch (DocumentException e) {
				LOGGER.error("read menus.xml error!", e);
				throw new Exception(e);
			}
		} else {
			LOGGER.error("read menus.xml error,file not found.");
		}
		return treeList;
	}

	public List<Tree> getMenuByUser(User user) throws Exception {
		LOGGER.info("get menus by user:" + user.getUserName());
		List<Tree> list = getMenusByUserName(user);
		return list;
	}
}
