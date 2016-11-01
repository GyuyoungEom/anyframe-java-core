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
package org.anyframe.xp.query.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.anyframe.xp.query.XPQueryService;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataTypes;
import com.tobesoft.xplatform.data.Variable;
import com.tobesoft.xplatform.data.VariableList;

public class XPQueryServiceDynamicTest extends
		AbstractDependencyInjectionSpringContextTests {
	
	private DataSource dataSource;
	private XPQueryService xpQueryService;

	public void setXpQueryService(XPQueryService xpQueryService) {
		this.xpQueryService = xpQueryService;
	}

	/**
	 * Spring Configuration 파일을 읽는다.
	 */
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/spring/context-*.xml" };
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	/**
	 * 테스트를 위한 기본 테이블 생성 및 기본 데이터 입력
	 */
	public void onSetUp() throws Exception {
		super.onSetUp();
		try {
			Connection conn = dataSource.getConnection();
			try {
				Statement statement = conn.createStatement();

				try {
					statement.executeUpdate("DROP TABLE TB_XP_USER");
				} catch (SQLException e) {
					System.out.println("Fail to DROP Table.");
				}

				statement.executeUpdate("CREATE TABLE TB_XP_USER ( "
						+ "LOGON_ID  VARCHAR(20), "
						+ "PASSWORD VARCHAR(20),"
						+ "NAME VARCHAR(20)," 
						+ "PRIMARY KEY (LOGON_ID))");
				
				statement.executeUpdate("INSERT INTO TB_XP_USER VALUES ('admin', 'admin', 'ADMIN')");
				statement.executeUpdate("INSERT INTO TB_XP_USER VALUES ('test', 'test123', 'TESTER')");
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println("Unable to initialize database for test." + e);
			fail("Unable to initialize database for test. " + e);
		}
	}

	public void testDynamicWithForEachVariableList() throws Exception {
		List logonIdList = new ArrayList();
		logonIdList.add("admin");
		logonIdList.add("test");
		
		VariableList variableList = new VariableList();
		Variable variable = new Variable("logonIdList");
		variable.set(logonIdList);
		
		variableList.add(variable);
		
		DataSet resultDs = xpQueryService.search("dynamicWithForEachVariableList",variableList );
		
		assertEquals( 2, resultDs.getRowCount() );
		assertEquals( "ADMIN", resultDs.getObject( 0 , "name") );
		assertEquals( "TESTER", resultDs.getObject( 1 , "name") );
	}

	public void testDynamicQueryUsingTextReplaceVariableList() throws Exception {
        // 1. set data for test
    	VariableList variableList = new VariableList();
		
    	Variable variable1 = new Variable("schema");
    	variable1.set("TB_XP_USER");
		
    	Variable variable2 = new Variable("sortColumn");
    	variable2.set("NAME");
		
		variableList.add(variable1);
		variableList.add(variable2);
		
        // 2. execute query
        DataSet resultDs =  xpQueryService.search("dynamicQueryUsingTextReplaceVariableList", variableList);
        assertEquals( 2, resultDs.getRowCount() );
		assertEquals( "admin", resultDs.getObject( 0 , "logonId") );
		assertEquals( "test", resultDs.getObject( 1 , "logonId") );
    }
    
    public void testDynamicQueryUsingIfVariableList() throws Exception {
        
    	//SEARCH_CONDITION이 NULL일 때 
    	// 1. set data for test
    	VariableList variableList = new VariableList();
		
    	Variable variable1 = new Variable("SEARCH_CONDITION");
    	variable1.set("");
		
		variableList.add(variable1);
		
        // 2. execute query
        DataSet resultDs =  xpQueryService.search("dynamicQueryUsingIfVariableList", variableList);
        assertEquals( 2 , resultDs.getRowCount() );
        
        //LOGON_ID가 admin인 USER 조회
        variableList.clear();
        
		variableList.add("SEARCH_CONDITION", "LOGON_ID");
		variableList.add("SEARCH_KEYWORD", "admin");
        
		resultDs =  xpQueryService.search("dynamicQueryUsingIfVariableList", variableList);
		
		assertEquals( 1 , resultDs.getRowCount() );
		assertEquals( "admin" , resultDs.getString( 0 , "logonId") );
		
		//NAME가 test123인  USER 조회
        variableList.clear();
        
		variableList.add("SEARCH_CONDITION", "NAME");
		variableList.add("SEARCH_KEYWORD", "TESTER");
        
		resultDs =  xpQueryService.search("dynamicQueryUsingIfVariableList", variableList);
		
		assertEquals( 1 , resultDs.getRowCount() );
		assertEquals( "TESTER" , resultDs.getString( 0 , "name") );
    }
    
    public void testDynamicQueryUsingIfDataSet() throws Exception {
        
    	//SEARCH_CONDITION이 NULL일 때 
    	// 1. set data for test
		DataSet dsSearch = new DataSet();
		dsSearch.addColumn("SEARCH_CONDITION", DataTypes.STRING);
		dsSearch.addColumn("SEARCH_KEYWORD", DataTypes.STRING);
		
		dsSearch.newRow();
		dsSearch.set( 0, "SEARCH_CONDITION", "" );
		
        // 2. execute query
        DataSet resultDs =  xpQueryService.search("dynamicQueryUsingIfDataSet", dsSearch);
        assertEquals( 2 , resultDs.getRowCount() );
        
        //LOGON_ID가 admin인 USER 조회
		dsSearch.set( 0, "SEARCH_CONDITION", "LOGON_ID" );
		dsSearch.set( 0, "SEARCH_KEYWORD", "admin" );
		
		resultDs =  xpQueryService.search("dynamicQueryUsingIfDataSet", dsSearch);
		
		assertEquals( 1 , resultDs.getRowCount() );
		assertEquals( "admin" , resultDs.getString( 0 , "logonId") );
		
		//NAME가 test123인  USER 조회
		dsSearch.set( 0, "SEARCH_CONDITION", "NAME" );
		dsSearch.set( 0, "SEARCH_KEYWORD", "TESTER" );
		
		resultDs =  xpQueryService.search("dynamicQueryUsingIfDataSet", dsSearch);
		
		assertEquals( 1 , resultDs.getRowCount() );
		assertEquals( "TESTER" , resultDs.getString( 0 , "name") );
    }
}
