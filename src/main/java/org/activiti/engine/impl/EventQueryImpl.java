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

package org.activiti.engine.impl;

import java.util.List;

import org.activiti.engine.identity.EventQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.task.Event;

/**
 * @author Joram Barrez
 */
public class EventQueryImpl extends AbstractQuery<EventQuery, Event> implements
		EventQuery {

	private static final long serialVersionUID = 1L;

	public EventQueryImpl() {
	}

	public EventQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public EventQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	public long executeCount(CommandContext commandContext) {
		checkQueryOk();
		return commandContext.getCommentEntityManager()
				.findEventCountByQueryCriteria(this);
	}

	public List<Event> executeList(CommandContext commandContext, Page page) {
		checkQueryOk();
		return commandContext.getCommentEntityManager()
				.findEventByQueryCriteria(this, page);
	}

}
