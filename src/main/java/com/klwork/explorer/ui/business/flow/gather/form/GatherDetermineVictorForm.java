package com.klwork.explorer.ui.business.flow.gather.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;

import com.klwork.business.domain.model.OutsourcingProject;
import com.klwork.business.domain.model.ProjectParticipant;
import com.klwork.business.domain.service.ProjectParticipantService;
import com.klwork.explorer.Messages;
import com.klwork.explorer.ViewToolManager;
import com.klwork.explorer.data.LazyLoadingContainer;
import com.klwork.explorer.data.LazyLoadingQuery;
import com.klwork.explorer.ui.Images;
import com.klwork.explorer.ui.business.flow.act.ParticipantListQuery;
import com.klwork.explorer.ui.business.flow.act.UploadWorkTaskContentComponent;
import com.klwork.explorer.ui.event.SubmitEvent;
import com.klwork.explorer.ui.form.FormPropertiesEvent;
import com.klwork.explorer.ui.handler.BinderHandler;
import com.klwork.explorer.ui.handler.CommonFieldHandler;
import com.klwork.explorer.ui.handler.TableFieldCache;
import com.klwork.explorer.ui.handler.TableHandler;
import com.klwork.explorer.ui.mainlayout.ExplorerLayout;
import com.klwork.explorer.ui.util.ThemeImageColumnGenerator;
import com.vaadin.data.Container;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class GatherDetermineVictorForm extends GatherPublishNeedForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected  ProjectParticipantService projectParticipantService;
	protected  RuntimeService runtimeService;
	
	private TableFieldCache fieldCache = new TableFieldCache();
	
	//获胜者
	VerticalLayout winTableLayout;
	
	//候选者
	VerticalLayout candidateTableLayout;
	
	// 奖金分配
	Label distributeLable;
	Label noDistributeLable;
	
	protected UploadWorkTaskContentComponent uploadContent;
	
	private FieldGroup gradefieldGroup = new FieldGroup();
	private ProjectParticipant projectScoreParticipant;
	
	public GatherDetermineVictorForm(Task task) {
		super(task);
		this.projectParticipantService = ViewToolManager
				.getBean("projectParticipantService");
		this.runtimeService = ProcessEngines.getDefaultProcessEngine()
				.getRuntimeService();
	}


	@Override
	public void notifyRelatedContentChanged() {

	}

	@Override
	protected void initPromptTitle() {
		setFormHelp(i18nManager.getMessage(Messages.TASK_FORM_GRADE_HELP));
	}

	@Override
	protected void initUpWorkContent() {
	}

	@Override
	protected void initRelatedContent() {

	}

	@Override
	protected void initFormPropertiesComponent() {
		// 绑定数据和初始grid布局
		initFormLayoutAndData();
		// 项目名称
		initProjectName(true);

		// 截止时间
		initDeadline(true);

		// 悬赏金额
		initBounty(true);

		// 需求类型
		initNeedType(true);

		// 需求描叙
		initDescription(true);
		
		//获胜者table
		initWin();
		
		//候选者table
		initCandidate();
	}

	
	/**
	 * 
	 */
	private void initCandidate() {
		addComponent(CommonFieldHandler.getSpacer());
		addTableTitle("候选者名单");
		candidateTableLayout = new VerticalLayout();
		candidateTableLayout.setSizeFull();
		candidateTableLayout.setSpacing(true);
		addComponent(candidateTableLayout);
		
		initCandidateTable(candidateTableLayout);
		
		
	}


	public void initCandidateTable(VerticalLayout tableLayout) {
		Table candidateTable = new Table();
		candidateTable.addStyleName(ExplorerLayout.STYLE_TASK_LIST);
		candidateTable.addStyleName(ExplorerLayout.STYLE_SCROLLABLE);
		candidateTable.setWidth(100, Unit.PERCENTAGE);
		candidateTable.setHeight(100, Unit.PERCENTAGE);
		
		//setExpandRatio(listTable, 1);
		
		initCandidateTableData(candidateTable);
	

		// Create column header
		candidateTable.addGeneratedColumn("icon", new ThemeImageColumnGenerator(
				Images.TASK_22));
		candidateTable.setColumnWidth("icon", 22);

		candidateTable.addContainerProperty("scoreUserId", String.class, "");
		candidateTable.addContainerProperty("score", String.class, "");
		//candidateTable.addContainerProperty("score", String.class, "");
		//candidateTable.setColumnExpandRatio("scoreUserId", 0.5f);
		candidateTable.addGeneratedColumn("edit", new CandidateTableColumnGenerator());
		/*listTable.setColumnExpandRatio("edit", 0.2f);*/
		 ArrayList<String> visibleColumnLabels = new ArrayList<String>();
		 visibleColumnLabels.add("");
		visibleColumnLabels.add("上传用户");
		visibleColumnLabels.add("得分");
		visibleColumnLabels.add("操作");
			
			candidateTable.setColumnHeaders(visibleColumnLabels.toArray(new String[0]));
		
		TableHandler.setTableHasHead(candidateTable);
		candidateTable.setImmediate(false);
		tableLayout.addComponent(candidateTable);
	}


	public void initCandidateTableData(Table candidateTable) {
		LazyLoadingQuery lazyLoadingQuery = new ParticipantListQuery(relateOutSourceingProject,false);
		LazyLoadingContainer listContainer = new LazyLoadingContainer(
				lazyLoadingQuery, 10);
		candidateTable.setContainerDataSource(listContainer);
		if (lazyLoadingQuery.size() < 10) {
			candidateTable.setPageLength(0);
		} else {
			candidateTable.setPageLength(10);
		}
	}
	
	public class WinnerTableColumnGenerator implements ColumnGenerator {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5950078454864053894L;

		@Override
		public Object generateCell(final Table source, final Object itemId, Object columnId) {
			final ProjectParticipant participant = BinderHandler.getTableBean(
					source, itemId);
			//删去win
			com.vaadin.ui.Embedded deleteButton = new com.vaadin.ui.Embedded(null, Images.DELETE);
			deleteButton.addStyleName(ExplorerLayout.STYLE_CLICKABLE);

			deleteButton.addClickListener(new com.vaadin.event.MouseEvents.ClickListener() {
				private static final long serialVersionUID = 3483384365910084806L;

				@Override
				public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
					ProjectParticipant newPart = new ProjectParticipant();
					newPart.setId(participant.getId());
					newPart.setIsWinner(false);
					//奖金为0
					newPart.setWinningAmount(0d);
					projectParticipantService.updateProjectParticipant(newPart);
					//source.removeItem(itemId);
					//重新加载
					refreshAllTable();
				}

			});
			return deleteButton;
		}

	}
	
	public class CandidateTableColumnGenerator implements ColumnGenerator {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5950078454864053894L;

		@Override
		public Object generateCell(final Table source, final Object itemId, Object columnId) {
			final ProjectParticipant participant = BinderHandler.getTableBean(
					source, itemId);
			Button addButton = new Button("加入到获胜者");
			addButton.addStyleName(Reindeer.BUTTON_SMALL);
			addButton.setDisableOnClick(true);

			addButton.addClickListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					ProjectParticipant newPart = new ProjectParticipant();
					newPart.setId(participant.getId());
					newPart.setIsWinner(true);
					projectParticipantService.updateProjectParticipant(newPart);
					//source.removeItem(itemId);
					//重新加载winTable
					refreshAllTable();
				}

			});
			return addButton;
		}

	}
	
	private void initWin() {
		addComponent(CommonFieldHandler.getSpacer());
		
		HorizontalLayout winHeader = new HorizontalLayout();
		winHeader.setSpacing(true);
		winHeader.setWidth(100, Unit.PERCENTAGE);
		winHeader.addStyleName(ExplorerLayout.STYLE_DETAIL_BLOCK);
		addComponent(winHeader);
		
		Label winLabel = currentLabel();
		winLabel.setValue("获胜者名单");
		winHeader.addComponent(winLabel);
		
		
		distributeLable = currentLabel();
		winHeader.addComponent(distributeLable);
		noDistributeLable = currentLabel();
		winHeader.addComponent(noDistributeLable);
		//重新计算分配的金额
		initDistributeData();
		
		winTableLayout = new VerticalLayout();
		winTableLayout.setSizeFull();
		winTableLayout.setSpacing(true);
		addComponent(winTableLayout);
		initWinTable(winTableLayout);
		
	}

	
	/**
	 * 计算奖金
	 */
	public void initDistributeData() {
		OutsourcingProject p = getRelateOutSourceingProject();
		double sumDistribute = projectParticipantService.queryDistributeBonusTotal(p.getId());
		double noDistribute = p.getBounty() - sumDistribute;
		noDistributeLable.setValue("未分配:" + noDistribute + "元");
		distributeLable.setValue("奖金分配:" + sumDistribute + "元");
	}


	public Label currentLabel() {
		Label winLabel = new Label();
		winLabel.addStyleName(ExplorerLayout.STYLE_H4);
		winLabel.setVisible(true);
		return winLabel;
	}


	public void initWinTable(VerticalLayout tableLayOut) {
		final Table  winTable = new Table();
		winTable.addStyleName(ExplorerLayout.STYLE_TASK_LIST);
		winTable.addStyleName(ExplorerLayout.STYLE_SCROLLABLE);
		winTable.setWidth(100, Unit.PERCENTAGE);
		winTable.setHeight(100, Unit.PERCENTAGE);
		addComponent(winTable);
		//setExpandRatio(listTable, 1);
		
		initWinTableData(winTable);
		
		
		
		// Create column header
		winTable.addGeneratedColumn("icon", new ThemeImageColumnGenerator(
				Images.TASK_22));
		winTable.setColumnWidth("icon", 22);
		
		winTable.addContainerProperty("scoreUserId", String.class, "");
		winTable.addContainerProperty("score", String.class, "");
		winTable.addContainerProperty("winningAmount", String.class, "");
		winTable.addGeneratedColumn("edit", new WinnerTableColumnGenerator());
		
		 ArrayList<String> visibleColumnLabels = new ArrayList<String>();
		 visibleColumnLabels.add("");
		visibleColumnLabels.add("上传用户");
		visibleColumnLabels.add("得分");
		visibleColumnLabels.add("奖金分配");
		visibleColumnLabels.add("操作");
			
		winTable.setColumnHeaders(visibleColumnLabels.toArray(new String[0]));
		winTable.setEditable(true);//只有设置上面的factory才生效
		winTable.setTableFieldFactory(new TableFieldFactory() {
			private static final long serialVersionUID = -5741977060384915110L;
			public Field createField(Container container, final Object itemId,
					final Object propertyId, Component uiContext) {
				TextField tf = null;
				
				if ("winningAmount".equals(propertyId)) {
					if (fieldCache.getPropertyFieldFromCache(itemId, propertyId) != null) {
						tf = fieldCache.getPropertyFieldFromCache(itemId, propertyId);
						tf.setReadOnly(false);
						return tf;
					}
					tf = new TextField((String) propertyId);
					tf.setImmediate(true);
					tf.setReadOnly(false);
					tf.setWidth("100%");
					tf.addBlurListener(new BlurListener() {
						private static final long serialVersionUID = -4497552765206819985L;
						public void blur(BlurEvent event) {
							//fieldCache.setFieldReadOnly(itemId, true);
							ProjectParticipant c = BinderHandler.getTableBean(winTable, itemId);
							ProjectParticipant newEntity = new ProjectParticipant();
							newEntity.setId(c.getId());
							newEntity.setWinningAmount(c.getWinningAmount());
							projectParticipantService.updateProjectParticipant(newEntity);
							//重新计算奖金额
							initDistributeData();
							//System.out.println("ggg:" + c.getWinningAmount());
						}
					});
					fieldCache.savePrppertyFieldToCache(itemId, propertyId, tf);
				}else {
					tf = new TextField((String) propertyId);
					tf.setData(itemId);
					tf.setImmediate(true);
					tf.setReadOnly(true);
				}
				return tf;
			}
		});
		
		TableHandler.setTableHasHead(winTable);
		//winTable.setImmediate(false);
		tableLayOut.addComponent(winTable);
	}


	public void initWinTableData(Table  winTable) {
		LazyLoadingQuery lazyLoadingQuery = new ParticipantListQuery(relateOutSourceingProject,true);
		LazyLoadingContainer listContainer = new LazyLoadingContainer(
				lazyLoadingQuery, 10);
		winTable.setContainerDataSource(listContainer);
		if (lazyLoadingQuery.size() < 10) {
			winTable.setPageLength(0);
		} else {
			winTable.setPageLength(10);
		}
	}


	public Label addTableTitle(String vale) {
		Label formTitle = new Label();
		formTitle.setValue(vale);
		formTitle.addStyleName(ExplorerLayout.STYLE_H4);
		formTitle.setVisible(true);
		addComponent(formTitle);
		return formTitle;
	}


	@Override
	protected void initActions() {
		submitFormButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6091586145870618870L;

			public void buttonClick(ClickEvent event) {
				Map<String, Object> formProperties = new HashMap<String, Object>();
				try {
					BinderHandler.commit(gradefieldGroup);
					ProjectParticipant p = BinderHandler
							.getFieldGroupBean(gradefieldGroup);
					
					formProperties = projectManagerService.submitGradeWork(p,getTask());
					/*formProperties.put("outsourcingProjectId",
							outsourcingProject.getId());
					// 把任务id传给下一个流程
					formProperties.put("taskId", getTask().getId());*/
					

					// 通知外边的调用任务,任务相关信息已经保存
					fireEvent(new SubmitEvent(GatherDetermineVictorForm.this,
							SubmitEvent.SUBMITTED, formProperties));
					submitFormButton.setComponentError(null);
				} catch (InvalidValueException ive) {
					// Error is presented to user by the form component
					ive.printStackTrace();
				}
			}
		});

		cancelFormButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -8980500491522472381L;

			public void buttonClick(ClickEvent event) {
				fireEvent(new FormPropertiesEvent(GatherDetermineVictorForm.this,
						FormPropertiesEvent.TYPE_CANCEL));
				submitFormButton.setComponentError(null);
			}
		});
	}


	public void refreshAllTable() {
		fieldCache = new TableFieldCache();
		winTableLayout.removeAllComponents();
		initWinTable(winTableLayout);
		
		candidateTableLayout.removeAllComponents();
		initCandidateTable(candidateTableLayout);
		
		//重新计算奖金
		initDistributeData();
	}
	
}
