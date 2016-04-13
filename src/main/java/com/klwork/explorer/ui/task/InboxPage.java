/* Licensed under the Apache License, Version 2.0 (the "License");
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
package com.klwork.explorer.ui.task;


import com.klwork.explorer.data.LazyLoadingQuery;
import com.klwork.explorer.navigation.UriFragment;
import com.klwork.explorer.ui.custom.TaskListHeader;
import com.klwork.explorer.ui.task.data.InboxListQuery;
import com.vaadin.ui.Component;




/**
 * The page displaying all tasks currently in ones inbox.
 * 
 * @author Joram Barrez
 */
public class InboxPage extends AbstractTaskPage {
  
  private static final long serialVersionUID = 1L;
  
  public InboxPage() {
	  logger.info("inboxpage init .....");
  }
  
  /**
   * Constructor called when page is accessed straight through the url, eg. /task/id=123
   */
  public InboxPage(String taskId) {
    super(taskId);
  }
  
  @Override
  protected LazyLoadingQuery createLazyLoadingQuery() {
    return new InboxListQuery();
  }
  
  @Override
  protected UriFragment getUriFragment(String taskId) {
   /* UriFragment taskFragment = new UriFragment(TaskNavigator.TASK_URI_PART);

    if(taskId != null) {
      taskFragment.addUriPart(taskId);
    }

    taskFragment.addParameter(TaskNavigator.PARAMETER_CATEGORY, TaskNavigator.CATEGORY_INBOX);
    return taskFragment;*/
	  return null;
  }
  
}
