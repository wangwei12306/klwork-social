package com.klwork.explorer.ui.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.klwork.common.utils.logging.Logger;
import com.klwork.common.utils.logging.LoggerFactory;
import com.klwork.explorer.I18nManager;
import com.klwork.explorer.ViewToolManager;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class AbstractTabViewPage extends CustomComponent implements
		TabSheet.SelectedTabChangeListener {

	protected I18nManager i18nManager;
	protected Map<String, Component> tabCache = new HashMap<String, Component>();
	protected transient Logger logger = LoggerFactory.getLogger(getClass());
	protected boolean forceLazLoad = true;

	public AbstractTabViewPage() {
		this.i18nManager = ViewToolManager.getI18nManager();
	}

	public AbstractTabViewPage(boolean forceLazLoad) {
		this();
		this.forceLazLoad = forceLazLoad;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8872314577819805067L;
	private TabSheet tabSheet = new TabSheet();
	VerticalLayout mainLayout;

	public TabSheet getTabSheet() {
		return tabSheet;
	}

	public void setTabSheet(TabSheet tabSheet) {
		this.tabSheet = tabSheet;
	}

	@Override
	public void attach() {
		super.attach();
		initUi();
	}

	protected void initUi() {
		setSizeFull();
		initMainLayout();
		initTabData();
	}

	protected void initTabData() {

	}

	private void initMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		setCompositionRoot(mainLayout);

		mainLayout.addComponent(tabSheet);
		mainLayout.setExpandRatio(tabSheet, 1.0f);
		tabSheet.setSizeFull();
		tabSheet.addStyleName("borderless");
		tabSheet.addStyleName("editors");
		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setCloseHandler(currentCloseHandler());
	}

	public CloseHandler currentCloseHandler() {
		return new CloseHandler() {
			private static final long serialVersionUID = -1764556772862038086L;

			@Override
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				Tab addTab = tabsheet.getTab(tabContent);
				String name = addTab.getCaption();
				if (getTabCache().get(name) != null) {
					getTabCache().remove(name);
				}
				tabsheet.removeComponent(tabContent);
			}
		};
	}

	public Tab addTab(Component c, String caption) {
		return addTab(c, caption, caption, null);
	}

	public Tab addTab(Component c, String key, String caption) {
		return addTab(c, key, caption, null);
	}

	public Tab addTabSpecial(Component c, String caption) {
		Component todoTabObj = null;
		if (tabCache.get(caption) != null) {
			todoTabObj = tabCache.get(caption);
			setSelectedTab(todoTabObj);
			return tabSheet.getTab(todoTabObj);
		} else {
			Tab t = addTab(c, caption);
			t.setClosable(true);
			setSelectedTab(c);
			return t;
		}
	}

	public Tab addTabSpecial(Component c, String caption, String key) {
		Component todoTabObj = null;
		if (tabCache.get(key) != null) {
			todoTabObj = tabCache.get(key);
			setSelectedTab(todoTabObj);
			return tabSheet.getTab(todoTabObj);
		} else {
			Tab t = addTab(c, key, caption);
			t.setClosable(true);
			setSelectedTab(c);
			return t;
		}
	}

	public String queryTabKey(Component c) {
		Set<String> kSet = tabCache.keySet();
		for (String s : kSet) {
			Component o = tabCache.get(s);
			if (o != null && c.equals(o)) {
				return s;
			}
		}
		return null;
	}

	public Tab addTab(Component c, String caption, Resource icon) {
		tabCache.put(caption, c);
		return tabSheet.addTab(c, caption, icon);
	}

	public Tab addTab(Component c, String key, String caption, Resource icon) {
		tabCache.put(key, c);
		return tabSheet.addTab(c, caption, icon);
	}

	public Tab addTab(Component c) {
		return addTab(c, c.getCaption());
	}

	public void setSelectedTab(Component tabObj) {
		tabSheet.setSelectedTab(tabObj);
	}

	public void refreshRelatedView() {
		// TODO Auto-generated method stub

	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		final TabSheet source = (TabSheet) event.getSource();
		Component c = source.getSelectedTab();
		if (c instanceof TabLayLoadComponent) {
			TabLayLoadComponent s = (TabLayLoadComponent) c;
			if (forceLazLoad && s.isLazyload() && !s.isStartInit()) {// 没有进行初始化
				logger.debug("tab进行切换，执行startInit--------" + c.toString());
				s.startInit();
				s.setStartInit(true);// 设置已经初始化完成了
			}
		}
	}

	public Map<String, Component> getTabCache() {
		return tabCache;
	}

	public void setTabCache(Map<String, Component> tabCache) {
		this.tabCache = tabCache;
	}

}
