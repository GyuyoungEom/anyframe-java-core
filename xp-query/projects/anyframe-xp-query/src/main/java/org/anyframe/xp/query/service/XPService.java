/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package org.anyframe.xp.query.service;

import com.tobesoft.xplatform.data.DataSetList;
import com.tobesoft.xplatform.data.VariableList;

/**
 * Interface class to send queries. 
 * 
 * @author Youngmin Jo
 */
public interface XPService {

	/**
	 * This is the method for inquiring using VariableList and DataSet.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void get(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is a method for querying at developing the screen using RIA
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void getList(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is a method for querying using the DataSet for paging.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void getPagingList(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is a method for querying using the DataSet for insert.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void create(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is the method for updating using VariableList and DataSet.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void update(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is the method for deleting using VariableList and DataSet.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void remove(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is the method for inserting, updating and deleting using VariableList and DataSet.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void saveAll(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

	/**
	 * This is the method for executing function, procedure, or package using VariableList and DataSet.
	 * @param inVl VariableList including the query id or query condition etc.
	 * @param inDl The DataSet list including query conditions
	 * @param outVl Output VaiableList including return values.
	 * @param outDl Output DataSetList including return values.
	 * @throws Exception if there is any problem executing the query
	 */
	void execute(VariableList inVl, DataSetList inDl, VariableList outVl, DataSetList outDl) throws Exception;

}
